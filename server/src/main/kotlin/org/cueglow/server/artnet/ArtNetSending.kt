package org.cueglow.server.artnet

import de.deltaeight.libartnet.builders.ArtDmxBuilder
import de.deltaeight.libartnet.network.ArtNetSender
import org.apache.logging.log4j.LogManager
import org.cueglow.server.StateProvider
import org.cueglow.server.gdtf.renderGdtfStateToDmx
import org.cueglow.server.objects.ArtNetAddress
import java.net.InetAddress
import java.time.Duration
import kotlin.concurrent.withLock

val logger = LogManager.getLogger()!!

class ArtNetSending(val state: StateProvider) : Runnable {
    val suppressionTime = Math.round(.8 * 1e9) // in ns
    var frameRate = 44
    val frameTime = Duration.ofSeconds(1).dividedBy(frameRate.toLong())

    override fun run() {
        val rateLimiter = RateLimiter(frameTime)

        var lastSent = Long.MIN_VALUE
        Thread.currentThread().priority = 10

        var prevData = mapOf<ArtNetAddress, ByteArray>()
        val builders = mutableMapOf<ArtNetAddress, ArtDmxBuilder>()

        val sender = ArtNetSender()
        sender.start()

        while (true) {
            try {
                // get copies of state to render
                val (rigState, fixtures, fixtureTypes) = state.lock.withLock {
                    Triple(state.rigStateContainer.rigState, state.patch.getFixtures(), state.patch.getFixtureTypes())
                }
                // render state
                fixtures.values
                    .groupBy { it.universe }
                    .forEach universe@{ universeSet ->
                        val universe = universeSet.key ?: return@universe
                        val fixturesInUniverse = universeSet.value
                        val buffer = ByteArray(512) { 0 }
                        fixturesInUniverse.forEach fixture@{ fixture ->
                            // skip unpatched
                            val dmxAddress = fixture.address?.value ?: return@fixture

                            val fixtureState = rigState[fixture.uuid]!!
                            val dmxMode = fixtureTypes[fixture.fixtureTypeId]
                                ?.modes
                                ?.find { it.name == fixture.dmxMode }!!
                            val values = renderGdtfStateToDmx(fixtureState.chValues, dmxMode)
                            val allowedChannels = 512 - dmxAddress + 1
                            val valuesTruncated = if (dmxAddress + dmxMode.channelCount - 1 > 512) {
                                values.slice(0 until allowedChannels).toByteArray()
                            } else {
                                values
                            }
                            valuesTruncated.copyInto(buffer, dmxAddress - 1)
                        }
                        val universeBuilder = builders[universe] ?: run {
                            val newBuilder = ArtDmxBuilder()
                                .withNetAddress(universe.getNet())
                                .withSubnetAddress(universe.getSubNet())
                                .withUniverseAddress(universe.getUniverse())
                            builders[universe] = newBuilder
                            newBuilder!!
                        }
                        universeBuilder.data = buffer
                    }

                // check if we should suppress
                // NOTE this could be done a per-universe basis
                if (builders.all { prevData[it.key]?.contentEquals(it.value.data) == true }) {
                    // logger.info("checking for suppression")
                    if (System.nanoTime() - lastSent < suppressionTime) {
                        rateLimiter.limitRate()
                        continue
                    }
                }

                prevData = builders.mapValues { it.value.data }

                rateLimiter.limitRate()

                builders.forEach { builderPair ->
                    val builder = builderPair.value
                    val dataToSend = builder.build()
                    sender.send(InetAddress.getByName("255.255.255.255"), dataToSend)
                }

                lastSent = System.nanoTime()
            } catch (e: Exception) {
                logger.error("Error in ArtNetSending Loop", e)
                Thread.sleep(2_000)
            }
        }
    }
}
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
        logger.info("ArtNetSender starting up")

        val rateLimiter = RateLimiter(frameTime)

        var lastSent = Long.MIN_VALUE
        Thread.currentThread().priority = 10

        var prevData = mapOf<ArtNetAddress, ByteArray>()
        val builders = mutableMapOf<ArtNetAddress, ArtDmxBuilder>()

        val sender = ArtNetSender()
        sender.start()

        while (true) {
            logger.info("rendering")
            // render data
            state.lock.withLock {
                val glowPatch = state.patch.getGlowPatch()
                glowPatch.fixtures
                    .groupBy { it.universe }
                    .forEach universe@{ universeSet ->
                        val universe = universeSet.key ?: return@universe
                        val fixtures = universeSet.value
                        val buffer = ByteArray(512) { 0 }
                        fixtures.forEach fixture@{ fixture ->
                            // skip unpatched
                            val dmxAddress = fixture.address?.value ?: return@fixture

                            val fixtureInd = glowPatch.fixtures.indexOf(fixture)
                            val fixtureState = state.rigState[fixtureInd]
                            val dmxMode = glowPatch.fixtureTypes
                                .find { it.fixtureTypeId == fixture.fixtureTypeId }
                                ?.modes
                                ?.find { it.name == fixture.dmxMode }!!
                            val values = renderGdtfStateToDmx(fixtureState.chValues, dmxMode)

                            values.copyInto(buffer, dmxAddress - 1)
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
            }

            logger.info("prevData is ${prevData.map { "${it.key}: ${it.value.slice(0..1).joinToString("\n")}" }}")
            logger.info("builder data is ${builders.map { "${it.key}: ${it.value.data.slice(0..1).joinToString("\n")}" }}")

            // check if we should suppress
            // NOTE this could be done a per-universe basis
            if (builders.all { prevData[it.key]?.contentEquals(it.value.data) == true }) {
                logger.info("checking for suppression")
                if (System.nanoTime() - lastSent < suppressionTime) {
                    rateLimiter.limitRate()
                    continue
                }
            }

            prevData = builders.mapValues { it.value.data }

            logger.info("setting prevData to ${prevData.map { "${it.key}: ${it.value.slice(0..1).joinToString("\n")}" }}")

            rateLimiter.limitRate()

            builders.forEach { builderPair ->
                logger.info("sending for universe ${builderPair.key.value}")
                val builder = builderPair.value
                val dataToSend = builder.build()
                sender.send(InetAddress.getByName("192.168.1.255"), dataToSend)
            }

            // TODO, somehow when suppressing we often send two packets in quick succession, followed by a suppression pause and another quick packet, and so on
            // TODO diagnose this

            lastSent = System.nanoTime()
        }
    }
}
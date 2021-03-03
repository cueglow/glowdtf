package org.cueglow.server.handlers

import com.github.michaelbull.result.*
import org.cueglow.server.StateProvider
import org.cueglow.server.api.*
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.gdtf.parseGdtf
import org.cueglow.server.objects.GlowError
import org.cueglow.server.objects.UnknownDmxModeError
import org.cueglow.server.objects.UnknownFixtureTypeIdError
import org.cueglow.server.patch.PatchFixture
import java.io.InputStream
import java.util.*

/**
 * Network-Agnostic Handler for Incoming Events
 */
class InEventHandler(private val state: StateProvider) {
    fun handleInRequest(glowRequest: GlowRequest) {
        // Delegate to the correct function / subroutine depending on the event
        when (glowRequest.glowMessage.event) {
            GlowEvent.SUBSCRIBE -> TODO()
            GlowEvent.UNSUBSCRIBE -> TODO()
            GlowEvent.STREAM_INITIAL_STATE -> TODO()
            GlowEvent.STREAM_UPDATE -> TODO()
            GlowEvent.REQUEST_STREAM_DATA -> TODO()
            GlowEvent.ERROR -> TODO()
            GlowEvent.ADD_FIXTURES -> handleAddFixtures(glowRequest)
            GlowEvent.FIXTURES_ADDED -> TODO()
            GlowEvent.UPDATE_FIXTURE -> {
                val data = (glowRequest.glowMessage.data as GlowDataUpdateFixture)
                val fixture = state.patch.getFixtures()[data.uuid] ?: TODO("Fixture UUID not found in Patch -> Implement GlowError")
                data.fid?.let {fixture.fid = it}
                data.name?.let {fixture.name = it}
                if (data.universe is Ok) {fixture.universe = data.universe.unwrap()}
                if (data.address is Ok) {fixture.address = data.address.unwrap()}
            }
            GlowEvent.DELETE_FIXTURES -> TODO()
            GlowEvent.FIXTURE_TYPE_ADDED -> TODO()
            GlowEvent.DELETE_FIXTURE_TYPES ->
                (glowRequest.glowMessage.data as GlowDataDeleteFixtureTypes)
                    .fixtureTypeIds.forEach{state.patch.removeFixtureType(it)}
        }
    }

    private fun handleAddFixtures(glowRequest: GlowRequest) {
        val data = (glowRequest.glowMessage.data as GlowDataAddFixtures)
        val uuidList: MutableList<UUID> = emptyList<UUID>().toMutableList()
        data.fixtures.forEach { fixture ->
            // validate fixtureTypeId is in Patch
            val fixtureType: GdtfWrapper = state.patch.getFixtureTypes()[fixture.fixtureTypeId] ?: run {
                glowRequest.returnError(UnknownFixtureTypeIdError(fixture.fixtureTypeId))
                return
            }
            // validate dmxMode exists in fixtureType
            fixtureType.modes.find { it.name == fixture.dmxMode } ?: run {
                glowRequest.returnError(UnknownDmxModeError(fixture.dmxMode))
                return
            }
            val patchFixture =
                PatchFixture(fixture.fid, fixture.name, fixtureType, fixture.dmxMode, fixture.universe, fixture.address)

            state.patch.putFixture(patchFixture)
            uuidList.add(patchFixture.uuid)
        }
        glowRequest.answerRequest(
            GlowMessage(
                GlowEvent.FIXTURES_ADDED,
                GlowDataFixturesAdded(uuidList),
                glowRequest.glowMessage.messageId
            )
        )
    }

    /**
     * Handler for new GDTF
     *
     * Parses it and adds it to the Patch
     */
    fun handleNewGdtf(inputStream: InputStream): Result<UUID, GlowError> {
        val parseResult = parseGdtf(inputStream)

        val parsedGdtf = parseResult.getOrElse { return Err(it) }
        val wrapper = GdtfWrapper(parsedGdtf)

        state.patch.putFixtureType(wrapper)

        return Ok(wrapper.fixtureTypeId)
    }
}




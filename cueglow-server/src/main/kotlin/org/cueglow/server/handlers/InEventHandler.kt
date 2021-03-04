package org.cueglow.server.handlers

import com.github.michaelbull.result.*
import org.cueglow.server.StateProvider
import org.cueglow.server.api.*
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.gdtf.parseGdtf
import org.cueglow.server.objects.GlowError
import org.cueglow.server.objects.UnknownDmxModeError
import org.cueglow.server.objects.UnknownFixtureTypeIdError
import org.cueglow.server.objects.UnknownFixtureUuidError
import org.cueglow.server.patch.PatchFixture
import java.io.InputStream
import java.util.*

/**
 * Network-Agnostic Handler for Incoming Events
 *
 * It is passed to different network handlers to provide access to the state. InEventHandler gets access to the state
 * through its constructor.
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
            GlowEvent.UPDATE_FIXTURE -> handleUpdateFixture(glowRequest)
            GlowEvent.DELETE_FIXTURES -> handleDeleteFixtures(glowRequest)
            GlowEvent.FIXTURE_TYPE_ADDED -> TODO()
            GlowEvent.DELETE_FIXTURE_TYPES -> handleDeleteFixtureTypes(glowRequest)
        }
    }

    private fun handleDeleteFixtureTypes(glowRequest: GlowRequest) {
        (glowRequest.glowMessage.data as GlowDataDeleteFixtureTypes)
            .fixtureTypeIds.forEach single@{
                state.patch.getFixtureTypes()[it] ?: run {glowRequest.answer(UnknownFixtureTypeIdError(it)); return@single}
                state.patch.removeFixtureType(it)
            }
    }

    private fun handleDeleteFixtures(glowRequest: GlowRequest) {
        (glowRequest.glowMessage.data as GlowDataDeleteFixtures)
            .uuids.forEach single@{
                state.patch.getFixtures()[it]?.uuid ?: run{glowRequest.answer(UnknownFixtureUuidError(it)); return@single}
                state.patch.removeFixture(it)
            }
    }

    private fun handleUpdateFixture(glowRequest: GlowRequest) {
        val data = (glowRequest.glowMessage.data as GlowDataUpdateFixture)
        val fixture = state.patch.getFixtures()[data.uuid] ?: run {
            glowRequest.answer(UnknownFixtureUuidError(data.uuid))
            return
        }
        data.fid?.let {fixture.fid = it}
        data.name?.let {fixture.name = it}
        if (data.universe is Ok) {fixture.universe = data.universe.unwrap()}
        if (data.address is Ok) {fixture.address = data.address.unwrap()}
    }

    private fun handleAddFixtures(glowRequest: GlowRequest) {
        val data = (glowRequest.glowMessage.data as GlowDataAddFixtures)
        val uuidList: MutableList<UUID> = emptyList<UUID>().toMutableList()
        data.fixtures.forEach { fixture ->
            // validate fixtureTypeId is in Patch
            val fixtureType: GdtfWrapper = state.patch.getFixtureTypes()[fixture.fixtureTypeId] ?: run {
                glowRequest.answer(UnknownFixtureTypeIdError(fixture.fixtureTypeId))
                return
            }
            // validate dmxMode exists in fixtureType
            fixtureType.modes.find { it.name == fixture.dmxMode } ?: run {
                glowRequest.answer(UnknownDmxModeError(fixture.dmxMode))
                return
            }
            val patchFixture =
                PatchFixture(fixture.fid, fixture.name, fixtureType, fixture.dmxMode, fixture.universe, fixture.address)

            state.patch.putFixture(patchFixture)
            uuidList.add(patchFixture.uuid)
        }
        glowRequest.answer(
            GlowMessage(
                GlowEvent.FIXTURES_ADDED,
                GlowDataFixturesAdded(uuidList),
                glowRequest.glowMessage.messageId
            )
        )
    }

    /**
     * Parses new GDTF and adds it to the Patch.
     *
     * In comparison to other events, it does not get wrapped in a [GlowRequest]. Instead, it is called directly
     * from the corresponding handler.
     */
    fun handleNewGdtf(inputStream: InputStream): Result<UUID, GlowError> {
        val parseResult = parseGdtf(inputStream)

        val parsedGdtf = parseResult.getOrElse { return Err(it) }
        val wrapper = GdtfWrapper(parsedGdtf)

        state.patch.putFixtureType(wrapper)

        return Ok(wrapper.fixtureTypeId)
    }
}




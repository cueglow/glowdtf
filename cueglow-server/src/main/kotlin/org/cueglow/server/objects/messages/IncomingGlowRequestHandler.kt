package org.cueglow.server.objects.messages

import com.github.michaelbull.result.getOrElse
import org.cueglow.server.StateProvider
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.patch.PatchFixtureUpdate
import java.util.*

abstract class IncomingGlowRequestHandler(private val state: StateProvider) {
    fun handle(request: GlowRequest) {
        when (request.originalMessage.event) {
            // TODO remove events that shouldn't come from outside and handle them with Error in else clause
            GlowEvent.SUBSCRIBE -> TODO()
            GlowEvent.UNSUBSCRIBE -> TODO()
            GlowEvent.STREAM_INITIAL_STATE -> TODO()
            GlowEvent.STREAM_UPDATE -> TODO()
            GlowEvent.REQUEST_STREAM_DATA -> TODO()
            GlowEvent.ERROR -> TODO()
            GlowEvent.ADD_FIXTURES -> handleAddFixtures(request)
            GlowEvent.FIXTURES_ADDED -> TODO()
            GlowEvent.UPDATE_FIXTURE -> handleUpdateFixture(request)
            GlowEvent.DELETE_FIXTURES -> handleDeleteFixtures(request)
            GlowEvent.FIXTURE_TYPE_ADDED -> TODO()
            GlowEvent.DELETE_FIXTURE_TYPES -> handleDeleteFixtureTypes(request)
        }
    }

    private fun handleAddFixtures(request: GlowRequest) {
        val data = (request.originalMessage.data as GlowData.AddFixtures)
        val fixtures: List<PatchFixture> = data.fixtures.map {
            PatchFixture(
                uuid = UUID.randomUUID(),
                fid = it.fid,
                name = it.name,
                fixtureTypeId = it.fixtureTypeId,
                dmxMode = it.dmxMode,
                universe = it.universe,
                address = it.address,
            )
        }

        state.patch.addFixtures(fixtures).getOrElse { errorList ->
            errorList.forEach {
                request.answer(it)
                return
            } }

        // note: will not answer if only some fixtures were added
        // reason: future API will remove this response
        request.answer(GlowData.FixturesAdded(fixtures.map{it.uuid}))
    }

    private fun handleDeleteFixtureTypes(glowRequest: GlowRequest) {
        val idsToDelete = (glowRequest.originalMessage.data as GlowData.DeleteFixtureTypes).fixtureTypeIds
        state.patch.removeFixtureTypes(idsToDelete).getOrElse { errorList ->
            errorList.forEach { glowRequest.answer(it) }
        }
    }

    private fun handleDeleteFixtures(glowRequest: GlowRequest) {
        val uuidsToDelete = (glowRequest.originalMessage.data as GlowData.DeleteFixtures).uuids
        state.patch.removeFixtures(uuidsToDelete).getOrElse { errorList ->
            errorList.forEach {glowRequest.answer(it)}
        }
    }

    private fun handleUpdateFixture(glowRequest: GlowRequest) {
        val data = (glowRequest.originalMessage.data as GlowData.UpdateFixture)

        val update = PatchFixtureUpdate(
            uuid = data.uuid,
            fid = data.fid,
            name = data.name,
            universe = data.universe,
            address = data.address,
        )

        state.patch.updateFixtures(listOf(update)).getOrElse { glowRequest.answer(it[0]) }
    }
}
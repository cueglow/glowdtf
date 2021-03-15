package org.cueglow.server.json

import com.github.michaelbull.result.getOrElse
import org.cueglow.server.StateProvider
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.patch.PatchFixtureUpdate
import org.cueglow.server.websocket.AsyncClient
import java.util.*

/** Represents a Receiver that takes a message string and can answer asynchronously with the provided GlowClient */
interface AsyncStringReceiver {
    fun receive(message: String, client: AsyncClient)
}

/** Receives JSON messages, parses them, dispatches based on event and
 * executes the requested event on the state, which is passed in during construction.
 */
class JsonHandler(private val state: StateProvider): AsyncStringReceiver {
    override fun receive(message: String, client: AsyncClient) {
        val jsonMessage = parseJsonMessage(message)
        val request = JsonRequest(jsonMessage, client)
        when (jsonMessage.event) {
            // TODO remove events that shouldn't come from outside and handle them with Error in else clause
            JsonEvent.SUBSCRIBE -> TODO()
            JsonEvent.UNSUBSCRIBE -> TODO()
            JsonEvent.STREAM_INITIAL_STATE -> TODO()
            JsonEvent.STREAM_UPDATE -> TODO()
            JsonEvent.REQUEST_STREAM_DATA -> TODO()
            JsonEvent.ERROR -> TODO()
            JsonEvent.ADD_FIXTURES -> handleAddFixtures(request)
            JsonEvent.FIXTURES_ADDED -> TODO()
            JsonEvent.UPDATE_FIXTURE -> handleUpdateFixture(request)
            JsonEvent.DELETE_FIXTURES -> handleDeleteFixtures(request)
            JsonEvent.FIXTURE_TYPE_ADDED -> TODO()
            JsonEvent.DELETE_FIXTURE_TYPES -> handleDeleteFixtureTypes(request)
        }
    }

    private fun handleAddFixtures(request: JsonRequest) {
        val data = (request.originalMessage.data as JsonDataAddFixtures)
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
        request.answer(JsonEvent.FIXTURES_ADDED, JsonDataFixturesAdded(fixtures.map{it.uuid}))
    }

    private fun handleDeleteFixtureTypes(jsonRequest: JsonRequest) {
        val idsToDelete = (jsonRequest.originalMessage.data as JsonDataDeleteFixtureTypes).fixtureTypeIds
        state.patch.removeFixtureTypes(idsToDelete).getOrElse { errorList ->
            errorList.forEach { jsonRequest.answer(it) }
        }
    }

    private fun handleDeleteFixtures(jsonRequest: JsonRequest) {
        val uuidsToDelete = (jsonRequest.originalMessage.data as JsonDataDeleteFixtures).uuids
        state.patch.removeFixtures(uuidsToDelete).getOrElse { errorList ->
            errorList.forEach {jsonRequest.answer(it)}
        }
    }

    private fun handleUpdateFixture(jsonRequest: JsonRequest) {
        val data = (jsonRequest.originalMessage.data as JsonDataUpdateFixture)

        val update = PatchFixtureUpdate(
            uuid = data.uuid,
            fid = data.fid,
            name = data.name,
            universe = data.universe,
            address = data.address,
        )

        state.patch.updateFixtures(listOf(update)).getOrElse { jsonRequest.answer(it[0]) }
    }
}
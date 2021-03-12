package org.cueglow.server.json

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.getOr
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.unwrap
import org.cueglow.server.StateProvider
import org.cueglow.server.objects.UnknownFixtureUuidError
import org.cueglow.server.objects.UnpatchedFixtureTypeIdError
import org.cueglow.server.patch.PatchFixture
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
        val uuidList: MutableList<UUID> = emptyList<UUID>().toMutableList()
        data.fixtures.forEach singleFixture@{ fixture ->
            val fixtureTypeId = fixture.fixtureTypeId
            val patchFixture = PatchFixture(
                    UUID.randomUUID(), fixture.fid, fixture.name, fixture.fixtureTypeId, fixture.dmxMode, fixture.universe, fixture.address
                )

            state.patch.putFixture(patchFixture).getOrElse { request.answer(it); return@singleFixture }
            uuidList.add(patchFixture.uuid)
        }
        if (uuidList.isNotEmpty()) { request.answer(JsonEvent.FIXTURES_ADDED, JsonDataFixturesAdded(uuidList)) }
    }

    private fun handleDeleteFixtureTypes(jsonRequest: JsonRequest) {
        (jsonRequest.originalMessage.data as JsonDataDeleteFixtureTypes)
            .fixtureTypeIds.forEach singleFixture@{
                state.patch.getFixtureTypes()[it] ?: run {jsonRequest.answer(UnpatchedFixtureTypeIdError(it)); return@singleFixture}
                state.patch.removeFixtureType(it)
            }
    }

    private fun handleDeleteFixtures(jsonRequest: JsonRequest) {
        (jsonRequest.originalMessage.data as JsonDataDeleteFixtures)
            .uuids.forEach singleFixture@{
                state.patch.getFixtures()[it]?.uuid ?: run{jsonRequest.answer(UnknownFixtureUuidError(it)); return@singleFixture}
                state.patch.removeFixture(it)
            }
    }

    private fun handleUpdateFixture(jsonRequest: JsonRequest) {
        val data = (jsonRequest.originalMessage.data as JsonDataUpdateFixture)
        val fixture = state.patch.getFixtures()[data.uuid] ?: run {
            jsonRequest.answer(UnknownFixtureUuidError(data.uuid))
            return
        }
        state.patch.putFixture(fixture.copy(
            fid = data.fid ?: fixture.fid,
            name = data.name ?: fixture.name,
            universe = data.universe.getOr(fixture.universe),
            address = data.address.getOr(fixture.address),
        ))
    }
}
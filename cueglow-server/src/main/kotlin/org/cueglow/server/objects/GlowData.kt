package org.cueglow.server.objects

import com.beust.klaxon.TypeAdapter
import org.cueglow.server.patch.PatchFixture
import java.util.*
import kotlin.reflect.KClass

sealed class GlowData

data class GlowDataSubscribe(val stream: String) : GlowData()
data class GlowDataUnsubscribe(val stream: String) : GlowData()
data class GlowDataStreamInitialState(val stream: String, val streamUpdateId: Int) : GlowData() // TODO Add Stream Content classes
data class GlowDataStreamUpdate(val stream: String, val streamUpdateId: Int) : GlowData() // TODO Add Stream Content classes
data class GlowDataRequestStreamData(val stream: String) : GlowData()
data class GlowDataError(val error: GlowError): GlowData()

data class GlowDataAddFixtures(val fixture: PatchFixture): GlowData() // TODO Requires diskussion regarding needed/optinal values
data class GlowDataFixturesAdded(val uuids : Array<UUID>): GlowData()
data class GlowDataUpdateFixture(val uuid: UUID): GlowData() // TODO Requires diskussion regarding needed/optinal values
data class GlowDataDeleteFixtures(val uuids : Array<UUID>): GlowData()
data class GlowDataFixtureTypeAdded(val fixtureTypeId : UUID): GlowData()
data class GlowDataDeleteFixtureTypes(val uuids : Array<UUID>): GlowData()

class GlowDataTypeAdapter: TypeAdapter<GlowData> {
    override fun classFor(type: Any): KClass<out GlowData> = when(type as String) {
        // TODO this mapping kind of repeats the mapping in GlowEvent but I don't know how else to do it
        "subscribe" -> GlowDataSubscribe::class
        "unsubscribe" -> GlowDataUnsubscribe::class
        "streamInitialState" -> GlowDataStreamInitialState::class
        "streamUpdate" -> GlowDataStreamUpdate::class
        "requestStreamData" -> GlowDataRequestStreamData::class
        "error" -> GlowDataError::class
        "addFixtures" ->GlowDataAddFixtures::class
        "fixturesAdded" -> GlowDataFixturesAdded::class
        "updateFixture" -> GlowDataUpdateFixture::class
        "deleteFixtures" -> GlowDataDeleteFixtures::class
        "fixtureTypeAdded" -> GlowDataFixtureTypeAdded::class
        "deleteFixtureTypes" -> GlowDataDeleteFixtureTypes::class
        else -> throw IllegalArgumentException("Unknown \"data\" type: $type")
    }
}





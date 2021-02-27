package org.cueglow.server.api

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.TypeAdapter
import org.cueglow.server.objects.GlowError
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
data class GlowDataFixturesAdded(val uuids : List<UUID>): GlowData()
data class GlowDataUpdateFixture(val uuid: UUID): GlowData() // TODO Requires diskussion regarding needed/optinal values
data class GlowDataDeleteFixtures(val uuids : List<UUID>): GlowData()
data class GlowDataFixtureTypeAdded(val fixtureTypeId : UUID): GlowData()
data class GlowDataDeleteFixtureTypes(val fixtureTypeIds : List<UUID>): GlowData()

class GlowDataTypeAdapter: TypeAdapter<GlowData> {
    override fun classFor(type: Any): KClass<out GlowData> =
        GlowEvent.fromDescriptor(type as String)?.eventDataClass ?:
        throw IllegalArgumentException("Unknown JSON event: $type")
}

val UUIDConverter = object: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == UUID::class.java

    override fun toJson(value: Any): String
            = """"${(value as UUID)}""""

    override fun fromJson(jv: JsonValue): UUID
            = UUID.fromString(jv.string)
}

val UUIDArrayConverter = object: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == Array<UUID>::class.java

    override fun toJson(value: Any): String = (value as Array<*>)
        .map { it.toString() }.joinToString(",", "[", "]") { "\"" + it + "\"" }

    override fun fromJson(jv: JsonValue): Array<UUID>
            = jv.array?.map{UUID.fromString(it as String)}?.toTypedArray() ?: throw Error("Parsing UUID Arry failed")
}





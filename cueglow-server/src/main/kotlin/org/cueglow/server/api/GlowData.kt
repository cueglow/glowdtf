package org.cueglow.server.api

import com.beust.klaxon.Converter
import com.beust.klaxon.Json
import com.beust.klaxon.JsonValue
import com.beust.klaxon.TypeAdapter
import com.github.michaelbull.result.unwrap
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.patch.PatchFixtureData
import java.util.*
import kotlin.reflect.KClass

sealed class GlowData

data class GlowDataSubscribe(val stream: String) : GlowData()
data class GlowDataUnsubscribe(val stream: String) : GlowData()
data class GlowDataStreamInitialState(val stream: String, val streamUpdateId: Int) : GlowData() // TODO Add Stream Content classes
data class GlowDataStreamUpdate(val stream: String, val streamUpdateId: Int) : GlowData() // TODO Add Stream Content classes
data class GlowDataRequestStreamData(val stream: String) : GlowData()
data class GlowDataError(@Json(index=0) val errorName: String, @Json(index=1) val errorDescription: String): GlowData()

data class GlowDataAddFixtures(val fixtures: List<PatchFixtureData>): GlowData() // TODO replace PatchFixture with its parent PatchFixtureData which does not contain UUID or fancy callbacks upon update
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

val ArtNetAddressConverter = object: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == ArtNetAddress::class.java

    override fun toJson(value: Any): String = (value as ArtNetAddress).value.toString()

    override fun fromJson(jv: JsonValue): ArtNetAddress
            = ArtNetAddress.tryFrom(jv.int ?: throw Error("No Int provided for ArtNetAddress")).unwrap()
}

val DmxAddressConverter = object: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == DmxAddress::class.java

    override fun toJson(value: Any): String = (value as DmxAddress).value.toString()

    override fun fromJson(jv: JsonValue): DmxAddress
            = DmxAddress.tryFrom(jv.int ?: throw Error("No Int provided for DmxAddress")).unwrap()
}





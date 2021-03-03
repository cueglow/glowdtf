package org.cueglow.server.api

import com.beust.klaxon.Converter
import com.beust.klaxon.Json
import com.beust.klaxon.JsonValue
import com.beust.klaxon.TypeAdapter
import com.github.michaelbull.result.*
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
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

data class GlowDataAddFixtures(val fixtures: List<PatchFixtureData>): GlowData()
data class GlowDataFixturesAdded(val uuids : List<UUID>): GlowData()
data class GlowDataUpdateFixture(
    val uuid: UUID,
    val fid: Int? = null,
    val name: String? = null,
    @ArtNetAddressResult
    val universe: Result<ArtNetAddress?, Unit> = Err(Unit),
    @DmxAddressResult
    val address: Result<DmxAddress?, Unit> = Err(Unit)
): GlowData()
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

@Target(AnnotationTarget.FIELD)
annotation class ArtNetAddressResult
val ArtNetAddressResultConverter = object: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == Result::class.java

    override fun toJson(value: Any): String = (value as Result<ArtNetAddress?, Unit>).map{it?.value.toString()}
        .unwrap() // throwing when Err because then we'd have to not serialize the field at all which I don't think we can control from the converter

    override fun fromJson(jv: JsonValue): Result<ArtNetAddress?, Unit> {
        val value = jv.int ?: return Ok(null)
        return Ok(ArtNetAddress.tryFrom(value).unwrap())
    }
}


@Target(AnnotationTarget.FIELD)
annotation class DmxAddressResult
val DmxAddressResultConverter = object: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == Result::class.java

    override fun toJson(value: Any): String = (value as Result<DmxAddress?, Unit>).map {it?.value.toString()}
        .unwrap() // throwing when Err because then we'd have to not serialize the field at all which I don't think we can control from the converter

    override fun fromJson(jv: JsonValue): Result<DmxAddress?, Unit> {
        val value = jv.int ?: return Ok(null)
        return Ok(DmxAddress.tryFrom(value).unwrap())
    }
}






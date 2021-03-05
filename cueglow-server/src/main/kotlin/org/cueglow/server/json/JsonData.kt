package org.cueglow.server.json

import com.beust.klaxon.Converter
import com.beust.klaxon.Json
import com.beust.klaxon.JsonValue
import com.beust.klaxon.TypeAdapter
import com.github.michaelbull.result.*
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import java.util.*
import kotlin.reflect.KClass

/**
 * Internal representation of the "data" field in the JSON message
 *
 * Has have different concrete type based on "event" field.
 */
sealed class JsonData

data class JsonDataSubscribe(val stream: String) : JsonData()
data class JsonDataUnsubscribe(val stream: String) : JsonData()
data class JsonDataStreamInitialState(val stream: String, val streamUpdateId: Int) : JsonData() // TODO Add Stream Content classes
data class JsonDataStreamUpdate(val stream: String, val streamUpdateId: Int) : JsonData() // TODO Add Stream Content classes
data class JsonDataRequestStreamData(val stream: String) : JsonData()
data class JsonDataError(@Json(index=0) val errorName: String, @Json(index=1) val errorDescription: String): JsonData()

data class JsonDataAddFixtures(val fixtures: List<AddFixtureData>): JsonData()
data class JsonDataFixturesAdded(val uuids : List<UUID>): JsonData()
data class JsonDataUpdateFixture(
    val uuid: UUID,
    val fid: Int? = null,
    val name: String? = null,
    @ArtNetAddressResult
    val universe: Result<ArtNetAddress?, Unit> = Err(Unit),
    @DmxAddressResult
    val address: Result<DmxAddress?, Unit> = Err(Unit)
): JsonData()
data class JsonDataDeleteFixtures(val uuids : List<UUID>): JsonData()
data class JsonDataFixtureTypeAdded(val fixtureTypeId : UUID): JsonData()
data class JsonDataDeleteFixtureTypes(val fixtureTypeIds : List<UUID>): JsonData()

data class AddFixtureData(
    val fid: Int,
    val name: String,
    val fixtureTypeId: UUID,
    val dmxMode: String,
    val universe: ArtNetAddress?,
    val address: DmxAddress?,
)

class JsonDataTypeAdapter: TypeAdapter<JsonData> {
    override fun classFor(type: Any): KClass<out JsonData> =
        JsonEvent.fromDescriptor(type as String)?.eventDataClass ?:
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
            = jv.array?.map{UUID.fromString(it as String)}?.toTypedArray() ?: throw Error("Parsing UUID Array failed")
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

    override fun toJson(value: Any): String = (value as Result<*, *>).map{(it as ArtNetAddress?)?.value.toString()}
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

    override fun toJson(value: Any): String = (value as Result<*, *>).map{(it as DmxAddress?)?.value.toString()}
        .unwrap() // throwing when Err because then we'd have to not serialize the field at all which I don't think we can control from the converter

    override fun fromJson(jv: JsonValue): Result<DmxAddress?, Unit> {
        val value = jv.int ?: return Ok(null)
        return Ok(DmxAddress.tryFrom(value).unwrap())
    }
}






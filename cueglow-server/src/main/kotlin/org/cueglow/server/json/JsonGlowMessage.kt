package org.cueglow.server.json

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.beust.klaxon.TypeAdapter
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.unwrap
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.objects.messages.*
import java.io.StringReader
import java.util.*
import kotlin.reflect.KClass

//--------------------------
// Serialization and Parsing
//--------------------------

/** Convert GlowMessage to JSON String by Extension Function */
fun GlowMessage.toJsonString(): String {
    return Klaxon()
        .fieldConverter(KlaxonGlowEvent::class, KlaxonGlowEventConverter)
        .converter(UUIDConverter)
        .converter(UUIDArrayConverter)
        .toJsonString(this)
}

/**
 * Parse JSON to the internal representation [GlowMessage]
 */
fun GlowMessage.Companion.fromJsonString(input: String): GlowMessage = Klaxon()
    .fieldConverter(KlaxonGlowEvent::class, KlaxonGlowEventConverter)
    .fieldConverter(KlaxonArtNetAddressResult::class, ArtNetAddressResultConverter)
    .fieldConverter(KlaxonDmxAddressResult::class, DmxAddressResultConverter)
    .converter(UUIDConverter)
    .converter(UUIDArrayConverter)
    .converter(DmxAddressConverter)
    .converter(ArtNetAddressConverter)
    .parse<GlowMessage>(StringReader(input))
    ?: TODO("Error Handling is WIP")

//-------------------------------
// Klaxon Adapters and Converters
//-------------------------------

class KlaxonGlowDataTypeAdapter: TypeAdapter<GlowData> {
    override fun classFor(type: Any): KClass<out GlowData> =
        GlowEvent.fromString(type as String)?.dataClass ?:
        throw IllegalArgumentException("Unknown JSON event: $type")
}

/**
 * Provide a Annotation to mark fields and allow Klaxon to use a special converter on all marked fields
 * See [KlaxonGlowEventConverter] for the special converter and [GlowMessage.event] for the marked field
 * A Klaxon instance with the linked Annotation and Converter ist used in [GlowMessage.toJsonString]
 */
@Target(AnnotationTarget.FIELD)
annotation class KlaxonGlowEvent

object KlaxonGlowEventConverter: Converter {
    override fun canConvert(cls: Class<*>): Boolean = cls == GlowEvent::class.java

    override fun toJson(value: Any): String = "\"$value\""

    override fun fromJson(jv: JsonValue): GlowEvent? = GlowEvent.fromString(jv.inside.toString())
}

object UUIDConverter: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == UUID::class.java

    override fun toJson(value: Any): String
            = """"${(value as UUID)}""""

    override fun fromJson(jv: JsonValue): UUID
            = UUID.fromString(jv.string)
}

object UUIDArrayConverter: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == Array<UUID>::class.java

    override fun toJson(value: Any): String = (value as Array<*>)
        .map { it.toString() }.joinToString(",", "[", "]") { "\"" + it + "\"" }

    override fun fromJson(jv: JsonValue): Array<UUID>
            = jv.array?.map{ UUID.fromString(it as String)}?.toTypedArray() ?: throw Error("Parsing UUID Array failed")
}

object ArtNetAddressConverter: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == ArtNetAddress::class.java

    override fun toJson(value: Any): String = (value as ArtNetAddress).value.toString()

    override fun fromJson(jv: JsonValue): ArtNetAddress
            = ArtNetAddress.tryFrom(jv.int ?: throw Error("No Int provided for ArtNetAddress")).unwrap()
}

object DmxAddressConverter: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == DmxAddress::class.java

    override fun toJson(value: Any): String = (value as DmxAddress).value.toString()

    override fun fromJson(jv: JsonValue): DmxAddress
            = DmxAddress.tryFrom(jv.int ?: throw Error("No Int provided for DmxAddress")).unwrap()
}

@Target(AnnotationTarget.FIELD)
annotation class KlaxonArtNetAddressResult

object ArtNetAddressResultConverter: Converter {
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
annotation class KlaxonDmxAddressResult

object DmxAddressResultConverter: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == Result::class.java

    override fun toJson(value: Any): String = (value as Result<*, *>).map{(it as DmxAddress?)?.value.toString()}
        .unwrap() // throwing when Err because then we'd have to not serialize the field at all which I don't think we can control from the converter

    override fun fromJson(jv: JsonValue): Result<DmxAddress?, Unit> {
        val value = jv.int ?: return Ok(null)
        return Ok(DmxAddress.tryFrom(value).unwrap())
    }
}
package org.cueglow.server.json

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.beust.klaxon.TypeAdapter
import com.github.michaelbull.result.*
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import java.io.StringReader
import java.util.*
import kotlin.reflect.KClass

//--------------------------
// Serialization and Parsing
//--------------------------

// TODO check if this works with the new GlowMessage design

/** Convert GlowMessage to JSON String by Extension Function */
fun GlowMessage.toJsonString(): String {
    return Klaxon()
        .fieldConverter(KlaxonArtNetAddressUpdate::class, ArtNetAddressResultConverter)
        .fieldConverter(KlaxonDmxAddressUpdate::class, DmxAddressResultConverter)
        .converter(KlaxonGlowEventConverter)
        .converter(UUIDConverter)
        .converter(DmxAddressConverter)
        .converter(ArtNetAddressConverter)
        .toJsonString(this)
}

// TODO check if this works with the new GlowMessage design
/**
 * Parse JSON to the internal representation [GlowMessage]
 */
fun GlowMessage.Companion.fromJsonString(input: String): GlowMessage = Klaxon()
    .fieldConverter(KlaxonArtNetAddressUpdate::class, ArtNetAddressResultConverter)
    .fieldConverter(KlaxonDmxAddressUpdate::class, DmxAddressResultConverter)
    .converter(KlaxonGlowEventConverter)
    .converter(UUIDConverter)
    .converter(DmxAddressConverter)
    .converter(ArtNetAddressConverter)
    .parse<GlowMessage>(StringReader(input))
    ?: TODO("Error Handling is WIP")

//-------------------------------
// Klaxon Adapters and Converters
//-------------------------------

class KlaxonGlowMessageAdapter: TypeAdapter<GlowMessage> {
    override fun classFor(type: Any): KClass<out GlowMessage> =
        GlowEvent.fromString(type as String)?.messageClass ?: throw IllegalArgumentException("Unknown JSON event: $type")
}

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

/** Annotation that is associated with [ArtNetAddressResultConverter] in the parsing/deserialization methods */
@Target(AnnotationTarget.FIELD)
annotation class KlaxonArtNetAddressUpdate

object ArtNetAddressResultConverter: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == Result::class.java

    override fun toJson(value: Any): String = (value as Result<*, *>)
        .map{(it as ArtNetAddress?)?.value?.toString() ?: "-1"} // null means unpatched and is encoded as -1
        .getOr("null") // Err means no update and is encoded as null/absent

    override fun fromJson(jv: JsonValue): Result<ArtNetAddress?, Unit> {
        val value = jv.int ?: return Err(Unit) // no update if it is null or absent
        if (value == -1) {return Ok(null)} // update to null if it is -1
        return Ok(ArtNetAddress.tryFrom(value).unwrap()) // update to new ArtNetAddress
    }
}

/** Annotation that is associated with [DmxAddressResultConverter] in the parsing/deserialization methods */
@Target(AnnotationTarget.FIELD)
annotation class KlaxonDmxAddressUpdate

object DmxAddressResultConverter: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == Result::class.java

    override fun toJson(value: Any): String = (value as Result<*, *>)
        .map{(it as DmxAddress?)?.value?.toString() ?: "-1"} // null means unpatched and is encoded as -1
        .getOr("null") // Err means no update and is encoded as null/absent

    override fun fromJson(jv: JsonValue): Result<DmxAddress?, Unit> {
        val value = jv.int ?: return Err(Unit) // no update if it is null or absent
        if (value == -1) {return Ok(null)} // update to null if it is -1
        return Ok(DmxAddress.tryFrom(value).unwrap()) // update to new ArtNetAddress
    }
}
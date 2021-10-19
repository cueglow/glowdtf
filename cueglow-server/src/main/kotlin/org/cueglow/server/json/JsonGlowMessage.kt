package org.cueglow.server.json

import com.beust.klaxon.*
import com.github.michaelbull.result.*
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.objects.messages.GlowTopic
import java.io.StringReader
import java.util.*
import kotlin.reflect.KClass

//--------------------------
// Serialization and Parsing
//--------------------------

/** Convert GlowMessage to JSON String by Extension Function */
fun GlowMessage.toJsonString(): String {
    return Klaxon()
        .fieldConverter(KlaxonArtNetAddressUpdate::class, ArtNetAddressResultConverter)
        .fieldConverter(KlaxonDmxAddressUpdate::class, DmxAddressResultConverter)
        .converter(KlaxonGlowEventConverter)
        .converter(KlaxonGlowTopicConverter)
        .converter(UUIDConverter)
        .converter(DmxAddressConverter)
        .converter(ArtNetAddressConverter)
        .toJsonString(this)
}

/**
 * Parse JSON to the internal representation [GlowMessage]
 */
fun GlowMessage.Companion.fromJsonString(input: String): GlowMessage = Klaxon()
    .fieldConverter(KlaxonArtNetAddressUpdate::class, ArtNetAddressResultConverter)
    .fieldConverter(KlaxonDmxAddressUpdate::class, DmxAddressResultConverter)
    .converter(KlaxonGlowEventConverter)
    .converter(KlaxonGlowTopicConverter)
    .converter(UUIDConverter)
    .converter(DmxAddressConverter)
    .converter(ArtNetAddressConverter)
    .parse<GlowMessage>(StringReader(input))
    ?: throw KlaxonException("Klaxon Parser returned null after parsing '$input'")

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

object KlaxonGlowTopicConverter: Converter {
    override fun canConvert(cls: Class<*>): Boolean = cls == GlowTopic::class.java

    override fun toJson(value: Any): String = "\"$value\""

    override fun fromJson(jv: JsonValue): GlowTopic? = GlowTopic.fromString(jv.inside.toString())
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

    override fun fromJson(jv: JsonValue): ArtNetAddress? {
        val value = jv.int ?: throw Error("No Int provided for ArtNetAddress")
        if (value == -1) {return null}
        return ArtNetAddress.tryFrom(value).unwrap()
    }
}

object DmxAddressConverter: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == DmxAddress::class.java

    override fun toJson(value: Any): String = (value as DmxAddress).value.toString()

    override fun fromJson(jv: JsonValue): DmxAddress? {
        val value = jv.int ?: throw Error("No Int provided for DmxAddress")
        if (value == -1) {return null}
        return DmxAddress.tryFrom(value).unwrap()
    }
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
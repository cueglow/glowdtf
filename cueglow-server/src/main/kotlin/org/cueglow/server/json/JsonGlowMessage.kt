package org.cueglow.server.json

import com.beust.klaxon.*
import com.github.michaelbull.result.*
import org.apache.logging.log4j.LogManager
import org.cueglow.server.gdtf.DependencyEdge
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.objects.messages.GlowTopic
import org.jgrapht.graph.DirectedAcyclicGraph
import org.jgrapht.nio.AttributeType
import org.jgrapht.nio.DefaultAttribute
import org.jgrapht.nio.json.JSONExporter
import java.io.StringReader
import java.io.StringWriter
import java.util.*
import kotlin.reflect.KClass

val logger = LogManager.getLogger()!!

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
        .converter(ShortConverter)
        .converter(UUIDConverter)
        .converter(DmxAddressConverter)
        .converter(ArtNetAddressConverter)
        .converter(DirectedAcyclicGraphConverter)
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
    .converter(ShortConverter)
    .converter(UUIDConverter)
    .converter(DmxAddressConverter)
    .converter(ArtNetAddressConverter)
    .converter(DirectedAcyclicGraphConverter)
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

object ShortConverter: Converter {
    override fun canConvert(cls: Class<*>)
            = cls == Short::class.java

    override fun toJson(value: Any): String
            = """$value"""

    override fun fromJson(jv: JsonValue): Short
            = jv.string!!.toShort()
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

object DirectedAcyclicGraphConverter: Converter {
    private val graphExporter = JSONExporter<Int, DependencyEdge>()

    init {
        graphExporter.setVertexIdProvider { it.toString() }
        graphExporter.setEdgeAttributeProvider { edge ->
            mapOf(
                "modeFromClipped" to DefaultAttribute(edge.from, AttributeType.LONG),
                "modeToClipped" to DefaultAttribute(edge.to, AttributeType.LONG),
            )
        }
    }

    override fun canConvert(cls: Class<*>)
            = cls == DirectedAcyclicGraph::class.java

    override fun toJson(value: Any): String {
        val writer = StringWriter()
        value as DirectedAcyclicGraph<Int, DependencyEdge>
        graphExporter.exportGraph(value, writer)
        return writer.buffer.toString()
    }

    override fun fromJson(jv: JsonValue): DirectedAcyclicGraph<Int, Pair<*,*>> {
        logger.error("Trying to parse a DirectedAcyclicGraph from JSON - not supported. Returning empty graph.")
        return DirectedAcyclicGraph<Int, Pair<*,*>>(Pair::class.java)
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
package org.cueglow.server.api

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.beust.klaxon.TypeFor
import java.io.StringReader

/**
 * Create a new GlowMessage object, which serves as the internal representation of a message
 *
 * The json object MUST have the fields "event" (String) and "data" (JsonObject)
 * and CAN have the field "messageId" (Int)
 */
class GlowMessage @JvmOverloads constructor(
    @Json(index = 0)
    @TypeFor(field="data", adapter=GlowDataTypeAdapter::class)
    @KlaxonGlowEvent
    val event: GlowEvent,
    @Json(index = 1)
    val data: GlowData, // TODO Is this field nullable?
    @Json(index = 2)
    val messageId: Int?
) {

    fun toJsonString(): String {
        return Klaxon()
            .fieldConverter(KlaxonGlowEvent::class, GlowEvent.glowEventConverter)
            .converter(UUIDConverter)
            .converter(UUIDArrayConverter)
            .toJsonString(this)
    }
}

/**
 * Parse JSON to GlowMessage
 */
fun parseGlowMessage(input: String): GlowMessage = Klaxon()
        .fieldConverter(KlaxonGlowEvent::class, GlowEvent.glowEventConverter)
        .converter(UUIDConverter)
        .converter(UUIDArrayConverter)
        .parse<GlowMessage>(StringReader(input))
    ?: TODO("Errorhandling is WIP")

package org.cueglow.server.objects

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon

/**
 * Create a new GlowMessage object, which serves as the internal representation of a message
 *
 * The json object MUST have the fields "event" (String) and "data" (JsonObject)
 * and CAN have the field "messageId" (Int)
 */
class GlowMessage @JvmOverloads constructor(
    @Json(name = "event", index = 0)
    @KlaxonGlowEvent
    val glowEvent: GlowEvent,
    @Json(index = 1)
    val data: GlowData, // TODO Is this field nullable?
    @Json(index = 2)
    val messageId: Int?
) {

    fun toJsonString(): String {
        return Klaxon()
            .fieldConverter(KlaxonGlowEvent::class, GlowEvent.glowEventConverter)
            .toJsonString(this)
    }
}

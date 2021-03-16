package org.cueglow.server.objects.messages

import com.beust.klaxon.Json
import com.beust.klaxon.TypeFor
import org.cueglow.server.json.KlaxonGlowDataTypeAdapter
import org.cueglow.server.json.KlaxonGlowEvent

/**
 * Represents a message inside CueGlow that may be parsed from or serialized to different formats like JSON
 *
 * The message MUST have the field "event" and CAN have the fields and "data" and "messageId".
 *
 * @throws IllegalArgumentException when the data type does not match the event
 */
class GlowMessage @JvmOverloads constructor(
    @Json(index = 0)
    @TypeFor(field="data", adapter=KlaxonGlowDataTypeAdapter::class)
    @KlaxonGlowEvent
    val event: GlowEvent,
    @Json(index = 1)
    val data: GlowData? = null,
    @Json(index = 2)
    val messageId: Int? = null,
) {
    init {
        // validate data type matches event
        val dataClass = data?.let {it::class}
        if (event.dataClass != dataClass) {
            throw IllegalArgumentException("Event ${event.name} should be accompanied by data type " +
                    "${event.dataClass?.simpleName} but was ${dataClass?.simpleName ?: "null"}")
        }
    }

    constructor(data: GlowData, messageId: Int? = null): this(GlowEvent.fromDataClass(data::class), data, messageId)

    companion object
}


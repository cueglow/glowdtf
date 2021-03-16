package org.cueglow.server.json

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.beust.klaxon.TypeFor
import java.io.StringReader

/**
 * Creates an object which serves as the internal representation of a JSON message
 *
 * The json object MUST have the field "event" and CAN have the fields and "data" and "messageId"
 */
class JsonMessage @JvmOverloads constructor(
    @Json(index = 0)
    @TypeFor(field="data", adapter= JsonDataTypeAdapter::class)
    @KlaxonJsonEvent
    val event: JsonEvent,
    @Json(index = 1)
    val data: JsonData? = null,
    @Json(index = 2)
    val messageId: Int? = null
) {

    init {
        // validate data type matches event
        val dataClass = data?.let {it::class}
        if (event.eventDataClass != dataClass) {
            throw IllegalArgumentException("Event ${event.name} should be accompanied by data type " +
                    "${event.eventDataClass?.simpleName} but was ${dataClass?.simpleName ?: "null"}")
        }
    }

    fun toJsonString(): String {
        return Klaxon()
            .fieldConverter(KlaxonJsonEvent::class, JsonEvent.jsonEventConverter)
            .converter(UUIDConverter)
            .converter(UUIDArrayConverter)
            .toJsonString(this)
    }
}

/**
 * Parse JSON to the internal representation JsonMessage
 */
fun parseJsonMessage(input: String): JsonMessage = Klaxon()
        .fieldConverter(KlaxonJsonEvent::class, JsonEvent.jsonEventConverter)
        .fieldConverter(ArtNetAddressResult::class, ArtNetAddressResultConverter)
        .fieldConverter(DmxAddressResult::class, DmxAddressResultConverter)
        .converter(UUIDConverter)
        .converter(UUIDArrayConverter)
        .converter(DmxAddressConverter)
        .converter(ArtNetAddressConverter)
        .parse<JsonMessage>(StringReader(input))
    ?: TODO("Error Handling is WIP")


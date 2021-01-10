package org.cueglow.server.objects

import com.beust.klaxon.JsonObject
import kotlin.properties.Delegates

/**
 * Create a new GlowMessage and parse the values from the [json] object.
 * The json object MUST have the fields "event" (String) and "data" (JsonObject)
 * and CAN have the field "messageId" (Int)
 */
class GlowMessage(json: JsonObject) {

    var glowEvent: GlowEvent
    lateinit var data: JsonObject
    var messageId by Delegates.notNull<Int>()

    init {

        // Read JSON Fields, check and write the data into the object
        try {
            val eventDescriptor = json.string("event")
            if (eventDescriptor != null) {
                val event = GlowEvent.fromDiscriptor(eventDescriptor);
                if (event != null) {
                    glowEvent = event
                } else {
                    TODO("Errorhandling is still WIP")
                }
            } else {
                TODO("Errorhandling is still WIP")
            }

            val jsonData = json.obj("data")
            if (jsonData != null) {
                data = jsonData
            }

            val id = json.int("messageId")
            if (id != null) {
                messageId = id;
            }

        } catch (e: ClassCastException) {
            TODO("Errorhandling is still WIP")
        }
    }

}
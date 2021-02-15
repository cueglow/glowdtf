package org.cueglow.server.objects

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.StringReader

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GlowMessageTest {

    @Test
    fun parseGlowMessageFromJsonStringWithMessageId() {

        val jsonString = """
            {
                "event": "subscribe",
                "data": "patch",
                "messageId": 1
            }
        """.trimIndent()

        val message = Klaxon()
            .fieldConverter(KlaxonGlowEvent::class, GlowEvent.glowEventConverter)
            .parse<GlowMessage>(StringReader(jsonString))

        assert(message != null)
        Assertions.assertEquals(GlowEvent.SUBSCRIBE, message?.glowEvent)
        Assertions.assertEquals("patch", message?.data)
        Assertions.assertEquals(1, message?.messageId)

    }

    @Test
    fun parseGlowMessageToJsonString() {
        val jsonObject = JsonObject()
        jsonObject["stream"] = "patch"
        jsonObject["streamUpdateId"] = 0
        // rest of the fields omitted for simplification of test

        val message = GlowMessage(GlowEvent.STREAM_INITIAL_STATE, jsonObject, 42)

        val jsonMessage = message.toJsonString()

        Assertions.assertEquals(
            """{"event" : "streamInitialState", "data" : {"stream": "patch", "streamUpdateId": 0}, "messageId" : 42}""",
            jsonMessage
        )
    }
}
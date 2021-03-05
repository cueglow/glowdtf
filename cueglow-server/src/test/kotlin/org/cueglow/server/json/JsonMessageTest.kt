package org.cueglow.server.json

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonMessageTest {

    @Test
    fun parseJsonMessageFromJsonStringWithMessageId() {
        val jsonString = """
            {
                "event": "subscribe",
                "data": {
                    "stream": "patch"
                },
                "messageId": 1
            }
        """.trimIndent()

        val message = parseJsonMessage(jsonString)

        Assertions.assertEquals(JsonEvent.SUBSCRIBE, message.event)
        Assertions.assertEquals(JsonDataSubscribe("patch"), message.data)
        Assertions.assertEquals(1, message.messageId)

    }

    @Test
    fun serializeJsonMessageToJsonString() {
        val dataObject = JsonDataStreamUpdate("patch", 0)
        // TODO rest of the fields omitted for simplification of test

        val message = JsonMessage(JsonEvent.STREAM_INITIAL_STATE, dataObject, 42)

        val jsonMessage = message.toJsonString()

        Assertions.assertEquals(
            """{"event" : "streamInitialState", "data" : {"stream" : "patch", "streamUpdateId" : 0}, "messageId" : 42}""",
            jsonMessage
        )
    }
}
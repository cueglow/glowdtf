package org.cueglow.server.json

import org.cueglow.server.objects.messages.GlowData
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GlowMessageTest {

    @Test
    fun parseGlowMessageFromJsonStringWithMessageId() {
        val jsonString = """
            {
                "event": "subscribe",
                "data": {
                    "stream": "patch"
                },
                "messageId": 1
            }
        """.trimIndent()

        val message = (GlowMessage.fromJsonString(jsonString) as GlowMessage.Subscribe)

        Assertions.assertEquals(GlowEvent.SUBSCRIBE, message.event)
        Assertions.assertEquals(GlowData.Subscribe("patch"), message.data)
        Assertions.assertEquals(1, message.messageId)

    }

    @Test
    fun serializeGlowMessageToJsonString() {
        val uuid = UUID.fromString("a5e98b9a-95db-4ab1-92ec-c4b72f1d546c")
        val dataObject = GlowData.FixturesAdded(listOf(uuid))

        val message = GlowMessage.DeleteFixtures(GlowData.DeleteFixtures(listOf(uuid)), 42)

        val jsonString = message.toJsonString()

        Assertions.assertEquals(
            """{"event" : "deleteFixtures", "data" : {"uuids" : ["a5e98b9a-95db-4ab1-92ec-c4b72f1d546c"]}, "messageId" : 42}""".trimIndent(),
            jsonString
        )
    }
}
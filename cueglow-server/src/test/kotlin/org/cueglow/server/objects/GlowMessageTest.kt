package org.cueglow.server.objects

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
                "data": {
                    "stream": "patch"
                },
                "messageId": 1
            }
        """.trimIndent()

        val jsonObject = Klaxon().parseJsonObject(StringReader(jsonString))
        val message = GlowMessage(jsonObject)

        Assertions.assertEquals(GlowEvent.SUBSCRIBE, message.glowEvent)
        Assertions.assertEquals("patch", message.data.string("stream"))
        Assertions.assertEquals(1, message.messageId)

    }
}
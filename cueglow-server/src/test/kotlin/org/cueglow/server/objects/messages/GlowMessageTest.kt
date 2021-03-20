package org.cueglow.server.objects.messages

import org.cueglow.server.json.fromJsonString
import org.cueglow.server.json.toJsonString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GlowMessageTest {

    @Test
    fun testRemoveFixtures() {
        val uuid = UUID.fromString("a5e98b9a-95db-4ab1-92ec-c4b72f1d546c")
        val messageId = 42
        val glowMessage = GlowMessage.RemoveFixtures(listOf(uuid), messageId)

        val expectedJson =
            """{"event" : "removeFixtures", "data" : ["a5e98b9a-95db-4ab1-92ec-c4b72f1d546c"], "messageId" : 42}""".trimIndent()

        assertEquals(expectedJson, glowMessage.toJsonString())

        val parsed = GlowMessage.fromJsonString(expectedJson) as GlowMessage.RemoveFixtures

        assertEquals(glowMessage.event, parsed.event)
        assertEquals(glowMessage.data, parsed.data)
        assertEquals(glowMessage.messageId, parsed.messageId)
    }

    @Test
    fun testSubscribeMessage() {
        val glowMessage = GlowMessage.PatchSubscribe()

        val expectedJson =
            """{"event" : "patchSubscribe"}""".trimIndent()

        assertEquals(expectedJson, glowMessage.toJsonString())

        val parsed = GlowMessage.fromJsonString(expectedJson)

        assertEquals(glowMessage.event, parsed.event)
        assertEquals(glowMessage.messageId, parsed.messageId)
    }
}
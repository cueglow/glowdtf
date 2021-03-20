package org.cueglow.server.integration

import org.awaitility.Awaitility
import org.cueglow.server.json.fromJsonString
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.patch.Patch
import org.junit.jupiter.api.Assertions
import java.util.*


fun deleteInvalidFixtureTest(wsClient: WsClient, patch: Patch) {
    val uuid1 = UUID.fromString("753eca6f-f5a8-4e0d-8907-16074534e08f")
    val uuid2 = UUID.fromString("9cf0450d-cef1-47e6-b467-631c4cfe45e7")

    val jsonToSend =
        """{
            "event": "deleteFixtures",
            "data": {
                "uuids": ["$uuid1", "$uuid2"]
            },
            "messageId": 729
        }""".trimIndent()

    wsClient.send(jsonToSend)

    val msg1 = GlowMessage.fromJsonString(wsClient.receiveOneMessageBlocking())
    val msg2 = GlowMessage.fromJsonString(wsClient.receiveOneMessageBlocking())

    // still one fixture in patch
    Assertions.assertEquals(1, patch.getFixtures().size)

    Assertions.assertEquals(GlowEvent.ERROR, msg1.event)
    Assertions.assertEquals("UnknownFixtureUuidError", (msg1 as GlowMessage.Error).data.name)
    Assertions.assertTrue(msg1.data.description.contains(uuid1.toString()))

    Assertions.assertEquals(GlowEvent.ERROR, msg2.event)
    Assertions.assertEquals("UnknownFixtureUuidError", (msg2 as GlowMessage.Error).data.name)
    Assertions.assertTrue(msg2.data.description.contains(uuid2.toString()))
}

fun deleteFixtureTest(wsClient: WsClient, patch: Patch) {
    val uuidToModify = patch.getFixtures().keys.first()

    val jsonToSend =
        """{
            "event": "deleteFixtures",
            "data": {
                "uuids": ["$uuidToModify"]
            },
            "messageId": 729
        }""".trimIndent()

    wsClient.send(jsonToSend)

    Awaitility.await().untilAsserted {
        Assertions.assertEquals(0, patch.getFixtures().size)
    }
}
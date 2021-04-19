package org.cueglow.server.integration

import org.awaitility.Awaitility.await
import org.cueglow.server.json.fromJsonString
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.test_utilities.ClientAndServerTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AddFixtureIntegrationTest: ClientAndServerTest() {

    val addFixtureJsonMessage =
        """{
        "event": "addFixtures",
        "data": [
            {
                "uuid": "91faaa61-624b-477a-a6c2-de00c717b3e6",
                "fid": 1,
                "name": "exampleFixture",
                "fixtureTypeId": "7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE",
                "dmxMode": "mode1",
                "universe": 1,
                "address": 1
            }
        ],
        "messageId": 42
    }""".trimIndent()

    @Test
    fun addFixture() {
        setupExampleFixtureType()

        wsClient.send(addFixtureJsonMessage)

        await().untilAsserted { assertEquals(1, patch.getFixtures().size) }

        assertTrue(examplePatchFixture.isSimilar(patch.getFixtures().asSequence().first().value))
    }

    @Test
    fun addFixtureDuplicateUuid() {
        setupExampleFixture()

        wsClient.send(addFixtureJsonMessage)

        val receivedString = wsClient.receiveOneMessageBlocking()
        val message = GlowMessage.fromJsonString(receivedString)

        assertEquals(GlowEvent.ERROR, message.event)
        assertEquals("FixtureUuidAlreadyExistsError", (message as GlowMessage.Error).data.name)
        assertEquals(42, message.messageId)

        assertEquals(1, patch.getFixtures().size)
    }

    @Test
    fun addFixtureInvalidFixtureTypeId() {
        setupExampleFixtureType()

        val initialFixtureCount = patch.getFixtures().size

        // fixtureTypeId has "3be" at end instead of "4be"
        val jsonToSend =
            """{
        "event": "addFixtures",
        "data": [
            {
                "uuid": "d81644b3-43e0-4b6d-8df4-e1e8dffaddeb",
                "fid": 2,
                "name": "exampleFixture2",
                "fixtureTypeId": "7FB33577-09C9-4BF0-BE3B-EF0DC3BEF3BE",
                "dmxMode": "mode1",
                "universe": 1,
                "address": 100
            }
        ],
        "messageId": 42
    }""".trimIndent()

        wsClient.send(jsonToSend)

        val received = wsClient.receiveOneMessageBlocking()
        val message = GlowMessage.fromJsonString(received)

        assertEquals(GlowEvent.ERROR, message.event)
        assertEquals("UnpatchedFixtureTypeIdError", (message as GlowMessage.Error).data.name)
        assertEquals(42, message.messageId)

        assertEquals(initialFixtureCount, patch.getFixtures().size)
    }

    @Test
    fun addFixtureInvalidDmxMode() {
        setupExampleFixtureType()

        val initialFixtureCount = patch.getFixtures().size

        // changed dmx mode
        val jsonToSend =
            """{
        "event": "addFixtures",
        "data": [
            {
                "uuid": "d81644b3-43e0-4b6d-8df4-e1e8dffaddeb",
                "fid": 2,
                "name": "exampleFixture2",
                "fixtureTypeId": "7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE",
                "dmxMode": "not_a_mode",
                "universe": 1,
                "address": 100
            }
        ],
        "messageId": 42
    }""".trimIndent()

        wsClient.send(jsonToSend)

        val received = wsClient.receiveOneMessageBlocking()
        val message = GlowMessage.fromJsonString(received)

        assertEquals(GlowEvent.ERROR, message.event)
        assertEquals("UnknownDmxModeError", (message as GlowMessage.Error).data.name)
        assertEquals(42, message.messageId)

        assertEquals(initialFixtureCount, patch.getFixtures().size)
    }
}



package org.cueglow.server.json

import org.awaitility.Awaitility
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.test_utilities.ClientAndServerTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class UpdateFixtureIntegrationTest: ClientAndServerTest() {
    @Test
    fun updateUnknownFixture() {
        setupExampleFixture()

        // random UUID
        val uuidToModify = UUID.fromString("cb9bd39b-303c-4105-8e14-1c5919c05750")

        val jsonToSend =
            """{
            "event": "updateFixtures",
            "data": [
                {
                    "uuid": "$uuidToModify",
                    "address": 432
                }
            ],
            "messageId": 90
        }""".trimIndent()

        wsClient.send(jsonToSend)

        val received = wsClient.receiveOneMessageBlocking()
        val message = GlowMessage.fromJsonString(received)

        Assertions.assertEquals(GlowEvent.ERROR, message.event)
        Assertions.assertEquals("UnknownFixtureUuidError", (message as GlowMessage.Error).data.name)
        Assertions.assertEquals(90, message.messageId)
    }

    @Test
    fun updateAddress() {
        setupExampleFixture()

        val uuidToModify = patch.getFixtures().keys.first()

        val prev = patch.getFixtures()[uuidToModify]!!

        val jsonToSend =
            """{
            "event": "updateFixtures",
            "data": [
                {
                "uuid": "$uuidToModify",
                "address": 432
                }
            ],
            "messageId": 89
        }""".trimIndent()

        wsClient.send(jsonToSend)

        Awaitility.await().untilAsserted {
            Assertions.assertEquals(432, patch.getFixtures()[uuidToModify]?.address?.value)
        }

        val post = patch.getFixtures()[uuidToModify]!!

        Assertions.assertEquals(prev.fid, post.fid)
        Assertions.assertEquals(prev.name, post.name)
        Assertions.assertEquals(prev.fixtureTypeId, post.fixtureTypeId)
        Assertions.assertEquals(prev.dmxMode, post.dmxMode)
        Assertions.assertEquals(prev.universe, post.universe)
    }

    @Test
    fun updateUniverse() {
        setupExampleFixture()

        val uuidToModify = patch.getFixtures().keys.first()

        val prev = patch.getFixtures()[uuidToModify]!!

        val jsonToSend =
            """{
            "event": "updateFixtures",
            "data": [
                {
                "uuid": "$uuidToModify",
                "universe": -1
                }
            ],
            "messageId": 89
        }""".trimIndent()

        wsClient.send(jsonToSend)

        Awaitility.await().untilAsserted {
            Assertions.assertEquals(null, patch.getFixtures()[uuidToModify]?.universe?.value)
        }

        val post = patch.getFixtures()[uuidToModify]!!

        Assertions.assertEquals(prev.fid, post.fid)
        Assertions.assertEquals(prev.name, post.name)
        Assertions.assertEquals(prev.fixtureTypeId, post.fixtureTypeId)
        Assertions.assertEquals(prev.dmxMode, post.dmxMode)
        Assertions.assertEquals(prev.address?.value, post.address?.value)
    }

    @Test
    fun updateNameAndFid() {
        setupExampleFixture()

        val uuidToModify = patch.getFixtures().keys.first()

        val prev = patch.getFixtures()[uuidToModify]!!

        val jsonToSend =
            """{
            "event": "updateFixtures",
            "data":[ 
                {
                "uuid": "$uuidToModify",
                "name": "newName",
                "fid": 523
                }
            ],
            "messageId": 89
        }""".trimIndent()

        wsClient.send(jsonToSend)

        Awaitility.await().untilAsserted {
            Assertions.assertEquals(523, patch.getFixtures()[uuidToModify]?.fid)
        }

        Awaitility.await().untilAsserted {
            Assertions.assertEquals("newName", patch.getFixtures()[uuidToModify]?.name)
        }

        val post = patch.getFixtures()[uuidToModify]!!

        Assertions.assertEquals(prev.fixtureTypeId, post.fixtureTypeId)
        Assertions.assertEquals(prev.dmxMode, post.dmxMode)
        Assertions.assertEquals(prev.address?.value, post.address?.value)
        Assertions.assertEquals(prev.universe, post.universe)
    }
}
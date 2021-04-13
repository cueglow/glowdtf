package org.cueglow.server.integration

import com.github.michaelbull.result.unwrap
import org.awaitility.Awaitility.await
import org.cueglow.server.gdtf.FixtureType
import org.cueglow.server.json.fromJsonString
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.objects.messages.GlowData
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.patch.Patch
import org.cueglow.server.patch.PatchFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.*

fun addFixtureTest(wsClient: WsClient, patch: Patch, exampleFixtureType: FixtureType, examplePatchFixture: PatchFixture) {
    val jsonToSend =
            """{
                "event": "addFixtures",
                "data": {
                    "fixtures": [
                        {
                            "fid": 1,
                            "name": "exampleFixture",
                            "fixtureTypeId": "7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE",
                            "dmxMode": "mode1",
                            "universe": 1,
                            "address": 1
                        }
                    ]
                },
                "messageId": 42
            }""".trimIndent()

    wsClient.send(jsonToSend)

    val received = wsClient.receiveOneMessageBlocking()
    val message = GlowMessage.fromJsonString(received)

    assertEquals(GlowEvent.FIXTURES_ADDED, message.event)
    assertEquals(1, (message.data as GlowData.FixturesAdded).uuids.size)
    assertEquals(42, message.messageId)

    assertEquals(1, patch.getFixtures().size)

    assertTrue(examplePatchFixture.isSimilar(patch.getFixtures().asSequence().first().value))
    // TODO test error responses
}

fun addFixtureInvalidFixtureTypeIdTest(wsClient: WsClient, patch: Patch) {
    val initialFixtureCount = patch.getFixtures().size

    // changed fixtureTypeId
    val jsonToSend =
        """{
                "event": "addFixtures",
                "data": {
                    "fixtures": [
                        {
                            "fid": 1,
                            "name": "exampleFixture",
                            "fixtureTypeId": "7FB33577-09C9-4BF0-BE3B-EF0DC3BEF3BE",
                            "dmxMode": "mode1",
                            "universe": 1,
                            "address": 1
                        }
                    ]
                },
                "messageId": 42
            }""".trimIndent()

    wsClient.send(jsonToSend)

    val received = wsClient.receiveOneMessageBlocking()
    val message = GlowMessage.fromJsonString(received)

    assertEquals(GlowEvent.ERROR, message.event)
    assertEquals("UnpatchedFixtureTypeIdError", (message.data as GlowData.Error).errorName)
    assertEquals(42, message.messageId)

    assertEquals(initialFixtureCount, patch.getFixtures().size)
}

fun addFixtureInvalidDmxModeTest(wsClient: WsClient, patch: Patch) {
    val initialFixtureCount = patch.getFixtures().size

    // changed dmx mode
    val jsonToSend =
        """{
                "event": "addFixtures",
                "data": {
                    "fixtures": [
                        {
                            "fid": 1,
                            "name": "exampleFixture",
                            "fixtureTypeId": "7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE",
                            "dmxMode": "not_a_mode",
                            "universe": 1,
                            "address": 1
                        }
                    ]
                },
                "messageId": 42
            }""".trimIndent()

    wsClient.send(jsonToSend)

    val received = wsClient.receiveOneMessageBlocking()
    val message = GlowMessage.fromJsonString(received)

    assertEquals(GlowEvent.ERROR, message.event)
    assertEquals("UnknownDmxModeError", (message.data as GlowData.Error).errorName)
    assertEquals(42, message.messageId)

    assertEquals(initialFixtureCount, patch.getFixtures().size)
}

fun updateUnknownFixtureTest(wsClient: WsClient) {
    val uuidToModify = UUID.fromString("cb9bd39b-303c-4105-8e14-1c5919c05750")

    val jsonToSend =
        """{
            "event": "updateFixture",
            "data": {
                "uuid": "$uuidToModify",
                "address": 432
            },
            "messageId": 90
        }""".trimIndent()

    wsClient.send(jsonToSend)

    val received = wsClient.receiveOneMessageBlocking()
    val message = GlowMessage.fromJsonString(received)

    assertEquals(GlowEvent.ERROR, message.event)
    assertEquals("UnknownFixtureUuidError", (message.data as GlowData.Error).errorName)
    assertEquals(90, message.messageId)
}

fun updateAddressTest(wsClient: WsClient, patch: Patch) {
    val uuidToModify = patch.getFixtures().keys.first()

    val prev = patch.getFixtures()[uuidToModify]!!

    val jsonToSend =
        """{
            "event": "updateFixture",
            "data": {
                "uuid": "$uuidToModify",
                "address": 432
            },
            "messageId": 89
        }""".trimIndent()

    wsClient.send(jsonToSend)

    await().untilAsserted {
        assertEquals(432, patch.getFixtures()[uuidToModify]?.address?.value)
    }

    val post = patch.getFixtures()[uuidToModify]!!

    assertEquals(prev.fid, post.fid)
    assertEquals(prev.name, post.name)
    assertEquals(prev.fixtureTypeId, post.fixtureTypeId)
    assertEquals(prev.dmxMode, post.dmxMode)
    assertEquals(prev.universe, post.universe)
}

fun updateUniverseTest(wsClient: WsClient, patch: Patch) {
    val uuidToModify = patch.getFixtures().keys.first()

    val prev = patch.getFixtures()[uuidToModify]!!

    val jsonToSend =
        """{
            "event": "updateFixture",
            "data": {
                "uuid": "$uuidToModify",
                "universe": null
            },
            "messageId": 89
        }""".trimIndent()

    wsClient.send(jsonToSend)

    await().untilAsserted {
        assertEquals(null, patch.getFixtures()[uuidToModify]?.universe?.value)
    }

    val post = patch.getFixtures()[uuidToModify]!!

    assertEquals(prev.fid, post.fid)
    assertEquals(prev.name, post.name)
    assertEquals(prev.fixtureTypeId, post.fixtureTypeId)
    assertEquals(prev.dmxMode, post.dmxMode)
    assertEquals(prev.address?.value, post.address?.value)
}

fun updateNameAndFidTest(wsClient: WsClient, patch: Patch) {
    val uuidToModify = patch.getFixtures().keys.first()

    val prev = patch.getFixtures()[uuidToModify]!!

    val jsonToSend =
        """{
            "event": "updateFixture",
            "data": {
                "uuid": "$uuidToModify",
                "name": "newName",
                "fid": 523
            },
            "messageId": 89
        }""".trimIndent()

    wsClient.send(jsonToSend)

    await().untilAsserted {
        assertEquals(523, patch.getFixtures()[uuidToModify]?.fid)
    }

    await().untilAsserted {
        assertEquals("newName", patch.getFixtures()[uuidToModify]?.name)
    }

    val post = patch.getFixtures()[uuidToModify]!!

    assertEquals(prev.fixtureTypeId, post.fixtureTypeId)
    assertEquals(prev.dmxMode, post.dmxMode)
    assertEquals(prev.address?.value, post.address?.value)
    assertEquals(prev.universe, post.universe)
}

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
    assertEquals(1, patch.getFixtures().size)

    assertEquals(GlowEvent.ERROR, msg1.event)
    assertEquals("UnknownFixtureUuidError", (msg1.data as GlowData.Error).errorName)
    assertTrue((msg1.data as GlowData.Error).errorDescription.contains(uuid1.toString()))

    assertEquals(GlowEvent.ERROR, msg2.event)
    assertEquals("UnknownFixtureUuidError", (msg2.data as GlowData.Error).errorName)
    assertTrue((msg2.data as GlowData.Error).errorDescription.contains(uuid2.toString()))
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

    await().untilAsserted {
        assertEquals(0, patch.getFixtures().size)
    }
}


package org.cueglow.server.api

import com.github.michaelbull.result.unwrap
import org.awaitility.Awaitility.await
import org.cueglow.server.WsClient
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.patch.Patch
import org.cueglow.server.patch.PatchFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.*

fun addFixtureTest(wsClient: WsClient, patch: Patch, exampleFixtureType: GdtfWrapper) {
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
    val message = parseGlowMessage(received)

    assertEquals(GlowEvent.FIXTURES_ADDED, message.event)
    assertEquals(1, (message.data as GlowDataFixturesAdded).uuids.size)
    assertEquals(42, message.messageId)

    val expectedPatchFixture = PatchFixture(
        1,
        "exampleFixture",
        exampleFixtureType,
        "mode1",
        ArtNetAddress.tryFrom(1).unwrap(),
        DmxAddress.tryFrom(1).unwrap(),
    )

    assertEquals(1, patch.getFixtures().size)

    assertTrue(expectedPatchFixture.isSimilar(patch.getFixtures().asSequence().first().value))
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
    val message = parseGlowMessage(received ?: "")

    assertEquals(GlowEvent.ERROR, message.event)
    assertEquals("UnknownFixtureTypeIdError", (message.data as GlowDataError).errorName)
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
    val message = parseGlowMessage(received ?: "")

    assertEquals(GlowEvent.ERROR, message.event)
    assertEquals("UnknownDmxModeError", (message.data as GlowDataError).errorName)
    assertEquals(42, message.messageId)

    assertEquals(initialFixtureCount, patch.getFixtures().size)
}

fun updateUnknownFixtureTest(wsClient: WsClient, patch: Patch) {
    val uuidToModify = UUID.randomUUID()

    // change address
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
    val message = parseGlowMessage(received)

    assertEquals(GlowEvent.ERROR, message.event)
    assertEquals("UnknownFixtureUuidError", (message.data as GlowDataError).errorName)
    assertEquals(90, message.messageId)
}

fun updateAddressTest(wsClient: WsClient, patch: Patch) {
    val uuidToModify = patch.getFixtures().keys.first()

    val prev = patch.getFixtures()[uuidToModify]!!

    // change address
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
    assertEquals(prev.fixtureType, post.fixtureType)
    assertEquals(prev.dmxMode, post.dmxMode)
    assertEquals(prev.universe, post.universe)
}

fun updateUniverseTest(wsClient: WsClient, patch: Patch) {
    val uuidToModify = patch.getFixtures().keys.first()

    val prev = patch.getFixtures()[uuidToModify]!!

    // change address
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
    assertEquals(prev.fixtureType, post.fixtureType)
    assertEquals(prev.dmxMode, post.dmxMode)
    assertEquals(prev.address?.value, post.address?.value)
}

fun updateNameAndFidTest(wsClient: WsClient, patch: Patch) {
    val uuidToModify = patch.getFixtures().keys.first()

    val prev = patch.getFixtures()[uuidToModify]!!

    // change address
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

    assertEquals(prev.fixtureType, post.fixtureType)
    assertEquals(prev.dmxMode, post.dmxMode)
    assertEquals(prev.address?.value, post.address?.value)
    assertEquals(prev.universe, post.universe)
}

//fun deleteFixtureTest(wsClient: WsClient, patch: Patch) {
//    val uuidToModify = patch.getFixtures().keys.first()
//
//    val prev = patch.getFixtures()[uuidToModify]!!
//
//    // change address
//    val jsonToSend =
//        """{
//            "event": "updateFixture",
//            "data": {
//                "uuid": "$uuidToModify",
//                "name": "newName",
//                "fid": 523
//            },
//            "messageId": 89
//        }""".trimIndent()
//
//    wsClient.send(jsonToSend)
//
//    await().untilAsserted {
//        assertEquals(523, patch.getFixtures()[uuidToModify]?.fid)
//    }
//
//    await().untilAsserted {
//        assertEquals("newName", patch.getFixtures()[uuidToModify]?.name)
//    }
//
//    val post = patch.getFixtures()[uuidToModify]!!
//
//    assertEquals(prev.fixtureType, post.fixtureType)
//    assertEquals(prev.dmxMode, post.dmxMode)
//    assertEquals(prev.address?.value, post.address?.value)
//    assertEquals(prev.universe, post.universe)
//}


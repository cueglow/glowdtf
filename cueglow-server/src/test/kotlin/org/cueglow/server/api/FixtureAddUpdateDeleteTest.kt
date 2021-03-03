package org.cueglow.server.api

import com.github.kittinunf.fuel.core.ResponseResultOf
import com.github.michaelbull.result.unwrap
import org.awaitility.Awaitility.*
import org.cueglow.server.CueGlowServer
import org.cueglow.server.WsClient
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.patch.Patch
import org.cueglow.server.patch.PatchFixture
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
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
    val message = parseGlowMessage(received ?: "")

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
}

fun addFixtureInvalidDmxModeTest(wsClient: WsClient, patch: Patch) {
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
}
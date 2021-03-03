package org.cueglow.server.api

import com.github.kittinunf.fuel.core.ResponseResultOf
import com.github.michaelbull.result.unwrap
import org.awaitility.Awaitility.*
import org.cueglow.server.CueGlowServer
import org.cueglow.server.WsClient
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.patch.Patch
import org.cueglow.server.patch.PatchFixture
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

fun addFixtureTest(wsClient: WsClient, patch: Patch) {
    val jsonToSend =
            """{
                "event": "addFixtures",
                "data": {
                    "quantity": 1,
                    "fixture": {
                        "fid": 1,
                        "name": "exampleFixture",
                        "fixtureTypeId": "7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE",
                        "dmxMode": "mode1",
                        "universe": 1,
                        "address": 1
                    }
                },
                "messageId": 42
            }""".trimIndent()

    wsClient.send(jsonToSend)

    // TODO wait for response

    // TODO assert on response

    val expectedPatchFixture = PatchFixture(
        1,
        "exampleFixture",
        UUID.fromString("7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE"),
        "mode1",
        ArtNetAddress.tryFrom(1).unwrap(),
        DmxAddress.tryFrom(1).unwrap(),
    )

    await().untilAsserted{
        assertEquals(1, patch.getFixtures().size)
    }

    assertEquals(expectedPatchFixture, patch.getFixtures().asSequence().first())
}
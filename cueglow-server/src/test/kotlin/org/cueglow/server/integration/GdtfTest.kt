package org.cueglow.server.integration

import com.github.kittinunf.fuel.core.ResponseResultOf
import org.awaitility.Awaitility.await
import org.cueglow.server.json.fromJsonString
import org.cueglow.server.objects.messages.GlowData
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.patch.Patch
import org.junit.jupiter.api.Assertions.*
import java.util.*

// These test functions are executed in ApiIntegrationTest.kt

fun gdtfUploadTest(uploadGdtfFile: (String) -> ResponseResultOf<String>, patch: Patch) {
    // HTTP Request
    val (_, response, result) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf")

    // evaluate response
    assertEquals(200, response.statusCode)

    val responseJSON = result.component1() ?: ""
    val jsonMessage = GlowMessage.fromJsonString(responseJSON)
    assertEquals(GlowEvent.FIXTURE_TYPE_ADDED, jsonMessage.event)

    val expectedUUID = UUID.fromString("7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE")
    val returnedUUID = (jsonMessage.data as GlowData.FixtureTypeAdded).fixtureTypeId
    assertEquals(expectedUUID, returnedUUID)

    // check that fixture is added to Patch
    assertEquals(1, patch.getFixtureTypes().size)
    assertEquals("Robin Esprite", patch.getFixtureTypes()[expectedUUID]?.name)

    // TODO check that streamUpdate is delivered (once streams are working)

    // TODO check error response when uploading fixture
}

fun deleteInvalidFixtureTypesTest(wsClient: WsClient, patch: Patch) {
    val uuid1 = UUID.fromString("049bbf91-25f4-495f-ae30-9289adb8c2cf")
    val uuid2 = UUID.fromString("37cd9136-58bf-423c-b373-07651940807d")

    // delete Fixture again via WebSocket
    val deleteJSONMsg =
        """
        {
            "event": "deleteFixtureTypes",
            "data": {
            "fixtureTypeIds": ["$uuid1", "$uuid2"]
        },
            "messageId": 42
        }
        """

    wsClient.send(deleteJSONMsg)

    val msg1 = GlowMessage.fromJsonString(wsClient.receiveOneMessageBlocking())
    val msg2 = GlowMessage.fromJsonString(wsClient.receiveOneMessageBlocking())

    // still one fixture type in patch
    assertEquals(1, patch.getFixtureTypes().size)

    assertEquals(GlowEvent.ERROR, msg1.event)
    assertEquals("UnpatchedFixtureTypeIdError", (msg1.data as GlowData.Error).errorName)
    assertTrue((msg1.data as GlowData.Error).errorDescription.contains(uuid1.toString()))

    assertEquals(GlowEvent.ERROR, msg2.event)
    assertEquals("UnpatchedFixtureTypeIdError", (msg2.data as GlowData.Error).errorName)
    assertTrue((msg2.data as GlowData.Error).errorDescription.contains(uuid2.toString()))
}

fun gdtfDeleteTest(wsClient: WsClient, patch: Patch) {
    // delete Fixture again via WebSocket
    val deleteJSONMsg =
        """
        {
            "event": "deleteFixtureTypes",
            "data": {
            "fixtureTypeIds": ["7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE"]
        },
            "messageId": 42
        }
        """

    wsClient.send(deleteJSONMsg)

    await().untilAsserted{
        assertEquals(0, patch.getFixtureTypes().size)
    }
    assertEquals(0, patch.getFixtures().size)
}

fun invalidGdtfUploadTest(uploadGdtfFile: (String) -> ResponseResultOf<String>, patch: Patch) {
    val (_, response, _) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf.broken")

    assertEquals(500, response.statusCode)
    assertTrue(response.body().asString("text/plain").contains(
        "Duplicate unique value [PanTilt] declared for identity constraint"
    ))

    // no fixtureType should be added
    assertEquals(0, patch.getFixtureTypes().size)

    // TODO check error response once error handling is more mature
}

fun noFilePartInGdtfUploadErrors(uploadGdtfFile: (String, String) -> ResponseResultOf<String>) {
    val (_, response, _) = uploadGdtfFile(
        "Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf",
        "strangePartName"
    )

    assertEquals(400, response.statusCode)

    val responseJSON = response.body().asString("text/plain")
    val jsonMessage = GlowMessage.fromJsonString(responseJSON)
    val data = jsonMessage.data as GlowData.Error
    assertEquals("MissingFilePartError", data.errorName)
    assertNotEquals("", data.errorDescription)
}

fun noDescriptionXmlUploadError(uploadGdtfFile: (String) -> ResponseResultOf<String>) {
    val (_, response, _) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf.noDescriptionXml")

    assertEquals(400, response.statusCode)

    val responseJSON = response.body().asString("text/plain")
    val jsonMessage = GlowMessage.fromJsonString(responseJSON)
    val data = jsonMessage.data as GlowData.Error
    assertEquals("MissingDescriptionXmlInGdtfError", data.errorName)
    assertNotEquals("", data.errorDescription)
}
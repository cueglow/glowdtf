package org.cueglow.server.gdtf

import com.github.kittinunf.fuel.core.ResponseResultOf
import org.awaitility.Awaitility.await
import org.cueglow.server.api.GlowDataError
import org.cueglow.server.api.GlowDataFixtureTypeAdded
import org.cueglow.server.api.GlowEvent
import org.cueglow.server.api.parseGlowMessage
import org.cueglow.server.patch.Patch
import org.java_websocket.client.WebSocketClient
import org.junit.jupiter.api.Assertions
import java.util.*

// These test functions are executed in ApiIntegrationTest.kt

fun gdtfUploadTest(uploadGdtfFile: (String) -> ResponseResultOf<String>, patch: Patch) {
    // HTTP Request
    val (_, response, result) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf")

    // evaluate response
    Assertions.assertEquals(200, response.statusCode)

    val responseJSON = result.component1() ?: ""
    val glowMessage = parseGlowMessage(responseJSON)
    Assertions.assertEquals(GlowEvent.FIXTURE_TYPE_ADDED, glowMessage.event)

    val expectedUUID = UUID.fromString("7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE")
    val returnedUUID = (glowMessage.data as GlowDataFixtureTypeAdded).fixtureTypeId
    Assertions.assertEquals(expectedUUID, returnedUUID)

    // check that fixture is added to Patch
    Assertions.assertEquals(1, patch.getFixtureTypes().size)
    Assertions.assertEquals("Robin Esprite", patch.getFixtureTypes()[expectedUUID]?.name)

    // TODO check that streamUpdate is delivered (once streams are working)

    // TODO check error response when uploading fixture
}

fun gdtfDeleteTest(wsClient: WebSocketClient, patch: Patch) {
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
        Assertions.assertEquals(0, patch.getFixtureTypes().size)
    }

    // TODO check error response when deleting fixture
}

fun invalidGdtfUploadTest(uploadGdtfFile: (String) -> ResponseResultOf<String>, patch: Patch) {
    val (_, response, _) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf.broken")

    Assertions.assertEquals(500, response.statusCode)
    Assertions.assertTrue(response.body().asString("text/plain").contains(
        "Duplicate unique value [PanTilt] declared for identity constraint"
    ))

    // no fixtureType should be added
    Assertions.assertEquals(0, patch.getFixtureTypes().size)

    // TODO check error response once error handling is more mature
}

fun noFilePartInGdtfUploadErrors(uploadGdtfFile: (String, String) -> ResponseResultOf<String>, patch: Patch) {
    val (_, response, _) = uploadGdtfFile(
        "Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf",
        "strangePartName"
    )

    Assertions.assertEquals(400, response.statusCode)

    val responseJSON = response.body().asString("text/plain")
    val glowMessage = parseGlowMessage(responseJSON)
    val data = glowMessage.data as GlowDataError
    Assertions.assertEquals("MissingFilePartError", data.errorName)
    Assertions.assertNotEquals("", data.errorDescription)
}

fun noDescriptionXmlUploadError(uploadGdtfFile: (String) -> ResponseResultOf<String>, patch: Patch) {
    val (_, response, _) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf.noDescriptionXml")

    Assertions.assertEquals(400, response.statusCode)

    val responseJSON = response.body().asString("text/plain")
    val glowMessage = parseGlowMessage(responseJSON)
    val data = glowMessage.data as GlowDataError
    Assertions.assertEquals("MissingDescriptionXmlInGdtfError", data.errorName)
    Assertions.assertNotEquals("", data.errorDescription)
}
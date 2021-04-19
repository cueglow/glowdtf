package org.cueglow.server.integration

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.ResponseResultOf
import org.awaitility.Awaitility.await
import org.cueglow.server.json.fromJsonString
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.test_utilities.ClientAndServerTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class GdtfIntegrationTest: ClientAndServerTest() {

    private fun uploadGdtfFile(filename: String, partname: String = "file"): ResponseResultOf<String> {
        val exampleGdtfFile =
            File(javaClass.classLoader.getResource(filename)?.file ?: throw Error("can't get resource"))
        return Fuel.upload("http://localhost:7000/api/fixturetype")
            .add(
                FileDataPart(
                    exampleGdtfFile, name = partname, filename = filename
                )
            )
            .responseString()
    }

    @Test
    fun gdtfUploadTest() {
        // HTTP Request
        val (_, response, result) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf")

        // evaluate response
        assertEquals(200, response.statusCode)

        val responseJSON = result.component1() ?: ""
        val jsonMessage = GlowMessage.fromJsonString(responseJSON)
        assertEquals(GlowEvent.FIXTURE_TYPE_ADDED, jsonMessage.event)

        val expectedUUID = UUID.fromString("7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE")
        val returnedUUID = (jsonMessage as GlowMessage.FixtureTypeAdded).data
        assertEquals(expectedUUID, returnedUUID)

        // check that fixture is added to Patch
        assertEquals(1, patch.getFixtureTypes().size)
        assertEquals("Robin Esprite", patch.getFixtureTypes()[expectedUUID]?.name)

        // TODO check that streamUpdate is delivered (once streams are working)
    }

    @Test
    fun removeInvalidFixtureTypesTest() {
        setupExampleFixture()

        val uuid1 = UUID.fromString("049bbf91-25f4-495f-ae30-9289adb8c2cf")
        val uuid2 = UUID.fromString("37cd9136-58bf-423c-b373-07651940807d")

        val deleteJSONMsg =
            """
        {
            "event": "removeFixtureTypes",
            "data": ["$uuid1", "$uuid2"],
            "messageId": 42
        }
        """

        wsClient.send(deleteJSONMsg)

        val msg1 = GlowMessage.fromJsonString(wsClient.receiveOneMessageBlocking())
        val msg2 = GlowMessage.fromJsonString(wsClient.receiveOneMessageBlocking())

        // still one fixture type in patch
        assertEquals(1, patch.getFixtureTypes().size)

        assertEquals(GlowEvent.ERROR, msg1.event)
        assertEquals("UnpatchedFixtureTypeIdError", (msg1 as GlowMessage.Error).data.name)
        assertTrue(msg1.data.description.contains(uuid1.toString()))

        assertEquals(GlowEvent.ERROR, msg2.event)
        assertEquals("UnpatchedFixtureTypeIdError", (msg2 as GlowMessage.Error).data.name)
        assertTrue(msg2.data.description.contains(uuid2.toString()))
    }

    @Test
    fun removeFixtureTypeTest() {
        setupExampleFixture()

        val deleteJSONMsg =
            """
        {
            "event": "removeFixtureTypes",
            "data": ["7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE"],
            "messageId": 42
        }
        """

        wsClient.send(deleteJSONMsg)

        await().untilAsserted {
            assertEquals(0, patch.getFixtureTypes().size)
        }
        assertEquals(0, patch.getFixtures().size)
    }

    @Test
    fun invalidGdtfUploadTest() {
        val (_, response, _) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf.broken")

        assertEquals(400, response.statusCode)
        assertTrue(
            response.body().asString("text/plain").contains(
                "Duplicate unique value [PanTilt] declared for identity constraint"
            )
        )

        // no fixtureType should be added
        assertEquals(0, patch.getFixtureTypes().size)

        // TODO check error response once error handling is more mature
    }

    @Test
    fun noFilePartInGdtfUploadErrors() {
        val (_, response, _) = uploadGdtfFile(
            "Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf",
            "strangePartName"
        )

        assertEquals(400, response.statusCode)

        val responseJSON = response.body().asString("text/plain")
        val jsonMessage = GlowMessage.fromJsonString(responseJSON)
        val data = (jsonMessage as GlowMessage.Error).data
        assertEquals("MissingFilePartError", data.name)
        assertNotEquals("", data.description)
    }

    @Test
    fun noDescriptionXmlUploadError() {
        val (_, response, _) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf.noDescriptionXml")

        assertEquals(400, response.statusCode)

        val responseJSON = response.body().asString("text/plain")
        val jsonMessage = GlowMessage.fromJsonString(responseJSON)
        val data = (jsonMessage as GlowMessage.Error).data
        assertEquals("MissingDescriptionXmlInGdtfError", data.name)
        assertNotEquals("", data.description)
    }

    @Test
    fun gdtfWithChannelClash() {
        val (_, response, _) = uploadGdtfFile("ChannelLayoutTest/Test@Channel_Layout_Test@v1_first_try.channel_clash.gdtf")

        assertEquals(400, response.statusCode)

        val responseJSON = response.body().asString("text/plain")
        println("Error returned by server: ")
        println(responseJSON)
        val jsonMessage = GlowMessage.fromJsonString(responseJSON)
        val data = (jsonMessage as GlowMessage.Error).data
        assertEquals("InvalidGdtfError", data.name)
        // Error Message should contain the names of the colliding channels
        assertTrue(data.description.contains("Element 1 -> AbstractElement_Pan"))
        assertTrue(data.description.contains("Main_Dimmer"))
        // Error Message should contain the Mode Name
        assertTrue(data.description.contains("Mode 1"))
    }

    @Test
    fun gdtfWithMissingBreakInGeometryReference() {
        val (_, response, _) = uploadGdtfFile("ChannelLayoutTest/Test@Channel_Layout_Test@v1_first_try.missing_break_in_geometry_reference.gdtf")

        assertEquals(400, response.statusCode)

        val responseJSON = response.body().asString("text/plain")
        println("Error returned by server: ")
        println(responseJSON)
        val jsonMessage = GlowMessage.fromJsonString(responseJSON)
        val data = (jsonMessage as GlowMessage.Error).data
        assertEquals("InvalidGdtfError", data.name)
        // Error Message should contain the name of the faulty geometry reference
        assertTrue(data.description.contains("Element 2"))
    }
}
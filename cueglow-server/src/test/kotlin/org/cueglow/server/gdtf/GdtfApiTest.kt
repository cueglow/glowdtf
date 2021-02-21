package org.cueglow.server.gdtf

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.ResponseResultOf
import org.cueglow.server.CueGlowServer
import org.cueglow.server.api.GlowDataFixtureTypeAdded
import org.cueglow.server.api.GlowEvent
import org.cueglow.server.api.parseGlowMessage
import org.cueglow.server.patch.Patch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.net.URI
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GdtfApiTest {
    @BeforeAll
    fun initiateEnvironmentForGdtfTest() {
        // Patch should be clean
        assertEquals(0, Patch.getFixtureTypes().size)

        // start Server
        CueGlowServer()
    }

    private fun uploadGdtfFile(filename: String): ResponseResultOf<String> {
        val exampleGdtfFile= File(javaClass.classLoader.getResource(filename)?.file ?: throw Error("can't get resource"))
        return Fuel.upload("http://localhost:7000/api/fixturetype")
            .add(FileDataPart(
                exampleGdtfFile, name="file", filename=filename
            ))
            .responseString()
    }

    @Test
    fun testGdtfUpload() {
        // HTTP Request
        val (_, response, result) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf")

        // evaluate response
        assertEquals(200, response.statusCode)

        val responseJSON = result.component1() ?: ""
        // println(responseJSON)
        val glowMessage = parseGlowMessage(responseJSON)
        assertEquals(GlowEvent.FIXTURE_TYPE_ADDED, glowMessage.event)

        val expectedUUID = UUID.fromString("7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE")
        val returnedUUID = (glowMessage.data as GlowDataFixtureTypeAdded).fixtureTypeId
        assertEquals(expectedUUID, returnedUUID)

        // check that fixture is added to Patch
        assertEquals(1, Patch.getFixtureTypes().size)
        assertEquals("Robin Esprite", Patch.getFixtureTypes()[expectedUUID]?.name)

        // TODO check that streamUpdate is delivered (once streams are working)

        // TODO check error response when uploading fixture
    }

    @Test
    fun deleteFixtureTypeViaWebSocket() {
        // TODO check error response when deleting fixture
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

        val wsClient = WsClient(URI("ws://localhost:7000/ws"))
        wsClient.connectBlocking()
        wsClient.send(deleteJSONMsg)
        wsClient.closeBlocking()
        assertEquals(0, Patch.getFixtureTypes().size)
    }

    @Test
    fun invalidGdtfUpload() {
        val (_, response, _) = uploadGdtfFile("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf.broken")

        assertEquals(500, response.statusCode)

        // no fixtureType should be added
        assertEquals(0, Patch.getFixtureTypes().size)

        // TODO check error response once error handling is more mature
    }
}

// Barebones WebSocket client for sending test messages
class WsClient(uri: URI): WebSocketClient(uri) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        println("WsClient opened")
    }

    override fun onMessage(message: String?) {
        println("WsClient received $message")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("WsClient closed")
    }

    override fun onError(ex: Exception?) {
        println("WsClient errored" + ex.toString())
    }

}
package org.cueglow.server.gdtf

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import org.cueglow.server.CueGlowServer
import org.cueglow.server.objects.*
import org.cueglow.server.patch.Patch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.io.StringReader
import java.net.URI
import java.util.*

internal class GdtfUploadTest {
    @Test
    fun testGdtfUpload() {
        // Patch should be clean
        assertEquals(0, Patch.getFixtureTypes().size)

        // start Server
        CueGlowServer()

        // client request
        val exampleGdtfFileName = "Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf"
        val exampleGdtfFile= File(javaClass.classLoader.getResource(exampleGdtfFileName)?.file ?: throw Error("can't get resource"))

        // HTTP Request
        val (_, response, result) = Fuel.upload("http://localhost:7000/api/fixturetype")
            .add(FileDataPart(
                exampleGdtfFile, name="file", filename=exampleGdtfFileName
            ))
            .responseString()

        // evaluate response
        assertEquals(200, response.statusCode)

        val responseJSON = result.component1() ?: ""
        // println(responseJSON)
        // TODO replace with factored out parsing from WebSocketHandler
        val glowMessage = Klaxon()
            .fieldConverter(KlaxonGlowEvent::class, GlowEvent.glowEventConverter)
            .converter(UUIDConverter)
            .parse<GlowMessage>(StringReader(responseJSON)) ?: throw Error("returned Message parses to null")
        assertEquals(GlowEvent.FIXTURE_TYPE_ADDED, glowMessage.event)

        val expectedUUID = UUID.fromString("7FB33577-09C9-4BF0-BE3B-EF0DC3BEF4BE")
        val returnedUUID = (glowMessage.data as GlowDataFixtureTypeAdded).fixtureTypeId
        assertEquals(expectedUUID, returnedUUID)

        // check that fixture is added to Patch
        assertEquals(1, Patch.getFixtureTypes().size)
        assertEquals("Robin Esprite", Patch.getFixtureTypes()[expectedUUID]?.name)

        // TODO check that streamUpdate is delivered (once streams are working)

        // TODO check error response upon upload/delete fixture

        // Delete Fixture Type via WebSocket API
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
}

// Barebones WebSocket client for sending test messages
class WsClient(uri: URI): WebSocketClient(uri) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        println("WsClient opened")
    }

    override fun onMessage(message: String?) {
        println("WsClient received" + message)
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("WsClient closed")
    }

    override fun onError(ex: Exception?) {
        println("WsClient errored" + ex.toString())
    }

}
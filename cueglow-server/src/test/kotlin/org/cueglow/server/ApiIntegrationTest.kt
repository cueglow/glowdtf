package org.cueglow.server

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.ResponseResultOf
import org.awaitility.Awaitility
import org.awaitility.pollinterval.FibonacciPollInterval.fibonacci
import org.cueglow.server.api.addFixtureTest
import org.cueglow.server.gdtf.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import java.io.File
import java.net.URI
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)
internal class ApiIntegrationTest {
    //-----------------------------------------------------
    // Initialization
    //-----------------------------------------------------
    val server = CueGlowServer()

    val patch = server.state.patch

    val wsClient = WsClient(URI("ws://localhost:7000/ws"))

    init {
        wsClient.connectBlocking()

        Awaitility.setDefaultPollInterval(fibonacci())
        Awaitility.setDefaultTimeout(Duration.ofSeconds(2))
    }

    //-----------------------------------------------------
    // Helpers
    //-----------------------------------------------------
    private fun uploadGdtfFile(filename: String, partname: String = "file"): ResponseResultOf<String> {
        val exampleGdtfFile= File(javaClass.classLoader.getResource(filename)?.file ?: throw Error("can't get resource"))
        return Fuel.upload("http://localhost:7000/api/fixturetype")
            .add(
                FileDataPart(
                exampleGdtfFile, name=partname, filename=filename
            )
            )
            .responseString()
    }

    //----------------------------
    // Test Series 1
    //----------------------------

    @Test
    @Order(0)
    fun uploadGdtfFixtureType() = gdtfUploadTest(::uploadGdtfFile, patch)

    @Test
    @Order(50)
    fun addFixture() = addFixtureTest(wsClient, patch)

    @Test
    @Order(100)
    fun deleteGdtfFixtureType() = gdtfDeleteTest(wsClient, patch)

    //-----------------------------------------------------
    // Invalid Tests (don't change state)
    //-----------------------------------------------------

    @Test
    @Order(200)
    fun invalidGdtfFileUpload() = invalidGdtfUploadTest(::uploadGdtfFile, patch)

    @Test
    @Order(300)
    fun noFilePartInGdtfUpload() = noFilePartInGdtfUploadErrors(::uploadGdtfFile, patch)

    @Test
    @Order(400)
    fun gdtfWithoutDescriptionXml() = noDescriptionXmlUploadError(::uploadGdtfFile, patch)

    //-----------------------------------------------------
    // Teardown
    //-----------------------------------------------------

    @AfterAll
    fun teardown() {
        wsClient.closeBlocking()
        server.stop()
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
        println("WsClient closed. Code $code. Reason: $reason")
    }

    override fun onError(ex: Exception?) {
        println("WsClient errored" + ex.toString())
    }
}
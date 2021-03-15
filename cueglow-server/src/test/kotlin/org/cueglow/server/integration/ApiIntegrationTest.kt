package org.cueglow.server.integration

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.ResponseResultOf
import com.github.michaelbull.result.unwrap
import org.apache.logging.log4j.kotlin.Logging
import org.awaitility.Awaitility
import org.awaitility.Awaitility.await
import org.awaitility.pollinterval.FibonacciPollInterval.fibonacci
import org.cueglow.server.CueGlowServer
import org.cueglow.server.gdtf.FixtureType
import org.cueglow.server.gdtf.parseGdtf
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import java.io.File
import java.io.InputStream
import java.net.URI
import java.time.Duration

/**
 * Tests the WebSocket and RESt API in one stateful sweep.
 *
 * Provides the environment as well as ordering and listing the run tests.
 * However the individual tests are placed in other files as top-level functions.
 *
 * To wait for the right response, we use Awaitility.
 *
 * When debugging the tests, I'd recommended to look at the whole standard output, not just the one from the failing
 * test. The individual tests tend to break each other.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)
internal class ApiIntegrationTest {
    //-----------------------------------------------------
    // Initialization
    //-----------------------------------------------------
    private val server = CueGlowServer()

    private val patch = server.state.patch

    private val wsClient = WsClient(URI("ws://localhost:7000/ws"))

    init {
        wsClient.connectBlocking()

        Awaitility.setDefaultPollInterval(fibonacci())
        Awaitility.setDefaultTimeout(Duration.ofSeconds(2))
    }

    private val exampleGdtfFileName = "Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf"
    private val inputStream: InputStream = javaClass.classLoader.getResourceAsStream(exampleGdtfFileName) ?:
    throw Error("inputStream is Null")
    private val parsedGdtf = parseGdtf(inputStream).unwrap()
    private val exampleFixtureType = FixtureType(parsedGdtf)

    //----------------------------
    // Test Series 1
    //----------------------------

    @Test
    @Order(0)
    fun uploadGdtfFixtureType() = gdtfUploadTest(::uploadGdtfFile, patch)

    // Add Fixture

    @Test
    @Order(50)
    fun addFixture() = addFixtureTest(wsClient, patch, exampleFixtureType)

    @Test
    @Order(75)
    fun addFixtureInvalidFixtureTypeId() = addFixtureInvalidFixtureTypeIdTest(wsClient, patch)

    @Test
    @Order(87)
    fun addFixtureInvalidDmxMode() = addFixtureInvalidDmxModeTest(wsClient, patch)

    // Update Fixture
    @Test
    @Order(90)
    fun updateUnknownFixture() = updateUnknownFixtureTest(wsClient)

    @Test
    @Order(93)
    fun updateAddress() = updateAddressTest(wsClient, patch)

    @Test
    @Order(96)
    fun updateUniverse() = updateUniverseTest(wsClient, patch)

    @Test
    @Order(97)
    fun updateNameAndFid() = updateNameAndFidTest(wsClient, patch)

    @Test
    @Order(98)
    fun deleteInvalidFixture() = deleteInvalidFixtureTest(wsClient, patch)

    @Test
    @Order(99)
    fun deleteFixture() = deleteFixtureTest(wsClient, patch)

    @Test
    @Order(100)
    fun deleteInvalidFixtureTypes() = deleteInvalidFixtureTypesTest(wsClient, patch)

    @Test
    @Order(150)
    fun deleteGdtfFixtureType() = gdtfDeleteTest(wsClient, patch)

    //-----------------------------------------------------
    // Invalid GDTF Upload Tests (don't change state)
    //-----------------------------------------------------

    @Test
    @Order(200)
    fun invalidGdtfFileUpload() = invalidGdtfUploadTest(::uploadGdtfFile, patch)

    @Test
    @Order(300)
    fun noFilePartInGdtfUpload() = noFilePartInGdtfUploadErrors(::uploadGdtfFile)

    @Test
    @Order(400)
    fun gdtfWithoutDescriptionXml() = noDescriptionXmlUploadError(::uploadGdtfFile)

    //-----------------------------------------------------
    // Teardown
    //-----------------------------------------------------

    @AfterAll
    fun teardown() {
        wsClient.closeBlocking()
        server.stop()
    }

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
}

// Barebones WebSocket client for sending test messages
class WsClient(uri: URI): WebSocketClient(uri), Logging {
    private val receivedMessages = ArrayDeque<String>()

    override fun onOpen(handshakedata: ServerHandshake?) {
        logger.info("WsClient opened")
    }

    override fun onMessage(message: String?) {
//        val pretty: String = (Parser.default().parse(StringBuilder(message)) as JsonObject).toJsonString(true)
        logger.info("WsClient received $message")
        receivedMessages.addLast(message ?: "")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        logger.info("WsClient closed. Code $code. Reason: $reason")
    }

    override fun onError(ex: Exception?) {
        logger.info("WsClient errored" + ex.toString())
    }

    fun receiveOneMessageBlocking(): String {
        await().until { receivedMessages.isNotEmpty() }
        return receivedMessages.removeFirst()
    }
}
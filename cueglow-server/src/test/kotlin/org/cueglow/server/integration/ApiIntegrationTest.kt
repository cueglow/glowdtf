package org.cueglow.server.integration

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.ResponseResultOf
import com.github.michaelbull.result.Ok
import org.cueglow.server.CueGlowServer
import org.cueglow.server.patch.Patch
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import java.io.File
import java.net.URI

/**
 * Tests the WebSocket and REST API
 *
 * Provides setup/teardown of the server for each test and utility functions.
 *
 * Individual tests are written as free functions in other files and called in @Test-methods.
 *
 * To wait for responses/state-changes we use Awaitility.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation::class)
internal class ApiIntegrationTest {
    //-----------------------------------------------------
    // Initialization
    //-----------------------------------------------------
    private lateinit var server: CueGlowServer

    private lateinit var patch: Patch

    private lateinit var wsClient: WsClient

    //-----------------------------------------------------
    // Helpers
    //-----------------------------------------------------

    private val exampleFixtureType = ExampleFixtureType.esprite

    private val examplePatchFixture = ExampleFixtureType.esprite_fixture

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

    private fun setupExampleFixtureType() {
        assertTrue(patch.addFixtureTypes(listOf(exampleFixtureType)) is Ok)
    }

    private fun setupExampleFixture() {
        setupExampleFixtureType()
        assert(patch.addFixtures(listOf(examplePatchFixture)) is Ok)
    }

    //-----------------------------------------------------
    // Setup/Teardown for Each Test
    //-----------------------------------------------------

    @BeforeEach
    fun setup() {
        server = CueGlowServer()
        patch = server.state.patch
        wsClient = WsClient(URI("ws://localhost:7000/ws"))
        wsClient.connectBlocking()
    }

    @AfterEach
    fun teardown() {
        wsClient.closeBlocking()
        server.stop()
    }

    //-----------------------------------------------------
    // Individual Tests
    //-----------------------------------------------------

    // GDTF / Fixture Type Tests

    @Test
    fun uploadGdtfFixtureType() = gdtfUploadTest(::uploadGdtfFile, patch)

    @Test
    fun invalidGdtfFileUpload() = invalidGdtfUploadTest(::uploadGdtfFile, patch)

    @Test
    fun noFilePartInGdtfUpload() = noFilePartInGdtfUploadErrors(::uploadGdtfFile)

    @Test
    fun gdtfWithoutDescriptionXml() = noDescriptionXmlUploadError(::uploadGdtfFile)

    @Test
    fun gdtfWithChannelClash() = gdtfWithChannelClash(::uploadGdtfFile)

    @Test
    fun gdtfWithMissingBreakInGeometryReference() = gdtfWithMissingBreakInGeometryReference(::uploadGdtfFile)

    @Test
    fun removeInvalidFixtureTypes() {
        setupExampleFixture()
        removeInvalidFixtureTypesTest(wsClient, patch)
    }

    @Test
    fun removeFixtureType() {
        setupExampleFixture()
        removeFixtureTypeTest(wsClient, patch)
    }

    // Add Fixture Tests

    @Test
    fun addFixture() {
        setupExampleFixtureType()
        addFixtureTest(wsClient, patch, examplePatchFixture)
    }

    @Test
    fun addFixtureDuplicateUuid() {
        setupExampleFixture()
        addFixtureDuplicateUuidTest(wsClient, patch)
    }

    @Test
    fun addFixtureInvalidFixtureTypeId() {
        setupExampleFixtureType()
        addFixtureInvalidFixtureTypeIdTest(wsClient, patch)
    }

    @Test
    fun addFixtureInvalidDmxMode() {
        setupExampleFixtureType()
        addFixtureInvalidDmxModeTest(wsClient, patch)
    }

    // Update Fixture Tests

    @Test
    fun updateUnknownFixture() {
        setupExampleFixture()
        updateUnknownFixtureTest(wsClient)
    }

    @Test
    fun updateAddress() {
        setupExampleFixture()
        updateAddressTest(wsClient, patch)
    }

    @Test
    fun updateUniverse() {
        setupExampleFixture()
        updateUniverseTest(wsClient, patch)
    }

    @Test
    fun updateNameAndFid() {
        setupExampleFixture()
        updateNameAndFidTest(wsClient, patch)
    }

    // Remove Fixture Tests

    @Test
    fun removeInvalidFixture() {
        setupExampleFixture()
        removeInvalidFixtureTest(wsClient, patch)
    }

    @Test
    fun removeFixture() {
        setupExampleFixture()
        removeFixtureTest(wsClient, patch)
    }
}
package org.cueglow.server.test_utilities

import com.github.michaelbull.result.Ok
import org.cueglow.server.CueGlowServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import java.net.URI

/**
 * Provides Setup/Teardown of the Server and Client for each test and utilities.
 *
 * Classes for such "integration" tests should inherit from this class.
 */
open class ClientAndServerTest {
    val server = CueGlowServer()
    val patch = server.state.patch
    val wsClient = WsClient(URI("ws://localhost:7000/ws"))

    init {
        wsClient.connectBlocking()
    }

    val exampleFixtureType = ExampleFixtureType.esprite

    val examplePatchFixture = ExampleFixtureType.esprite_fixture

    fun setupExampleFixtureType() {
        Assertions.assertTrue(patch.addFixtureTypes(listOf(exampleFixtureType)) is Ok)
    }

    fun setupExampleFixture() {
        setupExampleFixtureType()
        assert(patch.addFixtures(listOf(examplePatchFixture)) is Ok)
    }

    @AfterEach
    fun teardown() {
        wsClient.closeBlocking()
        server.stop()
    }
}
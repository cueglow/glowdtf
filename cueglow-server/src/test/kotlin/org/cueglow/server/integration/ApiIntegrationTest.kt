package org.cueglow.server.integration

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.ResponseResultOf
import org.cueglow.server.test_utilities.ClientAndServerTest
import org.junit.jupiter.api.Test
import java.io.File

/**
 * Tests the WebSocket and REST API
 *
 * Provides setup/teardown of the server for each test and utility functions.
 *
 * Individual tests are written as free functions in other files and called in @Test-methods.
 *
 * To wait for responses/state-changes we use Awaitility.
 */
internal class ApiIntegrationTest: ClientAndServerTest() {

    //-----------------------------------------------------
    // Individual Tests
    //-----------------------------------------------------

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
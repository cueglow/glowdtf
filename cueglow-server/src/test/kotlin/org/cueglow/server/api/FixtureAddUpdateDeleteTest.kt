package org.cueglow.server.api

import org.cueglow.server.CueGlowServer
import org.junit.jupiter.api.Test

internal class FixtureAddUpdateDeleteTest {
    @Test
    fun addFixture() {
        val server = CueGlowServer()
        server.stop()
    }
}
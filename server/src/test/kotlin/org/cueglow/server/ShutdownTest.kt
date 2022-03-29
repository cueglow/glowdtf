package org.cueglow.server

import org.awaitility.Awaitility.await
import org.cueglow.server.test_utilities.ClientAndServerTest
import org.java_websocket.enums.ReadyState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ShutdownTest: ClientAndServerTest() {
    @Test
    fun shutdownTest() {
        assertEquals(ReadyState.OPEN, wsClient.readyState)
        wsClient.send(
            """
            {
                "event": "shutdown"
            }
        """.trimIndent()
        )
        await().untilAsserted {
            assertEquals(ReadyState.CLOSED, wsClient.readyState)
        }
    }
}
package org.cueglow.server.json

import com.google.common.truth.Truth.assertThat
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.test_utilities.ClientAndServerTest
import org.junit.jupiter.api.Test

class RigStateIntegrationTest: ClientAndServerTest() {
    @Test
    fun getAndUpdateRigState() {
        setupExampleFixture()
        wsClient.send("""
            {
                "event": "subscribe",
                "data": "rigState"
            }
        """.trimIndent())

        val initialRigState = wsClient.receiveOneMessageBlocking()
        val parsedIntialState = GlowMessage.fromJsonString(initialRigState) as GlowMessage.RigState
        assertThat(parsedIntialState.event).isEqualTo(GlowEvent.RIG_STATE)
        assertThat(parsedIntialState.data[0].chValues[0]).isEqualTo(32_768L) // Pan is 32_768 by default

        // set the first channel value
        wsClient.send("""
            {
                "event": "setChannel",
                "data": {
                    "fixtureInd": 0,
                    "chInd": 0,
                    "value": 0
                }
            }
        """.trimIndent())

        val nextRigState = wsClient.receiveOneMessageBlocking()
        val parsedNextState = GlowMessage.fromJsonString(nextRigState) as GlowMessage.RigState
        assertThat(parsedNextState.event).isEqualTo(GlowEvent.RIG_STATE)
        assertThat(parsedNextState.data[0].chValues[0]).isEqualTo(0L) // Pan is 32_768 by default
    }
}
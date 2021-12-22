package org.cueglow.server.json

import com.google.common.truth.Truth.assertThat
import com.karumi.kotlinsnapshot.matchWithSnapshot
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.test_utilities.ClientAndServerTest
import org.junit.jupiter.api.Test

class RigStateIntegrationTest : ClientAndServerTest() {
    @Test
    fun getAndUpdateRigState() {
        setupExampleFixture()
        wsClient.send(
            """
            {
                "event": "subscribe",
                "data": "rigState"
            }
        """.trimIndent()
        )

        val initialRigState = wsClient.receiveOneMessageBlocking()
        val parsedIntialState = GlowMessage.fromJsonString(initialRigState) as GlowMessage.RigState
        assertThat(parsedIntialState.event).isEqualTo(GlowEvent.RIG_STATE)
        assertThat(parsedIntialState.data.values.first().chValues[0]).isEqualTo(32_768L) // Pan is 32_768 by default

        // set the first channel value
        wsClient.send(
            """
            {
                "event": "setChannel",
                "data": {
                    "fixtureUuid": "${examplePatchFixture.uuid}",
                    "chInd": 0,
                    "value": 0
                }
            }
        """.trimIndent()
        )

        val nextRigState = wsClient.receiveOneMessageBlocking()
        nextRigState.matchWithSnapshot()
        val parsedNextState = GlowMessage.fromJsonString(nextRigState) as GlowMessage.RigState
        assertThat(parsedNextState.event).isEqualTo(GlowEvent.RIG_STATE)
        assertThat(parsedNextState.data.values.first().chValues[0]).isEqualTo(0L) // Pan is 32_768 by default
    }
}
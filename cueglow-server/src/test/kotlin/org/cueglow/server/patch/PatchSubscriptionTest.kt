package org.cueglow.server.patch

import org.cueglow.server.json.toJsonString
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.objects.messages.GlowPatch
import org.cueglow.server.test_utilities.ClientAndServerTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PatchSubscriptionTest: ClientAndServerTest() {
    val subscriptionMessage =
        """{
            "event": "subscribe",
            "data": "patch"
        }""".trimIndent()

    @Test
    fun patchSubscriptionLifecycle() {
        // subscribe
        wsClient.send(subscriptionMessage)

        // client should receive empty initial state
        val expectedInitialStateString = GlowMessage.PatchInitialState(GlowPatch(listOf(), listOf())).toJsonString()
        val initialStateString = wsClient.receiveOneMessageBlocking()
        assertEquals(expectedInitialStateString, initialStateString)

        // update should be delivered
        setupExampleFixtureType()
        val expectedUpdateString = GlowMessage.AddFixtureTypes(listOf(exampleFixtureType)).toJsonString()
        val updateString = wsClient.receiveOneMessageBlocking()
        assertEquals(expectedUpdateString, updateString)

        // unsubscribe
        wsClient.send("""{"event":"unsubscribe", "data": "patch"}""")

        // update for this change should not be delivered (checked by re-subscribing and asserting on response)
        wsClient.send("""{"event": "removeFixtureTypes", "data": ["${exampleFixtureType.fixtureTypeId}"]}""")

        // subscribe again
        wsClient.send(subscriptionMessage)

        // should receive empty state again (and not the update from above)
        val emptyStateString = wsClient.receiveOneMessageBlocking()
        assertEquals(expectedInitialStateString, emptyStateString)

        // no messages should be left in queue
        assertEquals(0, wsClient.receivedMessages.size)
    }
}
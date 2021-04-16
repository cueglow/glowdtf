package org.cueglow.server

import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

class OutEventQueueTest {
    @Test
    fun addingFixtureTypeCreatesOutEventInQueue() {
        val state = StateProvider()
        val exampleFixtureType = ExampleFixtureType.esprite
        state.patch.addFixtureTypes(listOf(exampleFixtureType))

        val expected = GlowMessage.AddFixtureTypes(listOf(exampleFixtureType))
        val actual = (state.outEventQueue.poll(2, TimeUnit.SECONDS) ?:
            throw Error("Nothing in Queue until timeout")) as GlowMessage.AddFixtureTypes

        assertEquals(expected.event, actual.event)
        assertEquals(1, actual.data.size)
        assertEquals(expected.data[0], actual.data[0])
        assertEquals(expected.messageId, actual.messageId)
    }
}
package org.cueglow.server

import com.github.michaelbull.result.Err
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

class OutEventQueueTest {
    @Test
    fun addingFixtureTypeCreatesOutEvent() {
        val state = StateProvider()
        val exampleFixtureType = ExampleFixtureType.esprite

        // add the exampleFixtureType twice
        // first time should work and create an OutEvent
        state.patch.addFixtureTypes(listOf(exampleFixtureType))
        // the second time should error and not create an OutEvent
        assertTrue(state.patch.addFixtureTypes(listOf(exampleFixtureType)) is Err)

        val expected = GlowMessage.AddFixtureTypes(listOf(exampleFixtureType))

        val actual = (state.outEventQueue.poll(2, TimeUnit.SECONDS) ?:
            throw Error("Nothing in Queue until timeout")) as GlowMessage.AddFixtureTypes

        assertEquals(expected.event, actual.event)
        assertEquals(1, actual.data.size)
        assertEquals(expected.data[0], actual.data[0])
        assertEquals(expected.messageId, actual.messageId)

        // when we added the fixture type the second time, it should not have created an OutEvent and so now the
        // Queue should be empty
        assertEquals(null, state.outEventQueue.peek())
    }

    @Test
    fun addingFixtureTypeTwiceOnlyReportsItOnce() {
        val state = StateProvider()
        val exampleFixtureType = ExampleFixtureType.esprite

        // add the exampleFixtureType twice
        // first time should work
        // the second time should error
        assertTrue(state.patch.addFixtureTypes(listOf(exampleFixtureType, exampleFixtureType)) is Err)

        val expected = GlowMessage.AddFixtureTypes(listOf(exampleFixtureType))

        val actual = (state.outEventQueue.poll(2, TimeUnit.SECONDS) ?:
            throw Error("Nothing in Queue until timeout")) as GlowMessage.AddFixtureTypes

        assertEquals(expected.event, actual.event)
        assertEquals(1, actual.data.size)
        assertEquals(expected.data[0], actual.data[0])
        assertEquals(expected.messageId, actual.messageId)

        // now the Queue should be empty
        assertEquals(null, state.outEventQueue.peek())
    }
}
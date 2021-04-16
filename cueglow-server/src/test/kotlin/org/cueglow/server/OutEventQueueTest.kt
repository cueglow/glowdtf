package org.cueglow.server

import com.github.michaelbull.result.Err
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

class OutEventQueueTest {
    val state = StateProvider()
    val patch = state.patch
    val exampleFixtureType = ExampleFixtureType.esprite

    private fun <T> BlockingQueue<T>.pollTimeout(): T = this.poll(1, TimeUnit.SECONDS) ?: throw Error("No Message in Queue until Timeout")

    @Test
    fun addingFixtureTypeCreatesOutEvent() {
        // add the exampleFixtureType twice
        // first time should work and create an OutEvent
        patch.addFixtureTypes(listOf(exampleFixtureType))
        // the second time should error and not create an OutEvent
        assertTrue(patch.addFixtureTypes(listOf(exampleFixtureType)) is Err)

        val expected = GlowMessage.AddFixtureTypes(listOf(exampleFixtureType))

        val actual = state.outEventQueue.pollTimeout() as GlowMessage.AddFixtureTypes

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
        // add the exampleFixtureType twice
        // first time should work
        // the second time should error
        assertTrue(patch.addFixtureTypes(listOf(exampleFixtureType, exampleFixtureType)) is Err)

        val expected = GlowMessage.AddFixtureTypes(listOf(exampleFixtureType))
        val actual = state.outEventQueue.pollTimeout() as GlowMessage.AddFixtureTypes

        assertEquals(expected.event, actual.event)
        assertEquals(1, actual.data.size)
        assertEquals(expected.data[0], actual.data[0])
        assertEquals(expected.messageId, actual.messageId)

        // now the Queue should be empty
        assertEquals(null, state.outEventQueue.peek())
    }

    @Test
    fun removingFixtureTypeCreatesOutEvent() {
        patch.addFixtureTypes(listOf(exampleFixtureType))

        assertTrue(state.outEventQueue.pollTimeout() is GlowMessage.AddFixtureTypes)

        patch.removeFixtureTypes(listOf(exampleFixtureType.fixtureTypeId))

        val received = state.outEventQueue.pollTimeout() as GlowMessage.RemoveFixtureTypes

        assertEquals(1, received.data.size)
        assertEquals(exampleFixtureType.fixtureTypeId, received.data[0])
        assertEquals(null, received.messageId)

        assertEquals(null, state.outEventQueue.peek())
    }
}
package org.cueglow.server

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.unwrap
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.patch.PatchFixtureUpdate
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

class OutEventQueueTest {
    private val state = StateProvider()
    private val patch = state.patch
    private val queue = state.outEventQueue
    private val exampleFixtureType = ExampleFixtureType.esprite
    private val examplePatchFixture = PatchFixture(
        UUID.fromString("91faaa61-624b-477a-a6c2-de00c717b3e6"),
        1,
        "exampleFixture",
        exampleFixtureType.fixtureTypeId,
        "mode1",
        ArtNetAddress.tryFrom(1).unwrap(),
        DmxAddress.tryFrom(1).unwrap(),
    )

    private fun <T> BlockingQueue<T>.pollTimeout(): T = this.poll(1, TimeUnit.SECONDS) ?: throw Error("No Message in Queue until Timeout")

    @Test
    fun addingFixtureTypeCreatesOutEvent() {
        // add the exampleFixtureType twice
        // first time should work and create an OutEvent
        patch.addFixtureTypes(listOf(exampleFixtureType))
        // the second time should error and not create an OutEvent
        assertTrue(patch.addFixtureTypes(listOf(exampleFixtureType)) is Err)

        val expected = GlowMessage.AddFixtureTypes(listOf(exampleFixtureType))

        val actual = queue.pollTimeout() as GlowMessage.AddFixtureTypes

        assertEquals(expected.event, actual.event)
        assertEquals(1, actual.data.size)
        assertEquals(expected.data[0], actual.data[0])
        assertEquals(expected.messageId, actual.messageId)

        // when we added the fixture type the second time, it should not have created an OutEvent and so now the
        // Queue should be empty
        assertEquals(null, queue.peek())
    }

    @Test
    fun addingFixtureTypeTwiceOnlyReportsItOnce() {
        // add the exampleFixtureType twice
        // first time should work
        // the second time should error
        assertTrue(patch.addFixtureTypes(listOf(exampleFixtureType, exampleFixtureType)) is Err)

        val expected = GlowMessage.AddFixtureTypes(listOf(exampleFixtureType))
        val actual = queue.pollTimeout() as GlowMessage.AddFixtureTypes

        assertEquals(expected.event, actual.event)
        assertEquals(1, actual.data.size)
        assertEquals(expected.data[0], actual.data[0])
        assertEquals(expected.messageId, actual.messageId)

        // now the Queue should be empty
        assertEquals(null, queue.peek())
    }

    @Test
    fun removingFixtureTypeCreatesOutEvent() {
        patch.addFixtureTypes(listOf(exampleFixtureType))

        assertTrue(queue.pollTimeout() is GlowMessage.AddFixtureTypes)

        patch.removeFixtureTypes(listOf(exampleFixtureType.fixtureTypeId))

        val received = queue.pollTimeout() as GlowMessage.RemoveFixtureTypes

        assertEquals(1, received.data.size)
        assertEquals(exampleFixtureType.fixtureTypeId, received.data[0])
        assertEquals(null, received.messageId)

        assertEquals(null, queue.peek())
    }

    @Test
    fun FixtureLifecycleCreatesProperOutEvents() {
        // add fixture type
        patch.addFixtureTypes(listOf(exampleFixtureType))
        assertTrue(queue.pollTimeout() is GlowMessage.AddFixtureTypes)

        // add fixture
        patch.addFixtures(listOf(examplePatchFixture))
        val addMessage = queue.pollTimeout() as GlowMessage.AddFixtures
        assertEquals(1, addMessage.data.size)
        assertTrue(examplePatchFixture.isSimilar(addMessage.data[0]))

        // update fixture
        val update = PatchFixtureUpdate(examplePatchFixture.uuid, name = "new name")
        patch.updateFixtures(listOf(update))
        val updateMessage = queue.pollTimeout() as GlowMessage.UpdateFixtures
        assertEquals(1, updateMessage.data.size)
        assertEquals(update, updateMessage.data[0])

        // remove fixture
        patch.removeFixtures(listOf(examplePatchFixture.uuid))
        val removeMessage = queue.pollTimeout() as GlowMessage.RemoveFixtures
        assertEquals(1, removeMessage.data.size)
        assertEquals(examplePatchFixture.uuid, removeMessage.data[0])

        // queue should be empty
        assertEquals(null, queue.peek())
    }

    @Test
    fun removingFixtureViaFixtureTypeSendsOutEvent() {
        patch.addFixtureTypes(listOf(exampleFixtureType))
        assertTrue(queue.pollTimeout() is GlowMessage.AddFixtureTypes)

        patch.addFixtures(listOf(examplePatchFixture))
        assertTrue(queue.pollTimeout() is GlowMessage.AddFixtures)

        patch.removeFixtureTypes(listOf(exampleFixtureType.fixtureTypeId))
        val removeFixturesMessage = queue.pollTimeout() as GlowMessage.RemoveFixtures

        assertEquals(1, removeFixturesMessage.data.size)
        assertEquals(examplePatchFixture.uuid, removeFixturesMessage.data[0])

        val removeFixtureTypeMessage = queue.pollTimeout() as GlowMessage.RemoveFixtureTypes

        assertEquals(1, removeFixtureTypeMessage.data.size)
        assertEquals(exampleFixtureType.fixtureTypeId, removeFixtureTypeMessage.data[0])

        // queue should be empty now
        assertEquals(null, queue.peek())
    }
}
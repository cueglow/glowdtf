package org.cueglow.server.json

import org.cueglow.server.StateProvider
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.objects.messages.GlowPatch
import org.cueglow.server.objects.messages.GlowTopic
import org.cueglow.server.test_utilities.concurrentTaskListTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

class ConcurrentJsonSubscriptionHandlerTest {
    private val queue = LinkedBlockingQueue<GlowMessage>()
    private val state = StateProvider(queue)
    private val patch = state.patch

    private val client = TestClient()
    private val subscriptionHandler = JsonSubscriptionHandler()

    private val testMessage = GlowMessage.RemoveFixtures(listOf())
    private val testMessageString = testMessage.toJsonString()

    private val expectedInitialState = GlowMessage.PatchInitialState(GlowPatch(listOf(), listOf()))
    private val expectedInitialStateString = expectedInitialState.toJsonString()

    private fun isClientSubscribed(): Boolean {
        subscriptionHandler.receive(testMessage)
        return if (client.messages.size == 0) {
            false
        } else {
            assertEquals(testMessageString, client.messages.remove())
            true
        }
    }

    @Test
    fun subscribeSyncReceiveUnsubscribeOnDifferentThreads() {
        val subscribeTask = { barrier: CyclicBarrier ->
            barrier.await(1, TimeUnit.SECONDS)
            subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)
            assertEquals(expectedInitialStateString, client.messages.remove())
            barrier.await(1, TimeUnit.SECONDS)
            barrier.await(2, TimeUnit.SECONDS)
            assertFalse(isClientSubscribed())
            1
        }

        val syncTask = { barrier: CyclicBarrier ->
            barrier.await(1, TimeUnit.SECONDS)
            subscriptionHandler.receive(
                queue.poll(1, TimeUnit.SECONDS) ?: throw Error("Timeout while waiting for sync message")
            )
            barrier.await(1, TimeUnit.SECONDS)
            barrier.await(2, TimeUnit.SECONDS)
            1
        }

        val sendTask = { barrier: CyclicBarrier ->
            barrier.await(1, TimeUnit.SECONDS)
            barrier.await(1, TimeUnit.SECONDS)
            assertTrue(isClientSubscribed())
            subscriptionHandler.unsubscribe(client, GlowTopic.PATCH)
            barrier.await(2, TimeUnit.SECONDS)
            1
        }

        val taskList = listOf(subscribeTask, syncTask, sendTask)

        concurrentTaskListTest(1000,taskList, { results ->
            assertEquals(listOf(1,1,1), results)
        })
    }
}
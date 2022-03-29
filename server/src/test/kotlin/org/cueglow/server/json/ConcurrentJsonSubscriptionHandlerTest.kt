package org.cueglow.server.json

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.michaelbull.result.unwrap
import org.cueglow.server.StateProvider
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.objects.messages.GlowPatch
import org.cueglow.server.objects.messages.GlowTopic
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.cueglow.server.test_utilities.concurrentTaskListTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*
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
        val receivedMessage = client.messages.poll() ?: return false
        assertEquals(testMessageString, receivedMessage)
        return true
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

        concurrentTaskListTest(1000, taskList, { results ->
            assertEquals(listOf(1,1,1), results)
        })
    }

    /** Tests SubscriptionHandler Lock in publish, internalUnsubscribe and unsubscribeFromAllTopics.
     *  Unfortunately the performance impact is relatively large and the test isn't fully reliable.
     */
    @Test
    fun unsubscribeWhileReceiving() {
        val setupTask = { barrier: CyclicBarrier ->
            subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)
            subscriptionHandler.receive(queue.remove())
            assertEquals(expectedInitialStateString, client.messages.remove())
            assertTrue(isClientSubscribed())
            barrier.await(1, TimeUnit.SECONDS)
            1
        }

        val receiveTask = { barrier: CyclicBarrier ->
            barrier.await(1, TimeUnit.SECONDS)
            isClientSubscribed()
            1
        }

        val unsubscribeTask1 = { barrier: CyclicBarrier ->
            barrier.await(1, TimeUnit.SECONDS)
            subscriptionHandler.unsubscribeFromAllTopics(client)
            1
        }

        val unsubscribeTask2 = { barrier: CyclicBarrier ->
            barrier.await(1, TimeUnit.SECONDS)
            subscriptionHandler.unsubscribe(client, GlowTopic.PATCH)
            1
        }

        val taskList = mutableListOf(setupTask).apply {
            repeat(4) {this.add(receiveTask)}
            repeat(2) {this.add(unsubscribeTask1)}
            repeat(2) {this.add(unsubscribeTask2)}
        }

        concurrentTaskListTest(2000, taskList)
    }

    /**
     * Tests the state lock in SubscriptionHandler
     */
    @Test
    fun subscribeWhileChangingPatch() {
        patch.addFixtureTypes(listOf(ExampleFixtureType.esprite)).unwrap()
        val fixtureList = List(4) {
            ExampleFixtureType.esprite_fixture.copy(uuid = UUID.randomUUID())
        }

        val changePatchTask = { barrier: CyclicBarrier ->
            patch.addFixtures(fixtureList).unwrap()
            barrier.await(1, TimeUnit.SECONDS) // start
            fixtureList.forEach {
                patch.removeFixtures(listOf(it.uuid)).unwrap()
            }
            assertEquals(0, patch.getFixtures().size)
            barrier.await(1, TimeUnit.SECONDS) // main done
            // dump message queue
            while (queue.isNotEmpty()) {
                subscriptionHandler.receive(queue.remove())
            }
            barrier.await(1, TimeUnit.SECONDS) // queue dump done
            Unit
        }

        val subscribeTask = { barrier: CyclicBarrier ->
            val threadClient = TestClient()
            barrier.await(1, TimeUnit.SECONDS) // start
            subscriptionHandler.subscribe(threadClient, GlowTopic.PATCH, state)
            barrier.await(1, TimeUnit.SECONDS) // main done
            barrier.await(1, TimeUnit.SECONDS) // queue dump done
            // parse patch initial state
            val initialJsonMessage = Parser.default().parse(StringBuilder(threadClient.messages.remove())) as JsonObject
            val fixtureData = (initialJsonMessage["data"] as JsonObject)["fixtures"] as JsonArray<*>
            val initialFixtureUuids = fixtureData
                .map { (it as JsonObject)["uuid"] as String }
                .map(UUID::fromString)
                .toSet()
            val fixtureUuidsRemovedInMessages = threadClient.messages
                .map { GlowMessage.fromJsonString(it) as GlowMessage.RemoveFixtures }
                .flatMap { it.data }
                .toSet()
            try {
                assertEquals(initialFixtureUuids, fixtureUuidsRemovedInMessages)
            } catch (e: Throwable) {
                println("failing: initially got ${initialFixtureUuids.size} and removed ${fixtureUuidsRemovedInMessages.size}")
                throw e
            }
        }

        val taskList = mutableListOf(changePatchTask)
        repeat(3) {taskList.add(subscribeTask)}

        concurrentTaskListTest(3, taskList)
    }
}
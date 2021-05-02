package org.cueglow.server.json

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.StateProvider
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.objects.messages.GlowPatch
import org.cueglow.server.objects.messages.GlowTopic
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class JsonSubscriptionHandlerTest {
    private val outEventQueue = LinkedBlockingQueue<GlowMessage>() // will fill up and it'll be ignored
    private val state = StateProvider(outEventQueue)

    private val client = TestClient()
    private val subscriptionHandler = JsonSubscriptionHandler()

    private val testMessage = GlowMessage.RemoveFixtures(listOf())
    private val testMessageString = testMessage.toJsonString()

    private val expectedInitialState = GlowMessage.PatchInitialState(GlowPatch(listOf(), listOf()))
    private val expectedInitialStateString = expectedInitialState.toJsonString()

    private fun isClientGettingMessage(): Boolean {
        subscriptionHandler.receive(testMessage)
        val receivedMessage = client.messages.poll() ?: return false
        assertEquals(testMessageString, receivedMessage)
        return true
    }

    private fun clientReceivedInitialState() {
        assertEquals(expectedInitialStateString, client.messages.remove())
    }

    @Test
    fun singleSubscriberLifecycle() {
        // without subscription, the message should not arrive
        assertFalse(isClientGettingMessage())

        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)

        // client should get initial state
        assertEquals(1, client.messages.size)
        clientReceivedInitialState()

        // SubscriptionHandler should have put a sync message into the outEventQueue
        assertEquals(1, outEventQueue.size)
        val syncMessage = outEventQueue.remove() as GlowMessage.Sync

        // updates should not get delivered until sync is delivered to the SubscriptionHandler
        assertFalse(isClientGettingMessage())

        // when a foreign sync message arrives, updates are still not delivered
        subscriptionHandler.receive(GlowMessage.Sync(UUID.randomUUID()))
        assertFalse(isClientGettingMessage())

        // now feed the sync message to SubscriptionHandler to "activate" subscription
        subscriptionHandler.receive(syncMessage)

        // updates should now get delivered
        assertTrue(isClientGettingMessage())

        // unsubscribe
        subscriptionHandler.unsubscribe(client, GlowTopic.PATCH)

        // now the client should not get another message because he unsubscribed
        assertFalse(isClientGettingMessage())
    }

    @Test
    fun unsubscribeWithoutTopic() {
        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)

        assertEquals(1, client.messages.size)
        clientReceivedInitialState()

        subscriptionHandler.receive(outEventQueue.remove())

        // function under test - unsubscribe without specifying topic
        subscriptionHandler.unsubscribeFromAllTopics(client)

        assertFalse(isClientGettingMessage())
    }

    @Test
    fun resubscribeBlocksMessagesUntilSync() {
        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)
        subscriptionHandler.receive(outEventQueue.remove())

        // resubscribe - now messages must be blocked until second sync delivery
        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)

        assertEquals(2, client.messages.size)
        clientReceivedInitialState()
        clientReceivedInitialState()

        // client should not get messages yet
        assertFalse(isClientGettingMessage())

        // feed sync message
        subscriptionHandler.receive(outEventQueue.remove())

        // now client should get message
        assertTrue(isClientGettingMessage())
    }

    @Test
    fun unsubscribeAllBeforeSync() {
        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)
        clientReceivedInitialState()
        subscriptionHandler.unsubscribeFromAllTopics(client)
        // sync
        subscriptionHandler.receive(outEventQueue.remove())

        // client should not get updates
        assertFalse(isClientGettingMessage())
    }

    @Test
    fun unsubscribeBeforeSync() {
        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)
        clientReceivedInitialState()
        subscriptionHandler.unsubscribe(client, GlowTopic.PATCH)
        // sync
        subscriptionHandler.receive(outEventQueue.remove())

        // client should not get updates
        assertFalse(isClientGettingMessage())
    }
}


class TestClient: AsyncClient, Logging {
    val messages = LinkedBlockingQueue<String>()

    override fun send(message: GlowMessage) {
        send(message.toJsonString())
    }

    override fun send(message: String) {
        logger.debug("Client is instructed to send: $message")
        messages.add(message)
    }
}
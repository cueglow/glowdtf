package org.cueglow.server.json

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.StateProvider
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.objects.messages.GlowPatch
import org.cueglow.server.objects.messages.GlowTopic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class JsonSubscriptionHandlerTest {
    private val outEventQueue = LinkedBlockingQueue<GlowMessage>() // will fill up and it'll be ignored
    private val state = StateProvider(outEventQueue)

    private val client = TestClient()
    private val subscriptionHandler = JsonSubscriptionHandler()

    private val testMessage = GlowMessage.RemoveFixtures(listOf())

    private val expectedInitialState = GlowMessage.PatchInitialState(GlowPatch(listOf(), listOf()))

    // TODO we can probably refactor some often used operations into functions, like "does client receive messages?"

    @Test
    fun singleSubscriberLifecycle() {
        subscriptionHandler.receive(testMessage)
        // without subscription, the message should not arrive
        assertEquals(0, client.messages.size)

        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)

        // client should get initial state
        assertEquals(1, client.messages.size)
        assertEquals(expectedInitialState.toJsonString(), client.messages.remove())

        // SubscriptionHandler should have put a sync message into the outEventQueue
        assertEquals(1, outEventQueue.size)
        val syncMessage = outEventQueue.remove() as GlowMessage.Sync

        // updates should not get delivered until sync is delivered to the SubscriptionHandler
        subscriptionHandler.receive(testMessage)
        assertEquals(0, client.messages.size)

        // when a foreign sync message arrives, updates are still not delivered
        subscriptionHandler.receive(GlowMessage.Sync(UUID.randomUUID()))
        subscriptionHandler.receive(testMessage)
        assertEquals(0, client.messages.size)

        // now feed the sync message to SubscriptionHandler to "activate" subscription
        subscriptionHandler.receive(syncMessage)

        // updates should now get delivered
        subscriptionHandler.receive(testMessage)
        // now the client should have gotten the message
        assertEquals(1, client.messages.size)
        assertEquals(testMessage.toJsonString(), client.messages.remove())

        // unsubscribe
        subscriptionHandler.unsubscribe(client, GlowTopic.PATCH)

        // now the client should not get another message because he unsubscribed
        subscriptionHandler.receive(testMessage)
        assertEquals(0, client.messages.size)
    }

    @Test
    fun unsubscribeWithoutTopic() {
        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)

        assertEquals(1, client.messages.size)
        assertEquals(expectedInitialState.toJsonString(), client.messages.remove())

        subscriptionHandler.receive(outEventQueue.remove())

        // function under test - unsubscribe without specifying topic
        subscriptionHandler.unsubscribeFromAllTopics(client)

        subscriptionHandler.receive(testMessage)
        assertEquals(0, client.messages.size)
    }

    @Test
    fun resubscribeBlocksMessagesUntilSync() {
        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)
        subscriptionHandler.receive(outEventQueue.remove())

        // resubscribe - now messages must be blocked until second sync delivery
        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)

        assertEquals(2, client.messages.size)
        assertEquals(expectedInitialState.toJsonString(), client.messages.remove())
        assertEquals(expectedInitialState.toJsonString(), client.messages.remove())

        // client should not get messages yet
        subscriptionHandler.receive(testMessage)
        assertEquals(0, client.messages.size)

        // feed sync message
        subscriptionHandler.receive(outEventQueue.remove())

        // now client should get message
        subscriptionHandler.receive(testMessage)
        assertEquals(1, client.messages.size)
        assertEquals(testMessage.toJsonString(), client.messages.remove())
    }

    @Test
    fun unsubscribeAllBeforeSync() {
        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)
        assertEquals(expectedInitialState.toJsonString(), client.messages.remove())
        subscriptionHandler.unsubscribeFromAllTopics(client)
        // sync
        subscriptionHandler.receive(outEventQueue.remove())

        // client should not get updates
        subscriptionHandler.receive(testMessage)
        assertEquals(0, client.messages.size)
    }

    @Test
    fun unsubscribeBeforeSync() {
        subscriptionHandler.subscribe(client, GlowTopic.PATCH, state)
        assertEquals(expectedInitialState.toJsonString(), client.messages.remove())
        subscriptionHandler.unsubscribe(client, GlowTopic.PATCH)
        // sync
        subscriptionHandler.receive(outEventQueue.remove())

        // client should not get updates
        subscriptionHandler.receive(testMessage)
        assertEquals(0, client.messages.size)
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
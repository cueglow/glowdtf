package org.cueglow.server.json

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.objects.messages.GlowMessage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.LinkedBlockingQueue

class JsonSubscriptionHandlerTest {
    private val client = TestClient()
    private val subscriptionHandler = JsonSubscriptionHandler()

    private val testMessage = GlowMessage.RemoveFixtures(listOf())

    @Test
    fun singleSubscriberLifecycle() {
        subscriptionHandler.receive(testMessage)
        // without subscription, the message should not arrive
        assertEquals(0, client.messages.size)

        subscriptionHandler.subscribe(client, JsonTopic.PATCH)
        subscriptionHandler.receive(testMessage)
        // now the client should have gotten the message
        assertEquals(1, client.messages.size)
        assertEquals(testMessage.toJsonString(), client.messages.remove())

        subscriptionHandler.unsubscribe(client, JsonTopic.PATCH)
        subscriptionHandler.receive(testMessage)
        // now the client should not get another message because he unsubscribed
        assertEquals(0, client.messages.size)
    }

    @Test
    fun unsubscribeWithoutTopic() {
        subscriptionHandler.subscribe(client, JsonTopic.PATCH)
        subscriptionHandler.unsubscribe(client)

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
        logger.info("Client is instructed to send: $message")
        messages.add(message)
    }
}
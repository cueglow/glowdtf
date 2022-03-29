package org.cueglow.server.json

import org.cueglow.server.StateProvider
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.objects.messages.GlowRequest
import org.cueglow.server.objects.messages.IncomingGlowRequestHandler
import org.cueglow.server.objects.messages.SubscriptionHandler

/** Represents a Receiver that takes a message string and can answer asynchronously with the provided GlowClient */
interface StringReceiver {
    fun receive(message: String)
}

/**
 * A stateful handler, created for each JSON Connection.
 * It receives JSON messages, parses them and passes them to the handle implementation from [IncomingGlowRequestHandler].
 */
class JsonHandler(private val client: AsyncClient, state: StateProvider, subscriptionHandler: SubscriptionHandler): StringReceiver, IncomingGlowRequestHandler(state, subscriptionHandler) {
    override fun receive(message: String) {
        val glowMessage = GlowMessage.fromJsonString(message)
        val request = GlowRequest(glowMessage, client)
        handle(request)
    }
}
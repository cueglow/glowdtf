package org.cueglow.server.json

import org.cueglow.server.OutEventReceiver
import org.cueglow.server.objects.messages.GlowMessage
import java.util.*

class JsonSubscriptionHandler: OutEventReceiver, SubscriptionHandler {
    val subscriptions = EnumMap<JsonTopic, MutableSet<AsyncClient>>(JsonTopic::class.java) // TODO synchronize

    override fun receive(glowMessage: GlowMessage) {
        // TODO
    }

    override fun subscribe(subscriber: AsyncClient, topic: JsonTopic) {
        // TODO
    }

    override fun unsubscribe(subscriber: AsyncClient, topic: JsonTopic) {
        // TODO
    }
}

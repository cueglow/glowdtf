package org.cueglow.server.json

interface SubscriptionHandler {
    fun subscribe(subscriber: AsyncClient, topic: JsonTopic)
    fun unsubscribe(subscriber: AsyncClient, topic: JsonTopic)
}
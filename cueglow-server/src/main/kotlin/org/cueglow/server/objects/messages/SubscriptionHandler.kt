package org.cueglow.server.objects.messages

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.OutEventReceiver
import org.cueglow.server.json.AsyncClient
import org.cueglow.server.json.JsonTopic
import java.util.*

abstract class SubscriptionHandler: OutEventReceiver, Logging {
    private val subscriptions = EnumMap<JsonTopic, MutableSet<AsyncClient>>(JsonTopic::class.java) // TODO synchronize (see JavaDoc for EnumMap)

    init {
        // populate subscriptions with empty sets
        JsonTopic.values().forEach { subscriptions[it] = mutableSetOf() }
    }

    abstract fun serializeMessage(glowMessage: GlowMessage): String

    override fun receive(glowMessage: GlowMessage) {
        logger.info("Receiving ${glowMessage.toString()}")

        // lookup subscribers for the topic the event belongs to
        val topicSubscribers = when (glowMessage.event) {
            GlowEvent.ADD_FIXTURES, GlowEvent.UPDATE_FIXTURES,
            GlowEvent.REMOVE_FIXTURES, GlowEvent.ADD_FIXTURE_TYPES,
            GlowEvent.REMOVE_FIXTURE_TYPES -> subscriptions[JsonTopic.PATCH]
            else -> return
        }

        if (topicSubscribers!!.isNotEmpty()) { // null asserted because all possible keys are initialized in init block
            val stringMessage = serializeMessage(glowMessage)
            topicSubscribers.forEach {it.send(stringMessage)}
        }
    }

    fun subscribe(subscriber: AsyncClient, topic: JsonTopic) {
        subscriptions[topic]!!.add(subscriber) // null asserted because all possible keys are initialized in init block
    }

    fun unsubscribe(subscriber: AsyncClient, topic: JsonTopic) {
        subscriptions[topic]!!.remove(subscriber) // null asserted because all possible keys are initialized in init block
    }

    fun unsubscribe(subscriber: AsyncClient) {
        subscriptions.values.forEach {
            it.remove(subscriber)
        }
    }
}
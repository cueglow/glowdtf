package org.cueglow.server.objects.messages

import com.beust.klaxon.Json
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.OutEventReceiver
import org.cueglow.server.StateProvider
import org.cueglow.server.json.AsyncClient
import org.cueglow.server.json.JsonTopic
import java.util.*

abstract class SubscriptionHandler: OutEventReceiver, Logging {
    private val subscriptions = EnumMap<JsonTopic, MutableSet<AsyncClient>>(JsonTopic::class.java) // TODO synchronize (see JavaDoc for EnumMap)

    private val pendingSubscriptions = mutableMapOf<UUID, Pair<JsonTopic, AsyncClient>>()

    init {
        // populate subscriptions with empty sets
        JsonTopic.values().forEach { subscriptions[it] = mutableSetOf() }
    }

    abstract fun serializeMessage(glowMessage: GlowMessage): String

    override fun receive(glowMessage: GlowMessage) {
        logger.info("Receiving $glowMessage")

        when (glowMessage.event) {
            GlowEvent.ADD_FIXTURES, GlowEvent.UPDATE_FIXTURES,
            GlowEvent.REMOVE_FIXTURES, GlowEvent.ADD_FIXTURE_TYPES,
            GlowEvent.REMOVE_FIXTURE_TYPES -> publish(JsonTopic.PATCH, glowMessage)
            GlowEvent.SYNC -> activateSubscription((glowMessage as GlowMessage.Sync).data)
            else -> return
        }
    }

    private fun publish(topic: JsonTopic, glowMessage: GlowMessage) {
        val topicSubscribers = subscriptions[topic]
        if (topicSubscribers!!.isNotEmpty()) { // null asserted because all possible keys are initialized in init block
            val stringMessage = serializeMessage(glowMessage)
            topicSubscribers.forEach {it.send(stringMessage)}
        }
    }

    fun subscribe(subscriber: AsyncClient, topic: JsonTopic, state: StateProvider) {
        unsubscribe(subscriber, topic)
        when (topic) {
            JsonTopic.PATCH -> {
                val syncUuid = UUID.randomUUID()
                val syncMessage = GlowMessage.Sync(syncUuid)
                // TODO acquire state lock here
                val initialPatchState = state.patch.getGlowPatch()
                state.outEventQueue.put(syncMessage) // TODO possible deadlock because SubscriptionHandler is locked and cannot work to reduce message count in queue
                // TODO release state lock here
                val initialMessage = GlowMessage.PatchInitialState(initialPatchState)
                subscriber.send(initialMessage)
                pendingSubscriptions[syncUuid] = Pair(JsonTopic.PATCH, subscriber)
            }
        }
    }

    private fun activateSubscription(syncUuid: UUID) {
        val (topic, subscriber) = pendingSubscriptions.remove(syncUuid) ?: return
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
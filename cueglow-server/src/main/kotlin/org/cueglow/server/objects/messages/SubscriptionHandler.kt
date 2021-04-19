package org.cueglow.server.objects.messages

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.OutEventReceiver
import org.cueglow.server.StateProvider
import org.cueglow.server.json.AsyncClient
import java.util.*

abstract class SubscriptionHandler: OutEventReceiver, Logging {
    private val subscriptions = EnumMap<GlowTopic, MutableSet<AsyncClient>>(GlowTopic::class.java) // TODO synchronize (see JavaDoc for EnumMap)

    private val pendingSubscriptions = mutableMapOf<UUID, Pair<GlowTopic, AsyncClient>>()

    init {
        // populate subscriptions with empty sets
        GlowTopic.values().forEach { subscriptions[it] = mutableSetOf() }
    }

    abstract fun serializeMessage(glowMessage: GlowMessage): String

    override fun receive(glowMessage: GlowMessage) {
        logger.info("Receiving $glowMessage")

        when (glowMessage.event) {
            GlowEvent.ADD_FIXTURES, GlowEvent.UPDATE_FIXTURES,
            GlowEvent.REMOVE_FIXTURES, GlowEvent.ADD_FIXTURE_TYPES,
            GlowEvent.REMOVE_FIXTURE_TYPES -> publish(GlowTopic.PATCH, glowMessage)
            GlowEvent.SYNC -> activateSubscription((glowMessage as GlowMessage.Sync).data)
            else -> return
        }
    }

    private fun publish(topic: GlowTopic, glowMessage: GlowMessage) {
        val topicSubscribers = subscriptions[topic]
        if (topicSubscribers!!.isNotEmpty()) { // null asserted because all possible keys are initialized in init block
            val stringMessage = serializeMessage(glowMessage)
            topicSubscribers.forEach {it.send(stringMessage)}
        }
    }

    fun subscribe(subscriber: AsyncClient, topic: GlowTopic, state: StateProvider) {
        unsubscribe(subscriber, topic)
        when (topic) {
            GlowTopic.PATCH -> {
                val syncUuid = UUID.randomUUID()
                val syncMessage = GlowMessage.Sync(syncUuid)
                // TODO acquire state lock here
                val initialPatchState = state.patch.getGlowPatch()
                state.outEventQueue.put(syncMessage) // TODO possible deadlock because SubscriptionHandler is locked and cannot work to reduce message count in queue
                // TODO release state lock here
                val initialMessage = GlowMessage.PatchInitialState(initialPatchState)
                subscriber.send(initialMessage)
                pendingSubscriptions[syncUuid] = Pair(GlowTopic.PATCH, subscriber)
            }
        }
    }

    private fun activateSubscription(syncUuid: UUID) {
        val (topic, subscriber) = pendingSubscriptions.remove(syncUuid) ?: return
        subscriptions[topic]!!.add(subscriber) // null asserted because all possible keys are initialized in init block
    }

    fun unsubscribe(subscriber: AsyncClient, topic: GlowTopic) {
        subscriptions[topic]!!.remove(subscriber) // null asserted because all possible keys are initialized in init block
    }

    fun unsubscribe(subscriber: AsyncClient) {
        subscriptions.values.forEach {
            it.remove(subscriber)
        }
    }
}
package org.cueglow.server.objects.messages

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.OutEventReceiver
import org.cueglow.server.StateProvider
import org.cueglow.server.json.AsyncClient
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Handles Subscribe/Unsubscribe Events. Receives OutEvents from the OutEventHandler and sends them to the subscribers.
 **/
abstract class SubscriptionHandler : OutEventReceiver, Logging {
    val lock = ReentrantLock()

    private val activeSubscriptions = EnumMap<GlowTopic, MutableSet<AsyncClient>>(GlowTopic::class.java)

    /** Keeps subscriptions that were sent the initial state but do not get updates yet because older updates
     * in the OutEventQueue first need to be handled. Subscriptions move from pending to active once the sync message
     * (identified by UUID) is received by the SubscriptionHandler.
     **/
    private val pendingSubscriptions = mutableMapOf<UUID, Pair<GlowTopic, AsyncClient>>()

    init {
        // populate subscriptions with empty sets
        GlowTopic.values().forEach { activeSubscriptions[it] = mutableSetOf() }
    }

    abstract fun serializeMessage(glowMessage: GlowMessage): String

    /** Receive and handle messages from the OutEventQueue **/
    override fun receive(glowMessage: GlowMessage) {
        logger.debug("Receiving $glowMessage")

        when (glowMessage.event) {
            GlowEvent.ADD_FIXTURES, GlowEvent.UPDATE_FIXTURES,
            GlowEvent.REMOVE_FIXTURES, GlowEvent.ADD_FIXTURE_TYPES,
            GlowEvent.REMOVE_FIXTURE_TYPES -> publish(GlowTopic.PATCH, glowMessage)

            GlowEvent.RIG_STATE -> publish(GlowTopic.RIG_STATE, glowMessage)

            GlowEvent.SYNC -> activateSubscription((glowMessage as GlowMessage.Sync).data)

            else -> return
        }
    }

    private fun publish(topic: GlowTopic, glowMessage: GlowMessage) {
        lock.withLock {
            val topicSubscribers = activeSubscriptions[topic]
            if (topicSubscribers!!.isNotEmpty()) { // null asserted because all possible keys are initialized in init block
                val messageString = serializeMessage(glowMessage)
                topicSubscribers.forEach { it.send(messageString) }
            }
        }
    }

    /** Check if [syncUuid] is known. If yes, move subscription from pending to active **/
    private fun activateSubscription(syncUuid: UUID) {
        lock.withLock {
            val (topic, subscriber) = pendingSubscriptions.remove(syncUuid) ?: return
            activeSubscriptions[topic]!!.add(subscriber) // null asserted because all possible keys are initialized in init block
        }
    }

    fun subscribe(subscriber: AsyncClient, topic: GlowTopic, state: StateProvider) {
        // unsubscribe before subscribing
        if (internalUnsubscribe(subscriber, topic)) {
            logger.warn("Client $subscriber subscribed to $topic but was already subscribed. Subscription was reset. ")
        }
        when (topic) {
            GlowTopic.PATCH -> {
                val syncUuid = UUID.randomUUID()
                val syncMessage = GlowMessage.Sync(syncUuid)
                val initialPatchState = lock.withLock {
                    val initialPatchState = state.lock.withLock {
                        // -> need to test multiple threads subscribing while changes are happening -> all threads should have same state in the end with their respective updates applied
                        state.outEventQueue.offer(syncMessage, 1, TimeUnit.SECONDS)
                        state.patch.getGlowPatch()
                    }
                    pendingSubscriptions[syncUuid] = Pair(GlowTopic.PATCH, subscriber)
                    initialPatchState
                }
                val initialMessage = GlowMessage.PatchInitialState(initialPatchState)
                subscriber.send(initialMessage)
            }
            GlowTopic.RIG_STATE -> {
                lock.withLock {
                    state.lock.withLock {
                        val initialMessage = GlowMessage.RigState(state.rigStateContainer.rigState)
                        subscriber.send(initialMessage)
                    }
                    activeSubscriptions[GlowTopic.RIG_STATE]!!.add(subscriber) // null asserted because all possible keys are initialized in init block
                }
            }
        }
    }

    fun unsubscribe(subscriber: AsyncClient, topic: GlowTopic) {
        if (!internalUnsubscribe(subscriber, topic)) {
            logger.warn("Client $subscriber unsubscribed from $topic but was not subscribed")
        }
    }

    /** Returns true if the subscriber was successfully unsubscribed and false if the subscriber wasn't subscribed */
    private fun internalUnsubscribe(subscriber: AsyncClient, topic: GlowTopic): Boolean {
        lock.withLock {
            val numberOfSubscriptionsRemovedFromPending = pendingSubscriptions
                .filter { it.value.first == topic && it.value.second == subscriber }
                .keys
                .map { pendingSubscriptions.remove(it) }
                .size
            val removedFromActive =
                activeSubscriptions[topic]!!.remove(subscriber) // null asserted because all possible keys are initialized in init block
            return removedFromActive || numberOfSubscriptionsRemovedFromPending > 0
        }
    }

    fun unsubscribeFromAllTopics(subscriber: AsyncClient) {
        lock.withLock {
            pendingSubscriptions.filter { it.value.second == subscriber }.keys.map { pendingSubscriptions.remove(it) }
            activeSubscriptions.values.forEach {
                it.remove(subscriber)
            }
        }
    }
}
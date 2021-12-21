package org.cueglow.server.objects.messages

import kotlin.reflect.KClass

/**
 * The different events in the [GlowMessage]
 */
enum class GlowEvent(val string: String, val messageClass: KClass<out GlowMessage>?) {
    // Generic

    ERROR("error", GlowMessage.Error::class),
    SYNC("sync", GlowMessage.Sync::class),

    // Subscriptions

    SUBSCRIBE("subscribe", GlowMessage.Subscribe::class),
    UNSUBSCRIBE("unsubscribe", GlowMessage.Unsubscribe::class),

    // Patch-specific

    PATCH_INITIAL_STATE("patchInitialState", GlowMessage.PatchInitialState::class),

    ADD_FIXTURES("addFixtures", GlowMessage.AddFixtures::class),
    UPDATE_FIXTURES("updateFixtures", GlowMessage.UpdateFixtures::class),
    REMOVE_FIXTURES("removeFixtures", GlowMessage.RemoveFixtures::class),

    ADD_FIXTURE_TYPES("addFixtureTypes", GlowMessage.AddFixtureTypes::class),
    REMOVE_FIXTURE_TYPES("removeFixtureTypes", GlowMessage.RemoveFixtureTypes::class),

    FIXTURE_TYPE_ADDED("fixtureTypeAdded", GlowMessage.FixtureTypeAdded::class),

    // Topic rigState
    RIG_STATE("rigState", GlowMessage.RigState::class),
    SET_CHANNEL("setChannel", GlowMessage.SetChannel::class),

    // Simplified Ping API
    PING("ping", GlowMessage.Ping::class);

    override fun toString(): String {
        return string
    }

    companion object {
        // lookup event by event string
        private val map = values().associateBy(GlowEvent::string)
        fun fromString(string: String) = map[string]

        // lookup event by GlowMessage class
        private val classMap = values().associateBy(GlowEvent::messageClass)
        fun fromMessageClass(cls: KClass<out GlowMessage>) = classMap[cls] ?:
            throw IllegalArgumentException("GlowMessage Class $cls does not have an associated GlowEvent. ")
    }
}
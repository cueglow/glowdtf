package org.cueglow.server.objects.messages

import kotlin.reflect.KClass

// TODO change class associations to GlowMessage classes
/**
 * The different events in the [GlowMessage]
 */
enum class GlowEvent(val string: String, val messageClass: KClass<out GlowMessage>?) {

    SUBSCRIBE("subscribe", GlowMessage.Subscribe::class),

    ERROR("error", GlowMessage.Error::class),

    ADD_FIXTURES("addFixtures", GlowMessage.AddFixtures::class),
    UPDATE_FIXTURES("updateFixtures", GlowMessage.UpdateFixtures::class),
    REMOVE_FIXTURES("removeFixtures", GlowMessage.RemoveFixtures::class),
    FIXTURE_TYPE_ADDED("fixtureTypeAdded", GlowMessage.FixtureTypeAdded::class),
    REMOVE_FIXTURE_TYPES("removeFixtureTypes", GlowMessage.RemoveFixtureTypes::class),;

    override fun toString(): String {
        return string
    }

    companion object {
        // lookup event by event string
        private val map = values().associateBy(GlowEvent::string)
        fun fromString(string: String) = map[string]

        // lookup event by GlowData class
        private val classMap = values().associateBy(GlowEvent::messageClass)
        fun fromMessageClass(cls: KClass<out GlowMessage>) = classMap[cls] ?:
            throw IllegalArgumentException("GlowData Class $cls does not have an associated GlowEvent. ")
    }
}
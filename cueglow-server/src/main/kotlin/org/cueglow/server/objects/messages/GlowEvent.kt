package org.cueglow.server.objects.messages

import kotlin.reflect.KClass

/**
 * The different events in the [GlowMessage]
 */
enum class GlowEvent(val string: String, val dataClass: KClass<out GlowData>?) {

    SUBSCRIBE("subscribe", GlowData.Subscribe::class),
    UNSUBSCRIBE("unsubscribe", GlowData.Unsubscribe::class),
    STREAM_INITIAL_STATE("streamInitialState", GlowData.StreamInitialState::class),
    STREAM_UPDATE("streamUpdate", GlowData.StreamUpdate::class),
    REQUEST_STREAM_DATA("requestStreamData", GlowData.RequestStreamData::class),
    ERROR("error", GlowData.Error::class),

    ADD_FIXTURES("addFixtures", GlowData.AddFixtures::class),
    FIXTURES_ADDED("fixturesAdded", GlowData.FixturesAdded::class),
    UPDATE_FIXTURE("updateFixture", GlowData.UpdateFixture::class),
    DELETE_FIXTURES("deleteFixtures", GlowData.DeleteFixtures::class),
    FIXTURE_TYPE_ADDED("fixtureTypeAdded", GlowData.FixtureTypeAdded::class),
    DELETE_FIXTURE_TYPES("deleteFixtureTypes", GlowData.DeleteFixtureTypes::class),;

    override fun toString(): String {
        return string
    }

    companion object {
        // lookup event by event string
        private val map = values().associateBy(GlowEvent::string)
        fun fromString(string: String) = map[string]

        // lookup event by GlowData class
        private val classMap = values().associateBy(GlowEvent::dataClass)
        fun fromDataClass(cls: KClass<out GlowData>) = classMap[cls] ?:
            throw IllegalArgumentException("GlowData Class $cls does not have an associated GlowEvent. ")
    }
}
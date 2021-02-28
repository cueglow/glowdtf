package org.cueglow.server.api

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import kotlin.reflect.KClass

enum class GlowEvent(val eventDescriptor: String, val eventType: GlowEventType, val eventDataClass: KClass<out GlowData>) {

    SUBSCRIBE("subscribe", GlowEventType.CLIENT, GlowDataSubscribe::class),
    UNSUBSCRIBE("unsubscribe", GlowEventType.CLIENT, GlowDataUnsubscribe::class),
    STREAM_INITIAL_STATE("streamInitialState", GlowEventType.SERVER, GlowDataStreamInitialState::class),
    STREAM_UPDATE("streamUpdate", GlowEventType.SERVER, GlowDataStreamUpdate::class),
    REQUEST_STREAM_DATA("requestStreamData", GlowEventType.CLIENT, GlowDataRequestStreamData::class),
    ERROR("error", GlowEventType.SERVER, GlowDataError::class),

    ADD_FIXTURES("addFixtures", GlowEventType.CLIENT, GlowDataAddFixtures::class),
    FIXTURES_ADDED("fixturesAdded", GlowEventType.SERVER, GlowDataFixturesAdded::class),
    UPDATE_FIXTURES("updateFixtures", GlowEventType.CLIENT, GlowDataUpdateFixture::class),
    DELETE_FIXTURES("deleteFixtures", GlowEventType.CLIENT, GlowDataDeleteFixtures::class),
    FIXTURE_TYPE_ADDED("fixtureTypeAdded", GlowEventType.SERVER, GlowDataFixtureTypeAdded::class),
    DELETE_FIXTURE_TYPES("deleteFixtureTypes", GlowEventType.CLIENT, GlowDataDeleteFixtureTypes::class),;

    override fun toString(): String {
        return eventDescriptor
    }



    companion object {
        // Provide methode to lookup event enum by eventDescriptor string
        private val map = values().associateBy(GlowEvent::eventDescriptor)
        fun fromDescriptor(eventDescriptor: String) = map[eventDescriptor]

        val glowEventConverter = object: Converter {
            override fun toJson(value: Any): String = "\"$value\""

            override fun canConvert(cls: Class<*>): Boolean = cls == GlowEvent::class.java

            override fun fromJson(jv: JsonValue): GlowEvent? = fromDescriptor(jv.inside.toString())
        }
    }

}

/**
 * Provide a Annotation to mark fields and allow Klaxon to use a special converter on all marked fields
 * See [GlowEvent.glowEventConverter] for the special converter and [GlowMessage.event] for the marked field
 * A Klaxon instance with the linked Annotation and Converter ist used in [GlowMessage.toJsonString]
 */
@Target(AnnotationTarget.FIELD)
annotation class KlaxonGlowEvent



enum class GlowEventType {
    CLIENT, SERVER
}
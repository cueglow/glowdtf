package org.cueglow.server.objects

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue

enum class GlowEvent(val eventDescriptor: String, val eventType: GlowEventType) {

    SUBSCRIBE("subscribe", GlowEventType.CLIENT),
    UNSUBSCRIBE("unsubscribe", GlowEventType.CLIENT),
    STREAM_INITIAL_STATE("streamInitialState", GlowEventType.SERVER),
    STREAM_UPDATE("streamUpdate", GlowEventType.SERVER),
    REQUEST_STREAM_DATA("requestStreamData", GlowEventType.CLIENT),
    ERROR("error", GlowEventType.SERVER),

    ADD_FIXTURES("addFixtures", GlowEventType.CLIENT),
    FIXTURES_ADDED("fixturesAdded", GlowEventType.SERVER),
    UPDATE_FIXTURES("updateFixtures", GlowEventType.CLIENT),
    DELETE_FIXTURES("deleteFixtures", GlowEventType.CLIENT),
    FIXTURE_TYPE_ADDED("fixtureTypeAdded", GlowEventType.SERVER),
    DELETE_FIXTURE_TYPES("deleteFixtureTypes", GlowEventType.CLIENT),;

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

            override fun fromJson(jv: JsonValue): GlowEvent? = fromDescriptor(jv.inside as String)
        }
    }

}

@Target(AnnotationTarget.FIELD)
annotation class KlaxonGlowEvent



enum class GlowEventType {
    CLIENT, SERVER
}
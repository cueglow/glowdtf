package org.cueglow.server.json

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import kotlin.reflect.KClass

/**
 * Internal representation of the "event" field in the JSON message
 */
enum class JsonEvent(val eventDescriptor: String, val eventType: JsonEventType, val eventDataClass: KClass<out JsonData>) {

    SUBSCRIBE("subscribe", JsonEventType.CLIENT, JsonDataSubscribe::class),
    UNSUBSCRIBE("unsubscribe", JsonEventType.CLIENT, JsonDataUnsubscribe::class),
    STREAM_INITIAL_STATE("streamInitialState", JsonEventType.SERVER, JsonDataStreamInitialState::class),
    STREAM_UPDATE("streamUpdate", JsonEventType.SERVER, JsonDataStreamUpdate::class),
    REQUEST_STREAM_DATA("requestStreamData", JsonEventType.CLIENT, JsonDataRequestStreamData::class),
    ERROR("error", JsonEventType.SERVER, JsonDataError::class),

    ADD_FIXTURES("addFixtures", JsonEventType.CLIENT, JsonDataAddFixtures::class),
    FIXTURES_ADDED("fixturesAdded", JsonEventType.SERVER, JsonDataFixturesAdded::class),
    UPDATE_FIXTURE("updateFixture", JsonEventType.CLIENT, JsonDataUpdateFixture::class),
    DELETE_FIXTURES("deleteFixtures", JsonEventType.CLIENT, JsonDataDeleteFixtures::class),
    FIXTURE_TYPE_ADDED("fixtureTypeAdded", JsonEventType.SERVER, JsonDataFixtureTypeAdded::class),
    DELETE_FIXTURE_TYPES("deleteFixtureTypes", JsonEventType.CLIENT, JsonDataDeleteFixtureTypes::class),;

    override fun toString(): String {
        return eventDescriptor
    }

    companion object {
        // Provide methode to lookup event enum by eventDescriptor string
        private val map = values().associateBy(JsonEvent::eventDescriptor)
        fun fromDescriptor(eventDescriptor: String) = map[eventDescriptor]

        val jsonEventConverter = object: Converter {
            override fun toJson(value: Any): String = "\"$value\""

            override fun canConvert(cls: Class<*>): Boolean = cls == JsonEvent::class.java

            override fun fromJson(jv: JsonValue): JsonEvent? = fromDescriptor(jv.inside.toString())
        }
    }

}

/**
 * Provide a Annotation to mark fields and allow Klaxon to use a special converter on all marked fields
 * See [JsonEvent.jsonEventConverter] for the special converter and [JsonMessage.event] for the marked field
 * A Klaxon instance with the linked Annotation and Converter ist used in [JsonMessage.toJsonString]
 */
@Target(AnnotationTarget.FIELD)
annotation class KlaxonJsonEvent


enum class JsonEventType {
    CLIENT, SERVER
}
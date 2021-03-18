package org.cueglow.server.objects.messages

import com.beust.klaxon.Json
import com.beust.klaxon.TypeAdapter
import com.beust.klaxon.TypeFor
import org.cueglow.server.json.KlaxonGlowEvent
import org.cueglow.server.patch.PatchFixture
import kotlin.reflect.KClass

/**
 * Represents a message inside CueGlow that may be parsed from or serialized to different formats like JSON
 *
 * The message MUST have the field "event" and CAN have the fields and "data" and "messageId".
 *
 * @throws IllegalArgumentException when the data type does not match the event
 */
@TypeFor(field="event", adapter = KlaxonGlowMessageAdapter::class)
sealed class GlowMessage constructor(
    @Json(index = 0)
    @KlaxonGlowEvent
    val event: GlowEvent,
    @Json(index = 2)
    val messageId: Int?,
) {
    class Subscribe(@Json(index=1) val data: GlowData.Subscribe, messageId: Int? = null): GlowMessage(GlowEvent.SUBSCRIBE, messageId)

    class AddFixtures(@Json(index=1) val data: List<PatchFixture>, messageId: Int? = null): GlowMessage(GlowEvent.ADD_FIXTURES, messageId)
    class UpdateFixture(@Json(index=1) val data: GlowData.UpdateFixture, messageId: Int? = null): GlowMessage(GlowEvent.UPDATE_FIXTURE, messageId)
    class DeleteFixtures(@Json(index=1) val data: GlowData.DeleteFixtures, messageId: Int? = null): GlowMessage(GlowEvent.DELETE_FIXTURES, messageId)

    class Error(@Json(index=1) val data: GlowData.Error, messageId: Int? = null): GlowMessage(GlowEvent.ERROR, messageId)

    class FixtureTypeAdded(@Json(index=1) val data: GlowData.FixtureTypeAdded, messageId: Int? = null): GlowMessage(GlowEvent.FIXTURE_TYPE_ADDED, messageId)
    class DeleteFixtureTypes(@Json(index=1) val data: GlowData.DeleteFixtureTypes, messageId: Int? = null): GlowMessage(GlowEvent.DELETE_FIXTURE_TYPES, messageId)

    companion object
}


class KlaxonGlowMessageAdapter: TypeAdapter<GlowMessage> {
    override fun classFor(type: Any): KClass<out GlowMessage> = when(type as String) {
        "subscribe" -> GlowMessage.Subscribe::class
        "addFixtures" -> GlowMessage.AddFixtures::class
        "updateFixture" -> GlowMessage.UpdateFixture::class
        "deleteFixtures" -> GlowMessage.DeleteFixtures::class
        "error" -> GlowMessage.Error::class
        "fixtureTypeAdded" -> GlowMessage.FixtureTypeAdded::class
        "deleteFixtureTypes" -> GlowMessage.DeleteFixtureTypes::class
        else -> throw IllegalArgumentException("Unknown type: $type")
    }
}


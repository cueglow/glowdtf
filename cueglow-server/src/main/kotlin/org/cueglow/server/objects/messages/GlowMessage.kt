package org.cueglow.server.objects.messages

import com.beust.klaxon.Json
import com.beust.klaxon.TypeFor
import org.cueglow.server.json.KlaxonGlowMessageAdapter
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.patch.PatchFixtureUpdate

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
    val event: GlowEvent,
    @Json(index = 2)
    val messageId: Int?,
) {
    class Subscribe(@Json(index=1) val data: GlowData.Subscribe, messageId: Int? = null): GlowMessage(GlowEvent.SUBSCRIBE, messageId)

    class AddFixtures(@Json(index=1) val data: List<PatchFixture>, messageId: Int? = null): GlowMessage(GlowEvent.ADD_FIXTURES, messageId)
    class UpdateFixtures(@Json(index=1) val data: List<PatchFixtureUpdate>, messageId: Int? = null): GlowMessage(GlowEvent.UPDATE_FIXTURES, messageId)
    class DeleteFixtures(@Json(index=1) val data: GlowData.DeleteFixtures, messageId: Int? = null): GlowMessage(GlowEvent.DELETE_FIXTURES, messageId)

    class Error(@Json(index=1) val data: GlowData.Error, messageId: Int? = null): GlowMessage(GlowEvent.ERROR, messageId)

    class FixtureTypeAdded(@Json(index=1) val data: GlowData.FixtureTypeAdded, messageId: Int? = null): GlowMessage(GlowEvent.FIXTURE_TYPE_ADDED, messageId)
    class DeleteFixtureTypes(@Json(index=1) val data: GlowData.DeleteFixtureTypes, messageId: Int? = null): GlowMessage(GlowEvent.DELETE_FIXTURE_TYPES, messageId)

    companion object
}


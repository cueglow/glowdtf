package org.cueglow.server.objects.messages

import com.beust.klaxon.Json
import com.beust.klaxon.TypeFor
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.json.KlaxonGlowMessageAdapter
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.patch.PatchFixtureUpdate
import org.cueglow.server.rig.RigStateMap
import org.cueglow.server.rig.RigStateTransition
import java.util.*

/**
 * Represents a message inside GlowDTF that may be parsed from or serialized to different formats like JSON
 *
 * The message MUST have the field "event" and CAN have the fields and "data" and "messageId".
 *
 * @throws IllegalArgumentException when the data type does not match the event
 */
@TypeFor(field = "event", adapter = KlaxonGlowMessageAdapter::class)
sealed class GlowMessage constructor(
    @Json(index = 0)
    val event: GlowEvent,
    @Json(index = 2, serializeNull = false)
    val messageId: Int?,
) {
    // Generic

    class Error(@Json(index = 1) val data: GlowError, messageId: Int? = null) : GlowMessage(GlowEvent.ERROR, messageId)
    class Sync(@Json(index = 1) val data: UUID, messageId: Int? = null) : GlowMessage(GlowEvent.SYNC, messageId)

    // Subscriptions

    class Subscribe(@Json(index = 1) val data: GlowTopic) : GlowMessage(GlowEvent.SUBSCRIBE, null)
    class Unsubscribe(@Json(index = 1) val data: GlowTopic) : GlowMessage(GlowEvent.UNSUBSCRIBE, null)

    // Patch Topic

    class PatchInitialState(@Json(index = 1) val data: GlowPatch) : GlowMessage(GlowEvent.PATCH_INITIAL_STATE, null)

    class AddFixtures(@Json(index = 1) val data: List<PatchFixture>, messageId: Int? = null) :
        GlowMessage(GlowEvent.ADD_FIXTURES, messageId)

    class UpdateFixtures(@Json(index = 1) val data: List<PatchFixtureUpdate>, messageId: Int? = null) :
        GlowMessage(GlowEvent.UPDATE_FIXTURES, messageId)

    class RemoveFixtures(@Json(index = 1) val data: List<UUID>, messageId: Int? = null) :
        GlowMessage(GlowEvent.REMOVE_FIXTURES, messageId)

    class AddFixtureTypes(@Json(index = 1) val data: List<GdtfWrapper>, messageId: Int? = null) :
        GlowMessage(GlowEvent.ADD_FIXTURE_TYPES, messageId)

    class RemoveFixtureTypes(@Json(index = 1) val data: List<UUID>, messageId: Int? = null) :
        GlowMessage(GlowEvent.REMOVE_FIXTURE_TYPES, messageId)

    class FixtureTypeAdded(@Json(index = 1) val data: UUID, messageId: Int? = null) :
        GlowMessage(GlowEvent.FIXTURE_TYPE_ADDED, messageId)

    // rigState Topic

    class RigState(@Json(index = 1) val data: RigStateMap, messageId: Int? = null) :
        GlowMessage(GlowEvent.RIG_STATE, messageId)

    class SetChannel(@Json(index = 1) val data: RigStateTransition, messageId: Int? = null) :
        GlowMessage(GlowEvent.SET_CHANNEL, messageId)

    // Simplified Ping API

    class Ping(messageId: Int? = null) : GlowMessage(GlowEvent.PING, messageId)

    // Control

    class Shutdown(messageId: Int? = null) :
        GlowMessage(GlowEvent.SHUTDOWN, messageId)

    companion object
}


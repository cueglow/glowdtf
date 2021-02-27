package org.cueglow.server

import org.cueglow.server.api.GlowDataDeleteFixtureTypes
import org.cueglow.server.api.GlowEvent
import org.cueglow.server.api.GlowRequest
import org.cueglow.server.patch.Patch

fun handleInRequest(glowRequest: GlowRequest) {

    // Delegate to the correct function / subroutine depending on the event
    when (glowRequest.glowMessage.event) {
        GlowEvent.SUBSCRIBE -> TODO()
        GlowEvent.UNSUBSCRIBE -> TODO()
        GlowEvent.STREAM_INITIAL_STATE -> TODO()
        GlowEvent.STREAM_UPDATE -> TODO()
        GlowEvent.REQUEST_STREAM_DATA -> TODO()
        GlowEvent.ERROR -> TODO()
        GlowEvent.ADD_FIXTURES -> TODO()
        GlowEvent.FIXTURES_ADDED -> TODO()
        GlowEvent.UPDATE_FIXTURES -> TODO()
        GlowEvent.DELETE_FIXTURES -> TODO()
        GlowEvent.FIXTURE_TYPE_ADDED -> TODO()
        GlowEvent.DELETE_FIXTURE_TYPES ->
            (glowRequest.glowMessage.data as GlowDataDeleteFixtureTypes)
                .fixtureTypeIds.forEach{Patch.removeFixtureType(it)}
    }
}


package org.cueglow.server.handlers

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import org.cueglow.server.StateProvider
import org.cueglow.server.api.GlowDataDeleteFixtureTypes
import org.cueglow.server.api.GlowEvent
import org.cueglow.server.api.GlowRequest
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.gdtf.parseGdtf
import org.cueglow.server.objects.GlowError
import java.io.InputStream
import java.util.*

/**
 * Network-Agnostic Handler for Incoming Events
 */
class InEventHandler(private val state: StateProvider) {
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
                    .fixtureTypeIds.forEach{state.patch.removeFixtureType(it)}
        }
    }

    /**
     * Handler for new GDTF
     *
     * Parses it and adds it to the Patch
     */
    fun handleNewGdtf(inputStream: InputStream): Result<UUID, GlowError> {
        val parseResult = parseGdtf(inputStream)

        val parsedGdtf = parseResult.getOrElse {return Err(it) }
        val wrapper = GdtfWrapper(parsedGdtf)

        state.patch.putFixtureType(wrapper)

        return Ok(wrapper.fixtureTypeId)
    }
}




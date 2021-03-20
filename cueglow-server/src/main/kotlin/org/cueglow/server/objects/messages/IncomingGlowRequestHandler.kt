package org.cueglow.server.objects.messages

import com.github.michaelbull.result.getOrElse
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.StateProvider

abstract class IncomingGlowRequestHandler(private val state: StateProvider): Logging {
    fun handle(request: GlowRequest) {
        when (request.originalMessage.event) {
            // TODO remove events that shouldn't come from outside and handle them with Error in else clause
            GlowEvent.PATCH_SUBSCRIBE -> TODO()
            GlowEvent.PATCH_UNSUBSCRIBE -> TODO()
            GlowEvent.ERROR -> TODO()
            GlowEvent.ADD_FIXTURES -> handleAddFixtures(request)
            GlowEvent.UPDATE_FIXTURES -> handleUpdateFixture(request)
            GlowEvent.REMOVE_FIXTURES -> handleRemoveFixtures(request)
            GlowEvent.FIXTURE_TYPE_ADDED -> TODO()
            GlowEvent.REMOVE_FIXTURE_TYPES -> handleRemoveFixtureTypes(request)
        }
    }

    private fun handleAddFixtures(request: GlowRequest) {
        val fixtures = (request.originalMessage as GlowMessage.AddFixtures).data

        state.patch.addFixtures(fixtures).getOrElse { errorList ->
            errorList.forEach {
                request.answer(it)
                logger.warn("Adding fixture to the Patch failed. ${it.description}")
            }
        }
    }

    private fun handleRemoveFixtureTypes(glowRequest: GlowRequest) {
        val idsToRemove = (glowRequest.originalMessage as GlowMessage.RemoveFixtureTypes).data
        state.patch.removeFixtureTypes(idsToRemove).getOrElse { errorList ->
            errorList.forEach { glowRequest.answer(it) }
        }
    }

    private fun handleRemoveFixtures(glowRequest: GlowRequest) {
        val uuidsToRemove = (glowRequest.originalMessage as GlowMessage.RemoveFixtures).data
        state.patch.removeFixtures(uuidsToRemove).getOrElse { errorList ->
            errorList.forEach {glowRequest.answer(it)}
        }
    }

    private fun handleUpdateFixture(glowRequest: GlowRequest) {
        val data = (glowRequest.originalMessage as GlowMessage.UpdateFixtures).data
        state.patch.updateFixtures(data).getOrElse { glowRequest.answer(it[0]) }
    }
}
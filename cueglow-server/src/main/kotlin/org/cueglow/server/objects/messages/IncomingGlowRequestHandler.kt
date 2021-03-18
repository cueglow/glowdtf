package org.cueglow.server.objects.messages

import com.github.michaelbull.result.getOrElse
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.StateProvider

abstract class IncomingGlowRequestHandler(private val state: StateProvider): Logging {
    fun handle(request: GlowRequest) {
        when (request.originalMessage.event) {
            // TODO remove events that shouldn't come from outside and handle them with Error in else clause
            GlowEvent.SUBSCRIBE -> TODO()
            GlowEvent.ERROR -> TODO()
            GlowEvent.ADD_FIXTURES -> handleAddFixtures(request)
            GlowEvent.UPDATE_FIXTURES -> handleUpdateFixture(request)
            GlowEvent.DELETE_FIXTURES -> handleDeleteFixtures(request)
            GlowEvent.FIXTURE_TYPE_ADDED -> TODO()
            GlowEvent.DELETE_FIXTURE_TYPES -> handleDeleteFixtureTypes(request)
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

    private fun handleDeleteFixtureTypes(glowRequest: GlowRequest) {
        val idsToDelete = (glowRequest.originalMessage as GlowMessage.DeleteFixtureTypes).data.fixtureTypeIds
        state.patch.removeFixtureTypes(idsToDelete).getOrElse { errorList ->
            errorList.forEach { glowRequest.answer(it) }
        }
    }

    private fun handleDeleteFixtures(glowRequest: GlowRequest) {
        val uuidsToDelete = (glowRequest.originalMessage as GlowMessage.DeleteFixtures).data.uuids
        state.patch.removeFixtures(uuidsToDelete).getOrElse { errorList ->
            errorList.forEach {glowRequest.answer(it)}
        }
    }

    private fun handleUpdateFixture(glowRequest: GlowRequest) {
        val data = (glowRequest.originalMessage as GlowMessage.UpdateFixtures).data
        state.patch.updateFixtures(data).getOrElse { glowRequest.answer(it[0]) }
    }
}
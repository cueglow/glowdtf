package org.cueglow.server.objects.messages

import com.github.michaelbull.result.getOrElse
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.StateProvider

abstract class IncomingGlowRequestHandler(private val state: StateProvider, private val subscriptionHandler: SubscriptionHandler): Logging {
    fun handle(request: GlowRequest) {
        when (request.originalMessage.event) {
            GlowEvent.SUBSCRIBE -> subscriptionHandler.subscribe(request.client, (request.originalMessage as GlowMessage.Subscribe).data, state)
            GlowEvent.UNSUBSCRIBE -> subscriptionHandler.unsubscribe(request.client, (request.originalMessage as GlowMessage.Unsubscribe).data)
            GlowEvent.ADD_FIXTURES -> handleAddFixtures(request)
            GlowEvent.UPDATE_FIXTURES -> handleUpdateFixture(request)
            GlowEvent.REMOVE_FIXTURES -> handleRemoveFixtures(request)
            GlowEvent.REMOVE_FIXTURE_TYPES -> handleRemoveFixtureTypes(request)
            else -> logger.warn("Received a message with event ${request.originalMessage.event} which should not be sent by client. Discarding message. ")
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
package org.cueglow.server.json

import org.cueglow.server.objects.GlowError
import org.cueglow.server.websocket.AsyncClient

/**
 * Wrapper around [JsonMessage] that provides a convenient way to answer.
 */
class JsonRequest(val originalMessage: JsonMessage, private val client: AsyncClient) {
    fun answer(answerMessage: JsonMessage) = client.send(answerMessage.toJsonString())

    fun answer(event: JsonEvent, data: JsonData) = answer(JsonMessage(event, data, originalMessage.messageId))

    fun answer(error: GlowError) = answer(error.toJsonMessage(originalMessage.messageId))
}
package org.cueglow.server.objects.messages

import org.cueglow.server.json.AsyncClient
import org.cueglow.server.objects.GlowError

/**
 * Wrapper around [GlowMessage] that provides a convenient way to answer.
 */
class GlowRequest(val originalMessage: GlowMessage, private val client: AsyncClient) {
    fun answer(data: GlowMessage) = client.send(data)

    fun answer(error: GlowError) = client.send(error.toGlowMessage(originalMessage.messageId))
}
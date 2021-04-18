package org.cueglow.server.json

import org.cueglow.server.objects.messages.GlowMessage

/**
 * Represents a Client of the Server that can be sent a [GlowMessage] asynchronously (i.e. at any time)
 */
interface AsyncClient {
    /**
     * Send a [message].
     *
     * If the message cannot be sent (e.g. because the client is disconnected), nothing should happen.
     */
    fun send(message: GlowMessage)
}
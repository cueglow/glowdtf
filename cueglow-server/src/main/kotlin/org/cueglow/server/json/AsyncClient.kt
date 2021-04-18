package org.cueglow.server.json

import org.cueglow.server.objects.messages.GlowMessage

/**
 * Represents a Client of the Server that can be sent a [GlowMessage] asynchronously (i.e. at any time)
 */
interface AsyncClient {
    fun send(message: GlowMessage)
    // TODO what happens if send is called but client is disconnected? -> specify! maybe move to boolean return value to indicate this.
}
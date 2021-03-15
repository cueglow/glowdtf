package org.cueglow.server.websocket

import org.eclipse.jetty.websocket.api.Session

/**
 * Represents a Client of the Server that can be sent a String message asynchronously
 */
interface AsyncClient {
    fun send(message: String)
}

class WebSocketAsyncClient(private val session: Session) : AsyncClient {
    override fun send(message: String) {
        session.remote.sendStringByFuture(message)
    }
}
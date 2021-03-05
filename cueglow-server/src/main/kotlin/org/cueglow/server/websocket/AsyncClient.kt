package org.cueglow.server.websocket

import io.javalin.websocket.WsMessageContext

/**
 * Represents a Client of the Server that can be sent a String message asynchronously
 */
interface AsyncClient {
    fun send(message: String)
}

class WebSocketAsyncClient(private val websocket: WsMessageContext) : AsyncClient {
    override fun send(message: String) {
        websocket.send(message)
    }
}
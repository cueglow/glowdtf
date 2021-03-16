package org.cueglow.server.websocket

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.StateProvider
import org.cueglow.server.json.AsyncClient
import org.cueglow.server.json.JsonHandler
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.WebSocketAdapter

// TODO move from WebSocketAdapter to own implementation for WebSocketListener to improve null safety
class WebSocketConnection(val state: StateProvider): WebSocketAdapter(), AsyncClient, Logging {

    lateinit var jsonHandler: JsonHandler

    //--------------------------
    // AsyncClient functionality
    //--------------------------

    /**
     * Sends a message to the WebSocket client.
     * @throws NullPointerException when the WebSocket is disconnected
     */
    override fun send(message: String) {
        session.remote.sendStringByFuture(message)
    }

    //-------------------------------
    // WebSocketAdapter functionality
    //-------------------------------

    override fun onWebSocketConnect(newSession: Session) {
        super.onWebSocketConnect(newSession)
        logger.info("WebSocket connection with ${newSession.remoteAddress} established")
        jsonHandler = JsonHandler(this, state)
    }

    override fun onWebSocketText(message: String?) {
        super.onWebSocketText(message)
        logger.info("Received \"$message\" from websocket")
        jsonHandler.receive(message ?: "")
    }

    override fun onWebSocketBinary(payload: ByteArray?, offset: Int, len: Int) {
        super.onWebSocketBinary(payload, offset, len)
        logger.info("Received binary message from WebSocket: \"$payload\"")
    }

    override fun onWebSocketError(cause: Throwable?) {
        super.onWebSocketError(cause)
        logger.warn("WebSocket connection to ${session.remoteAddress} experienced an error. Cause: $cause")
    }

    override fun onWebSocketClose(statusCode: Int, reason: String?) {
        super.onWebSocketClose(statusCode, reason)
        logger.info("WebSocket connection to ${session.remoteAddress} closed. Status: $statusCode. Reason: $reason. ")
        // TODO pass close event to jsonHandler (unsubscribe, etc.)
    }
}

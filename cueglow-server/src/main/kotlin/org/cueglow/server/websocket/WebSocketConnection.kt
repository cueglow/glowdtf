package org.cueglow.server.websocket

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.StateProvider
import org.cueglow.server.json.JsonHandler
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.WebSocketAdapter

class WebSocketConnection(val state: StateProvider): WebSocketAdapter(), Logging {

    lateinit var jsonHandler: JsonHandler

    override fun onWebSocketConnect(session: Session) {
        super.onWebSocketConnect(session)
        logger.info("WebSocket connection with ${session.remoteAddress} established")
        val client = WebSocketAsyncClient(session)
        jsonHandler = JsonHandler(client, state)
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

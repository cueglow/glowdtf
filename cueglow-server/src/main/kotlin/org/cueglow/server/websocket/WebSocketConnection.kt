package org.cueglow.server.websocket

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.StateProvider
import org.cueglow.server.json.AsyncClient
import org.cueglow.server.json.JsonHandler
import org.cueglow.server.json.toJsonString
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.objects.messages.SubscriptionHandler
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.WebSocketListener

class WebSocketConnection(val state: StateProvider, val subscriptionHandler: SubscriptionHandler): WebSocketListener, AsyncClient, Logging {

    @Volatile
    var session: Session? = null

    lateinit var jsonHandler: JsonHandler

    //--------------------------
    // AsyncClient functionality
    //--------------------------

    /**
     * Sends a message to the WebSocket client.
     * If the client is disconnected, nothing will be done.
     */
    override fun send(message: String) {
        session?.remote?.sendStringByFuture(message)
    }

    override fun send(message: GlowMessage) {
        send(message.toJsonString())
    }

    //-------------------------------
    // WebSocketAdapter functionality
    //-------------------------------

    override fun onWebSocketConnect(newSession: Session) {
        session = newSession
        logger.info("WebSocket connection with ${newSession.remoteAddress} established")
        jsonHandler = JsonHandler(this, state, subscriptionHandler)
    }

    override fun onWebSocketText(message: String?) {
        logger.info("Received \"$message\" from websocket")
        jsonHandler.receive(message ?: "")
    }

    override fun onWebSocketBinary(payload: ByteArray?, offset: Int, len: Int) {
        logger.info("Received binary message from WebSocket: \"$payload\"")
    }

    override fun onWebSocketError(cause: Throwable?) {
        logger.warn("WebSocket connection to ${session?.remoteAddress} experienced an error. Cause: $cause")
    }

    override fun onWebSocketClose(statusCode: Int, reason: String?) {
        logger.info("WebSocket connection to ${session?.remoteAddress} closed. Status: $statusCode. Reason: $reason. ")
        session = null
        // TODO pass close event to jsonHandler (unsubscribe, etc.) (must still be added in StringReceiver interface)
    }
}

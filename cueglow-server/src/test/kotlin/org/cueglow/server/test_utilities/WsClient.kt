package org.cueglow.server.test_utilities

import org.apache.logging.log4j.kotlin.Logging
import org.awaitility.Awaitility
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

/*** WebSocket client for Testing */
class WsClient(uri: URI): WebSocketClient(uri), Logging {
    private val receivedMessages = ArrayDeque<String>()

    override fun onOpen(handshakedata: ServerHandshake?) {
        logger.info("WsClient opened")
    }

    override fun onMessage(message: String?) {
//        val pretty: String = (Parser.default().parse(StringBuilder(message)) as JsonObject).toJsonString(true)
        logger.info("WsClient received $message")
        receivedMessages.addLast(message ?: "")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        logger.info("WsClient closed. Code $code. Reason: $reason")
    }

    override fun onError(ex: Exception?) {
        logger.info("WsClient errored" + ex.toString())
    }

    fun receiveOneMessageBlocking(): String {
        Awaitility.await().until { receivedMessages.isNotEmpty() }
        return receivedMessages.removeFirst()
    }
}
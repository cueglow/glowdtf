package org.cueglow.server.objects

import io.javalin.websocket.WsMessageContext
import org.cueglow.server.api.GlowMessage

/**
 * Represents a Client of the Server
 */
interface GlowClient {
    fun sendMessage(message: GlowMessage)
}

class WebSocketGlowClient(private val websocket: WsMessageContext) : GlowClient {
    override fun sendMessage(message: GlowMessage) {
        websocket.send(message.toJsonString())
    }
}
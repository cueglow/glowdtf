package org.cueglow.server.handlers

import io.javalin.websocket.*
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.api.GlowRequest
import org.cueglow.server.api.parseGlowMessage
import org.cueglow.server.objects.WebSocketGlowClient

/** Handles WebSocket events and keeps a list of all connected clients */
class WebSocketHandler() : Logging {

    // TODO keep List<WebSocketGlowClient> instead?
    private val websocketList: MutableList<WsContext> = mutableListOf()

    fun handleMessage(ctx: WsMessageContext, handler: InEventHandler) {
        logger.info("Received \"${ctx.message()}\" from websocket")

        val glowMessage = parseGlowMessage(ctx.message())
        val glowRequest = GlowRequest(glowMessage, WebSocketGlowClient(ctx))

        handler.handleInRequest(glowRequest)
    }

    fun broadcastMessage(message: String) {
        logger.info("Sending $message to ${websocketList.size} sockets")
        for (ws in websocketList) {
            ws.send(message)
        }
    }

    fun handleConnect(ctx: WsConnectContext, handler: InEventHandler) {
//        ctx.send("Websocket to CueGlowServer opened")
        websocketList.add(ctx)
    }

    fun handleClose(ctx: WsCloseContext, handler: InEventHandler) {
        TODO("Not yet implemented")
        // TODO remove from websocketList
    }

    fun handleError(ctx: WsErrorContext, handler: InEventHandler) {
        TODO("Not yet implemented")
    }

}

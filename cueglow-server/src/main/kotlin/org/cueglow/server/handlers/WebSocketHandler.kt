package org.cueglow.server.handlers

import io.javalin.websocket.*
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.api.GlowRequest
import org.cueglow.server.api.parseGlowMessage
import org.cueglow.server.objects.WebSocketGlowClient

class WebSocketHandler() : Logging {

    private val websocketList: MutableList<WsContext> = mutableListOf()

    fun handleMessage(ctx: WsMessageContext, handler: InEventHandler) {
        logger.info("Received \"${ctx.message()}\" from websocket")

        val glowMessage = parseGlowMessage(ctx.message())

        handler.handleInRequest(GlowRequest(glowMessage, WebSocketGlowClient(ctx)))
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
    }

    fun handleError(ctx: WsErrorContext, handler: InEventHandler) {
        TODO("Not yet implemented")
    }

}

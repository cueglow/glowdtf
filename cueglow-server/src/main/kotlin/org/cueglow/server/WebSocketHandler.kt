package org.cueglow.server

import io.javalin.websocket.WsConnectContext
import io.javalin.websocket.WsContext
import io.javalin.websocket.WsMessageContext
import org.apache.logging.log4j.kotlin.Logging

class WebSocketHandler : Logging {

    private val websocketList: MutableList<WsContext> = mutableListOf()

    fun handleSocketMessage(ctx: WsMessageContext) {
        logger.info("Received \"${ctx.message()}\" from websocket")
    }

    fun broadcastMessage(message: String) {
        logger.info("Sending $message to ${websocketList.size} sockets")
        for (ws in websocketList) {
            ws.send(message)
        }
    }

    fun connectSocket(ctx: WsConnectContext) {
        ctx.send("Websocket to CueGlow opened")
        websocketList.add(ctx)
    }

}

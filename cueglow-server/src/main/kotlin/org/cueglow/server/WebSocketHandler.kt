package org.cueglow.server

import io.javalin.websocket.*
import org.apache.logging.log4j.kotlin.Logging

class WebSocketHandler : Logging {

    private val websocketList: MutableList<WsContext> = mutableListOf()

    fun handleMessage(ctx: WsMessageContext) {
        logger.info("Received \"${ctx.message()}\" from websocket")
        EventHandler.parseMessage(ctx);
    }

    fun broadcastMessage(message: String) {
        logger.info("Sending $message to ${websocketList.size} sockets")
        for (ws in websocketList) {
            ws.send(message)
        }
    }

    fun handleConnect(ctx: WsConnectContext) {
        ctx.send("Websocket to CueGlowServer opened")
        websocketList.add(ctx)
    }

    fun handleClose(ctx: WsCloseContext) {
        TODO("Not yet implemented")
    }

    fun handleError(ctx: WsErrorContext) {
        TODO("Not yet implemented")
    }

}

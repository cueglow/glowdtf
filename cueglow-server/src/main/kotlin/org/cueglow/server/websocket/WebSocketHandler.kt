package org.cueglow.server.websocket

import io.javalin.websocket.WsCloseContext
import io.javalin.websocket.WsConnectContext
import io.javalin.websocket.WsErrorContext
import io.javalin.websocket.WsMessageContext
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.json.AsyncStringReceiver

/** Handles WebSocket events and keeps a list of all connected clients */
class WebSocketHandler: Logging {

    private val websocketList: MutableList<WsConnectContext> = mutableListOf()

    fun broadcastMessage(message: String) {
        logger.info("Sending $message to ${websocketList.size} sockets")
        for (ws in websocketList) {
            ws.send(message)
        }
    }

    fun handleMessage(ctx: WsMessageContext, receiver: AsyncStringReceiver) {
        logger.info("Received \"${ctx.message()}\" from websocket")
        val client = WebSocketAsyncClient(ctx)
        receiver.receive(ctx.message(), client)
    }

    fun handleConnect(ctx: WsConnectContext) {
        websocketList.add(ctx)
    }

    fun handleClose(ctx: WsCloseContext) {
        TODO("Not yet implemented")
        // TODO remove from websocketList
    }

    fun handleError(ctx: WsErrorContext) {
        TODO("Not yet implemented")
    }

}



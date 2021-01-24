package org.cueglow.server

import com.beust.klaxon.Klaxon
import io.javalin.websocket.*
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.objects.*
import java.io.StringReader

class WebSocketHandler : Logging {

    private val websocketList: MutableList<WsContext> = mutableListOf()

    fun handleMessage(ctx: WsMessageContext) {
        logger.info("Received \"${ctx.message()}\" from websocket")

        // Parse JSON Message to GlowMessage
        val glowMessage = Klaxon()
            .fieldConverter(KlaxonGlowEvent::class, GlowEvent.glowEventConverter)
            .parse<GlowMessage>(StringReader(ctx.message()))

        if (glowMessage != null) {
            InEventDispatcher.dispatchRequest(GlowRequest(glowMessage, WebSocketGlowClient(ctx)))
        } else {
            TODO("Errorhandling is still WIP")
        }
    }

    fun broadcastMessage(message: String) {
        logger.info("Sending $message to ${websocketList.size} sockets")
        for (ws in websocketList) {
            ws.send(message)
        }
    }

    fun handleConnect(ctx: WsConnectContext) {
//        ctx.send("Websocket to CueGlowServer opened")
        websocketList.add(ctx)
    }

    fun handleClose(ctx: WsCloseContext) {
        TODO("Not yet implemented")
    }

    fun handleError(ctx: WsErrorContext) {
        TODO("Not yet implemented")
    }

}

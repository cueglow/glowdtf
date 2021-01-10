package org.cueglow.server

import io.javalin.Javalin
import org.apache.logging.log4j.kotlin.Logging


fun main(args: Array<String>) {
    CueGlowServer()
}

class CueGlowServer(port: Int = 7000) : Logging {

    init {
        val webSocketHandler = WebSocketHandler()
        logger.info("Starting CueGlow Server")
        val app = Javalin.create { config ->
            config.requestLogger { ctx, executionTimeMs ->
                logger.info("HTTP Request (${executionTimeMs}ms) \"${ctx.req.pathInfo}\"")
            }
            config.addStaticFiles("/webui")
            config.addSinglePageRoot("/", "/webui/index.html")
        }.apply {
            ws("/ws") { ws ->
                ws.onConnect {
                    EventHandler.webSocketHandler.handleConnect(it);
                }
                ws.onMessage {
                    EventHandler.webSocketHandler.handleMessage(it)
                }
                ws.onClose {
                    EventHandler.webSocketHandler.handleClose(it)
                }
                ws.onError {
                    EventHandler.webSocketHandler.handleError(it)
                }
            }
        }.start(port)
        logger.info("Serving frontend at http://localhost:$port")
    }

}


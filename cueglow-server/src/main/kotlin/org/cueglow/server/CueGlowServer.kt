package org.cueglow.server

import io.javalin.Javalin
import org.apache.logging.log4j.kotlin.Logging


fun main(args: Array<String>) {
    CueGlowServer()
}

class CueGlowServer(port: Int = 7000) : Logging {

    init {
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
                    InEventDispatcher.webSocketHandler.handleConnect(it)
                }
                ws.onMessage {
                    InEventDispatcher.webSocketHandler.handleMessage(it)
                }
                ws.onClose {
                    InEventDispatcher.webSocketHandler.handleClose(it)
                }
                ws.onError {
                    InEventDispatcher.webSocketHandler.handleError(it)
                }
            }
        }.start(port)
        logger.info("Serving frontend at http://localhost:$port")
    }

}

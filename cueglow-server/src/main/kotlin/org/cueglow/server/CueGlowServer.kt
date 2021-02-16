package org.cueglow.server

import io.javalin.Javalin
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.gdtf.handleNewFixtureType


fun main(args: Array<String>) {
    CueGlowServer()
}

class CueGlowServer(port: Int = 7000) : Logging {

    init {
        logger.info("Starting CueGlow Server")
        val webSocketHandler = WebSocketHandler()
        val app = Javalin.create { config ->
            config.requestLogger { ctx, executionTimeMs ->
                logger.info("HTTP Request (${executionTimeMs}ms) \"${ctx.req.pathInfo}\"")
            }
            config.addStaticFiles("/webui")
            config.addSinglePageRoot("/", "/webui/index.html")
        }.apply {
            ws("/ws") { ws ->
                ws.onConnect {
                    webSocketHandler.handleConnect(it)
                }
                ws.onMessage {
                    webSocketHandler.handleMessage(it)
                }
                ws.onClose {
                    webSocketHandler.handleClose(it)
                }
                ws.onError {
                    webSocketHandler.handleError(it)
                }
            }
        }.apply {
            post("/api/fixturetype") { handleNewFixtureType(it) }
        }.start(port)
        logger.info("Serving frontend at http://localhost:$port")
    }

}

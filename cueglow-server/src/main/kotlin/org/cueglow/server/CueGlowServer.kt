package org.cueglow.server

import io.javalin.Javalin
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.WebSocketHandler
import org.cueglow.server.gdtf.handleNewFixtureType
import org.cueglow.server.gdtf.handleNewGdtf


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
                    webSocketHandler.connectSocket(it)
                }

                ws.onMessage {
                    WebSocketHandler().handleSocketMessage(it)
                }
            }
        }.apply {
            post("/api/fixturetype") { handleNewFixtureType(it) }
        }.start(port)
        logger.info("Serving frontend at http://localhost:$port")
    }

}


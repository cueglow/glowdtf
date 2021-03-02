package org.cueglow.server

import io.javalin.Javalin
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.handlers.InEventHandler
import org.cueglow.server.handlers.WebSocketHandler
import org.cueglow.server.handlers.handleNewFixtureType


fun main(args: Array<String>) {
    CueGlowServer()
}

class CueGlowServer(port: Int = 7000) : Logging {
    init {
        logger.info("Starting CueGlow Server")
    }

    val state = StateProvider()

    val inEventHandler = InEventHandler(state)

    val webSocketHandler = WebSocketHandler()

    val app = Javalin.create { config ->
        config.requestLogger { ctx, executionTimeMs ->
            logger.info("HTTP Request (${executionTimeMs}ms) \"${ctx.req.pathInfo}\"")
        }
        config.addStaticFiles("/webui")
        config.addSinglePageRoot("/", "/webui/index.html")
    }.apply {
        ws("/ws") { ws ->
            ws.onConnect { ctx ->
                webSocketHandler.handleConnect(ctx, inEventHandler)
            }
            ws.onMessage { ctx ->
                webSocketHandler.handleMessage(ctx, inEventHandler)
            }
            ws.onClose { ctx ->
                webSocketHandler.handleClose(ctx, inEventHandler)
            }
            ws.onError { ctx ->
                webSocketHandler.handleError(ctx, inEventHandler)
            }
        }
    }.apply {
        post("/api/fixturetype") { ctx -> handleNewFixtureType(ctx, inEventHandler) }
    }.start(port)

    init {
        logger.info("Serving frontend at http://localhost:$port")
    }

    fun stop() = app.stop()
}

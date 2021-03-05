package org.cueglow.server

import io.javalin.Javalin
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.gdtf.GdtfHandler
import org.cueglow.server.json.JsonHandler
import org.cueglow.server.rest.handleGdtfUpload
import org.cueglow.server.websocket.WebSocketHandler


fun main(args: Array<String>) {
    CueGlowServer()
}

/**
 * The main class of CueGlow Server.
 *
 * Starts the Javalin server, associates network handlers and initiates state.
 */
class CueGlowServer(port: Int = 7000) : Logging {
    init {
        logger.info("Starting CueGlow Server")
    }

    val state = StateProvider()

    val jsonHandler = JsonHandler(state)
    val gdtfHandler = GdtfHandler(state.patch)

    val webSocketHandler = WebSocketHandler()

    val app: Javalin = Javalin.create { config ->
        config.requestLogger { ctx, executionTimeMs ->
            logger.info("HTTP Request (${executionTimeMs}ms) \"${ctx.req.pathInfo}\"")
        }
        config.addStaticFiles("/webui")
        config.addSinglePageRoot("/", "/webui/index.html")
    }.apply {
        ws("/ws") { ws ->
            ws.onConnect { ctx ->
                webSocketHandler.handleConnect(ctx)
            }
            ws.onMessage { ctx ->
                webSocketHandler.handleMessage(ctx, jsonHandler)
            }
            ws.onClose { ctx ->
                webSocketHandler.handleClose(ctx)
            }
            ws.onError { ctx ->
                webSocketHandler.handleError(ctx)
            }
        }
    }.apply {
        post("/api/fixturetype") { ctx -> handleGdtfUpload(ctx, gdtfHandler) }
    }.start(port)

    init {
        logger.info("Serving frontend at http://localhost:$port")
    }

    fun stop() {
        app.stop()
        logger.info("Server stopped")
    }
}

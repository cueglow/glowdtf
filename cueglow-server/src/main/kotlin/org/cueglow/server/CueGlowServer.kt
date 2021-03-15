package org.cueglow.server

import io.javalin.Javalin
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.gdtf.GdtfHandler
import org.cueglow.server.rest.handleGdtfUpload
import org.cueglow.server.websocket.WebSocketHandler
import org.eclipse.jetty.server.Server


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

    private val gdtfHandler = GdtfHandler(state.patch)

    val app: Javalin = Javalin.create { config ->
        // add our own WebSocket Handler
        config.server {
            val server = Server()
            server.handler = WebSocketHandler(state)
            return@server server
        }
        config.requestLogger { ctx, executionTimeMs ->
            logger.info("HTTP Request (${executionTimeMs}ms) \"${ctx.req.pathInfo}\"")
        }
        config.addStaticFiles("/webui")
        config.addSinglePageRoot("/", "/webui/index.html")
    }.apply {
        post("/api/fixturetype") { ctx -> handleGdtfUpload(ctx, gdtfHandler) }
    }.apply {
        start(port)
    }

    init {
        logger.info("Serving frontend at http://localhost:$port")
    }

    fun stop() {
        app.stop()
        logger.info("Server stopped")
    }
}

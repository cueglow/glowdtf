package org.cueglow.server

import io.javalin.Javalin
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.gdtf.GdtfHandler
import org.cueglow.server.json.JsonSubscriptionHandler
import org.cueglow.server.rest.handleGdtfUpload
import org.cueglow.server.websocket.GlowWebSocketHandler
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

    val jsonSubscriptionHandler = JsonSubscriptionHandler()

    val outEventHandler = OutEventHandler(listOf(jsonSubscriptionHandler))

    val state = StateProvider(outEventHandler.queue)

    private val gdtfHandler = GdtfHandler(state.patch)

    val app: Javalin = Javalin.create { config ->
        // add our own WebSocket Handler
        config.server {
            val server = Server()
            server.handler = GlowWebSocketHandler(state, jsonSubscriptionHandler)
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

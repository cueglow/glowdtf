package org.cueglow.server

import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.artnet.ArtNetSending
import org.cueglow.server.gdtf.GdtfHandler
import org.cueglow.server.json.JsonSubscriptionHandler
import org.cueglow.server.rest.handleGdtfUpload
import org.cueglow.server.websocket.GlowWebSocketHandler
import org.eclipse.jetty.server.Server
import java.util.concurrent.Executors
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    // TODO what happens if there is an exception in this main thread?
    GlowDtfServer()
}

/**
 * The main class of GlowDTF Server.
 *
 * Starts the Javalin server, associates network handlers and initiates state.
 */
class GlowDtfServer(port: Int = 7000) : Logging {
    init {
        logger.info("Starting GlowDTF Server")
    }

    val jsonSubscriptionHandler = JsonSubscriptionHandler()

    val outEventHandler = OutEventHandler(listOf(jsonSubscriptionHandler))

    val state = StateProvider(outEventHandler.queue)

    private val gdtfHandler = GdtfHandler(state.patch)

    val app: Javalin = Javalin.create { config ->
        // add our own WebSocket Handler
        config.server {
            val server = Server()
            server.handler = GlowWebSocketHandler(state, jsonSubscriptionHandler, this)
            return@server server
        }
        config.requestLogger { ctx, executionTimeMs ->
            logger.info("HTTP Request (${executionTimeMs}ms) \"${ctx.req.pathInfo}\"")
        }
        config.addStaticFiles("/webui", Location.CLASSPATH)
        config.addSinglePageRoot("/", "/webui/index.html")
    }.apply {
        post("/api/fixturetype") { ctx -> handleGdtfUpload(ctx, gdtfHandler) }
    }.apply {
        start(port)
    }

    init {
        logger.info("Serving frontend at http://localhost:$port")
    }

    val artNetSendingExecutor = Executors.newSingleThreadExecutor()
    val artNetSendingFuture = artNetSendingExecutor.submit(ArtNetSending(state))

    fun stop() {
        artNetSendingExecutor.shutdownNow()
        app.stop()
        logger.info("Server stopped")
        exitProcess(0)
    }
}

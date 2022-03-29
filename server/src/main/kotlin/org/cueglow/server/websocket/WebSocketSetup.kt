package org.cueglow.server.websocket

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.GlowDtfServer
import org.cueglow.server.StateProvider
import org.cueglow.server.objects.messages.SubscriptionHandler
import org.eclipse.jetty.websocket.server.WebSocketHandler
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse
import org.eclipse.jetty.websocket.servlet.WebSocketCreator
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory

/** Register the [WebSocketCreator] with the [WebSocketServletFactory].  */
class GlowWebSocketHandler(
    val state: StateProvider,
    val subscriptionHandler: SubscriptionHandler,
    private val server: GlowDtfServer,
): WebSocketHandler(), Logging {
    override fun configure(factory: WebSocketServletFactory?) {
        factory?.creator = GlowWebSocketCreator(state, subscriptionHandler, server)
    }
}

/** For every new WebSocket connection, create a [WebSocketConnection] and inject access to the state */
class GlowWebSocketCreator(val state: StateProvider, val subscriptionHandler: SubscriptionHandler, private val server: GlowDtfServer): WebSocketCreator {
    override fun createWebSocket(req: ServletUpgradeRequest?, resp: ServletUpgradeResponse?): Any {
        return WebSocketConnection(state, subscriptionHandler, server)
    }
}


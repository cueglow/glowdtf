package org.cueglow.server.websocket

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.StateProvider
import org.eclipse.jetty.websocket.server.WebSocketHandler
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse
import org.eclipse.jetty.websocket.servlet.WebSocketCreator
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory

/** Register the [WebSocketCreator] with the [WebSocketServletFactory].  */
class WebSocketHandler(val state: StateProvider): WebSocketHandler(), Logging {
    override fun configure(factory: WebSocketServletFactory?) {
        factory?.creator = WebSocketCreator(state)
    }
}

/** For every new WebSocket connection, create a [WebSocketConnection] and inject access to the state */
class WebSocketCreator(val state: StateProvider):
    WebSocketCreator {
    override fun createWebSocket(req: ServletUpgradeRequest?, resp: ServletUpgradeResponse?): Any {
        return WebSocketConnection(state)
    }
}


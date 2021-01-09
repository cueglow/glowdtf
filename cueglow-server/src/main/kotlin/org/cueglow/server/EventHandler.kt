package org.cueglow.server

import io.javalin.websocket.WsMessageContext
import org.cueglow.server.objects.GlowMessage

/**
 * Singleton Object to hold all openconnections and handle all websocket connection / messages
 */
object EventHandler {

    val webSocketHandler = WebSocketHandler();

    fun parseMessage(message: WsMessageContext) {
        val glowMessage = GlowMessage(message.message());
        // was will der von mir?
        // message aus json parsen
        // feststellen welches event
        //
        // Subscriptionhandler.newSubscriber(ctx, eventdata)
    }

}


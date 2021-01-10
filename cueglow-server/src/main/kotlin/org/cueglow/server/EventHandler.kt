package org.cueglow.server

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonJson
import io.javalin.websocket.WsMessageContext
import org.cueglow.server.objects.GlowEvent
import org.cueglow.server.objects.GlowMessage
import java.io.StringReader

/**
 * Singleton Object to hold all openconnections and handle all websocket connection / messages
 */
object EventHandler {

    val webSocketHandler = WebSocketHandler();

    fun parseMessage(message: WsMessageContext) {

        val json = Klaxon().parseJsonObject(StringReader(message.message()))

        val glowMessage = GlowMessage(json)

        // Delegate to0 the correct function / subroutine depending on the event
        when (glowMessage.glowEvent) {
            GlowEvent.SUBSCRIBE -> TODO()
            GlowEvent.UNSUBSCRIBE -> TODO()
            GlowEvent.STREAMINITIALSTATE -> TODO()
            GlowEvent.STREAMUPDATE -> TODO()
            GlowEvent.REQUESTSTREAMDATA -> TODO()
            GlowEvent.ERROR -> TODO()
            GlowEvent.ADDFIXTURES -> TODO()
            GlowEvent.FIXTURESADDED -> TODO()
            GlowEvent.UPDATEFIXTURES -> TODO()
            GlowEvent.DELETEFIXTURES -> TODO()
            GlowEvent.FIXTURETYPEADDED -> TODO()
            GlowEvent.DELETEFIXTURETYPES -> TODO()
        }
    }
}


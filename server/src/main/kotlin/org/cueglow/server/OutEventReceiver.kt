package org.cueglow.server

import org.cueglow.server.objects.messages.GlowMessage

interface OutEventReceiver {
    fun receive(glowMessage: GlowMessage)
}
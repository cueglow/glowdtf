package org.cueglow.server.json

import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.objects.messages.SubscriptionHandler

class JsonSubscriptionHandler: SubscriptionHandler() {
    override fun serializeMessage(glowMessage: GlowMessage): String = glowMessage.toJsonString()
}

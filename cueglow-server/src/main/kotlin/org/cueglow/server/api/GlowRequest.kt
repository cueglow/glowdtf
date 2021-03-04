package org.cueglow.server.api

import org.cueglow.server.objects.GlowClient
import org.cueglow.server.objects.GlowError

class GlowRequest(val glowMessage: GlowMessage, private val requestSource: GlowClient) {
    fun answer(answerMessage: GlowMessage) {
        requestSource.sendMessage(answerMessage)
    }
    fun answer(error: GlowError) = answer(error.toGlowMessage(glowMessage.messageId))
}
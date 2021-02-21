package org.cueglow.server.api

import org.cueglow.server.objects.GlowClient

class GlowRequest(val glowMessage: GlowMessage, private val requestSource: GlowClient) {
    fun answerRequest(answerMessage: GlowMessage) {
        requestSource.sendMessage(glowMessage)
    }
}
package org.cueglow.server.objects

class GlowRequest(val glowMessage: GlowMessage, private val requestSource: GlowClient) {
    fun answerRequest(answerMessage: GlowMessage) {
        requestSource.sendMessage(glowMessage)
    }
}
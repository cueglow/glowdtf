package org.cueglow.server.objects

class GlowRequest(val glowMessage: GlowMessage, private val glowClient: GlowClient) {
    fun answerRequest(answerMessage: GlowMessage) {
        glowClient.sendMessage(glowMessage)
    }
}
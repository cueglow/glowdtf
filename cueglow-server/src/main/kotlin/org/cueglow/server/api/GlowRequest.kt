package org.cueglow.server.api

import org.cueglow.server.objects.GlowClient
import org.cueglow.server.objects.GlowError

class GlowRequest(val glowMessage: GlowMessage, private val requestSource: GlowClient) {
    fun answerRequest(answerMessage: GlowMessage) {
        requestSource.sendMessage(answerMessage)
    }
    fun returnError(error: GlowError) = answerRequest(error.toGlowMessage())
}
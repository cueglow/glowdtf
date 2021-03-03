package org.cueglow.server.api

import org.cueglow.server.objects.GlowClient
import org.cueglow.server.objects.GlowError

class GlowRequest(val glowMessage: GlowMessage, private val requestSource: GlowClient) {
    fun answerRequest(answerMessage: GlowMessage) {
        requestSource.sendMessage(glowMessage)
    }
    fun returnError(error: GlowError) = requestSource.sendMessage(error.toGlowMessage())
}
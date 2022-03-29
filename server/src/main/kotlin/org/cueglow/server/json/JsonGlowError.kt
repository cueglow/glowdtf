package org.cueglow.server.json

import org.cueglow.server.objects.messages.GlowError

fun GlowError.toJsonString(messageId: Int? = null): String = this.toGlowMessage(messageId).toJsonString()
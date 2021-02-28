package org.cueglow.server.handlers

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.getError
import com.github.michaelbull.result.unwrap
import io.javalin.http.Context
import org.cueglow.server.api.GlowDataFixtureTypeAdded
import org.cueglow.server.api.GlowEvent
import org.cueglow.server.api.GlowMessage
import org.cueglow.server.gdtf.handleNewGdtf
import org.cueglow.server.objects.MissingFilePartError

/**
 * Network Handler for New Fixture Types
 */
fun handleNewFixtureType(ctx: Context) {
    val uploadedFile = ctx.uploadedFile("file") ?: run {
        ctx.status(400).result(MissingFilePartError.toJsonString())
        return
    }

    val result = handleNewGdtf(uploadedFile.content)

    if (result is Ok) {
        // return 200 with FixtureTypeId in JSON Message
        val glowMessage = GlowMessage(
            GlowEvent.FIXTURE_TYPE_ADDED,
            GlowDataFixtureTypeAdded(result.unwrap()),
            null
        )
        val jsonResponse = glowMessage.toJsonString()
        ctx.result(jsonResponse)
        ctx.status(200)
        ctx.contentType("application/json")
    } else {
        // result is Err
        ctx.status(400)
            .result(result.getError()?.toJsonString() ?: "")
    }
}
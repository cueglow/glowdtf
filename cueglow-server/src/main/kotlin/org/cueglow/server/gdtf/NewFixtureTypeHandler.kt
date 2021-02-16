package org.cueglow.server.gdtf

import com.beust.klaxon.JsonObject
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.github.michaelbull.result.unwrap
import io.javalin.http.Context
import org.cueglow.server.objects.GlowEvent
import org.cueglow.server.objects.GlowMessage

/**
 * Network Handler for New Fixture Types
 */
fun handleNewFixtureType(ctx: Context) {
    val uploadedFile = ctx.uploadedFile("file") ?: run {
        ctx.status(400).result("no file found under field file")
        return
    }

    val result = handleNewGdtf(uploadedFile.content)

    if (result is Ok) {
        // return 200 with FixtureTypeId in JSON Message
        val glowMessage = GlowMessage(
            GlowEvent.FIXTURE_TYPE_ADDED,
            JsonObject(mapOf("fixtureTypeId" to result.get())),
            null
        )
        ctx.result(glowMessage.toJsonString())
        ctx.status(200)
        ctx.contentType("application/json")
    } else {
        // result is Err
        // TODO
        // return 200 with error in JSON Message
        ctx.status(500)
    }
}
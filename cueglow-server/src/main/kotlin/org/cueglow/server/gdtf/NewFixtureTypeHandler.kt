package org.cueglow.server.gdtf

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.github.michaelbull.result.unwrap
import io.javalin.http.Context
import org.cueglow.server.objects.GlowDataFixtureTypeAdded
import org.cueglow.server.objects.GlowEvent
import org.cueglow.server.objects.GlowMessage
import org.cueglow.server.objects.UUIDConverter

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
            GlowDataFixtureTypeAdded(result.unwrap()),
            null
        )
        val JSONResponse = glowMessage.toJsonString()
        ctx.result(JSONResponse)
        ctx.status(200)
        ctx.contentType("application/json")
    } else {
        // result is Err
        // TODO
        // return 200 with error in JSON Message
        ctx.status(500)
    }
}
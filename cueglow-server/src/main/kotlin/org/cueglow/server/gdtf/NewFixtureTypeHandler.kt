package org.cueglow.server.gdtf

import io.javalin.http.Context

/**
 * Network Handler for New Fixture Types
 */
fun handleNewFixtureType(ctx: Context) {
    val uploadedFile = ctx.uploadedFile("file") ?: run {
        ctx.status(400).result("no file found under field file")
        return
    }

    handleNewGdtf(uploadedFile.content)
    // TODO
    // on Ok, return 200 with new FixtureTypeId in JSON Message
    // on Err, return 200 with error in JSON Message
}
package org.cueglow.server.rest

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.getError
import com.github.michaelbull.result.unwrap
import io.javalin.http.Context
import org.cueglow.server.gdtf.SyncGdtfReceiver
import org.cueglow.server.json.JsonDataFixtureTypeAdded
import org.cueglow.server.json.JsonEvent
import org.cueglow.server.json.JsonMessage
import org.cueglow.server.objects.MissingDescriptionXmlInGdtfError
import org.cueglow.server.objects.MissingFilePartError

/**
 * REST Network Handler for New Fixture Types via GDTF Upload
 */
fun handleGdtfUpload(ctx: Context, gdtfHandler: SyncGdtfReceiver) {
    val uploadedFile = ctx.uploadedFile("file") ?: run {
        ctx.status(400).result(MissingFilePartError().toJsonString())
        return
    }

    val result = gdtfHandler.receive(uploadedFile.content)

    when {
        result is Ok -> {
            // return 200 with FixtureTypeId in JSON Message
            val responseJsonMessage = JsonMessage(
                JsonEvent.FIXTURE_TYPE_ADDED,
                JsonDataFixtureTypeAdded(result.unwrap())
            )
            val jsonResponse = responseJsonMessage.toJsonString()
            ctx.result(jsonResponse)
            ctx.status(200)
            ctx.contentType("application/json")
        }
        result.getError() is MissingDescriptionXmlInGdtfError -> {
            // result is Err
            ctx.status(400)
                .result(result.getError()?.toJsonString() ?: "")
        }
        else -> {
            ctx.status(500)
                .result(result.getError()?.toJsonString() ?: "")
        }
    }
}
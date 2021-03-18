package org.cueglow.server.rest

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.getError
import com.github.michaelbull.result.unwrap
import io.javalin.http.Context
import org.cueglow.server.gdtf.SyncGdtfReceiver
import org.cueglow.server.json.toJsonString
import org.cueglow.server.objects.GdtfUnmarshalError
import org.cueglow.server.objects.MissingDescriptionXmlInGdtfError
import org.cueglow.server.objects.MissingFilePartError
import org.cueglow.server.objects.messages.GlowData
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage

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
            val jsonResponse = GlowMessage.FixtureTypeAdded(
                GlowData.FixtureTypeAdded(result.unwrap())
            ).toJsonString()
            ctx.result(jsonResponse)
            ctx.status(200)
            ctx.contentType("application/json")
        }
        result.getError().let {it is MissingDescriptionXmlInGdtfError || it is GdtfUnmarshalError} -> {
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
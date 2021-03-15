package org.cueglow.server.gdtf

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import org.cueglow.server.objects.GlowError
import org.cueglow.server.patch.Patch
import java.io.InputStream
import java.util.*

/** Represents a Receiver that takes a GDTF file in the form of an InputStream and
 * answers synchronously via a Result that needs to be handled by the network handler.
 */
interface SyncGdtfReceiver {
    fun receive(file: InputStream): Result<UUID, GlowError>
}

class GdtfHandler(val patch: Patch): SyncGdtfReceiver {
    /** Parses new GDTF and adds it to the Patch. */
    override fun receive(file: InputStream): Result<UUID, GlowError> {
        val parsedGdtf = parseGdtf(file).getOrElse { return Err(it) }
        val fixtureType = FixtureType(parsedGdtf)

        patch.addFixtureTypes(listOf(fixtureType)).getOrElse { return Err(it[0]) }

        return Ok(fixtureType.fixtureTypeId)
    }
}
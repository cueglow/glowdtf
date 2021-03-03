package org.cueglow.server.objects

import org.cueglow.server.api.GlowDataError
import org.cueglow.server.api.GlowEvent
import org.cueglow.server.api.GlowMessage
import java.util.*

/**
 * List of all custom Error types for CueGlow Server
 */
sealed class GlowError(val description: String = "") {
    fun toJsonString(): String = this.toGlowMessage().toJsonString()

    fun toGlowMessage(): GlowMessage = GlowMessage(
        GlowEvent.ERROR,
        GlowDataError(this::class.simpleName ?: "Unnamed GlowError", description),
        null
    )
}

/**
 * All Errors related to the ArtNetAddress class
 */
sealed class ArtNetAddressError: GlowError()
object InvalidArtNetAddress: ArtNetAddressError()
object InvalidArtNetNet: ArtNetAddressError()
object InvalidArtNetSubNet: ArtNetAddressError()
object InvalidArtNetUniverse: ArtNetAddressError()

/**
 * Errors related to DmxAddress class
 */
object InvalidDmxAddress: GlowError()

/**
 * All Errors related to GDTF files
 */
sealed class GdtfError(description: String): GlowError(description)
object MissingDescriptionXmlInGdtfError: GdtfError("The uploaded GDTF file must contain a file in its archive called \"description.xml\".")
class GdtfUnmarshalError(description: String): GlowError(description)

/**
 * Errors related to the Network API
 */
sealed class ApiError(description: String): GlowError(description)
object MissingFilePartError: ApiError("The GDTF upload request should include the GDTF file in a part with the name \"file\".")
class UnknownFixtureTypeIdError(fixtureTypeId: UUID): GlowError("The specified fixtureTypeId ${fixtureTypeId.toString()} was not found in the Patch. ")
class UnknownDmxModeError(dmxMode: String): GlowError("The specified DMX Mode ${dmxMode.toString()} was not found in the Fixture Type. ")
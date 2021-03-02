package org.cueglow.server.objects

import org.cueglow.server.api.GlowDataError
import org.cueglow.server.api.GlowEvent
import org.cueglow.server.api.GlowMessage

/**
 * List of all custom Error types for CueGlow Server
 */
sealed class GlowError(val description: String = "") {
    fun toJsonString(): String =
        GlowMessage(
            GlowEvent.ERROR,
            GlowDataError(this::class.simpleName ?: "Unnamed GlowError", description),
            null
        ).toJsonString()
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
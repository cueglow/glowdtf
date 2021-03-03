package org.cueglow.server.objects

import org.cueglow.server.api.GlowDataError
import org.cueglow.server.api.GlowEvent
import org.cueglow.server.api.GlowMessage
import java.util.*

/**
 * List of all custom Error types for CueGlow Server
 */
sealed class GlowError(val description: String = "") {
    val name: String = this::class.simpleName ?: "Unnamed GlowError"

    fun toJsonString(): String = this.toGlowMessage().toJsonString()

    fun toGlowMessage(messageId: Int? = null): GlowMessage = GlowMessage(
        GlowEvent.ERROR,
        GlowDataError(name, description),
        messageId
    )
}

// Errors related to the ArtNetAddress class
class InvalidArtNetAddress(value: Int): GlowError("The proposed Art-Net Address ${value} is not within 0 to 32_767. ")
class InvalidArtNetNet(value: Int): GlowError("The proposed Art-Net Net ${value} is not within 0 to 127. ")
class InvalidArtNetSubNet(value: Int): GlowError("The proposed Art-Net Sub-Net ${value} is not within 0 to 15. ")
class InvalidArtNetUniverse(value: Int): GlowError("The proprosed Art-Net Universe ${value} is not within 0 to 15. ")

// Errors related to DmxAddress class
class InvalidDmxAddress(value: Int): GlowError("The proposed DMX Address ${value} is not within 1 to 512. ")

// Errors related to GDTF files
class MissingDescriptionXmlInGdtfError: GlowError("The uploaded GDTF file did not contain a file called 'description.xml' in its archive. ")
class GdtfUnmarshalError(description: String): GlowError(description)

// Errors related to the Network API
class MissingFilePartError: GlowError("The GDTF upload request should include the GDTF file in a part with the name 'file'. ")
class UnknownFixtureTypeIdError(fixtureTypeId: UUID): GlowError("The specified fixtureTypeId '${fixtureTypeId}' was not found in the Patch. ")
class UnknownDmxModeError(dmxMode: String): GlowError("The specified DMX Mode '${dmxMode}' was not found in the Fixture Type. ")
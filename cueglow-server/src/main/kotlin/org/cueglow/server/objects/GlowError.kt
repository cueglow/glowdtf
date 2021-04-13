package org.cueglow.server.objects

import org.cueglow.server.json.toJsonString
import org.cueglow.server.objects.messages.GlowData
import org.cueglow.server.objects.messages.GlowEvent
import org.cueglow.server.objects.messages.GlowMessage
import java.util.*

/**
 * Custom Error Type for CueGlow Server
 *
 * The inheritors of GlowError are meant to be instantiated with the corresponding error information and
 * can then be converted to [GlowMessage] or serialized to JSON directly.
 */
sealed class GlowError(val description: String = "") {
    val name: String = this::class.simpleName ?: "Unnamed GlowError"


    // TODO move these things into json package -> they don't belong into GlowError because GlowError is API independent
    fun toJsonString(messageId: Int? = null): String = this.toGlowMessage(messageId).toJsonString()

    fun toGlowMessage(messageId: Int? = null): GlowMessage = GlowMessage(
        GlowEvent.ERROR,
        GlowData.Error(name, description),
        messageId
    )
}

// Errors related to the ArtNetAddress class
class InvalidArtNetAddress(value: Int): GlowError("The proposed Art-Net Address $value is not within 0 to 32_767. ")
class InvalidArtNetNet(value: Int): GlowError("The proposed Art-Net Net $value is not within 0 to 127. ")
class InvalidArtNetSubNet(value: Int): GlowError("The proposed Art-Net Sub-Net $value is not within 0 to 15. ")
class InvalidArtNetUniverse(value: Int): GlowError("The proposed Art-Net Universe $value is not within 0 to 15. ")

// Errors related to DmxAddress class
class InvalidDmxAddress(value: Int): GlowError("The proposed DMX Address $value is not within 1 to 512. ")

// Errors related to GDTF files
class MissingDescriptionXmlInGdtfError: GlowError("The uploaded GDTF file did not contain a file called 'description.xml' in its archive. ")
class GdtfUnmarshalError(description: String): GlowError(description)
class FixtureTypeAlreadyExistsError(fixtureTypeId: UUID): GlowError("A fixture type with the fixtureTypeId '$fixtureTypeId' already exists in the Patch. Updating Fixture Types is not supported. ")

// Errors related to the Network API
class MissingFilePartError: GlowError("The GDTF upload request should include the GDTF file in a part with the name 'file'. ")
class UnpatchedFixtureTypeIdError(fixtureTypeId: UUID): GlowError("The specified fixtureTypeId '${fixtureTypeId}' was not found in the Patch. ")
class UnknownDmxModeError(dmxMode: String, fixtureTypeId: UUID): GlowError("The specified DMX Mode '${dmxMode}' was not found in the Fixture Type with fixtureTypeId '$fixtureTypeId'. ")
class UnknownFixtureUuidError(uuid: UUID): GlowError("The specified fixture uuid '$uuid' was not found in the Patch. ")
class FixtureUuidAlreadyExistsError(uuid: UUID): GlowError("A fixture with the uuid '$uuid' already exists in the Patch. ")
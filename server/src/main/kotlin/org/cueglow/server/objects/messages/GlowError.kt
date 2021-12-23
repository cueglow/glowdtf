package org.cueglow.server.objects.messages

import com.beust.klaxon.Json
import java.util.*

/**
 * Custom Error Type for CueGlow Server
 *
 * The inheritors of GlowError are meant to be instantiated with the corresponding error information and
 * can then be converted to [GlowMessage] or serialized to JSON directly.
 *
 * While this used to be a sealed class, it now is open to allow the Klaxon JSON Parser to construct a
 * GlowError from JSON.
 */
open class GlowError(@Json(index=1)val description: String = "") {
    @Json(index=0)val name: String = this::class.simpleName ?: "Unnamed GlowError"

    fun toGlowMessage(messageId: Int? = null): GlowMessage = GlowMessage.Error(
        this,
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
class InvalidGdtfError(description: String): GlowError(description)

// Errors related to GDTF Fixture State
class InvalidFixtureState(description: String): GlowError(description)

// Errors related to the Network API
class MissingFilePartError: GlowError("The GDTF upload request should include the GDTF file in a part with the name 'file'. ")
class UnpatchedFixtureTypeIdError(fixtureTypeId: UUID): GlowError("The specified fixtureTypeId '${fixtureTypeId}' was not found in the Patch. ")
class UnknownDmxModeError(dmxMode: String, fixtureTypeId: UUID): GlowError("The specified DMX Mode '${dmxMode}' was not found in the Fixture Type with fixtureTypeId '$fixtureTypeId'. ")
class UnknownFixtureUuidError(uuid: UUID): GlowError("The specified fixture uuid '$uuid' was not found in the Patch. ")
class FixtureUuidAlreadyExistsError(uuid: UUID): GlowError("A fixture with the uuid '$uuid' already exists in the Patch. ")
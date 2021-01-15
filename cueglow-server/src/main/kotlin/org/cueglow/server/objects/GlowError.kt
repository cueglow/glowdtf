package org.cueglow.server.objects

/**
 * List of all custom Error types for CueGlow Server
 */
sealed class GlowError

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
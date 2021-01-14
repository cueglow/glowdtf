package org.cueglow.server.objects

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.Ok

/**
 * Represents an Art-Net v4 Port-Address.
 *
 * Can only be instantiated with [tryFrom].
 *
 * @property[value] A Short representing the 15-bit Port-Address.
 */
class ArtNetAddress private constructor(val value: Short) {

    companion object Factory {
        /**
         * Instantiate ArtNetAddress from Short.
         *
         * @return Result of valid ArtNetAddress or Error for invalid input (i.e. negative)
         */
        fun tryFrom(input: Short): Result<ArtNetAddress, InvalidArtNetAddress> {
            return if (input in 0..32_767) {
                Ok(ArtNetAddress(input))
            } else {
                Err(InvalidArtNetAddress)
            }
        }
        /**
         * Instantiate ArtNetAddress from Int.
         *
         * @return Result of valid ArtNetAddress or Error for invalid input (i.e. negative or too big)
         */
        fun tryFrom(input: Int): Result<ArtNetAddress, InvalidArtNetAddress> {
            return if (input in 0..32_767) {
                Ok(ArtNetAddress(input.toShort()))
            } else {
                Err(InvalidArtNetAddress)
            }
        }
        /**
         * Instantiate ArtNetAddress from Net, Sub-Net and Universe.
         *
         * Net must be a 7-bit number from 0 to 127, Sub-Net a 4-bit number from 0 to 15
         * and Universe a 4-bit number from 0 to 15.
         *
         * @return Result of valid ArtNetAddress or Error for invalid input
         */
        fun tryFrom(net: Int, subNet: Int, universe: Int): Result<ArtNetAddress, ArtNetAddressError> {
            return when {
                net !in 0..127 -> Err(InvalidArtNetNet)
                subNet !in 0..15 -> Err(InvalidArtNetSubNet)
                universe !in 0..15 -> Err(InvalidArtNetUniverse)
                else -> {
                    val value = (net shl 8) + (subNet shl 4) + universe
                    Ok(ArtNetAddress(value.toShort()))
                }
            }
        }
    }

    /**
     * @return 7-bit Art-Net "Net" address as Int
     */
    fun getNet() = (value.toInt() shr 8) and 0b111_1111

    /**
     * @return 4-bit Art-Net "Sub-Net" address as Int
     */
    fun getSubNet() = (value.toInt() shr 4) and 0b1111

    /**
     * @return 4-bit Art-Net "Universe" address as Int
     */
    fun getUniverse() = value.toInt() and 0b1111
}
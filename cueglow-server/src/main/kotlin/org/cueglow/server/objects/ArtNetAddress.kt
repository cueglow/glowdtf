package org.cueglow.server.objects

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.cueglow.server.objects.ArtNetAddress.Factory.tryFrom

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
        fun tryFrom(input: Short) = tryFrom(input.toInt())
        /**
         * Instantiate ArtNetAddress from Int.
         *
         * @return Result of valid ArtNetAddress or Error for invalid input (i.e. negative or too big)
         */
        fun tryFrom(input: Int): Result<ArtNetAddress, InvalidArtNetAddress> {
            return if (input in 0..32_767) {
                Ok(ArtNetAddress(input.toShort()))
            } else {
                Err(InvalidArtNetAddress(input))
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
        fun tryFrom(net: Int, subNet: Int, universe: Int): Result<ArtNetAddress, GlowError> {
            return when {
                net !in 0..127 -> Err(InvalidArtNetNet(net))
                subNet !in 0..15 -> Err(InvalidArtNetSubNet(subNet))
                universe !in 0..15 -> Err(InvalidArtNetUniverse(universe))
                else -> {
                    val value = (net shl 8) + (subNet shl 4) + universe
                    Ok(ArtNetAddress(value.toShort()))
                }
            }
        }
    }

    /**
     * @return 7-bit Art-Net "Net" address as Int
     *
     * The "Net" address is given by the bits 8 to 14 (starting from 0 for the least significant bit)
     * in the Art-Net PortAddress.
     * To access these bits, we just shift right by 8.
     * This works because all bits of higher significance than the "Net"-bits are ensured to be zero (ArtNetAddress is
     * positive and in range). When shifting right, the leftmost bits are filled with copies of the sign bit, here 0
     * because the ArtNetAddress is positive. Therefore, after shifting, all bits but the lowest 7 bits (that form
     * the "Net" address) are zero. No bitmasking is needed.
     */
    fun getNet() = value.toInt() shr 8

    /**
     * @return 4-bit Art-Net "Sub-Net" address as Int
     *
     * The "Sub-Net" address is given by the bits 4 to 7 (starting from 0 for the least significant bit)
     * in the Art-Net PortAddress.
     * To access these bits, we first shift right by 4. Then, the relevant 4 bits are the lowest bits in the number.
     * Finally, the remaining higher significance bits are "and"-masked with a number where the 4 lowest bits are set
     * to 1 and the other bits are 0.
     */
    fun getSubNet() = (value.toInt() shr 4) and 0b1111

    /**
     * @return 4-bit Art-Net "Universe" address as Int
     *
     * The "Universe" address is given by the 4 bits 0 to 3 (starting from 0 for the least significant bit)
     * in the Art-Net PortAddress.
     * To access these bits, we "and"-mask with a number where the 4 lowest bits are set to 1 and the other bits are
     * set to 0.
     */
    fun getUniverse() = value.toInt() and 0b1111
}
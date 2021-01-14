package org.cueglow.server.objects

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.Ok

class ArtNetAddress private constructor(val value: Short) {

    companion object Factory {
        // from Short
        fun tryFrom(input: Short): Result<ArtNetAddress, InvalidArtNetAddress> {
            return if (input in 0..32_767) {
                Ok(ArtNetAddress(input))
            } else {
                Err(InvalidArtNetAddress)
            }
        }
        // from Int
        fun tryFrom(input: Int): Result<ArtNetAddress, InvalidArtNetAddress> {
            return if (input in 0..32_767) {
                Ok(ArtNetAddress(input.toShort()))
            } else {
                Err(InvalidArtNetAddress)
            }
        }
        // from Triple
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


    fun getNet() = (value.toInt() shr 8) and 0b111_1111

    fun getSubNet() = (value.toInt() shr 4) and 0b1111

    fun getUniverse() = value.toInt() and 0b1111
}
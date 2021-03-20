package org.cueglow.server.objects

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.cueglow.server.objects.DmxAddress.Factory.tryFrom
import org.cueglow.server.objects.messages.InvalidDmxAddress

/**
 * Represents a DMX Address.
 *
 * Can only be instantiated with [tryFrom].
 *
 * DMX refers to DMX512-A specified in ANSI E1.11.
 * The DMX Address refers to DMX slots 1 to 512.
 *
 * @property[value] A Short representing the DMX Address.
 */
class DmxAddress private constructor(val value: Short) {
    companion object Factory {
        /**
         * Instantiate DmxAddress from Short.
         *
         * @return Result of valid DmxAddress or Error for invalid input (i.e. too small or too big)
         */
        fun tryFrom(input: Short) = tryFrom(input.toInt())
        /**
         * Instantiate DmxAddress from Int.
         *
         * @return Result of valid DmxAddress or Error for invalid input (i.e. too small or too big)
         */
        fun tryFrom(input: Int): Result<DmxAddress, InvalidDmxAddress> {
            return if (input in 1..512) {
                Ok(DmxAddress(input.toShort()))
            } else {
                Err(InvalidDmxAddress(input))
            }
        }
    }
}
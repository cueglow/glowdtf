package org.cueglow.server.gdtf

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.cueglow.server.objects.messages.GlowError
import org.cueglow.server.objects.messages.InvalidGdtfError

/**
 * Parse a GDTF DMXValue like "255/1" or "255/1s" into a numeric value with `outputBytes` many Bytes.
 *
 * To downconvert, we always use truncation.
 *
 * Upconversion depends on the mode:
 * - Without an "s" in the string, we periodically repeat the bits and truncate to the wanted outputBytes
 * - With an "s" in the string, we just shift left, filling with zeros from the right.
 */
fun parseDmxValue(str: String, outputBytes: Short): Result<Long, GlowError> {
    val parts = str.split("/")
    if (parts.size != 2) { return Err(InvalidGdtfError("The DMXValue '${str}' does not contain exactly one delimiting slash."))
    }
    // parse and validate input value
    val inputValue = parts[0].toLongOrNull() ?: return Err(InvalidGdtfError("The first part of DMXValue '${str}' could not be parsed to a 64-bit signed integer."))
    if (inputValue < 0) { return Err(InvalidGdtfError("The DMXValue '${str}' contains a negative DMX value."))
    }
    var byteSpec = parts[1]
    val shiftMode = if (byteSpec.last() == 's') {
        byteSpec = byteSpec.dropLast(1)
        true
    } else {
        false
    }
    val inputBytes = byteSpec.toByteOrNull() ?: return Err(InvalidGdtfError("The second part of the DMXValue '${str}' could not be parsed to a number of bytes."))

    // Validate Input Value is not too big for input bytes
    val inputMaxValue = (1L shl 8*inputBytes) - 1
    if (inputValue > inputMaxValue ) { return Err(InvalidGdtfError("The DMXValue '${str}' is too big for its number of bytes."))
    }

    val byteDiff = outputBytes - inputBytes
    val output = if (byteDiff <= 0) {
        // downconvert by truncation
        inputValue shr -8*byteDiff
    } else if (shiftMode) {
        // upconvert by shift
        inputValue shl 8*byteDiff
    } else {
        // upconvert in periodic mode
        // shifts where whole input is repeated
        val fullShifts = byteDiff / inputBytes
        val partialShifts = byteDiff % inputBytes
        val dropForPartial = inputBytes - partialShifts
        var out = inputValue
        repeat(fullShifts) {
            out = (out shl 8*inputBytes) or inputValue
        }
        (out shl 8*partialShifts) or (inputValue shr 8*dropForPartial)
    }
    return Ok(output)
}
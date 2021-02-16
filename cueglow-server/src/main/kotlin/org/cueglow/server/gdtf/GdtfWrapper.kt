package org.cueglow.server.gdtf

import org.cueglow.gdtf.GDTF
import java.util.*

/**
 * Immutable wrapper around GDTF
 *
 * Currently only provides the most basic properties
 */
class GdtfWrapper(private val gdtf: GDTF) {
    val name: String = gdtf.fixtureType.name
    val manufacturer: String = gdtf.fixtureType.manufacturer
    val fixtureTypeId: UUID = UUID.fromString(gdtf.fixtureType.fixtureTypeID)
    val modes: List<DmxMode> = run {
        gdtf.fixtureType.dmxModes.dmxMode.map {
            // TODO
            // calculating channel count is hard for GDTF due to Geometry Reference
            // https://gdtf-share.com/forum/index.php?/topic/340-getting-channel-count-from-a-gdtf-fixture/
            // this should be done differently once the facilities to calculate actual DMX Channel Layout are in place

            val dmxChannels = it.dmxChannels?.dmxChannel ?: TODO("Err")

            val channelCount = dmxChannels.map{ dmxChannel ->
                        val offsetString = dmxChannel.offset ?: TODO("Err")
                        offsetString.split(",").map { it.toInt() }.maxOf {it}
                    }.maxOrNull() ?: TODO("Err")

            DmxMode(it.name, channelCount)
        }}
}

data class DmxMode(val name: String, val channelCount: Int)
package org.cueglow.server.gdtf

import com.gdtf_share.schemas.device.GDTF
import java.util.*

/**
 * Immutable wrapper around GDTF
 *
 * Currently only provides the most basic properties
 */
class GdtfWrapper(private val gdtf: GDTF) {
    val name = gdtf.fixtureType.name
    val manufacturer = gdtf.fixtureType.manufacturer
    val fixtureTypeId = UUID.fromString(gdtf.fixtureType.fixtureTypeID)
    val modes: List<DmxMode> = run {
        gdtf.fixtureType.dmxModes.dmxMode.map {
            // TODO
            // calculating channel count is hard for GDTF
            // https://gdtf-share.com/forum/index.php?/topic/340-getting-channel-count-from-a-gdtf-fixture/
            // current approach does not work for multi-instance fixtures

            val dmxChannels = it.dmxChannels?.dmxChannel ?: TODO("Err")

            val channelCount = dmxChannels.map{ dmxChannel ->
                        val offsetString = dmxChannel.offset ?: TODO("Err")
                        offsetString.split(",").map { it.toInt() }.maxOf {it}
                    }.maxOrNull() ?: TODO("Err")

            DmxMode(it.name, channelCount)
        }}
}

data class DmxMode(val name: String, val channelCount: Int)
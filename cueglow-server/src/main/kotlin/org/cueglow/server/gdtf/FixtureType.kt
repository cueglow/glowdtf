package org.cueglow.server.gdtf

import org.cueglow.gdtf.GDTF
import org.cueglow.server.objects.ImmutableList
import java.util.*

/**
 * Immutable wrapper around GDTF
 *
 * Currently only provides the most basic properties (dmxMode channelCount is also wrong, see comments).
 */
class FixtureType(private val gdtf: GDTF) {
    val name: String = gdtf.fixtureType.name
    val manufacturer: String = gdtf.fixtureType.manufacturer
    val fixtureTypeId: UUID = UUID.fromString(gdtf.fixtureType.fixtureTypeID)
    val modes: ImmutableList<DmxMode> = run {
        ImmutableList(
            gdtf.fixtureType.dmxModes.dmxMode.map { dmxMode ->
                // TODO
            // calculating channel count is hard for GDTF due to Geometry Reference
            // https://gdtf-share.com/forum/index.php?/topic/340-getting-channel-count-from-a-gdtf-fixture/
            // this should be done differently once the facilities to calculate actual DMX Channel Layout are in place

            val dmxChannels = dmxMode.dmxChannels?.dmxChannel ?: TODO("Err")

            val channelCount = dmxChannels.map{ dmxChannel ->
                        val offsetString = dmxChannel.offset ?: TODO("Err")
                        offsetString.split(",").map { it.toInt() }.maxOf {it}
                    }.maxOrNull() ?: TODO("Err")

            DmxMode(dmxMode.name, channelCount, listOf(listOf())) //TODO create channelLayout
    })}
}

/**
 * @property channelLayout A list of DMX breaks, each of which is a list of channel name strings. If the channel name is
 *     null, it means this specific DMX offset is empty.
 *     Example for a channel name: "LED1 -> GenericLED_Dimmer (1/2)"
 *     where the channel references the Geometry GenericLED, the channel is instantiated by the Geometry Reference LED1,
 *     the controlled Attribute is Dimmer and it is the coarse channel of two channels controlling the dimmer with
 *     16 bits.
 */
data class DmxMode(val name: String, val channelCount: Int, val channelLayout: List<List<String?>>)
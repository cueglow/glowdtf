package org.cueglow.server.gdtf

import org.cueglow.gdtf.DMXChannel
import org.cueglow.gdtf.DMXMode
import org.cueglow.server.objects.InvalidGdtfException

/**
 * Represents a GDTF DMX Mode
 *
 * @property multiByteChannels A list of MultiByteChannel's, which correspond to the DmxChannel's in GDTF but with
 *     abstract channels instantiated.
 *
 * @property channelLayout A list of DMX breaks, each of which is a list of channel name strings. If the channel name is
 *     null, it means this specific DMX offset is empty.
 *     Example for a channel name: "LED1 -> GenericLED_Dimmer (1/2)"
 *     where the channel references the Geometry GenericLED, the channel is instantiated by the Geometry Reference LED1,
 *     the controlled Attribute is Dimmer and it is the coarse channel of two channels controlling the dimmer with
 *     16 bits.
 *     If the channel is only 8-bit, the " (1/1)" at the end is omitted. If the channel does not come from a
 *     referenced Geometry, the "LED1 -> " is omitted.
 */
data class GlowDmxMode(
    val name: String,
    val channelCount: Int,
    val multiByteChannels: List<MultiByteChannel>,
    // TODO ADG of channel function dependencies
    val channelLayout: List<List<String?>>,
)

//--------------------
// Create from GDTF
//--------------------

fun GlowDmxMode(mode: DMXMode, abstractGeometries: List<AbstractGeometry>): GlowDmxMode {
    // allocate
    val channelLayout: MutableList<MutableList<String?>> = mutableListOf(mutableListOf())
    val multiByteChannels: MutableList<MultiByteChannel> = mutableListOf()
    try {
        // populate multiByteChannels
        mode.dmxChannels.dmxChannel.forEach { channel ->
            val instantiatedChannels = instantiateChannel(channel, abstractGeometries)
            multiByteChannels.addAll(instantiatedChannels)
        }
        // iterate over multiByteChannels while filling channelLayout
        multiByteChannels.forEach { multiByteChannel ->
            channelLayout.putMultiByteChannelNames(multiByteChannel)
        }
    } catch (exception: InvalidGdtfException) {
        throw InvalidGdtfException("Error in DMX Mode '${mode.name}'", exception)
    }
    val channelCount: Int = channelLayout.sumBy { it.size }
    return GlowDmxMode(mode.name, channelCount, multiByteChannels, channelLayout)
}

fun instantiateChannel(channel: DMXChannel, abstractGeometries: List<AbstractGeometry>): List<MultiByteChannel> {
    // check if geometry referenced by channel is abstract
    val abstractGeometry = abstractGeometries.find { it.name == channel.geometry }
    return if (abstractGeometry != null) {
        // channel is abstract, so it is instantiated by each GeometryReference to AbstractGeometry
        abstractGeometry.referencedBy.map { geometryReference ->
            MultiByteChannel.fromAbstractChannel(channel, geometryReference)
        }
    } else {
        // channel is concrete (i.e. not abstract)
        listOf(MultiByteChannel.fromConcreteChannel(channel))
    }
}

private fun MutableList<MutableList<String?>>.putMultiByteChannelNames(multiByteChannel: MultiByteChannel) {
    multiByteChannel.offsets.forEachIndexed { offsetIndex, offset ->
        val nameToWrite =
            appendByteNumber(multiByteChannel.name, offsetIndex, multiByteChannel.offsets.size)
        this.putChannelNameAt(nameToWrite, multiByteChannel.dmxBreak, offset)
    }
}

private fun MutableList<MutableList<String?>>.putChannelNameAt(nameToWrite: String, dmxBreak: Int, offset: Int) {
    val breakList = this.elementAtOrFillWith(dmxBreak - 1, mutableListOf())
    val nameAtWriteIndex = breakList.elementAtOrFillWith(offset - 1, null)
    if (nameAtWriteIndex != null) {
        // channel name already set -> there is a collision between two channels
        throw InvalidGdtfException(
            "GDTF file produces DMX Channel collision at break $dmxBreak and offset $offset between '$nameAtWriteIndex' and '$nameToWrite'"
        )
    } else {
        breakList[offset - 1] = nameToWrite
    }
}

private fun appendByteNumber(nameWithoutByteNumber: String, offsetIndex: Int, byteNumber: Int) =
    if (byteNumber == 1) {
        nameWithoutByteNumber
    } else {
        "$nameWithoutByteNumber (${offsetIndex + 1}/$byteNumber)"
    }

/**
 * Gets Element at the given index. If the list is too short, it fills the list with [fillWith] until the given index is accessible.
 */
fun <T> MutableList<T>.elementAtOrFillWith(index: Int, fillWith: T): T {
    return this.elementAtOrElse(index) {
        while (this.size < index + 1) {
            this.add(fillWith)
        }
        this[index]
    }
}
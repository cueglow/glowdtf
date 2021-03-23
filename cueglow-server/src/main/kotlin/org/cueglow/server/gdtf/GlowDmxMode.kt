package org.cueglow.server.gdtf

import org.cueglow.gdtf.DMXChannel
import org.cueglow.gdtf.DMXMode
import org.cueglow.gdtf.GeometryReference

/**
 * Represents a GDTF DMX Mode
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
data class GlowDmxMode(val name: String, val channelCount: Int, val channelLayout: List<List<String?>>)

//--------------------
// Create from GDTF
//--------------------

fun GlowDmxMode(mode: DMXMode, abstractGeometries: List<AbstractGeometry>): GlowDmxMode {
    // allocate channelLayout
    val channelLayout: MutableList<MutableList<String?>> = mutableListOf(mutableListOf())
    // iterate over channels while filling channelLayout
    mode.dmxChannels.dmxChannel.forEach { channel ->
        try {
            channelLayout.addChannel(channel, abstractGeometries)
        } catch (error: java.lang.IllegalArgumentException) {
            throw java.lang.IllegalArgumentException("GDTF Error in DMX Mode ${mode.name}", error)
        }
    }
    val channelCount: Int = channelLayout.sumBy { it.size }
    return GlowDmxMode(mode.name, channelCount, channelLayout)
}

private fun MutableList<MutableList<String?>>.addChannel(channel: DMXChannel, abstractGeometries: List<AbstractGeometry>) {
    // check if geometry referenced by channel is abstract
    val abstractGeometry = abstractGeometries.find { it.name == channel.geometry }
    if (abstractGeometry != null) {
        // channel is abstract, so it is instantiated by each GeometryReference to AbstractGeometry
        abstractGeometry.referencedBy.forEach { geometryReference ->
            val multiByteChannel = MultiByteChannel.fromAbstractChannel(channel, geometryReference)
            this.putMultiByteChannelNames(multiByteChannel)
        }
    } else {
        // channel is concrete (i.e. not abstract)
        val multiByteChannel = MultiByteChannel.fromConcreteChannel(channel)
        this.putMultiByteChannelNames(multiByteChannel)
    }
}

data class MultiByteChannel(val nameWithoutByteNumber: String, val dmxBreak: Int, val offsets: List<Int>) {
    companion object Factory {
        fun fromConcreteChannel(channel: DMXChannel): MultiByteChannel {
            val nameWithoutByteNumber = channelNamePrototype(channel)
            val dmxBreak = channel.dmxBreak.toInt()
            val offsets = channel.getOffsetList()
            return MultiByteChannel(nameWithoutByteNumber, dmxBreak, offsets)
        }

        fun fromAbstractChannel(channel: DMXChannel, geometryReference: GeometryReference): MultiByteChannel {
            val nameWithoutByteNumber = "${geometryReference.name} -> ${channelNamePrototype(channel)}"
            val (dmxBreak, offsets) = try {
                val (dmxBreak, referenceOffset) = getAbstractBreakAndReferenceOffset(channel, geometryReference)
                val channelOffsets = channel.getOffsetList()
                val offsets = channelOffsets.map{it + referenceOffset - 1}
                Pair(dmxBreak, offsets)
            } catch (error: java.lang.IllegalArgumentException) {
                throw java.lang.IllegalArgumentException("Error while parsing channel $nameWithoutByteNumber")
            }
            return MultiByteChannel(nameWithoutByteNumber, dmxBreak, offsets)
        }

        /** Name Prototype where reference name has to be prepended and byte-number appended. */
        private fun channelNamePrototype(channel: DMXChannel): String {
            val geometry = channel.geometry
            val attribute = channel.logicalChannel[0].attribute
            return "${geometry}_${attribute}"
        }

        private fun DMXChannel.getOffsetList(): List<Int> = this.offset.split(",").map { it.toInt() }

        private fun getAbstractBreakAndReferenceOffset(channel: DMXChannel, geometryReference: GeometryReference): Pair<Int, Int> {
            if (channel.dmxBreak == "Overwrite") {
                // use last Break element in geometry reference as override element
                val lastBreakMap = geometryReference.`break`.last()
                val dmxBreak = lastBreakMap.dmxBreak.toInt()
                val refOffset = lastBreakMap.dmxOffset
                return Pair(dmxBreak, refOffset)
            } else {
                // use first Break element in geometry reference that matches break of channel
                val dmxBreak = channel.dmxBreak.toInt()
                val refOffset = geometryReference.`break`.find { it.dmxBreak.toInt() == dmxBreak }?.dmxOffset
                    ?: throw java.lang.IllegalArgumentException(
                        "The Geometry Reference ${geometryReference.name} does not " +
                                "provide an offset for the break $dmxBreak"
                    )
                return Pair(dmxBreak, refOffset)
            }
        }
    }
}

private fun MutableList<MutableList<String?>>.putMultiByteChannelNames(multiByteChannel: MultiByteChannel) {
    multiByteChannel.offsets.forEachIndexed { offsetIndex, offset ->
        val nameToWrite = appendByteNumber(multiByteChannel.nameWithoutByteNumber, offsetIndex, multiByteChannel.offsets.size)
        this.putChannelNameAt(nameToWrite, multiByteChannel.dmxBreak, offset)
    }
}

private fun MutableList<MutableList<String?>>.putChannelNameAt(nameToWrite: String, dmxBreak: Int, offset: Int) {
    val breakList = this.elementAtOrFillWith(dmxBreak - 1, mutableListOf())
    val nameAtWriteIndex = breakList.elementAtOrFillWith(offset - 1, null)
    if (nameAtWriteIndex != null) {
        // channel name already set -> there is a collision between two channels
        throw IllegalArgumentException(
            "GDTF file produces DMX Channel collision at break $dmxBreak and offset " +
                    "$offset between $nameAtWriteIndex and $nameToWrite"
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
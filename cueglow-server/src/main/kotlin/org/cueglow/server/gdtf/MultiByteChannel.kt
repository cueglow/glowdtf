package org.cueglow.server.gdtf

import org.cueglow.gdtf.DMXChannel
import org.cueglow.gdtf.GeometryReference
import org.cueglow.server.objects.InvalidGdtfException
import org.cueglow.server.objects.UnsupportedGdtfException
import com.github.michaelbull.result.unwrap

data class MultiByteChannel(
    val name: String,
    val dmxBreak: Int,
    val offsets: List<Int>,
    val bytes: Short,
    val channelFunctionIndices: IntRange, // Raw ChannelFunction is always first one
) {
    companion object Factory {
        fun fromConcreteChannel(
            channel: DMXChannel,
            channelFunctions: MutableList<GlowChannelFunction>,
            multiByteChannelInd: Int
        ): MultiByteChannel {
            val nameWithoutByteNumber = channelNamePrototype(channel)
            val dmxBreak = channel.dmxBreak.toInt()
            val offsets = channel.getOffsetList()
            return fromChannelNameBreakOffsets(channel, nameWithoutByteNumber, dmxBreak, offsets, channelFunctions, multiByteChannelInd)
        }

        fun fromAbstractChannel(
            channel: DMXChannel,
            geometryReference: GeometryReference,
            channelFunctions: MutableList<GlowChannelFunction>,
            multiByteChannelInd: Int
        ): MultiByteChannel {
            val nameWithoutByteNumber = "${geometryReference.name} -> ${channelNamePrototype(channel)}"
            val (dmxBreak, offsets) = try {
                val (dmxBreak, referenceOffset) = getAbstractBreakAndReferenceOffset(channel, geometryReference)
                val channelOffsets = channel.getOffsetList()
                val offsets = channelOffsets.map { it + referenceOffset - 1 }
                Pair(dmxBreak, offsets)
            } catch (exception: InvalidGdtfException) {
                throw InvalidGdtfException("Error in channel '$nameWithoutByteNumber'", exception)
            }
            return fromChannelNameBreakOffsets(channel, nameWithoutByteNumber, dmxBreak, offsets, channelFunctions, multiByteChannelInd)
        }

        private fun fromChannelNameBreakOffsets(
            channel: DMXChannel,
            nameWithoutByteNumber: String,
            dmxBreak: Int,
            offsets: List<Int>,
            channelFunctions: MutableList<GlowChannelFunction>,
            multiByteChannelInd: Int,
        ): MultiByteChannel {
            val bytes = offsets.size.toShort()
            if (bytes > 7) {
                throw UnsupportedGdtfException("Channel '${nameWithoutByteNumber}' has $bytes Bytes but only up to 7 Bytes are supported.")
            }
            val currentChannelFunctions = channel.getChannelFunctions(bytes, multiByteChannelInd, nameWithoutByteNumber)
            val channelFunctionIndexStart = channelFunctions.size
            channelFunctions.addAll(currentChannelFunctions)
            val channelFunctionIndexStop = channelFunctions.size - 1
            val channelFunctionIndices = channelFunctionIndexStart..channelFunctionIndexStop
            return MultiByteChannel(nameWithoutByteNumber, dmxBreak, offsets, bytes, channelFunctionIndices)
        }

        /** Name Prototype where reference name has to be prepended and byte-number appended. */
        private fun channelNamePrototype(channel: DMXChannel): String {
            val geometry = channel.geometry
            val attribute = channel.logicalChannel[0].attribute
            return "${geometry}_${attribute}"
        }

        private fun DMXChannel.getOffsetList(): List<Int> = this.offset.split(",").map { it.toInt() }

        // TODO refactor for better readability
        private fun DMXChannel.getChannelFunctions(bytes: Short, multiByteChannelInd: Int, multiByteChannelName: String): List<GlowChannelFunction> {
            val channelFunctions: MutableList<GlowChannelFunction> = mutableListOf()
            // Raw DMX Channel Function
            channelFunctions.add(
                GlowChannelFunction(multiByteChannelName, 0, (1L shl 8*bytes)-1, multiByteChannelInd, raw=true)
            )
            // Normal Channel Functions
            channelFunctions.addAll(this.logicalChannel.flatMap { logicalChannel ->
                val channelFunctions = logicalChannel.channelFunction

                // each ModeMasterGroup consists of ChannelFunctions with the exact same ModeMaster configuration
                val modeMasterGroups = channelFunctions.groupBy {
                    Triple(
                        it.modeMaster,
                        parseDmxValue(it.modeFrom, bytes).unwrap(), // TODO is this unwrap caught right?
                        parseDmxValue(it.modeTo, bytes).unwrap(), // TODO is this unwrap caught right?
                    )
                }

                val glowChannelFunctions = modeMasterGroups.flatMap { modeMasterGroup ->
                    val modeMasterConfig = modeMasterGroup.key
                    val channelFunctionsInModeMasterGroup = modeMasterGroup.value

                    val groupedByDmxFrom =
                        channelFunctionsInModeMasterGroup.groupBy { parseDmxValue(it.dmxFrom, bytes).unwrap() }
                            .toSortedMap()

                    // validate that each DMXFrom value occurs the same amount of time
                    // this ensures we can uniquely identify DmxTo in this ModeMasterGroup
                    // Examples:
                    // allowed: 1 x from 0, 1 x from 128 (1 full dmx range)
                    // allowed: 2 x from 0 and 2 x from 128. (2 full dmx ranges)
                    // not allowed: 2 x from 0, 1 x from 128, 1 x from 200 (2 full dmx ranges, but 128/200 can't be
                    // assigned to one or the other full range -> error)
                    val numberOfFullDmxRanges = groupedByDmxFrom.values
                        .map { it.size }
                        .distinct()
                        .let { occurences ->
                            if (occurences.size == 1) {
                                occurences[0]
                            } else {
                                throw InvalidGdtfException(
                                    "ChannelFunctions with Mode Master '${modeMasterConfig.first}' from ${modeMasterConfig.second} " +
                                            "to ${modeMasterConfig.third} don't have an unambiguous DMXTo value because not every DMXFrom " +
                                            "value occurs the same amount of times."
                                )
                            } // TODO is this throw caught right?
                        }
                    // TODO have a test that the above catches errors with ambiguous DMXFrom values

                    val channelMaxValue = (1L shl bytes * 8) - 1
                    val dmxTos = groupedByDmxFrom.keys.drop(1).map { it - 1 }.toMutableList()
                    dmxTos.add(channelMaxValue)

                    val dmxFroms = groupedByDmxFrom.keys.toList()

                    groupedByDmxFrom.values.flatMapIndexed { dmxFromIndex, channelFunctions ->
                        val dmxFrom = dmxFroms[dmxFromIndex]
                        val dmxTo = dmxTos[dmxFromIndex]
                        channelFunctions.map { GlowChannelFunction(it.name, dmxFrom, dmxTo, multiByteChannelInd) }
                    }
                }
                glowChannelFunctions
            })
            return channelFunctions
        }

        private fun getAbstractBreakAndReferenceOffset(
            channel: DMXChannel,
            geometryReference: GeometryReference
        ): Pair<Int, Int> {
            return if (channel.dmxBreak == "Overwrite") {
                // use last Break element in geometry reference as override element
                val lastBreakMap = geometryReference.`break`.last()
                val dmxBreak = lastBreakMap.dmxBreak.toInt()
                val refOffset = lastBreakMap.dmxOffset
                Pair(dmxBreak, refOffset)
            } else {
                // use first Break element in geometry reference that matches break of channel
                val dmxBreak = channel.dmxBreak.toInt()
                val refOffset = geometryReference.`break`.find { it.dmxBreak.toInt() == dmxBreak }?.dmxOffset
                    ?: throw InvalidGdtfException(
                        "The Geometry Reference '${geometryReference.name}' does not provide an offset for the break $dmxBreak"
                    )
                Pair(dmxBreak, refOffset)
            }
        }
    }
}
package org.cueglow.server.gdtf

import org.apache.logging.log4j.LogManager
import org.cueglow.server.rig.FixtureState



fun renderGdtfStateToDmx(chValues: List<Long>, dmxMode: GlowDmxMode): ByteArray {
    val output = mutableListOf<Byte>()
    dmxMode.multiByteChannels
        .groupBy { it.dmxBreak }
        .toSortedMap()
        .forEach { dmxBreak ->
            val startInd = output.size
            //logger.info("doing dmxBreak ${dmxBreak.key}")
            dmxBreak.value.forEach { ch ->
                val chInd = dmxMode.multiByteChannels.indexOf(ch)
                val chValue = chValues[chInd]
                ch.offsets
                    .reversed() // from least to most significant
                    .forEachIndexed { offsetIndex, offset ->
                        //logger.info("doing offset $offset with offsetIndex $offsetIndex")
                        val byteValue = (chValue shr 8*offsetIndex).toByte()
                        // grow to right size
                        val dmxChannelInd = startInd + offset - 1
                        //logger.info("#wanting to insert at $dmxChannelInd")
                        while (output.size <= dmxChannelInd) {
                            //logger.info("growing one element for channel ${ch.name}")
                            output.add(0)
                        }
                        //logger.info("grown to ${output.size}")
                        output[dmxChannelInd] = byteValue
                    }
            }
        }

    return output.toByteArray()
}
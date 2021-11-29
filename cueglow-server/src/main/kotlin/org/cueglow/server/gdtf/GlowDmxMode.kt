package org.cueglow.server.gdtf

import com.github.michaelbull.result.unwrap
import org.apache.logging.log4j.LogManager
import org.cueglow.gdtf.DMXChannel
import org.cueglow.gdtf.DMXMode
import org.cueglow.server.objects.InvalidGdtfException
import org.jgrapht.graph.DirectedAcyclicGraph
import kotlin.math.max
import kotlin.math.min

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
    val channelFunctions: List<GlowChannelFunction>,
    val multiByteChannels: List<MultiByteChannel>,
    val channelFunctionDependencies: DirectedAcyclicGraph<Int, Pair<*, *>>, // should be Pair<Long, Long>
    val channelLayout: List<List<String?>>,
)

//--------------------
// Create from GDTF
//--------------------

val logger = LogManager.getLogger() ?: throw Exception("Logger from getLogger is null")

fun GlowDmxMode(mode: DMXMode, abstractGeometries: List<AbstractGeometry>): GlowDmxMode {
    // allocate
    val channelLayout: MutableList<MutableList<String?>> = mutableListOf(mutableListOf())
    val channelFunctions: MutableList<GlowChannelFunction> = mutableListOf()
    val multiByteChannels: MutableList<MultiByteChannel> = mutableListOf()
    val channelFunctionDependencies = DirectedAcyclicGraph<Int, Pair<*, *>>(Pair::class.java)
    try {
        // populate multiByteChannels
        mode.dmxChannels.dmxChannel.forEach { channel ->
            val multiByteChannelStartInd = multiByteChannels.size
            val instantiatedChannels = instantiateChannel(channel, abstractGeometries, channelFunctions, multiByteChannelStartInd)
            multiByteChannels.addAll(instantiatedChannels)
        }
        // iterate over multiByteChannels while filling channelLayout
        multiByteChannels.forEach { multiByteChannel ->
            channelLayout.putMultiByteChannelNames(multiByteChannel)
        }
        // populate channelFunctionDependencies
        channelFunctions.forEachIndexed { dependentChFInd, dependentChF ->
            // check if modeMaster set
            val modeMaster = dependentChF.originalChannelFunction?.modeMaster ?: return@forEachIndexed
            val dependentCh = multiByteChannels[dependentChF.multiByteChannelInd]

            val dependencySequence = modeMaster.split(".")

            val dependencyChs = multiByteChannels.filter { it.originalName == dependencySequence[0] }

            val dependencyCh: MultiByteChannel = if (dependencyChs.isEmpty()) {
                throw InvalidGdtfException("In ChannelFunction '${dependentCh.name}.${dependentChF.logicalChannel}.${dependentChF.name}', " +
                        "the DMXChannel of mode master reference '${modeMaster}' couldn't be found.")
            } else if (dependencyChs.size == 1) {
                // either it is a concrete dependency or a singular abstract one - both are issue-free
                dependencyChs[0]
            } else {
                // multiple dependency channels, which implies they are instances of an abstract channel
                // this is only okay if dependent and dependency channels reference the same abstract geometry,
                // then there is a 1:1 mapping for each Geometry Reference
                if (dependentCh.abstractGeometry != dependencyChs[0].abstractGeometry) {
                    throw InvalidGdtfException("In ChannelFunction '${dependentCh.name}.${dependentChF.logicalChannel}.${dependentChF.name}', " +
                            "the mode master reference '${modeMaster}' is to an abstract channel, but the abstract geometry of the depending channel is not the same.")
                }
                // abstractGeometry of dependentCh and all dependencyChs are the same
                // select dependencyCh which has the same GeometryReference
                dependencyChs.find { it.geometry == dependentCh.geometry } ?: throw Exception("The Channel Function '${dependentCh.name}.${dependentChF.logicalChannel}.${dependentChF.name}', " +
                        "comes from an instantiated abstract channel and the mode master references an abstract channel with the same abstract geometry, but somehow " +
                        "I couldn't find the instantiated Channel with the same geometry. This is likely a bug.")
            }

            val dependencyChFInd = if (dependencySequence.size == 1) {
                // reference to a channel means dependency on raw ChF
                dependencyCh.channelFunctionIndices.first // first is raw ChF
            } else if (dependencySequence.size == 3){
                val candidateChFs = dependencyCh.channelFunctionIndices
                var matchChF: Int? = null
                for (i in candidateChFs) {
                    val candidate = channelFunctions[i]
                    if (candidate.logicalChannel == dependencySequence[1] && candidate.name == dependencySequence[2]) {
                        matchChF = i
                    }
                }
                matchChF ?: throw InvalidGdtfException("In ChannelFunction '${dependentCh.name}.${dependentChF.logicalChannel}.${dependentChF.name}', " +
                        "the mode master reference '${modeMaster}' could not be resolved")
            } else {
                throw InvalidGdtfException("In ChannelFunction '${dependentCh.name}.${dependentChF.logicalChannel}.${dependentChF.name}', " +
                        "the mode master reference '${modeMaster}' is invalid because it does not have either 1 part or 3 parts separated with dots.")
            }

            val dependencyChF = channelFunctions[dependencyChFInd]

            // parse modeFrom and modeTo
            val modeFrom = parseDmxValue(dependentChF.originalChannelFunction.modeFrom, dependencyCh.bytes).unwrap()
            val modeTo = parseDmxValue(dependentChF.originalChannelFunction.modeTo, dependencyCh.bytes).unwrap()

            if (modeTo < modeFrom) {
                logger.warn("In ChannelFunction '${dependentCh.name}.${dependentChF.logicalChannel}.${dependentChF.name}', " +
                        "modeTo is smaller than modeFrom. The ChannelFunction is unreachable.")
            }

            val modeFromClipped = max(modeFrom, dependencyChF.dmxFrom)
            val modeToClipped = min(modeTo, dependencyChF.dmxTo)

            if (modeToClipped > modeFromClipped) {
                logger.warn("In ChannelFunction '${dependentCh.name}.${dependentChF.logicalChannel}.${dependentChF.name}', " +
                        "modeTo is smaller than modeFrom after clipping to the DMX range of the dependency. The ChannelFunction is unreachable.")
            }

            channelFunctionDependencies.addVertex(dependencyChFInd)
            channelFunctionDependencies.addVertex(dependentChFInd)
            channelFunctionDependencies.addEdge(dependencyChFInd, dependentChFInd, Pair(modeFromClipped, modeToClipped))
        }
    } catch (exception: InvalidGdtfException) {
        throw InvalidGdtfException("Error in DMX Mode '${mode.name}'", exception)
    }
    val channelCount: Int = channelLayout.sumBy { it.size }
    return GlowDmxMode(mode.name, channelCount, channelFunctions, multiByteChannels, channelFunctionDependencies, channelLayout)
}

fun instantiateChannel(
    channel: DMXChannel,
    abstractGeometries: List<AbstractGeometry>,
    channelFunctions: MutableList<GlowChannelFunction>,
    multiByteChannelStartInd: Int,
): List<MultiByteChannel> {
    // check if geometry referenced by channel is abstract
    val abstractGeometry = abstractGeometries.find { it.name == channel.geometry }
    return if (abstractGeometry != null) {
        // channel is abstract, so it is instantiated by each GeometryReference to AbstractGeometry
        abstractGeometry.referencedBy.mapIndexed { multiByteChannelIndOffset, geometryReference ->
            val multiByteChannelInd = multiByteChannelStartInd + multiByteChannelIndOffset
            MultiByteChannel.fromAbstractChannel(channel, geometryReference, channelFunctions, multiByteChannelInd, abstractGeometry)
        }
    } else {
        // channel is concrete (i.e. not abstract)
        listOf(MultiByteChannel.fromConcreteChannel(channel, channelFunctions, multiByteChannelStartInd))
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
package org.cueglow.server.gdtf

import org.cueglow.server.rig.ChannelFunctionState
import org.cueglow.server.rig.FixtureState
import org.cueglow.server.rig.active
import org.jgrapht.traverse.TopologicalOrderIterator

fun gdtfDefaultState(dmxMode: GlowDmxMode): FixtureState {
    // allocate
    val state = MutableList(dmxMode.channelFunctions.size) {
        ChannelFunctionState(0, false, null)
    }

    // for each channel, the initialFunction determines which defaultValue we use
    dmxMode.multiByteChannels.forEach { ch ->
        val initialChF = dmxMode.channelFunctions[ch.initialChannelFunctionInd]
        val defaultValue = initialChF.defaultValue!! // initial chF cannot be raw, therefore defaultValue is not null
        for (i in ch.channelFunctionIndices) {
            val chF = dmxMode.channelFunctions[i]
            if (defaultValue in chF.dmxFrom..chF.dmxTo) {
                // if defaultValue is in the DMX range of the ChF, outOfRange stays false and we can use the defaultValue
                state[i].value = defaultValue
            } else {
                // outOfRange must be true
                state[i].outOfRange = true
                // use own default value
                state[i].value =
                    chF.defaultValue!! // outOfRange ChannelFunctions are never raw, so defaultValue is not null
            }
        }
    }

    // Then, go through the dependency graph edges, starting at root node, and set the modeDisabled fields accordingly
    // if a state is modeDisabled, we use their own default Values
    val graph = dmxMode.channelFunctionDependencies
    val itr = TopologicalOrderIterator(graph)
    itr.forEachRemaining { parentInd ->
        parentInd ?: return@forEachRemaining
        val outEdges = graph.outgoingEdgesOf(parentInd) ?: return@forEachRemaining
        val parentValue = state[parentInd].value
        val parent = dmxMode.channelFunctions[parentInd]
        if (state[parentInd].active().not()) {
            outEdges.forEach { inactiveEdge ->
                val targetInd = graph.getEdgeTarget(inactiveEdge)
                state[targetInd].modeDisabled = "${parent.name} must be active"
                state[targetInd].value =
                    dmxMode.channelFunctions[targetInd].defaultValue!! // chF is dependent part of dependency graph so is not raw
            }
        } else {
            // parent is active
            // check for each edge whether in ModeMaster range
            outEdges.forEach { edge ->

                if (parentValue !in edge.from..edge.to) {
                    // target is disabled by ModeMaster value
                    val targetInd = graph.getEdgeTarget(edge)
                    state[targetInd].modeDisabled = "${parent.name} must be ${edge.from}-${edge.to}"
                    state[targetInd].value =
                        dmxMode.channelFunctions[targetInd].defaultValue!! // chF is dependent part in dependency graph so is not raw
                }
            }
        }
    }

    return state
}
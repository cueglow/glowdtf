package org.cueglow.server.gdtf

import org.jgrapht.traverse.BreadthFirstIterator

data class FixtureState(
    val chValues: List<Long>, // size = multiByteChannels.size
    val chFDisabled: List<String?>, // size = channelFunctions.size
    // chFDisabled is null if enabled or string with reason if disabled
)

fun gdtfDefaultState(dmxMode: GlowDmxMode): FixtureState {
    val chs = dmxMode.multiByteChannels
    val chFs = dmxMode.channelFunctions

    val chValues = MutableList(chs.size) { -1L }

    // for each channel, the initialFunction determines which defaultValue we use
    dmxMode.multiByteChannels.forEachIndexed { chInd, ch ->
        val initialChF = chFs[ch.initialChannelFunctionInd]
        val defaultValue = initialChF.defaultValue!! // initial chF cannot be raw, therefore defaultValue is not null
        chValues[chInd] = defaultValue
    }

    val chFDisabled = generateChFDisabled(chValues, dmxMode)

    // the remaining chFDisabled are expected null as default
    return FixtureState(chValues, chFDisabled)
}

fun generateChFDisabled(
    chValues: List<Long>,
    dmxMode: GlowDmxMode,
): MutableList<String?> {
    val g = dmxMode.channelFunctionDependencies // dependency graph
    val chFs = dmxMode.channelFunctions
    // allocate
    val chFDisabled = MutableList<String?>(chFs.size) { null }
    // for handling ModeMaster Dependencies, iterate over all ChF in the graph
    val slaveIterator = BreadthFirstIterator(g)
    slaveIterator.forEachRemaining { slaveChFInd ->
        // iterate over its masters
        val masterIterator =
            BreadthFirstIterator(g, slaveChFInd) // Breadth-First is sufficient as each node only has one outgoing edge
        val firstEdgeTrue = checkChannelFunctionModeMasterEdge(masterIterator.next(), chValues, dmxMode)
        while (masterIterator.hasNext()) {
            val masterChFInd = masterIterator.next()
            val edgeTrue = checkChannelFunctionModeMasterEdge(masterChFInd, chValues, dmxMode)
            if (!edgeTrue) {
                // an edge which isn't the first one is false, which means the direct ModeMaster of the slaveChF is not active
                // Therefore, put a message that the direct ModeMaster must be active
                val firstOutEdges = g.outgoingEdgesOf(slaveChFInd)
                assert(firstOutEdges.size == 1)
                val firstOutEdge = firstOutEdges.first()
                val directModeMasterInd = g.getEdgeTarget(firstOutEdge)
                val directModeMaster = chFs[directModeMasterInd]
                chFDisabled[slaveChFInd] = "${directModeMaster.name} must be active"
                break
            }
        }
        // if master iteration didn't find another false edge, check the first one
        if (chFDisabled[slaveChFInd] == null && !firstEdgeTrue) {
            val firstOutEdges = g.outgoingEdgesOf(slaveChFInd)
            assert(firstOutEdges.size == 1)
            val firstOutEdge = firstOutEdges.first()
            val directModeMasterInd = g.getEdgeTarget(firstOutEdge)
            val directModeMaster = chFs[directModeMasterInd]
            chFDisabled[slaveChFInd] = "${directModeMaster.name} must be ${firstOutEdge.from}-${firstOutEdge.to}"
        }
    }
    return chFDisabled
}

fun checkChannelFunctionModeMasterEdge(chFInd: Int, chValues: List<Long>, dmxMode: GlowDmxMode): Boolean {
    val g = dmxMode.channelFunctionDependencies
    val outgoingEdges = g.outgoingEdgesOf(chFInd)
    if (outgoingEdges.size == 0) {
        return true // null -> no outgoing edge -> no Mode Master -> true, because always enabled
    }
    assert(outgoingEdges.size == 1) // each chF can only have one ModeMaster
    val outgoingEdge = outgoingEdges.first()
    val masterChFInd = g.getEdgeTarget(outgoingEdge)
    val masterChInd = dmxMode.channelFunctions[masterChFInd].multiByteChannelInd
    val masterValue = chValues[masterChInd]
    if (masterValue in outgoingEdge.from..outgoingEdge.to) {
        return true
    }
    return false
}
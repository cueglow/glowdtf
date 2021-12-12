package org.cueglow.server.rig

import org.cueglow.server.objects.messages.GlowPatch
import org.jgrapht.traverse.DepthFirstIterator

data class RigStateTransition(
    val fixtureInd: Int,
    val chFInd: Int,
    val value: Long,
)

fun RigState.transition(transition: RigStateTransition, patch: GlowPatch): Unit {
    // get data
    val (fixtureInd, changedChFInd, newValue) = transition
    val fixture = patch.fixtures[fixtureInd]
    val fixtureType = patch.fixtureTypes.find { it.fixtureTypeId == fixture.fixtureTypeId }!!
    val dmxMode = fixtureType.modes.find { it.name == fixture.dmxMode }!!
    val changedChF = dmxMode.channelFunctions[changedChFInd]
    val ch = dmxMode.multiByteChannels[changedChF.multiByteChannelInd]
    val fixtureState = this[fixtureInd]

    // check that newValue is inBounds
    assert(newValue in changedChF.dmxFrom..changedChF.dmxTo)

    if (fixtureState[changedChFInd].modeDisabled != null) {
        // changed ChF is disabled
        // updates only affect the changed ChF
        fixtureState[changedChFInd].value = newValue
        return
    }

    // Changes inside the DMXChannel
    // iterate over channelFunctions in the corresponding channel
    ch.channelFunctionIndices.forEach { chFInd ->
        val chF = dmxMode.channelFunctions[chFInd]
        val chFState = fixtureState[chFInd]
        chFState.outOfRange = newValue !in chF.dmxFrom..chF.dmxTo
        if (chFState.modeDisabled == null && chFState.outOfRange.not()) {
            // we aren't disabled nor outOfRange for the new value, so we have to follow
            chFState.value = newValue
            // This is the only case where some ModeMaster activations might change, as it's the only case where a value changes
            // So here come CHANGES OUTSIDE CHANNEL
            val g = dmxMode.channelFunctionDependencies
            // check whether chF is in dependecy tree
            if (g.containsVertex(chFInd).not()) { return@forEach }
            // NOTE optimization potential if no change in outgoing ModeMaster states
            // iterate through descendants in topological order (depth-first guaranteed to be topological, right?)
            val itr = DepthFirstIterator(g, chFInd)
            itr.next() // first one is already good
            itr.forEachRemaining { childInd ->
                val incomingEdge = g.incomingEdgesOf(childInd).first()!! // can't be null or many, cause we come from some and only one parent
                val parentInd = g.getEdgeSource(incomingEdge)!! // can't be null, cause each edge has a parent
                val parent = dmxMode.channelFunctions[parentInd]
                val parentState = fixtureState[parentInd]
                val childState = fixtureState[childInd]
                if (parentState.active().not()) {
                    childState.modeDisabled = "${parent.name} must be active"
                } else { // parent is active
                    if (parentState.value in incomingEdge.from..incomingEdge.to) {
                        // child is enabled
                        childState.modeDisabled = null
                    } else { // parent is active, but its value isn't right to active the child
                        childState.modeDisabled = "${parent.name} must be ${incomingEdge.from}-${incomingEdge.to}"
                    }
                }
                // TODO this is missing updating of channels when a modeDisabled within them changes
            }

        }
    }

    // Changes outside the DMXChannel
    // this happens due to ModeMaster dependencies
    // we take all ChannelFunction from the channel (they might have been updated),
    // check whether they are in the dependency tree
    // if yes, start at them and iterate through all their descendants in topological order
}
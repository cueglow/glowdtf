package org.cueglow.server.rig

import org.cueglow.server.gdtf.generateChFDisabled
import org.cueglow.server.gdtf.maximumValueOfBytes
import org.cueglow.server.objects.messages.GlowPatch

data class RigStateTransition(
    val fixtureInd: Int,
    val chInd: Int,
    val value: Long,
)

fun RigState.transition(transition: RigStateTransition, patch: GlowPatch) {
    // get data
    val (fixtureInd, changedChInd, newValue) = transition
    val fixture = patch.fixtures[fixtureInd]
    val fixtureType = patch.fixtureTypes.find { it.fixtureTypeId == fixture.fixtureTypeId }!!
    val dmxMode = fixtureType.modes.find { it.name == fixture.dmxMode }!!
    val changedCh = dmxMode.multiByteChannels[changedChInd]
    val fixtureState = this[fixtureInd]

    // check that newValue is in bounds
    assert(newValue in 0..maximumValueOfBytes(changedCh.bytes))

    // update value
    fixtureState.chValues[changedChInd] = newValue

    // update chFDisabled
    // NOTE this can be more efficient by only iterating over channel functions whose ModeMaster changed
    // Since we know whether the channelFunctions in the updated channel are disabled, we can iterate through the dependent
    // channel functions just once and update their state without a problem (cycles need some thought...)
    fixtureState.chFDisabled = generateChFDisabled(fixtureState.chValues, dmxMode)
}

package org.cueglow.server.rig

import org.cueglow.server.gdtf.*
import org.cueglow.server.patch.PatchFixture
import java.util.*

typealias RigStateMap = Map<UUID, FixtureState> // fixture uuid -> fixture state

data class RigStateTransition(
    val fixtureUuid: UUID,
    val chInd: Int,
    val value: Long,
)

fun defaultRigState(fixtures: Map<UUID, PatchFixture>, fixtureTypes: Map<UUID, GdtfWrapper>): RigStateMap {
    val groupedFixtures = fixtures.values.groupBy { Pair(it.fixtureTypeId, it.dmxMode) }
    val rigState: MutableMap<UUID, FixtureState> = mutableMapOf()
    groupedFixtures.forEach { (pair, fixtures) ->
        val (fixtureTypeId, dmxModeName) = pair
        val fixtureType = fixtureTypes[fixtureTypeId] ?: return@forEach
        val dmxMode = fixtureType.modes.find { it.name == dmxModeName }!!
        val defaultState = gdtfDefaultState(dmxMode)
        fixtures.forEach { fixture ->
            rigState[fixture.uuid] = defaultState
        }
    }
    return rigState
}

fun RigStateMap.transition(
    transition: RigStateTransition,
    fixtures: Map<UUID, PatchFixture>,
    fixtureTypes: Map<UUID, GdtfWrapper>
): RigStateMap {
    // get data
    val (fixtureUuid, changedChInd, newValue) = transition
    val fixture = fixtures[fixtureUuid]!!
    val fixtureType = fixtureTypes[fixture.fixtureTypeId]!!
    val dmxMode = fixtureType.modes.find { it.name == fixture.dmxMode }!!
    val changedCh = dmxMode.multiByteChannels[changedChInd]
    val fixtureState = this[fixtureUuid]!!

    // check that newValue is in bounds
    assert(newValue in 0..maximumValueOfBytes(changedCh.bytes))

    // update value
    val chValues = fixtureState.chValues.toMutableList()
    chValues[changedChInd] = newValue

    // update chFDisabled
    // NOTE this can be more efficient by only iterating over channel functions whose ModeMaster changed
    // Since we know whether the channelFunctions in the updated channel are disabled, we can iterate through the dependent
    // channel functions just once and update their state without a problem (cycles need some thought...)
    val chFDisabled = generateChFDisabled(chValues, dmxMode)

    val newFixtureState = FixtureState(chValues, chFDisabled)

    val newRigState = this.toMutableMap()
    newRigState[fixtureUuid] = newFixtureState

    return newRigState
}

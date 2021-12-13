package org.cueglow.server.rig

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.unwrap
import com.google.common.truth.Truth.assertThat
import org.cueglow.server.gdtf.gdtfDefaultState
import org.cueglow.server.json.toJsonString
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.objects.messages.GlowError
import org.cueglow.server.objects.messages.GlowPatch
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Test
import java.util.*

fun <V> Result<V, GlowError>.unwrapLog(): V {
    return this.mapError { logger.error(it.toJsonString()); it }
        .unwrap()
}

internal class StateTransitionTest {

    private val fixtureType = ExampleFixtureType.rigStateTestGdtf
    private val dmxMode = fixtureType.modes[0]
    private val patch = GlowPatch(
        fixtures = listOf(PatchFixture(
            uuid = UUID.randomUUID(),
            fid = 1,
            name = "",
            fixtureTypeId = fixtureType.fixtureTypeId,
            dmxMode = "mode1",
            ArtNetAddress.tryFrom(1).unwrapLog(),
            DmxAddress.tryFrom(1).unwrapLog()
        )),
        fixtureTypes = listOf(fixtureType),
    )
/*
    @Test
    fun trackingTest() {
        val state: RigState = mutableListOf(gdtfDefaultState(dmxMode))
        // we'll focus on the Control1 through Control4 ChannelFunctions tracking properly
        // first, set their ModeMaster so none of them are disabled
        state.transition(RigStateTransition(0, 0, 125), patch) // set body_Dimmer
        val fixtureState = state[0]
        val controlInds = 3..6 // indices of Control1 through Control4 ChannelFunctions
        // the four interesting chF shouldn't be disabled now
        assertThat(fixtureState.slice(controlInds)).isEqualTo(listOf(
            ChannelFunctionState(0, false, null), // Control1 1
            ChannelFunctionState(255, true, null), // Control2 2
            ChannelFunctionState(0, false, null), // Control3 3
            ChannelFunctionState(255, true, null), // Control 4 4
        ))
        validateFixtureState(fixtureState, dmxMode).unwrapLog()

        // track active channel functions to first point of interest
        state.transition(RigStateTransition(0, 2, 99), patch) // set body_Control1
        // not much happened yet, just simple tracking without transitions
        assertThat(fixtureState.slice(controlInds)).isEqualTo(listOf(
            ChannelFunctionState(99, false, null), // Control1 1
            ChannelFunctionState(255, true, null), // Control2 2
            ChannelFunctionState(99, false, null), // Control3 3
            ChannelFunctionState(255, true, null), // Control 4 4
        ))
        validateFixtureState(fixtureState, dmxMode).unwrapLog()

        // now, switch the first pair of ChannelFunctions
        state.transition(RigStateTransition(0, 2, 100), patch) // set body_Control1
        assertThat(fixtureState.slice(controlInds)).isEqualTo(listOf(
            ChannelFunctionState(99, true, null), // Control1 1
            ChannelFunctionState(100, false, null), // Control2 2
            ChannelFunctionState(100, false, null), // Control3 3
            ChannelFunctionState(255, true, null), // Control 4 4
        ))
        validateFixtureState(fixtureState, dmxMode).unwrapLog()

        // track to next interesting point
        state.transition(RigStateTransition(0, 5, 199), patch) // set Control3
        assertThat(fixtureState.slice(controlInds)).isEqualTo(listOf(
            ChannelFunctionState(99, true, null), // Control1 1
            ChannelFunctionState(199, false, null), // Control2 2
            ChannelFunctionState(199, false, null), // Control3 3
            ChannelFunctionState(255, true, null), // Control 4 4
        ))
        validateFixtureState(fixtureState, dmxMode).unwrapLog()

        // induce transition
        state.transition(RigStateTransition(0, 4, 200), patch) // set Control2
        assertThat(fixtureState.slice(controlInds)).isEqualTo(listOf(
            ChannelFunctionState(99, true, null), // Control1 1
            ChannelFunctionState(200, false, null), // Control2 2
            ChannelFunctionState(199, true, null), // Control3 3
            ChannelFunctionState(200, false, null), // Control 4 4
        ))
        validateFixtureState(fixtureState, dmxMode).unwrapLog()

        // setting inactive chF activates it and others follow suit
        state.transition(RigStateTransition(0, 3, 50), patch) // set Control1
        assertThat(fixtureState.slice(controlInds)).isEqualTo(listOf(
            ChannelFunctionState(50, false, null), // Control1 1
            ChannelFunctionState(200, true, null), // Control2 2
            ChannelFunctionState(50, false, null), // Control3 3
            ChannelFunctionState(200, true, null), // Control 4 4
        ))
        validateFixtureState(fixtureState, dmxMode).unwrapLog()
    }

    @Test
    fun nestedModeMaster() {
        val state: RigState = mutableListOf(gdtfDefaultState(dmxMode))
        val fixtureState = state[0]

        // we'll focus on the ColorEffects and ColorMacro channels
        val controlInds = setOf(8,9,11,12) // indices of Control1 through Control4 ChannelFunctions
        // first, set the two currently active channelFunctions to some values
        state.transition(RigStateTransition(0, 8, 21), patch) // set ColorEffects1 1
        validateFixtureState(fixtureState, dmxMode).unwrapLog()
        state.transition(RigStateTransition(0, 10, 42), patch) // set body_ColorMacro1
        assertThat(fixtureState.slice(controlInds)).isEqualTo(listOf(
            ChannelFunctionState(21, false, null), // ColorEffects1 1
            ChannelFunctionState(0, false, "body_Dimmer must be 128-255"), // ColorEffects2 2
            ChannelFunctionState(42, false, null), // ColorMacro1 1
            ChannelFunctionState(0, false, "ColorEffects2 2 must be active"), // ColorMacro2 2
        ))
        validateFixtureState(fixtureState, dmxMode).unwrapLog()

        // set a disabled channelFunction
        state.transition(RigStateTransition(0, 12, 83), patch) // set ColorMacro2 2
        assertThat(fixtureState.slice(controlInds)).isEqualTo(listOf(
            ChannelFunctionState(21, false, null), // ColorEffects1 1
            ChannelFunctionState(0, false, "body_Dimmer must be 128-255"), // ColorEffects2 2
            ChannelFunctionState(42, false, null), // ColorMacro1 1
            ChannelFunctionState(83, false, "ColorEffects2 2 must be active"), // ColorMacro2 2
        ))
        validateFixtureState(fixtureState, dmxMode).unwrapLog()

        // now, set the highest level modeMaster such that the chain changes values
        state.transition(RigStateTransition(0, 1, 250), patch) // set Dimmer1
        assertThat(fixtureState.slice(controlInds)).isEqualTo(listOf(
            ChannelFunctionState(21, false, "body_Dimmer must be 0-127"), // ColorEffects1 1
            ChannelFunctionState(0, false, null), // ColorEffects2 2
            ChannelFunctionState(42, false, "ColorEffects1 1 must be active"), // ColorMacro1 1
            ChannelFunctionState(83, false, null), // ColorMacro2 2
        ))
        validateFixtureState(fixtureState, dmxMode).unwrapLog() // TODO fails due to no value changes in channels affected by ModeMaster (rawDmx currently doesn't track new enabled chF)
    }
    */
}
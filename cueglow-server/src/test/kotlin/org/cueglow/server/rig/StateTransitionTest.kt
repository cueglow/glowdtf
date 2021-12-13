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

    @Test
    fun trackingTest() {
        val state: RigStateList = mutableListOf(gdtfDefaultState(dmxMode))
        val fixtureState = state[0]
        // we'll focus on the Control1 through Control4 ChannelFunctions
        // first, set their ModeMaster so none of them are disabled
        state.transition(RigStateTransition(0, 0, 125), patch) // set body_Dimmer
        val controlInds = 3..6 // indices of Control1 through Control4 ChannelFunctions
        assertThat(fixtureState.chValues).isEqualTo(listOf<Long>(
            125, // body_Dimmer
            0, // body_Control1
            0, // bodyColorEffects1
            0, // body_ColorMacro1
            0, // body_Pan
            0, // body_Shutter1
        ))
        // the four interesting chF shouldn't be disabled now
        assertThat(fixtureState.chFDisabled).isEqualTo(listOf(
            null, // body_Dimmer
            null, // Dimmer 1
            null, // body_Control1
            null, // Control1 1
            null, // Control2 2
            null, // Control3 3
            null, // Control 4 4
            null, // body_ColorEffects1
            null, // ColorEffects1 1
            "body_Dimmer must be 128-255", // ColorEffects2 2
            null, // body_ColorMacro1
            null, // ColorMacro1 1
            "ColorEffects2 2 must be active", // ColorMacro2 2
            null, // body_Pan
            "Shutter1 1 must be active", // Pan 1
            null, // Tilt 2
            null, // body_Shutter1
            "Tilt 2 must be 128-255", // Shutter1 1
        ))
        validateFixtureState(fixtureState, dmxMode)

        // track active channel functions to first point of interest
        state.transition(RigStateTransition(0, 1, 42), patch) // set body_Control1
        assertThat(fixtureState.chValues).isEqualTo(listOf<Long>(
            125, // body_Dimmer
            42, // body_Control1
            0, // bodyColorEffects1
            0, // body_ColorMacro1
            0, // body_Pan
            0, // body_Shutter1
        ))
        assertThat(fixtureState.chFDisabled).isEqualTo(listOf(
            null, // body_Dimmer
            null, // Dimmer 1
            null, // body_Control1
            null, // Control1 1
            null, // Control2 2
            null, // Control3 3
            null, // Control 4 4
            null, // body_ColorEffects1
            null, // ColorEffects1 1
            "body_Dimmer must be 128-255", // ColorEffects2 2
            null, // body_ColorMacro1
            null, // ColorMacro1 1
            "ColorEffects2 2 must be active", // ColorMacro2 2
            null, // body_Pan
            "Shutter1 1 must be active", // Pan 1
            null, // Tilt 2
            null, // body_Shutter1
            "Tilt 2 must be 128-255", // Shutter1 1
        ))
        validateFixtureState(fixtureState, dmxMode)
    }

    @Test
    fun nestedModeMaster() {
        val state: RigStateList = mutableListOf(gdtfDefaultState(dmxMode))
        val fixtureState = state[0]

        // we'll focus on the ColorEffects and ColorMacro channels
        state.transition(RigStateTransition(0, 2, 21), patch)// set body_ColorEffects1
        state.transition(RigStateTransition(0, 3, 42), patch) // set body_ColorMacro1
        assertThat(fixtureState.chValues).isEqualTo(listOf<Long>(
            0, // body_Dimmer
            0, // body_Control1
            21, // bodyColorEffects1
            42, // body_ColorMacro1
            0, // body_Pan
            0, // body_Shutter1
        ))
        assertThat(fixtureState.chFDisabled).isEqualTo(listOf<String?>(
            null, // body_Dimmer
            null, // Dimmer 1
            null, // body_Control1
            null, // Control1 1
            null, // Control2 2
            "body_Dimmer must be 100-200", // Control3 3
            "body_Dimmer must be 100-200", // Control 4 4
            null, // body_ColorEffects1
            null, // ColorEffects1 1
            "body_Dimmer must be 128-255", // ColorEffects2 2
            null, // body_ColorMacro1
            null, // ColorMacro1 1
            "ColorEffects2 2 must be active", // ColorMacro2 2
            null, // body_Pan
            "Shutter1 1 must be active", // Pan 1
            null, // Tilt 2
            null, // body_Shutter1
            "Tilt 2 must be 128-255", // Shutter1 1
        ))

        // changing the root ModeMaster changes all nested dependencies' disabled state
        state.transition(RigStateTransition(0, 0, 250), patch) // set body_Dimmer
        assertThat(fixtureState.chValues).isEqualTo(listOf<Long>(
            250, // body_Dimmer
            0, // body_Control1
            21, // bodyColorEffects1
            42, // body_ColorMacro1
            0, // body_Pan
            0, // body_Shutter1
        ))
        assertThat(fixtureState.chFDisabled).isEqualTo(listOf<String?>(
            null, // body_Dimmer
            null, // Dimmer 1
            null, // body_Control1
            "body_Dimmer must be 0-150", // Control1 1
            "body_Dimmer must be 0-150", // Control2 2
            "body_Dimmer must be 100-200", // Control3 3
            "body_Dimmer must be 100-200", // Control 4 4
            null, // body_ColorEffects1
            "body_Dimmer must be 0-127", // ColorEffects1 1
            null, // ColorEffects2 2
            null, // body_ColorMacro1
            "ColorEffects1 1 must be active", // ColorMacro1 1
            null, // ColorMacro2 2
            null, // body_Pan
            "Shutter1 1 must be active", // Pan 1
            null, // Tilt 2
            null, // body_Shutter1
            "Tilt 2 must be 128-255", // Shutter1 1
        ))
    }

    // TODO test cyclic ChannelFunction dependencies
}
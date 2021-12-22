package org.cueglow.server.rig

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.unwrap
import com.google.common.truth.Truth.assertThat
import org.cueglow.server.json.toJsonString
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.objects.messages.GlowError
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Test
import java.util.*

fun <V> Result<V, GlowError>.unwrapLog(): V {
    return this.mapError { logger.error(it.toJsonString()); it }
        .unwrap()
}

internal class StateTransitionTest {
    val fixtureType = ExampleFixtureType.rigStateTestGdtf
    val dmxMode = fixtureType.modes[0]
    val fixture = PatchFixture(
        uuid = UUID.randomUUID(),
        fid = 1,
        name = "",
        fixtureTypeId = fixtureType.fixtureTypeId,
        dmxMode = "mode1",
        ArtNetAddress.tryFrom(1).unwrapLog(),
        DmxAddress.tryFrom(1).unwrapLog()
    )
    val fixtures = mapOf(fixture.uuid to fixture)
    val fixtureTypes = mapOf(fixtureType.fixtureTypeId to fixtureType)
    var state = defaultRigState(fixtures, fixtureTypes)
    val fixtureState get() = state[fixture.uuid]!!

    @Test
    fun trackingTest() {
        println(state)
        // we'll focus on the Control1 through Control4 ChannelFunctions
        // first, set their ModeMaster so none of them are disabled
        state = state.transition(RigStateTransition(fixture.uuid, 0, 125), fixtures, fixtureTypes) // set body_Dimmer
        println(state)
        assertThat(fixtureState.chValues).isEqualTo(
            listOf<Long>(
                125, // body_Dimmer
                0, // body_Control1
                0, // bodyColorEffects1
                0, // body_ColorMacro1
                0, // body_Pan
                0, // body_Shutter1
                0, // body_XYZ_X
                0, // body_XYZ_Y
            )
        )
        // the four interesting chF shouldn't be disabled now
        assertThat(fixtureState.chFDisabled).isEqualTo(
            listOf(
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
                null, // body_XYZ_X
                "XYZ_Y 1 must be active", // XYZ_X 1
                null, // body_XYZ_Y
                "XYZ_X 1 must be 100-255", // XYZ_Y 1
            )
        )
        validateFixtureState(fixtureState, dmxMode)

        // track active channel functions to first point of interest
        state = state.transition(RigStateTransition(fixture.uuid, 1, 42), fixtures, fixtureTypes) // set body_Control1
        assertThat(fixtureState.chValues).isEqualTo(
            listOf<Long>(
                125, // body_Dimmer
                42, // body_Control1
                0, // bodyColorEffects1
                0, // body_ColorMacro1
                0, // body_Pan
                0, // body_Shutter1
                0, // body_XYZ_X
                0, // body_XYZ_Y
            )
        )
        assertThat(fixtureState.chFDisabled).isEqualTo(
            listOf(
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
                null, // body_XYZ_X
                "XYZ_Y 1 must be active", // XYZ_X 1
                null, // body_XYZ_Y
                "XYZ_X 1 must be 100-255", // XYZ_Y 1
            )
        )
        validateFixtureState(fixtureState, dmxMode)
    }

    @Test
    fun nestedModeMaster() {
        // we'll focus on the ColorEffects and ColorMacro channels
        state =
            state.transition(RigStateTransition(fixture.uuid, 2, 21), fixtures, fixtureTypes)// set body_ColorEffects1
        state =
            state.transition(RigStateTransition(fixture.uuid, 3, 42), fixtures, fixtureTypes) // set body_ColorMacro1
        assertThat(fixtureState.chValues).isEqualTo(
            listOf<Long>(
                0, // body_Dimmer
                0, // body_Control1
                21, // bodyColorEffects1
                42, // body_ColorMacro1
                0, // body_Pan
                0, // body_Shutter1
                0, // body_XYZ_X
                0, // body_XYZ_Y
            )
        )
        assertThat(fixtureState.chFDisabled).isEqualTo(
            listOf(
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
                null, // body_XYZ_X
                "XYZ_Y 1 must be active", // XYZ_X 1
                null, // body_XYZ_Y
                "XYZ_X 1 must be 100-255", // XYZ_Y 1
            )
        )

        // changing the root ModeMaster changes all nested dependencies' disabled state
        state = state.transition(RigStateTransition(fixture.uuid, 0, 250), fixtures, fixtureTypes) // set body_Dimmer
        assertThat(fixtureState.chValues).isEqualTo(
            listOf<Long>(
                250, // body_Dimmer
                0, // body_Control1
                21, // bodyColorEffects1
                42, // body_ColorMacro1
                0, // body_Pan
                0, // body_Shutter1
                0, // body_XYZ_X
                0, // body_XYZ_Y
            )
        )
        assertThat(fixtureState.chFDisabled).isEqualTo(
            listOf(
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
                null, // body_XYZ_X
                "XYZ_Y 1 must be active", // XYZ_X 1
                null, // body_XYZ_Y
                "XYZ_X 1 must be 100-255", // XYZ_Y 1
            )
        )
    }

    @Test
    fun cyclicDependencyTest() {
        state = state.transition(
            RigStateTransition(fixture.uuid, 6, 150),
            fixtures,
            fixtureTypes
        )// set body_XYZ_X as suggested in chFDisabled
        assertThat(fixtureState.chValues).isEqualTo(
            listOf<Long>(
                0, // body_Dimmer
                0, // body_Control1
                0, // bodyColorEffects1
                0, // body_ColorMacro1
                0, // body_Pan
                0, // body_Shutter1
                150, // body_XYZ_X
                0, // body_XYZ_Y
            )
        )
        assertThat(fixtureState.chFDisabled).isEqualTo(
            listOf(
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
                null, // body_XYZ_X
                null, // XYZ_X 1
                null, // body_XYZ_Y
                null, // XYZ_Y 1
            )
        )

        state = state.transition(
            RigStateTransition(fixture.uuid, 7, 200),
            fixtures,
            fixtureTypes
        )// set body_XYZ_Y out of ModeMaster range - both should disable
        assertThat(fixtureState.chValues).isEqualTo(
            listOf<Long>(
                0, // body_Dimmer
                0, // body_Control1
                0, // bodyColorEffects1
                0, // body_ColorMacro1
                0, // body_Pan
                0, // body_Shutter1
                150, // body_XYZ_X
                200, // body_XYZ_Y
            )
        )
        assertThat(fixtureState.chFDisabled).isEqualTo(
            listOf(
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
                null, // body_XYZ_X
                "XYZ_Y 1 must be 0-150", // XYZ_X 1
                null, // body_XYZ_Y
                "XYZ_X 1 must be active", // XYZ_Y 1
            )
        )
    }
}
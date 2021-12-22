package org.cueglow.server.rig

import com.google.common.truth.Truth.assertThat
import org.apache.logging.log4j.LogManager
import org.cueglow.server.gdtf.*
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val logger = LogManager.getLogger()!!

internal class DefaultStateTest {
    @Test
    fun defaultStateTest() {
        val fixtureType = ExampleFixtureType.rigStateTestGdtf

        val expectedDefaultState = FixtureState(
            chValues = mutableListOf(
                0, // body_Dimmer
                0, // body_Control1
                0, // bodyColorEffects1
                0, // body_ColorMacro1
                0, // body_Pan
                0, // body_Shutter1
                0, // body_XYZ_X
                0, // body_XYZ_Y
            ),
            chFDisabled = mutableListOf(
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
            ),
        )

        validateFixtureState(expectedDefaultState, fixtureType.modes[0])

        val defaultState = gdtfDefaultState(fixtureType.modes[0])

        assertThat(defaultState.chValues).isEqualTo(expectedDefaultState.chValues)
        assertThat(defaultState.chFDisabled).isEqualTo(expectedDefaultState.chFDisabled)
    }

    @Test
    fun initialFunctionIndIsRightInStateTestFixture() {
        val fixtureType = ExampleFixtureType.rigStateTestGdtf

        assertEquals(
            listOf(1, 3, 8, 11, 14, 17, 19, 21),
            fixtureType.modes[0].multiByteChannels.map { it.initialChannelFunctionInd }
        )
    }
}

/**
 * Check if the fixtureState is a valid state for the provided dmxMode. If not, throws an AssertionError.
 */
fun validateFixtureState(fixtureState: FixtureState, dmxMode: GlowDmxMode): Unit {
    val (chValues, chFDisabled) = fixtureState
    val chs = dmxMode.multiByteChannels
    val chFs = dmxMode.channelFunctions
    // check there are as many chValues as multiByteChannels
    assert(chValues.size == chs.size)
    // check there are as many channel function disabled values as channelFunctions
    assert(chFDisabled.size == chFs.size)

    // check each value is inside the bytes range of the channel
    chValues.forEachIndexed { chInd, chValue ->
        val maxDmxValue = maximumValueOfBytes(chs[chInd].bytes)
        assert(chValue in 0..maxDmxValue)
    }

    printModeMasterDependencies(dmxMode)
}


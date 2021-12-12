package org.cueglow.server.rig

import com.github.michaelbull.result.*
import org.apache.logging.log4j.LogManager
import org.cueglow.server.gdtf.GlowDmxMode
import org.cueglow.server.gdtf.gdtfDefaultState
import org.cueglow.server.gdtf.printModeMasterDependencies
import org.cueglow.server.json.toJsonString
import org.cueglow.server.objects.messages.InvalidFixtureState
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val logger = LogManager.getLogger()!!

internal class DefaultStateTest {
    @Test
    fun defaultStateTest() {
        val fixtureType = ExampleFixtureType.rigStateTestGdtf

        val expectedDefaultState: FixtureState = mutableListOf(
            ChannelFunctionState(0, false, null), // body_Dimmer
            ChannelFunctionState(0, false, null), // Dimmer 1
            ChannelFunctionState(0, false, null), // body_Control1
            ChannelFunctionState(0, false, null), // Control1 1
            ChannelFunctionState(255, true, null), // Control2 2
            ChannelFunctionState(0, false, "body_Dimmer must be 100-200"), // Control3 3
            ChannelFunctionState(255, true, "body_Dimmer must be 100-200"), // Control 4 4
            ChannelFunctionState(0, false, null), // body_ColorEffects1
            ChannelFunctionState(0, false, null), // ColorEffects1 1
            ChannelFunctionState(0, false, "body_Dimmer must be 128-255"), // ColorEffects2 2
            ChannelFunctionState(0, false, null), // body_ColorMacro1
            ChannelFunctionState(0, false, null), // ColorMacro1 1
            ChannelFunctionState(0, false, "ColorEffects2 2 must be active"), // ColorMacro2 2
            ChannelFunctionState(0, false, null), // body_Pan
            ChannelFunctionState(0, false, "Shutter1 1 must be active"), // Pan 1
            ChannelFunctionState(255, true, null), // Tilt 2
            ChannelFunctionState(0, false, null), // body_Shutter1
            ChannelFunctionState(0, false, "Tilt 2 must be active"), // Shutter1 1
        )

        validateFixtureState(expectedDefaultState, fixtureType.modes[0])
            .mapError { logger.error(it.toJsonString()); it }
            .unwrap()

        assertEquals(expectedDefaultState, gdtfDefaultState(fixtureType.modes[0]))
    }

    @Test
    fun initialFunctionIndIsRightInStateTestFixture() {
        val fixtureType = ExampleFixtureType.rigStateTestGdtf

        assertEquals(
            listOf(1, 3, 8, 11, 14, 17),
            fixtureType.modes[0].multiByteChannels.map{it.initialChannelFunctionInd}
        )
    }
}

/**
 * Check if the fixtureState is a valid state for the provided dmxMode. If not, returns an Err.
 */
fun validateFixtureState(fixtureState: FixtureState, dmxMode: GlowDmxMode): Result<Unit, InvalidFixtureState> {
    // check there are as many ChannelFunctionStates as ChannelFunctions
    if (fixtureState.size != dmxMode.channelFunctions.size) { return Err(InvalidFixtureState(
        "Number of ChannelFunctionStates is ${fixtureState.size} but should be ${dmxMode.channelFunctions.size}"
    )) }

    fixtureState.forEachIndexed { chFInd, chFState ->
        val chF = dmxMode.channelFunctions[chFInd]
        // check value is in dmxFrom/dmxTo
        if (chFState.value !in chF.dmxFrom..chF.dmxTo) {
            return Err(
                InvalidFixtureState(
                    "Value ${chFState.value} for ChannelFunction with index $chFInd is outside the range ${chF.dmxFrom} to ${chF.dmxTo}"
                )
            )
        }
        // check that outOfRange is true if the current value of the rawDmx ChannelFunction is outside dmxFrom/dmxTo and false otherwise
        val ch = dmxMode.multiByteChannels[chF.multiByteChannelInd]
        val rawChFInd = ch.channelFunctionIndices.first
        val rawValue = fixtureState[rawChFInd].value
        val expectedOutOfRange = rawValue !in chF.dmxFrom..chF.dmxTo
        if (expectedOutOfRange != chFState.outOfRange) {
            return Err(
                InvalidFixtureState(
                    "ChannelFunction with index $chFInd has has outOfRange of ${chFState.outOfRange} but should be $expectedOutOfRange"
                )
            )
        }
    }

    dmxMode.multiByteChannels.forEach { ch ->
        // ensure all active channels in a channel have the same value
        val chFStates = fixtureState.slice(ch.channelFunctionIndices)
        val rawValue = chFStates[0].value
        for (i in 1 until chFStates.size) {
            if (chFStates[i].active().not()) { continue }
            val chFValue = chFStates[i].value
            if (chFValue != rawValue) { return Err(InvalidFixtureState(
                "ChannelFunction with Index ${ch.channelFunctionIndices.first + i} is active and does not have " +
                        "the same value as its rawDmx ChannelFunction."
            ))}
        }
    }

    // check that modeDisabled is right
    printModeMasterDependencies(dmxMode)
    val visited = MutableList(fixtureState.size) { false }

    val graph = dmxMode.channelFunctionDependencies
    val edges = graph.edgeSet()

    edges.forEach { edge ->
        val parentInd = graph.getEdgeSource(edge)!!
        val childInd = graph.getEdgeTarget(edge)!!

        val parent = dmxMode.channelFunctions[parentInd]
        val parentState = fixtureState[parentInd]

        val childState = fixtureState[childInd]

        if (parentState.active()) {
            if (parentState.value in edge.from..edge.to) {
                // parent is active and its value is in ModeFrom/ModeTo range
                // modeDisabled should be null
                if (childState.modeDisabled != null) {return Err(InvalidFixtureState(
                    "ChannelFunction with index $childInd is modeDisabled even though it shouldn't be"
                ))}
            } else {
                // parent is active, but its value is not in ModeFrom/ModeTo range
                // modeDisabled should show the reason for being disabled
                val expectedText = "${parent.name} must be ${edge.from}-${edge.to}"
                if (childState.modeDisabled != expectedText) { return Err(InvalidFixtureState(
                    "ChannelFunction with index $childInd has a modeDisabled of '${childState.modeDisabled}' but it should be '$expectedText'"
                )) }
            }
        } else {
            // parent is inactive, which should be reflected in ModeMaster text
            val expectedText = "${parent.name} must be active"
            if (childState.modeDisabled != expectedText) {return Err(InvalidFixtureState(
                "ChannelFunction with index $childInd has a modeDisabled of '${childState.modeDisabled}' but it should be '$expectedText'"
            ))}
        }
        visited[childInd] = true
    }

    visited.forEachIndexed { ind, alreadyVisited ->
        if (!alreadyVisited) {
            // modeDisabled has to be null for all ChannelFunctions not visited yet
            val state = fixtureState[ind]
            if (state.modeDisabled != null) { return Err(
                InvalidFixtureState(
                    "ChannelFunction with index $ind has no ModeMaster but its modeDisabled isn't null"
            ))}
        }
    }

    return Ok(Unit)
}
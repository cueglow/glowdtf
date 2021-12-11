package org.cueglow.server.rig

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.unwrap
import org.cueglow.server.gdtf.GlowDmxMode
import org.cueglow.server.gdtf.gdtfDefaultState
import org.cueglow.server.json.DirectedAcyclicGraphConverter
import org.cueglow.server.objects.messages.InvalidFixtureState
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.jgrapht.traverse.BreadthFirstIterator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class DefaultStateTest {
    @Disabled // TODO
    @Test
    private fun defaultStateTest() {
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
            ChannelFunctionState(0, false, "body_ColorEffects1.ColorEffects1.ColorEffects2 2 must be active"), // ColorMacro2 2
            ChannelFunctionState(0, false, null), // body_Pan
            ChannelFunctionState(0, false, "body_Shutter1.Shutter1.Shutter1 1 must be active"), // Pan 1
            ChannelFunctionState(255, true, null), // Tilt 2
            ChannelFunctionState(0, false, null), // body_Shutter1
            ChannelFunctionState(0, false, "body_Pan.Pan.Tilt 2 must be active"), // Shutter1 1
        )

        validateFixtureState(expectedDefaultState, fixtureType.modes[0]).unwrap()

        assertEquals(expectedDefaultState, gdtfDefaultState(fixtureType))
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
        // check that outOfRange true if the current value of the rawDmx ChannelFunction is outside dmxFrom/dmxTo and false otherwise
        val ch = dmxMode.multiByteChannels[chF.multiByteChannelInd]
        val rawChFInd = ch.channelFunctionIndices.first
        val chValue = fixtureState[rawChFInd].value
        val expectedOutOfRange = chValue !in chF.dmxFrom..chF.dmxTo
        if (expectedOutOfRange != chFState.outOfRange) {
            return Err(
                InvalidFixtureState(
                    "ChannelFunction with index $chFInd is marked outOfRange even though the current DMX value " +
                            "$chValue is within its bounds of  ${chF.dmxFrom} to ${chF.dmxTo}"
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
    val graphJson = Klaxon().converter(DirectedAcyclicGraphConverter).toJsonString(dmxMode.channelFunctionDependencies)
    val pretty: String = (Parser.default().parse(StringBuilder(graphJson)) as JsonObject).toJsonString(true)
    println(pretty)
    println(dmxMode.channelFunctions.mapIndexed { ind, it -> ind.toString() + " " + it.name }.joinToString("\n"))
    val visited = List(fixtureState.size) { false }
    val itr = BreadthFirstIterator(dmxMode.channelFunctionDependencies)
    itr.forEach { chFInd ->
        // printing for debug
        println(chFInd)
        println(dmxMode.channelFunctions[chFInd].name)
        println(itr.getParent(chFInd) ?: "no parent")
        println(itr.getSpanningTreeEdge(chFInd))
        println("----------------------------------")
        /*// check if we're a root node
        val isRoot = itr.getParent(chFInd) == null
        if (isRoot) {
            if (fixtureState[chFInd].modeDisabled != null) { return Err(InvalidFixtureState(
                "ChannelFunction with index ${chFInd} is a root node in the ModeMaster dependency tree but is modeDisabled"
            ))}
            TODO("set visited")
            return@forEach
        }
        // from here: channelFunction depends on a ModeMaster
        val masterInd = itr.getParent(chFInd) // TODO after this do a null check and handle root nodes
        val master = dmxMode.channelFunctions[]*/

    }





    // go through all tress in channelFunctionDependencies and validate their modeDisabled field (including text content).
    // modeDisabled must only be null if enabled.
    // Check that all ChannelFunctions are visited with this check (even the ones not in dependencies, which must be null).



    TODO()
}
package org.cueglow.server.gdtf

import com.google.common.truth.Truth.assertThat
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Test

internal class ModeMasterDependencyGraphTest {
    private data class Dep(
        val source: Int,
        val range: Pair<Long, Long>,
        val target: Int,
    )

    @Test
    fun testModeMasterDependencyGraphWithStateTestFixture() {
        val fixtureType = ExampleFixtureType.rigStateTestGdtf
        val dmxMode = fixtureType.modes[0]
        val deps = dmxMode.channelFunctionDependencies
        val edges = deps.edgeSet()

        // print for debug
        printModeMasterDependencies(dmxMode)

        val expectedDependencies = setOf(
            Dep(3, Pair(0, 150), 0),
            Dep(4, Pair(0, 150), 0),
            Dep(5, Pair(100, 200), 0),
            Dep(6, Pair(100, 200), 0),
            Dep(8, Pair(0, 127), 0),
            Dep(9, Pair(128, 255), 0),
            Dep(11, Pair(0, 255), 8),
            Dep(12, Pair(0, 255), 9),
            Dep(14, Pair(0, 255), 17),
            Dep(17, Pair(128, 255), 15),
            Dep(19, Pair(0, 150), 21),
            Dep(21, Pair(100, 255), 19)
        )

        val actualDependencies = edges
            .map { Dep(deps.getEdgeSource(it), Pair(it.from, it.to), deps.getEdgeTarget(it)) }
            .toSet()

        //assertEquals(expectedDependencies, actualDependencies)
        assertThat(actualDependencies).isEqualTo(expectedDependencies)
    }
}

fun printModeMasterDependencies(
    dmxMode: GlowDmxMode,
) {
    val deps = dmxMode.channelFunctionDependencies
    val edges = deps.edgeSet()
    println()
    println("Channel Functions")
    println("===================")
    println(dmxMode.channelFunctions.mapIndexed { ind, it -> ind.toString() + " " + it.name }.joinToString("\n"))

    println()
    println("Calculated Edges")
    println("===================")
    println(edges.joinToString("\n"))
    println()
}
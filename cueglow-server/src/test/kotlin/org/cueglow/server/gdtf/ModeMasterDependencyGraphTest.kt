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
        printModeMasterDependencies(dmxMode, edges)

        val expectedDependencies = setOf(
            Dep(0, Pair(0, 150), 3),
            Dep(0, Pair(0, 150), 4),
            Dep(0, Pair(100, 200), 5),
            Dep(0, Pair(100, 200), 6),
            Dep(0, Pair(0, 127), 8),
            Dep(0, Pair(128, 255), 9),
            Dep(8, Pair(0, 255), 11),
            Dep(9, Pair(0, 255), 12),
            Dep(17, Pair(0, 255), 14),
            Dep(15, Pair(128, 255), 17),
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
    edges: MutableSet<DependencyEdge>,
) {
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
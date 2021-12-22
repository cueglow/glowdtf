package org.cueglow.server.gdtf

import com.google.common.truth.Truth.assertThat
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.rig.RigStateTransition
import org.cueglow.server.rig.defaultRigState
import org.cueglow.server.rig.transition
import org.cueglow.server.rig.unwrapLog
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Test
import java.util.*

class RenderTest {
    val fixtureType = ExampleFixtureType.channelLayoutTestGdtf
    val dmxMode = fixtureType.modes[0]
    val fixture = PatchFixture(
        uuid = UUID.randomUUID(),
        fid = 1,
        name = "",
        fixtureTypeId = fixtureType.fixtureTypeId,
        dmxMode = "Mode 1",
        ArtNetAddress.tryFrom(1).unwrapLog(),
        DmxAddress.tryFrom(1).unwrapLog()
    )
    val fixtures = mapOf(fixture.uuid to fixture)
    val fixtureTypes = mapOf(fixtureType.fixtureTypeId to fixtureType)
    var state = defaultRigState(fixtures, fixtureTypes)
    val fixtureState get() = state[fixture.uuid]!!

    @Test
    fun testRender() {
        // print layout
        println(dmxMode.multiByteChannels.mapIndexed { ind, it -> "$ind ${it.name}" }.joinToString("\n"))
        println()
        println(dmxMode.channelLayout.flatten().mapIndexed { ind, it -> "$ind $it" }.joinToString("\n"))

        state = state.transition(RigStateTransition(fixture.uuid, 0, 255), fixtures, fixtureTypes) // turn on Dimmer
        state =
            state.transition(RigStateTransition(fixture.uuid, 10, 100_000), fixtures, fixtureTypes) // change Main_XYZ_Y

        val expected = MutableList<Byte>(20) { 0 }
        expected[0] = -1
        expected[15] = 0xa0.toByte() // LSB
        expected[13] = 0x86.toByte()
        expected[14] = 0x01.toByte() // MSB

        assertThat(renderGdtfStateToDmx(fixtureState.chValues, dmxMode).toList()).isEqualTo(expected)
    }
}
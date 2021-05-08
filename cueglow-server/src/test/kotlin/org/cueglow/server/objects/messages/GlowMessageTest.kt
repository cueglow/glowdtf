package org.cueglow.server.objects.messages

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.unwrap
import com.karumi.kotlinsnapshot.matchWithSnapshot
import org.cueglow.server.json.fromJsonString
import org.cueglow.server.json.toJsonString
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.patch.PatchFixtureUpdate
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GlowMessageTest {

    @Test
    fun testRemoveFixtures() {
        val uuid = UUID.fromString("a5e98b9a-95db-4ab1-92ec-c4b72f1d546c")
        val messageId = 42
        val glowMessage = GlowMessage.RemoveFixtures(listOf(uuid), messageId)

        val expectedJson =
            """{"event" : "removeFixtures", "data" : ["a5e98b9a-95db-4ab1-92ec-c4b72f1d546c"], "messageId" : 42}""".trimIndent()

        assertEquals(expectedJson, glowMessage.toJsonString())

        val parsed = GlowMessage.fromJsonString(expectedJson) as GlowMessage.RemoveFixtures

        assertEquals(glowMessage.event, parsed.event)
        assertEquals(glowMessage.data, parsed.data)
        assertEquals(glowMessage.messageId, parsed.messageId)
    }

    @Test
    fun testSubscribe() {
        val glowMessage = GlowMessage.Subscribe(GlowTopic.PATCH)

        val expectedJson =
            """{"event" : "subscribe", "data" : "patch"}""".trimIndent()

        assertEquals(expectedJson, glowMessage.toJsonString())

        val parsed = GlowMessage.fromJsonString(expectedJson)

        assertEquals(glowMessage.event, parsed.event)
        assertEquals(glowMessage.messageId, parsed.messageId)
    }

    @Test
    fun testUnsubscribe() {
        val glowMessage = GlowMessage.Unsubscribe(GlowTopic.PATCH)

        val expectedJson =
            """{"event" : "unsubscribe", "data" : "patch"}""".trimIndent()

        assertEquals(expectedJson, glowMessage.toJsonString())

        val parsed = GlowMessage.fromJsonString(expectedJson)

        assertEquals(glowMessage.event, parsed.event)
        assertEquals(glowMessage.messageId, parsed.messageId)
    }

    @Test
    fun testPatchFixtureUpdate() {
        val uuid = UUID.fromString("132a4794-e7fe-4e19-9e26-ce0f09663625")
        val update = PatchFixtureUpdate(
            uuid,
            fid = 42,
            address = Ok(DmxAddress.tryFrom(58).unwrap()),
        )
        val glowMessage = GlowMessage.UpdateFixtures(listOf(update))

        val expectedJson =
            """{"event" : "updateFixtures", "data" : [{"uuid" : "132a4794-e7fe-4e19-9e26-ce0f09663625", "fid" : 42, "universe" : null, "address" : 58}]}""".trimIndent()

        assertEquals(expectedJson, glowMessage.toJsonString())

        val parsed = GlowMessage.fromJsonString(expectedJson) as GlowMessage.UpdateFixtures

        assertEquals(glowMessage.event, parsed.event)
        assertEquals(glowMessage.data, parsed.data)
        assertEquals(glowMessage.messageId, parsed.messageId)
    }

    @Test
    fun testPatchFixtureSerializeNull() {
        val uuid = UUID.fromString("2fef9c95-d472-49e9-8e2e-9d9dcc1511be")
        val fixtureTypeId = UUID.fromString("2f00d1f2-4019-4069-9d21-7d90decf24a0")
        val fixture = PatchFixture(
            uuid,
            fid = 42,
            name = "fixture name",
            fixtureTypeId = fixtureTypeId,
            dmxMode = "mode1",
            universe = null,
            address = null,
        )
        val glowMessage = GlowMessage.AddFixtures(listOf(fixture))
        val serialized = glowMessage.toJsonString()
        assertFalse(serialized.contains("address"))
        assertFalse(serialized.contains("universe"))
    }

    private val exampleFixtureType = ExampleFixtureType.esprite

    private val examplePatchFixture = ExampleFixtureType.esprite_fixture

    @Test
    fun addFixtureTypesSnapshotTest() {
        val glowMessage = GlowMessage.AddFixtureTypes(listOf(exampleFixtureType))
        val serialized = glowMessage.toJsonString()
        serialized.matchWithSnapshot()
    }

    @Test
    fun patchInitialStateSnapshotTest() {
        val glowPatch = GlowPatch(listOf(examplePatchFixture), listOf(exampleFixtureType))
        val glowMessage = GlowMessage.PatchInitialState(glowPatch)
        val serialized = glowMessage.toJsonString()
        serialized.matchWithSnapshot()
    }
}
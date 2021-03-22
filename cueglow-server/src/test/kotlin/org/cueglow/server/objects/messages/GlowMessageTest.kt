package org.cueglow.server.objects.messages

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.unwrap
import org.cueglow.server.gdtf.fixtureTypeFromGdtfResource
import org.cueglow.server.json.fromJsonString
import org.cueglow.server.json.toJsonString
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.patch.PatchFixture
import org.cueglow.server.patch.PatchFixtureUpdate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
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
    fun testPatchSubscribe() {
        val glowMessage = GlowMessage.PatchSubscribe()

        val expectedJson =
            """{"event" : "patchSubscribe"}""".trimIndent()

        assertEquals(expectedJson, glowMessage.toJsonString())

        val parsed = GlowMessage.fromJsonString(expectedJson)

        assertEquals(glowMessage.event, parsed.event)
        assertEquals(glowMessage.messageId, parsed.messageId)
    }

    @Test
    fun testPatchUnsubscribe() {
        val glowMessage = GlowMessage.PatchUnsubscribe()

        val expectedJson =
            """{"event" : "patchUnsubscribe"}""".trimIndent()

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

    private val exampleFixtureType =
        fixtureTypeFromGdtfResource("Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf", this.javaClass)

    private val examplePatchFixture = PatchFixture(
        UUID.fromString("91faaa61-624b-477a-a6c2-de00c717b3e6"),
        1,
        "exampleFixture",
        exampleFixtureType.fixtureTypeId,
        "mode1",
        ArtNetAddress.tryFrom(1).unwrap(),
        DmxAddress.tryFrom(1).unwrap(),
    )

    @Test
    @Disabled
    fun addFixtureTypesSnapshotTest() {
        val glowMessage = GlowMessage.AddFixtureTypes(listOf(exampleFixtureType))
        val serialized = glowMessage.toJsonString()
        println(serialized)
        // this is just a snapshot test! Will break in the future.
        // TODO update once channelLayout is documented
        // TODO move to proper snapshot testing tool
        assertEquals(
            """{"event" : "addFixtureTypes", "data" : [{"fixtureTypeId" : "7fb33577-09c9-4bf0-be3b-ef0dc3bef4be", "manufacturer" : "Robe lighting", "modes" : [{"channelCount" : 49, "name" : "mode1"}, {"channelCount" : 42, "name" : "mode 2"}], "name" : "Robin Esprite"}]}""",

            serialized
        )
    }

    @Test
    @Disabled
    fun patchInitialStateSnapshotTest() {
        val glowPatch = GlowPatch(listOf(examplePatchFixture), listOf(exampleFixtureType))
        val glowMessage = GlowMessage.PatchInitialState(glowPatch)
        val serialized = glowMessage.toJsonString()
        println(serialized)
        // this is just a snapshot test! Will break in the future.
        // TODO update once channelLayout is documented
        // TODO move to proper snapshot testing tool
        assertEquals(
            """{"event" : "patchInitialState", "data" : {"fixtures" : [{"address" : 1, "dmxMode" : "mode1", "fid" : 1, "fixtureTypeId" : "7fb33577-09c9-4bf0-be3b-ef0dc3bef4be", "name" : "exampleFixture", "universe" : 1, "uuid" : "91faaa61-624b-477a-a6c2-de00c717b3e6"}], "fixtureTypes" : [{"fixtureTypeId" : "7fb33577-09c9-4bf0-be3b-ef0dc3bef4be", "manufacturer" : "Robe lighting", "modes" : [{"channelCount" : 49, "name" : "mode1"}, {"channelCount" : 42, "name" : "mode 2"}], "name" : "Robin Esprite"}]}}""",
            serialized
        )
    }
}
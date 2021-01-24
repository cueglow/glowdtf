package org.cueglow.server.patch

import com.github.michaelbull.result.unwrap
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.HashMap

internal class PatchTest {
    @Test
    fun patchFixtureList() {
        // assert empty map in the beginning
        assertEquals(HashMap<UUID, PatchFixture>(), Patch.getFixtureList())
        // add example fixture
        val exampleFixture = PatchFixture(1, "", UUID.randomUUID(),
            "1ch", ArtNetAddress.tryFrom(1).unwrap(), DmxAddress.tryFrom(1).unwrap())
        Patch.addFixture(exampleFixture)
        // check it's not empty anymore
        assertNotEquals(HashMap<UUID, PatchFixture>(), Patch.getFixtureList())
        // check it's in there
        val exampleMap = HashMap<UUID, PatchFixture>()
        exampleMap[exampleFixture.uuid] = exampleFixture
        assertEquals(exampleMap, Patch.getFixtureList())
        // remove it
        Patch.removeFixture(exampleFixture.uuid)
        // check it's gone
        assertEquals(HashMap<UUID, PatchFixture>(), Patch.getFixtureList())
    }

    @Test
    fun patchFixtureTypeList() {
        // assert empty map in the beginning
        assertEquals(HashMap<UUID, GDTF>(), Patch.getFixtureTypeList())
        // add example fixture type
        val exampleFixtureType = GDTF(UUID.randomUUID())
        Patch.addFixtureType(exampleFixtureType)
        // check it's not empty
        assertNotEquals(HashMap<UUID, GDTF>(), Patch.getFixtureTypeList())
        // check the example fixture type is in there
        val exampleMap = HashMap<UUID, GDTF>()
        exampleMap[exampleFixtureType.fixtureTypeId] = exampleFixtureType
        assertEquals(
            exampleMap,
            Patch.getFixtureTypeList()
        )
        // remove it
        Patch.removeFixtureType(exampleFixtureType.fixtureTypeId)
        // check it's gone
        assertEquals(HashMap<UUID, GDTF>(), Patch.getFixtureTypeList())
    }

    // TODO
    // test that stream updates are called
}
package org.cueglow.server.patch

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.unwrap
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.locks.ReentrantLock

internal class PatchTest {

    private val patch = Patch(LinkedBlockingQueue(), ReentrantLock())

    private val exampleFixtureType = ExampleFixtureType.esprite

    private val exampleFixture = PatchFixture(UUID.randomUUID(),1, "", exampleFixtureType.fixtureTypeId,
        "mode1", ArtNetAddress.tryFrom(1).unwrap(), DmxAddress.tryFrom(1).unwrap())
    private val exampleFixture2 = PatchFixture(UUID.randomUUID(),2, "", exampleFixtureType.fixtureTypeId,
        "mode1", ArtNetAddress.tryFrom(2).unwrap(), DmxAddress.tryFrom(1).unwrap())

    @Test
    fun patchList() {
        assertTrue(patch.getFixtures().isEmpty())
        assertTrue(patch.getFixtureTypes().isEmpty())

        // without fixture type in patch, fixture cannot be added
        assertTrue(patch.addFixtures(listOf(exampleFixture)) is Err)
        assertTrue(patch.getFixtures().isEmpty())

        // add fixture type
        patch.addFixtureTypes(listOf(exampleFixtureType)).unwrap()
        assertEquals(1, patch.getFixtureTypes().size)
        assertEquals(exampleFixtureType, patch.getFixtureTypes()[exampleFixtureType.fixtureTypeId])

        // adding fixture type again does not work
        assertTrue(patch.addFixtureTypes(listOf(exampleFixtureType)) is Err)
        assertEquals(1, patch.getFixtureTypes().size)

        // add fixture
        patch.addFixtures(listOf(exampleFixture)).unwrap()
        assertEquals(1, patch.getFixtures().size)
        assertEquals(exampleFixture, patch.getFixtures()[exampleFixture.uuid])

        // update fixture
        patch.updateFixtures(listOf(PatchFixtureUpdate(exampleFixture.uuid, fid = 100)))
        assertEquals(100, patch.getFixtures()[exampleFixture.uuid]?.fid)

        // updating unpatched fixture does not work
        val aRandomUuid = UUID.fromString("59fa8699-8478-45ca-91e7-b72eec895954")
        assertTrue(patch.updateFixtures(listOf(PatchFixtureUpdate(aRandomUuid, name="a name"))) is Err)

        // removing unknown fixture type does not work
        val randomUuid = UUID.fromString("8eff8f2b-c818-4a3e-87a3-ba5b43f8fd0c")
        assertTrue(patch.removeFixtureTypes(listOf(randomUuid)) is Err)
        assertEquals(1, patch.getFixtureTypes().size)

        // removing unknown fixture does not work
        assertTrue(patch.removeFixtures(listOf(randomUuid)) is Err)
        assertEquals(1, patch.getFixtures().size)

        // remove fixture
        patch.removeFixtures(listOf(exampleFixture.uuid))
        assertEquals(0, patch.getFixtures().size)

        // test that when removing fixture type, the associated fixtures are also deleted
        // first add two fixtures
        patch.addFixtures(listOf(exampleFixture, exampleFixture2)).unwrap()
        assertEquals(2, patch.getFixtures().size)
        // now delete fixture type
        patch.removeFixtureTypes(listOf(exampleFixtureType.fixtureTypeId)).unwrap()
        assertTrue(patch.getFixtures().isEmpty())
        assertTrue(patch.getFixtureTypes().isEmpty())
    }

    @Test
    fun getGlowPatchIsImmutable() {
        patch.addFixtureTypes(listOf(exampleFixtureType)).unwrap()
        patch.addFixtures(listOf(exampleFixture)).unwrap()
        val glowPatch = patch.getGlowPatch()
        assertEquals(1, glowPatch.fixtures.size)
        patch.addFixtures(listOf(exampleFixture2)).unwrap()
        assertEquals(2, patch.getFixtures().size)
        assertEquals(1, glowPatch.fixtures.size)
    }

    @Test
    fun getFixturesIsImmutable() {
        patch.addFixtureTypes(listOf(exampleFixtureType)).unwrap()
        patch.addFixtures(listOf(exampleFixture)).unwrap()
        // get fixtures
        val fixtures = patch.getFixtures()
        assertEquals(1, fixtures.size)
        // change fixtures
        patch.addFixtures(listOf(exampleFixture2)).unwrap()
        // getting again should change, the map we got before should not change
        assertEquals(2, patch.getFixtures().size)
        assertEquals(1, fixtures.size)
        // we should not be able to cast the map to a mutable map
        assertThrows <Exception> { (fixtures as MutableMap).remove(exampleFixture.uuid) }
        // nothing should have changed from trying to cast
        assertEquals(2, patch.getFixtures().size)
        assertEquals(1, fixtures.size)
        // update a fixture
        patch.updateFixtures(listOf(PatchFixtureUpdate(
            exampleFixture.uuid,
            fid = 42,
            name = "newName",
            universe = Ok(ArtNetAddress.tryFrom(42).unwrap()),
            address = Ok(DmxAddress.tryFrom(42).unwrap()))))
        // old map should not change, getting again should change
        assertTrue(exampleFixture.isSimilar(fixtures[exampleFixture.uuid]!!))
        assertFalse(exampleFixture.isSimilar(patch.getFixtures()[exampleFixture.uuid]!!))
    }

    @Test
    fun getFixtureTypesIsImmutable() {
        patch.addFixtureTypes(listOf(exampleFixtureType)).unwrap()
        patch.addFixtures(listOf(exampleFixture)).unwrap()
        // get fixture types
        val fixtureTypes = patch.getFixtureTypes()
        assertEquals(1, fixtureTypes.size)
        // remove the fixture type
        patch.removeFixtureTypes(listOf(exampleFixtureType.fixtureTypeId))
        // getting again should change, the map we got before should not change
        assertEquals(0, patch.getFixtureTypes().size)
        assertEquals(1, fixtureTypes.size)
        // we should not be able to cast the map to a mutable map
        assertThrows <Exception> { (fixtureTypes as MutableMap).remove(exampleFixtureType.fixtureTypeId) }
        // nothing should have changed from trying to cast
        assertEquals(0, patch.getFixtureTypes().size)
        assertEquals(1, fixtureTypes.size)
    }
}
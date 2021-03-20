package org.cueglow.server.patch

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.unwrap
import org.cueglow.server.gdtf.FixtureType
import org.cueglow.server.gdtf.parseGdtf
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.util.*

internal class PatchTest {

    private val exampleGdtfFileName = "Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf"
    private val inputStream: InputStream = javaClass.classLoader.getResourceAsStream(exampleGdtfFileName) ?:
        throw Error("inputStream is Null")
    private val parsedGdtf = parseGdtf(inputStream).unwrap()
    private val exampleFixtureType = FixtureType(parsedGdtf)

    private val exampleFixture = PatchFixture(UUID.randomUUID(),1, "", exampleFixtureType.fixtureTypeId,
        "mode1", ArtNetAddress.tryFrom(1).unwrap(), DmxAddress.tryFrom(1).unwrap())
    private val exampleFixture2 = PatchFixture(UUID.randomUUID(),2, "", exampleFixtureType.fixtureTypeId,
        "mode1", ArtNetAddress.tryFrom(2).unwrap(), DmxAddress.tryFrom(1).unwrap())

    @Test
    fun patchList() {
        // instantiate
        val patch = Patch()
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
        val patch = Patch()
        patch.addFixtureTypes(listOf(exampleFixtureType)).unwrap()
        patch.addFixtures(listOf(exampleFixture)).unwrap()
        val glowPatch = patch.getGlowPatch()
        assertEquals(1, glowPatch.fixtures.size)
        patch.addFixtures(listOf(exampleFixture2)).unwrap()
        assertEquals(2, patch.getFixtures().size)
        assertEquals(1, glowPatch.fixtures.size)
    }
}
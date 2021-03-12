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
        assertTrue(patch.putFixture(exampleFixture) is Err)
        assertTrue(patch.getFixtures().isEmpty())

        // add fixture type
        patch.putFixtureType(exampleFixtureType).unwrap()
        assertEquals(1, patch.getFixtureTypes().size)
        assertEquals(exampleFixtureType, patch.getFixtureTypes()[exampleFixtureType.fixtureTypeId])

        // adding fixture type again does not work
        assertTrue(patch.putFixtureType(exampleFixtureType) is Err)
        assertEquals(1, patch.getFixtureTypes().size)

        // add fixture
        patch.putFixture(exampleFixture).unwrap()
        assertEquals(1, patch.getFixtures().size)
        assertEquals(exampleFixture, patch.getFixtures()[exampleFixture.uuid])

        // removing unknown fixture type does not work
        val randomUuid = UUID.fromString("8eff8f2b-c818-4a3e-87a3-ba5b43f8fd0c")
        assertTrue(patch.removeFixtureType(randomUuid) is Err)
        assertEquals(1, patch.getFixtureTypes().size)

        // removing unknown fixture does not work
        assertTrue(patch.removeFixture(randomUuid) is Err)
        assertEquals(1, patch.getFixtures().size)

        // remove fixture
        patch.removeFixture(exampleFixture.uuid)
        assertEquals(0, patch.getFixtures().size)

        // test that when removing fixture type, the associated fixtures are also deleted
        // first add two fixtures
        patch.putFixture(exampleFixture).unwrap()
        patch.putFixture(exampleFixture2).unwrap()
        assertEquals(2, patch.getFixtures().size)
        // now delete fixture type
        patch.removeFixtureType(exampleFixtureType.fixtureTypeId).unwrap()
        assertTrue(patch.getFixtures().isEmpty())
        assertTrue(patch.getFixtureTypes().isEmpty())
    }
}
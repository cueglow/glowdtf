package org.cueglow.server.patch

import com.github.michaelbull.result.unwrap
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.gdtf.parseGdtf
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.util.*

internal class PatchTest {
    val patch = Patch()

    @Test
    fun patchFixtureList() {
        assertTrue(patch.getFixtures().isEmpty())

        val exampleFixture = PatchFixture(1, "", UUID.randomUUID(),
            "1ch", ArtNetAddress.tryFrom(1).unwrap(), DmxAddress.tryFrom(1).unwrap())
        patch.putFixture(exampleFixture)

        assertEquals(1, patch.getFixtures().size)
        assertEquals(exampleFixture, patch.getFixtures()[exampleFixture.uuid])

        patch.removeFixture(exampleFixture.uuid)

        assertTrue(patch.getFixtures().isEmpty())
    }

    @Test
    fun patchFixtureTypeList() {
        assertTrue(patch.getFixtureTypes().isEmpty())

        val exampleGdtfFileName = "Robe_Lighting@Robin_Esprite@20112020v1.7.gdtf"
        val inputStream: InputStream = javaClass.classLoader.getResourceAsStream(exampleGdtfFileName) ?:
                throw Error("inputStream is Null")
        val parsedGdtf = parseGdtf(inputStream).unwrap()
        val exampleFixtureType = GdtfWrapper(parsedGdtf)

        patch.putFixtureType(exampleFixtureType)

        assertEquals(1, patch.getFixtureTypes().size)
        assertEquals(exampleFixtureType, patch.getFixtureTypes()[exampleFixtureType.fixtureTypeId])

        patch.removeFixtureType(exampleFixtureType.fixtureTypeId)

        assertTrue(patch.getFixtureTypes().isEmpty())
    }

    // TODO
    // test that stream updates are called
}
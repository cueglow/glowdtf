package org.cueglow.server.patch

import com.github.michaelbull.result.unwrap
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

internal class PatchTest {
    @Test
    fun patchFixtureList() {
        assertTrue(Patch.getFixtureList().isEmpty())

        val exampleFixture = PatchFixture(1, "", UUID.randomUUID(),
            "1ch", ArtNetAddress.tryFrom(1).unwrap(), DmxAddress.tryFrom(1).unwrap())
        Patch.putFixture(exampleFixture)

        assertEquals(1, Patch.getFixtureList().size)
        assertEquals(exampleFixture, Patch.getFixtureList()[exampleFixture.uuid])

        Patch.removeFixture(exampleFixture.uuid)

        assertTrue(Patch.getFixtureList().isEmpty())
    }

    @Test
    fun patchFixtureTypeList() {
        assertTrue(Patch.getFixtureTypeList().isEmpty())

        val exampleFixtureType = GDTF(UUID.randomUUID())
        Patch.putFixtureType(exampleFixtureType)

        assertEquals(1, Patch.getFixtureTypeList().size)
        assertEquals(exampleFixtureType, Patch.getFixtureTypeList()[exampleFixtureType.fixtureTypeId])

        Patch.removeFixtureType(exampleFixtureType.fixtureTypeId)

        assertTrue(Patch.getFixtureTypeList().isEmpty())
    }

    // TODO
    // test that stream updates are called
}
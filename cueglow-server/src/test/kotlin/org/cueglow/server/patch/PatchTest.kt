package org.cueglow.server.patch

import com.github.michaelbull.result.unwrap
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import org.cueglow.server.objects.ImmutableMap
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.HashMap

internal class PatchTest {
    @Test
    fun patchFixtureList() {
        assertTrue(Patch.getFixtureList().isEmpty())
        assertTrue(Patch.getFixtureList() is ImmutableMap<*, *>)

        val exampleFixture = PatchFixture(1, "", UUID.randomUUID(),
            "1ch", ArtNetAddress.tryFrom(1).unwrap(), DmxAddress.tryFrom(1).unwrap())
        Patch.addFixture(exampleFixture)

        assertEquals(1, Patch.getFixtureList().size)
        assertEquals(exampleFixture, Patch.getFixtureList()[exampleFixture.uuid])

        Patch.removeFixture(exampleFixture.uuid)

        assertTrue(Patch.getFixtureList().isEmpty())
    }

    @Test
    fun patchFixtureTypeList() {
        assertTrue(Patch.getFixtureTypeList().isEmpty())
        assertTrue(Patch.getFixtureTypeList() is ImmutableMap<*, *>)

        val exampleFixtureType = GDTF(UUID.randomUUID())
        Patch.addFixtureType(exampleFixtureType)

        assertEquals(1, Patch.getFixtureTypeList().size)
        assertEquals(exampleFixtureType, Patch.getFixtureTypeList()[exampleFixtureType.fixtureTypeId])

        Patch.removeFixture(exampleFixtureType.fixtureTypeId)

        assertTrue(Patch.getFixtureList().isEmpty())
    }

    // TODO
    // test that stream updates are called
}
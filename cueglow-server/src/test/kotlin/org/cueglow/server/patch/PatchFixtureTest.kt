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

internal class PatchFixtureTest {

    @Test
    fun instantiateAndClonePatchFixture() {
        val fixtureId = UUID.randomUUID()
        val fid = 1
        val name = "Lamp"
        val fixtureTypeId = UUID.randomUUID()
        val dmxMode = "mode1"
        val universe = ArtNetAddress.tryFrom(1).unwrap()
        val address = DmxAddress.tryFrom(1).unwrap()

        val fixture = PatchFixture(fixtureId, fid, name, fixtureTypeId, dmxMode, universe, address)

        val fixture2 = fixture.copy(fid=2)

        assertEquals(fid, fixture.fid)
        assertEquals(2, fixture2.fid)
        assertEquals(fixtureId, fixture2.uuid)
    }
}
package org.cueglow.server.patch

import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import java.util.*

/**
 * A single Fixture in the Patch
 *
 * @property[uuid] The unique identifier of the fixture
 * @property[fid] The **F**ixture **ID**, an Int arbitrarily assigned by the user (negative FIDs are allowed but questionable)
 * @property[name] The Fixture Name, assigned by the user
 * @property[fixtureTypeId] The fixtureTypeId identifying the fixture type (as defined by a GDTF file)
 * @property[dmxMode] The DMX Mode, chosen by the user from the ones supplied in the GDTF file
 * @property[universe] The Art-Net Port-Address of the Universe in which the Fixture operates, assigned by the user
 * @property[address] The DMX Address in the specified Universe, assigned by the user
 *
 * The properties [universe] and [address] are nullable.
 * If at least one of [universe] or [address] is null, no Art-Net output should be sent for this fixture.
 */
data class PatchFixture(
    val uuid: UUID,
    val fid: Int,
    val name: String,
    val fixtureTypeId: UUID,
    val dmxMode: String,
    val universe: ArtNetAddress?,
    val address: DmxAddress?,
) {
    /** Returns true if both PatchFixtures contain the same values.
     * Does not consider UUID and only uses fixtureTypeId to compare fixtureType
     * */
    fun isSimilar(other: PatchFixture): Boolean {
        if (fid != other.fid) return false
        if (name != other.name) return false
        if (fixtureTypeId != other.fixtureTypeId) return false
        if (dmxMode != other.dmxMode) return false
        if (universe?.value != other.universe?.value) return false
        if (address?.value != other.address?.value) return false
        return true
    }
}









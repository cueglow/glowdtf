package org.cueglow.server.patch

import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import java.util.*

/**
 * A single Fixture in the Patch
 *
 * @property[fid] The **F**ixture **ID**, an Int arbitrarily assigned by the user (negative FIDs are still questionable)
 * @property[name] The Fixture Name, assigned by the user
 * @property[fixtureTypeId] The UUID of the Fixture Type as specified in the corresponding GDTF file
 * @property[dmxMode] The DMX Mode, chosen by the user from the ones supplied in the GDTF file
 * @property[universe] The Art-Net Port-Address of the Universe in which the Fixture operatres, assigned by the user
 * @property[address] The DMX Address in the specified Universe, assigned by the user
 * @property[uuid] The UUID of the Fixture, assigned by the Server upon creation
 *
 * The properties `universe` and `address` are nullable.
 * If at least one of `universe` or `address` is null, no Art-Net output should be sent for this fixture.
 */
data class PatchFixture(
    var fid: Int,
    var name: String,
    val fixtureTypeId: UUID,
    val dmxMode: String,
    var universe: ArtNetAddress?,
    var address: DmxAddress?,
) {
    val uuid: UUID = UUID.randomUUID()

    override fun hashCode() = this.uuid.hashCode()
}






package org.cueglow.server.patch

import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
import java.util.*
import kotlin.properties.Delegates

/**
 * A single Fixture in the Patch
 *
 * @property[uuid] The UUID of the Fixture, assigned by the Server upon creation
 * @property[fid] The **F**ixture **ID**, an Int arbitrarily assigned by the user (negative FIDs are allowed but questionable)
 * @property[name] The Fixture Name, assigned by the user
 * @property[fixtureTypeId] The UUID of the Fixture Type as specified in the corresponding GDTF file
 * @property[dmxMode] The DMX Mode, chosen by the user from the ones supplied in the GDTF file
 * @property[universe] The Art-Net Port-Address of the Universe in which the Fixture operates, assigned by the user
 * @property[address] The DMX Address in the specified Universe, assigned by the user
 *
 * The properties [universe] and [address] are nullable.
 * If at least one of [universe] or [address] is null, no Art-Net output should be sent for this fixture.
 *
 * [fixtureTypeId] and [dmxMode] are immutable to avoid conversion issues when changing fixture type.
 */
class PatchFixture(
    fid: Int,
    name: String,
    val fixtureType: GdtfWrapper,
    val dmxMode: String, // TODO how do we ensure this mode exists in the fixtureType?
    universe: ArtNetAddress?,
    address: DmxAddress?,
) {
    val uuid: UUID = UUID.randomUUID()

    // --------------------------------
    // Initialize Observable Properties
    // --------------------------------

    var fid: Int by Delegates.observable(fid) {
        _, _, _ -> //TODO("notify patch stream")
    }
    var name: String by Delegates.observable(name) {
        _, _, _ -> //TODO("notify patch stream")
    }
    var universe: ArtNetAddress? by Delegates.observable(universe) {
        _, _, _ -> //TODO("notify patch stream")
    }
    var address: DmxAddress? by Delegates.observable(address) {
            _, _, _ -> //TODO("notify patch stream")
    }

    // ---------
    // Utilities
    // ---------

    override fun hashCode() = this.uuid.hashCode()

    /**
     * Checks for Equality
     *
     * Generated by IntelliJ
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PatchFixture

        if (fid != other.fid) return false
        if (name != other.name) return false
        if (fixtureType != other.fixtureType) return false
        if (dmxMode != other.dmxMode) return false
        if (universe != other.universe) return false
        if (address != other.address) return false
        if (uuid != other.uuid) return false

        return true
    }

    fun isSimilar(other: PatchFixture): Boolean {
        if (fid != other.fid) return false
        if (name != other.name) return false
        if (fixtureType.fixtureTypeId != other.fixtureType.fixtureTypeId) return false
        if (dmxMode != other.dmxMode) return false
        if (universe?.value != other.universe?.value) return false
        if (address?.value != other.address?.value) return false
        return true
    }
}

data class PatchFixtureData(
    val fid: Int,
    val name: String,
    val fixtureTypeId: UUID,
    val dmxMode: String,
    val universe: ArtNetAddress?,
    val address: DmxAddress?,
)






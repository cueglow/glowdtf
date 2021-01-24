package org.cueglow.server.patch

import org.cueglow.server.objects.ImmutableMap
import java.util.*
import kotlin.collections.HashMap

// TODO
// import GDTF class from gdtf branch later
class GDTF(val fixtureTypeId: UUID)

/**
 * Singleton that holds Patch Data and notifies Stream Handler on Changes
 */
object Patch {
    private val fixtureList: HashMap<UUID, PatchFixture> = HashMap()
    private val fixtureTypeList: HashMap<UUID, GDTF> = HashMap()

    fun getFixtureList() = ImmutableMap(this.fixtureList)

    fun getFixtureTypeList() = ImmutableMap(this.fixtureTypeList)

    // -------------------
    // Modify Fixture List
    // -------------------
    fun putFixture(new: PatchFixture) {
        fixtureList[new.uuid] = new
        // TODO notify patch stream handler
    }

    fun removeFixture(uuid: UUID) {
        fixtureList.remove(uuid)
        // TODO notify patch stream handler
    }

    // ------------------------
    // Modify Fixture Type List
    // ------------------------
    fun putFixtureType(new: GDTF) {
        fixtureTypeList[new.fixtureTypeId] = new
        // TODO notify patch stream handler
    }

    fun removeFixtureType(fixtureTypeId: UUID) {
        fixtureTypeList.remove(fixtureTypeId)
        // TODO notify patch stream handler
    }
}
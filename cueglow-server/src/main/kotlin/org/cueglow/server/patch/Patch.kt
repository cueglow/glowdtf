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
    private val fixtures: HashMap<UUID, PatchFixture> = HashMap()
    private val fixtureTypes: HashMap<UUID, GDTF> = HashMap()

    fun getFixtures() = ImmutableMap(this.fixtures)

    fun getFixtureTypes() = ImmutableMap(this.fixtureTypes)

    // -------------------
    // Modify Fixture List
    // -------------------
    fun putFixture(new: PatchFixture) {
        fixtures[new.uuid] = new
        // TODO notify patch stream handler
    }

    fun removeFixture(uuid: UUID) {
        fixtures.remove(uuid)
        // TODO notify patch stream handler
    }

    // ------------------------
    // Modify Fixture Type List
    // ------------------------
    fun putFixtureType(new: GDTF) {
        fixtureTypes[new.fixtureTypeId] = new
        // TODO notify patch stream handler
    }

    fun removeFixtureType(fixtureTypeId: UUID) {
        fixtureTypes.remove(fixtureTypeId)
        // TODO notify patch stream handler
    }
}
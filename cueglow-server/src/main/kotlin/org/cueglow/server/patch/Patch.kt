package org.cueglow.server.patch

import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.objects.ImmutableMap
import java.util.*
import kotlin.collections.HashMap

/**
 * Singleton that holds Patch Data and notifies Stream Handler on Changes
 */
class Patch {
    private val fixtures: HashMap<UUID, PatchFixture> = HashMap()
    private val fixtureTypes: HashMap<UUID, GdtfWrapper> = HashMap()

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
    fun putFixtureType(new: GdtfWrapper) {
        fixtureTypes[new.fixtureTypeId] = new
        // TODO notify patch stream handler
    }

    fun removeFixtureType(fixtureTypeId: UUID) {
        fixtureTypes.remove(fixtureTypeId)
        // TODO notify patch stream handler
    }
}
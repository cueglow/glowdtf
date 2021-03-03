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

    // TODO write unit tests for this algorithm
    fun nextFreeFid(targetFid: Int = 1): Int {
        val list = fixtures.values.map{it.fid}.sorted()
        val firstCandidate = list.binarySearch(targetFid)

        if (firstCandidate < 0) {
            // negative returned index means the targetFid does not exist in Patch yet
            return targetFid
        }

        var lastFid: Int = targetFid
        var currentFid: Int

        for (i in firstCandidate+1..list.lastIndex) {
            currentFid = list[i]
            if (currentFid > lastFid + 1) {
                return lastFid + 1
            }
            lastFid = currentFid
        }

        return lastFid + 1
    }

    fun findGapAtOrAfter(list: List<Number>, gapSize: Int = 1) {
        
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
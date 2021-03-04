package org.cueglow.server.patch

import com.github.michaelbull.result.getOr
import com.github.michaelbull.result.unwrap
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.objects.ArtNetAddress
import org.cueglow.server.objects.DmxAddress
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
        // TODO how do we know the added fixture type is currently in the patch and not just an object in memory?
    }

    fun removeFixture(uuid: UUID) {
        fixtures.remove(uuid)
        // TODO notify patch stream handler
    }

    // TODO logic will be moved to the client
    fun nextFreeFid(targetFid: Int = 1): Int =
        nextGap(fixtures.values.map{it.fid}, targetFid)

    // TODO logic will be moved to the client
    /**
     * Tries to find the next free address at or after [target] in [universe] where there is a gap of [channelCount].
     * If no space is found in this universe, null is returned.
     */
    fun nextFreeAddress(universe: ArtNetAddress, target: DmxAddress = DmxAddress.tryFrom(1).unwrap(), channelCount: Int): ArtNetAddress? {
        // TODO write unit test
        val list = fixtures.values.filter {it.universe == universe}.map{it.address?.value?.toInt()}.filterNotNull()
        val candidate = nextGap(list, target.value.toInt(), channelCount)
        if (candidate + channelCount - 1 > 512) {
            return null
        } else {
            return ArtNetAddress.tryFrom(candidate).getOr(null)
        }
    }


    // ------------------------
    // Modify Fixture Type List
    // ------------------------
    fun putFixtureType(new: GdtfWrapper) {
        fixtureTypes[new.fixtureTypeId] = new
        // TODO notify patch stream handler
    }

    fun removeFixtureType(fixtureTypeId: UUID) {
        // remove associated fixtures
        fixtures.filter { it.value.fixtureType.fixtureTypeId == fixtureTypeId }.keys.forEach {fixtures.remove(it)}
        // remove fixture type
        fixtureTypes.remove(fixtureTypeId)
        // TODO notify patch stream handler
    }
}

// TODO logic will be moved to the client
/**
 * Finds the first gap in [inputList] at or after [target]. The minimum size of the gap can be specified by [gapSize].
 *
 * [inputList] does not have to be sorted.
 */
fun nextGap(inputList: List<Int>, target: Int, gapSize: Int = 1): Int {
    val list = inputList.sorted()
    var firstCandidate = list.binarySearch(target)

    if (firstCandidate < 0) {
        // negative returned index means the targetFid does not exist in Patch yet
        firstCandidate = -firstCandidate-1
    }

    var last: Int = target-1
    var current: Int

    for (i in firstCandidate..list.lastIndex) {
        current = list[i]
        if (current > last + gapSize) {
            return last + 1
        }
        last = current
    }

    return last + 1
}
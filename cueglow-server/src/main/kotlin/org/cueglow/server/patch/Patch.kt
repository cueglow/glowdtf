package org.cueglow.server.patch

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.cueglow.server.gdtf.FixtureType
import org.cueglow.server.objects.*
import java.util.*
import kotlin.collections.HashMap

/**
 * Holds Patch Data
 *
 * The data is isolated such that it can only be modified by methods that notify the StreamHandler on change.
 */
class Patch {
    private val fixtures: HashMap<UUID, PatchFixture> = HashMap()
    private val fixtureTypes: HashMap<UUID, FixtureType> = HashMap()

    fun getFixtures() = ImmutableMap(this.fixtures)

    fun getFixtureTypes() = ImmutableMap(this.fixtureTypes)

    // -------------------
    // Modify Fixture List
    // -------------------
    fun putFixture(new: PatchFixture): Result<Unit, UnpatchedFixtureTypeIdError> {
        val fixtureTypeId = new.fixtureType.fixtureTypeId

        // ensure fixtureType is in Patch
        if (!fixtureTypes.containsKey(fixtureTypeId)) {return Err(UnpatchedFixtureTypeIdError(fixtureTypeId))}

        fixtures[new.uuid] = new

        // TODO notify patch stream handler

        return Ok(Unit)
    }

    fun removeFixture(uuid: UUID): Result<Unit, UnknownFixtureUuidError> {
        fixtures.remove(uuid) ?: return Err(UnknownFixtureUuidError(uuid))
        // TODO notify patch stream handler
        return Ok(Unit)
    }

    // ------------------------
    // Modify Fixture Type List
    // ------------------------
    fun putFixtureType(new: FixtureType): Result<Unit, FixtureTypeAlreadyExistsError> {
        // ensure fixture type is not patched already
        if (fixtureTypes.containsKey(new.fixtureTypeId)) {return Err(FixtureTypeAlreadyExistsError(new.fixtureTypeId))}
        fixtureTypes[new.fixtureTypeId] = new
        // TODO notify patch stream handler
        return Ok(Unit)
    }

    fun removeFixtureType(fixtureTypeId: UUID): Result<Unit, UnpatchedFixtureTypeIdError> {
        // remove fixture type
        fixtureTypes.remove(fixtureTypeId) ?: return Err(UnpatchedFixtureTypeIdError(fixtureTypeId))
        // remove associated fixtures
        fixtures.filter { it.value.fixtureType.fixtureTypeId == fixtureTypeId }.keys.forEach {fixtures.remove(it)}
        // TODO notify patch stream handler
        return Ok(Unit)
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
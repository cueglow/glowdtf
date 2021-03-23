package org.cueglow.server.patch

import com.github.michaelbull.result.*
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.objects.*
import org.cueglow.server.objects.messages.*
import java.util.*
import kotlin.collections.HashMap

/**
 * Holds Patch Data
 *
 * The data is isolated such that it can only be modified by methods that notify the StreamHandler on change.
 */
class Patch {
    private val fixtures: HashMap<UUID, PatchFixture> = HashMap()
    private val fixtureTypes: HashMap<UUID, GdtfWrapper> = HashMap()

    fun getFixtures() = ImmutableMap(this.fixtures)

    fun getFixtureTypes() = ImmutableMap(this.fixtureTypes)

    /** Returns an immutable copy of the Patch */
    fun getGlowPatch(): GlowPatch = GlowPatch(fixtures.values.toList(), fixtureTypes.values.toList())

    // -------------------
    // Modify Fixture List
    // -------------------

    /**
     * Execute [lambda] for every element in [collection]. If the result of [lambda] is an Error, add it to the
     * error list which is returned once all elements are dealt with.
     */
    private fun <T, E>executeWithErrorList(collection: Iterable<T>, lambda: (T) -> Result<Unit, E>): Result<Unit, List<E>> {
        val errorList: MutableList<E> = mutableListOf()
        collection.forEach{ element ->
            lambda(element).getError()?.let{errorList.add(it)}
        }
        if (errorList.isNotEmpty()) {return Err(errorList)}
        return Ok(Unit)
    }

    fun addFixtures(fixturesToAdd: Iterable<PatchFixture>): Result<Unit, List<GlowError>> {
        return executeWithErrorList(fixturesToAdd) eachFixture@{ patchFixtureToAdd ->
            // validate uuid does not exist yet
            if (fixtures.contains(patchFixtureToAdd.uuid)) {
                return@eachFixture Err(FixtureUuidAlreadyExistsError(patchFixtureToAdd.uuid))
            }
            // validate fixtureTypeId exists while grabbing DMX Modes
            val fixtureTypeModes = fixtureTypes[patchFixtureToAdd.fixtureTypeId]?.modes ?: run {
                return@eachFixture Err(UnpatchedFixtureTypeIdError(patchFixtureToAdd.fixtureTypeId))
            }
            // validate DMX Mode exists
            if (fixtureTypeModes.map { it.name }.contains(patchFixtureToAdd.dmxMode).not()) {
                return@eachFixture Err(UnknownDmxModeError(patchFixtureToAdd.dmxMode, patchFixtureToAdd.fixtureTypeId))
            }
            fixtures[patchFixtureToAdd.uuid] = patchFixtureToAdd
            return@eachFixture Ok(Unit)
        }
    }

    fun updateFixtures(fixtureUpdates: Iterable<PatchFixtureUpdate>): Result<Unit, List<GlowError>> {
        return executeWithErrorList(fixtureUpdates) eachUpdate@{fixtureUpdate ->
            // validate fixture uuid exists already
            val oldFixture = fixtures[fixtureUpdate.uuid] ?: run {
                return@eachUpdate Err(UnknownFixtureUuidError(fixtureUpdate.uuid))
            }
            val newFixture = oldFixture.copy(
                fid = fixtureUpdate.fid ?: oldFixture.fid,
                name = fixtureUpdate.name ?: oldFixture.name,
                universe = fixtureUpdate.universe.getOr(oldFixture.universe),
                address = fixtureUpdate.address.getOr(oldFixture.address),
            )
            fixtures[newFixture.uuid] = newFixture
            return@eachUpdate Ok(Unit)
        }
    }

    fun removeFixtures(uuids: Iterable<UUID>): Result<Unit, List<UnknownFixtureUuidError>> {
        return executeWithErrorList(uuids) eachFixture@{uuidToRemove ->
            fixtures.remove(uuidToRemove) ?: return@eachFixture Err(UnknownFixtureUuidError(uuidToRemove))
            return@eachFixture Ok(Unit)
        }
    }

    // ------------------------
    // Modify Fixture Type List
    // ------------------------
    fun addFixtureTypes(fixtureTypesToAdd: Iterable<GdtfWrapper>): Result<Unit, List<FixtureTypeAlreadyExistsError>> {
        return executeWithErrorList(fixtureTypesToAdd) eachFixtureType@{ fixtureTypeToAdd ->
            // validate fixture type is not patched already
            if (fixtureTypes.containsKey(fixtureTypeToAdd.fixtureTypeId)) {
                return@eachFixtureType Err(FixtureTypeAlreadyExistsError(fixtureTypeToAdd.fixtureTypeId))
            }
            fixtureTypes[fixtureTypeToAdd.fixtureTypeId] = fixtureTypeToAdd
            return@eachFixtureType Ok(Unit)
        }
    }

    fun removeFixtureTypes(fixtureTypeIdsToRemove: List<UUID>): Result<Unit, List<UnpatchedFixtureTypeIdError>> {
        return executeWithErrorList(fixtureTypeIdsToRemove) eachFixtureType@{ fixtureTypeIdToRemove ->
            fixtureTypes.remove(fixtureTypeIdToRemove) ?:
                return@eachFixtureType Err(UnpatchedFixtureTypeIdError(fixtureTypeIdToRemove))
            // remove associated fixtures
            fixtures.filter { it.value.fixtureTypeId == fixtureTypeIdToRemove }.keys.forEach {fixtures.remove(it)}
            return@eachFixtureType Ok(Unit)
        }
    }
}

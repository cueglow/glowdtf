package org.cueglow.server.patch

import com.github.michaelbull.result.*
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.objects.ImmutableMap
import org.cueglow.server.objects.messages.*
import java.util.*
import java.util.concurrent.BlockingQueue
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Holds Patch Data
 *
 * The data is isolated such that it can only be modified by methods that notify the StreamHandler on change.
 */
class Patch(private val outEventQueue: BlockingQueue<GlowMessage>) {
    private val fixtures: HashMap<UUID, PatchFixture> = HashMap()
    private val fixtureTypes: HashMap<UUID, GdtfWrapper> = HashMap()

    fun getFixtures() = ImmutableMap(this.fixtures)

    fun getFixtureTypes() = ImmutableMap(this.fixtureTypes)

    /** Returns an immutable copy of the Patch */
    fun getGlowPatch(): GlowPatch = GlowPatch(fixtures.values.toList(), fixtureTypes.values.toList())

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

    /**
     * Calls [executeWithErrorList] but also keeps a list of successful operations. When the operations are done, the
     * successful operations are wrapped in the specified [messageType] and added to the [outEventQueue].
     */
    private fun <T,R,E>executeWithErrorListAndSendOutEvent(messageType: KClass<out GlowMessage>, collection: Iterable<T>, lambda: (T) -> Result<R, E>): Result<Unit, List<E>> {
        val successList = mutableListOf<R>()
        val mainResult = executeWithErrorList(collection) { collectionElement ->
            lambda(collectionElement).map{
                successList.add(it)
                Unit
            }
        }
        if (successList.isNotEmpty()) {
            val glowMessage = messageType.primaryConstructor?.call(successList, null) ?: throw IllegalArgumentException("messageType does not have a primary constructor")
            outEventQueue.add(glowMessage)
        }
        return mainResult
    }

    // -------------------
    // Modify Fixture List
    // -------------------

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
        return executeWithErrorListAndSendOutEvent(GlowMessage.AddFixtureTypes::class, fixtureTypesToAdd) eachFixtureType@{ fixtureTypeToAdd ->
            // validate fixture type is not patched already
            if (fixtureTypes.containsKey(fixtureTypeToAdd.fixtureTypeId)) {
                return@eachFixtureType Err(FixtureTypeAlreadyExistsError(fixtureTypeToAdd.fixtureTypeId))
            }
            fixtureTypes[fixtureTypeToAdd.fixtureTypeId] = fixtureTypeToAdd
            return@eachFixtureType Ok(fixtureTypeToAdd)
        }
    }

    fun removeFixtureTypes(fixtureTypeIdsToRemove: List<UUID>): Result<Unit, List<UnpatchedFixtureTypeIdError>> {
        return executeWithErrorListAndSendOutEvent(GlowMessage.RemoveFixtureTypes::class, fixtureTypeIdsToRemove) eachFixtureType@{ fixtureTypeIdToRemove ->
            fixtureTypes.remove(fixtureTypeIdToRemove) ?:
                return@eachFixtureType Err(UnpatchedFixtureTypeIdError(fixtureTypeIdToRemove))
            // remove associated fixtures
            fixtures.filter { it.value.fixtureTypeId == fixtureTypeIdToRemove }.keys.forEach {fixtures.remove(it)} // TODO use this.removeFixtures to create outEvent (create test, too)
            return@eachFixtureType Ok(fixtureTypeIdToRemove)
        }
    }
}

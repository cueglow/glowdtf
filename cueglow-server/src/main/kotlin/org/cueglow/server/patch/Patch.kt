package org.cueglow.server.patch

import com.github.michaelbull.result.*
import org.cueglow.server.gdtf.GdtfWrapper
import org.cueglow.server.gdtf.gdtfDefaultState
import org.cueglow.server.objects.messages.*
import org.cueglow.server.rig.RigStateList
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.locks.Lock
import kotlin.concurrent.withLock
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Holds Patch Data
 *
 * The data is isolated such that it can only be modified by methods that notify the StreamHandler on change.
 */
class Patch(private val outEventQueue: BlockingQueue<GlowMessage>, val lock: Lock, private val rigState: RigStateList) {
    private val fixtures: HashMap<UUID, PatchFixture> = HashMap()
    private val fixtureTypes: HashMap<UUID, GdtfWrapper> = HashMap()

    // TODO maybe move getters from copy-on-read to copy-on-write for possible performance improvement

    /** Returns an immutable copy of the fixtures in the Patch. **/
    fun getFixtures() = lock.withLock{
        fixtures.toMap()
    }

    /** Returns an immutable copy of the fixture types in the Patch. **/
    fun getFixtureTypes() = lock.withLock {
        fixtureTypes.toMap()
    }

    /** Returns an immutable copy of the Patch */
    fun getGlowPatch(): GlowPatch = lock.withLock {
        GlowPatch(fixtures.values.toList(), fixtureTypes.values.toList())
    }

    /**
     * Execute [lambda] for every element in [collection]. If the result of [lambda] is [Ok], wrap it in the specified
     * [messageType] and add it to the [outEventQueue]. If the result of [lambda] is an Error, add it to the
     * error list which is returned once all elements are dealt with.
     *
     * This function handles locking for [Patch] modifications. All modifications in [Patch] must only call this
     * function and nothing else to ensure thread safety.
     */
    private fun <T,R,E>executeWithErrorListAndSendOutEvent(
        messageType: KClass<out GlowMessage>,
        collection: Iterable<T>,
        lambda: (T) -> Result<R, E>
    ): Result<Unit, List<E>> {
        val successList = mutableListOf<R>()
        val errorList= mutableListOf<E>()
        lock.withLock {
            collection.forEach{ element ->
                lambda(element).mapError{errorList.add(it)}.map{successList.add(it)}
            }
            if (successList.isNotEmpty()) {
                val glowMessage = messageType.primaryConstructor?.call(successList, null) ?: throw IllegalArgumentException("messageType does not have a primary constructor")
                outEventQueue.put(glowMessage) // TODO possibly blocks rendering/etc. but the changes were already made so the message needs to be put into the queue
            }
        }
        if (errorList.isNotEmpty()) {return Err(errorList)}
        return Ok(Unit)
    }

    // -------------------
    // Modify Fixture List
    // -------------------

    fun addFixtures(fixturesToAdd: Iterable<PatchFixture>): Result<Unit, List<GlowError>> =
        executeWithErrorListAndSendOutEvent(GlowMessage.AddFixtures::class, fixturesToAdd) eachFixture@{ patchFixtureToAdd ->
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
            // add to Patch
            fixtures[patchFixtureToAdd.uuid] = patchFixtureToAdd
            // reset default state and add new fixtures
            rigState.clear()
            val newDefaultState = fixtures
                .values
                .toList()
                .map { fixture ->
                    val fixtureType = fixtureTypes[fixture.fixtureTypeId]!! // already validated
                    val dmxMode = fixtureType.modes.find{ it.name == fixture.dmxMode }!! // already validated
                    gdtfDefaultState(dmxMode)
                }
            rigState.addAll(newDefaultState)
            outEventQueue.put(GlowMessage.RigState(rigState.map { it.copy() }.toMutableList()))
            return@eachFixture Ok(patchFixtureToAdd)
        }


    fun updateFixtures(fixtureUpdates: Iterable<PatchFixtureUpdate>): Result<Unit, List<GlowError>> =
        executeWithErrorListAndSendOutEvent(GlowMessage.UpdateFixtures::class, fixtureUpdates) eachUpdate@{ fixtureUpdate ->
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
            return@eachUpdate Ok(fixtureUpdate)
        }

    fun removeFixtures(uuids: Iterable<UUID>): Result<Unit, List<UnknownFixtureUuidError>> =
        executeWithErrorListAndSendOutEvent(GlowMessage.RemoveFixtures::class, uuids) eachFixture@{ uuidToRemove ->
            fixtures.remove(uuidToRemove) ?: return@eachFixture Err(UnknownFixtureUuidError(uuidToRemove))
            rigState.clear()
            val newDefaultState = fixtures
                .values
                .toList()
                .filter { fixtureTypes.containsKey(it.fixtureTypeId) }
                .map { fixture ->
                    val fixtureType = fixtureTypes[fixture.fixtureTypeId]!! // filtered out before
                    val dmxMode = fixtureType.modes.find{ it.name == fixture.dmxMode }!! // already validated
                    gdtfDefaultState(dmxMode)
                }
            rigState.addAll(newDefaultState)
            return@eachFixture Ok(uuidToRemove)
        }

    // ------------------------
    // Modify Fixture Type List
    // ------------------------

    fun addFixtureTypes(fixtureTypesToAdd: Iterable<GdtfWrapper>): Result<Unit, List<FixtureTypeAlreadyExistsError>> =
        executeWithErrorListAndSendOutEvent(GlowMessage.AddFixtureTypes::class, fixtureTypesToAdd) eachFixtureType@{ fixtureTypeToAdd ->
            // validate fixture type is not patched already
            if (fixtureTypes.containsKey(fixtureTypeToAdd.fixtureTypeId)) {
                return@eachFixtureType Err(FixtureTypeAlreadyExistsError(fixtureTypeToAdd.fixtureTypeId))
            }
            fixtureTypes[fixtureTypeToAdd.fixtureTypeId] = fixtureTypeToAdd
            return@eachFixtureType Ok(fixtureTypeToAdd)
        }

    fun removeFixtureTypes(fixtureTypeIdsToRemove: List<UUID>): Result<Unit, List<UnpatchedFixtureTypeIdError>> =
        executeWithErrorListAndSendOutEvent(GlowMessage.RemoveFixtureTypes::class, fixtureTypeIdsToRemove) eachFixtureType@{ fixtureTypeIdToRemove ->
            fixtureTypes.remove(fixtureTypeIdToRemove) ?:
                return@eachFixtureType Err(UnpatchedFixtureTypeIdError(fixtureTypeIdToRemove))
            // remove associated fixtures
            fixtures.filter { it.value.fixtureTypeId == fixtureTypeIdToRemove }.keys.let {this.removeFixtures(it)}
            return@eachFixtureType Ok(fixtureTypeIdToRemove)
        }
}

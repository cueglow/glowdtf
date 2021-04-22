package org.cueglow.server.patch

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Test
import java.util.concurrent.*
import java.util.concurrent.locks.ReentrantLock

// TODO for concurrent patch testing
// single-method interactions
// TODO add the same fixture from multiple threads at once -> only one should succeed
// TODO add the same fixture type form multiple threads -> only one should succeed
// multi-method interactions
// TODO update fixture while deleting it -> could not be deleted in the end
// TODO Removing Fixture Type while adding fixtures for that fixture type should not leave any fixtures behind
// TODO while removing a fixture type, fixtures where the associated fixture type is already removed should not be observable in the fixtures map


class ConcurrentPatchGetterTest {
    val patch = Patch(LinkedBlockingQueue(), ReentrantLock())

    // Utility Method for Getter Tests below
    private fun <T> getterTest(errorString: (T) -> String, readLambda: (CyclicBarrier) -> Result<Unit, T>) {
        val readingThreadCount = 3
        val iterationCount = 60

        val barrier = CyclicBarrier(readingThreadCount + 1)

        val writeTask = Callable {
            barrier.await(2, TimeUnit.SECONDS)
            patch.removeFixtureTypes(listOf(
                ExampleFixtureType.esprite.fixtureTypeId,
                ExampleFixtureType.channelLayoutTestGdtf.fixtureTypeId
            ))
            val result: Result<Unit, T> = Ok(Unit)
            result
        }

        val pool = Executors.newFixedThreadPool(readingThreadCount + 1)
        val readTask = Callable {readLambda(barrier)}
        val tasks = mutableListOf(writeTask).apply {
            repeat(readingThreadCount) {this.add(readTask)}
        }

        repeat(iterationCount) { iterationNumber ->
            //println("iteration $it")
            // fill patch
            patch.addFixtureTypes(listOf(ExampleFixtureType.esprite, ExampleFixtureType.channelLayoutTestGdtf))
            patch.addFixtures(listOf(ExampleFixtureType.esprite_fixture, ExampleFixtureType.esprite_fixture2, ExampleFixtureType.channelLayoutTestGdtfFixture))

            val results = pool.invokeAll(tasks, 2, TimeUnit.SECONDS)

            results.forEach { future ->
                try {
                    future.get().getOrElse {
                        throw Error(errorString(it))
                    }
                } catch (error: Throwable) {
                    println("FAIL in iteration ${iterationNumber + 1} of $iterationCount")
                    throw error
                }
            }
        }
    }

    @Test
    fun getFixturesReturnsCoherentStateDuringRemove() {
        getterTest(
            errorString = {
            "Patch returned fixture map with nonsensical size $it"
            },
            readLambda = { barrier ->
                barrier.await(2, TimeUnit.SECONDS)
                val returnedFixtures = patch.getFixtures()
                if (returnedFixtures.size == 3 || returnedFixtures.isEmpty()) {
                    Ok(Unit)
                } else {
                    Err(returnedFixtures.size)
                }
            }
        )
    }

    @Test
    fun getFixtureTypesReturnsCoherentStateDuringRemove() {
        getterTest(
            errorString = {
                "Patch returned nonsensical fixture type count $it"
            },
            readLambda = { barrier ->
                barrier.await(2, TimeUnit.SECONDS)
                val returnedFixtureTypes = patch.getFixtureTypes()
                if (returnedFixtureTypes.size == 2 || returnedFixtureTypes.isEmpty()) {
                    Ok(Unit)
                } else {
                    Err(returnedFixtureTypes.size)
                }
            }
        )
    }

    @Test
    fun getGlowPatchReturnsCoherentStateDuringRemove() {
        getterTest(
            errorString = { (fixtures, fixtureTypes) ->
                "Patch returned nonsensical fixture count of $fixtures and fixture type count of $fixtureTypes"
            },
            readLambda = { barrier ->
                barrier.await(2, TimeUnit.SECONDS)
                val returnedGlowPatch = patch.getGlowPatch()
                val fixtureCountIsRight = when (returnedGlowPatch.fixtures.size) {
                    0,3 -> true
                    else -> false
                }
                val fixtureTypeCountIsRight = when (returnedGlowPatch.fixtureTypes.size) {
                    0,2 -> true
                    else -> false
                }
                if (fixtureCountIsRight && fixtureTypeCountIsRight) {
                    Ok(Unit)
                } else {
                    Err(Pair(returnedGlowPatch.fixtures.size, returnedGlowPatch.fixtureTypes.size))
                }
            }
        )
    }
}
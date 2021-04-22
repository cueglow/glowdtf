package org.cueglow.server.patch

import com.github.michaelbull.result.Ok
import org.cueglow.server.objects.messages.GlowError
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.*
import java.util.concurrent.locks.ReentrantLock
import com.github.michaelbull.result.Result
import java.util.*

// TODO for concurrent patch testing
// multi-method interactions
// TODO update fixture while deleting it -> could not be deleted in the end

class ConcurrentPatchModificationTest {
    val patch = Patch(LinkedBlockingQueue(), ReentrantLock())
    val threadCount = 4
    val iterationCount = 100
    val barrier = CyclicBarrier(threadCount)
    val pool = Executors.newFixedThreadPool(threadCount)

    val exampleFixtureType = ExampleFixtureType.esprite
    val exampleFixture = ExampleFixtureType.esprite_fixture
    val exampleFixture2 = ExampleFixtureType.esprite_fixture2

    fun <T> singleActionTest(task: Callable<T>, assertAndClean: (List<T>) -> Unit) {
        val taskList = mutableListOf(task).apply {
            repeat(threadCount - 1) {this.add(task)}
        }
        repeat(iterationCount) { iteration ->
            val futures = pool.invokeAll(taskList)

            val results = futures.map { it.get() }

            try {
                assertAndClean(results)
            } catch (error: Throwable) {
                println("failing in iteration $iteration")
                println("failing list of Results: $results")
                println("failing with ${patch.getFixtures().size} fixtures: ${patch.getFixtures()}")
                println("failing with ${patch.getFixtureTypes().size} fixture types: ${patch.getFixtureTypes()}")
                throw error
            }
        }
    }

    @Test
    fun addingSameFixtureFromMultipleThreadOnlyWorksOnce() {
        patch.addFixtureTypes(listOf(exampleFixtureType))

        singleActionTest(
            task = Callable {
                barrier.await(2, TimeUnit.SECONDS)
                patch.addFixtures(listOf(exampleFixture))
            },
            assertAndClean = { results ->
                assertEquals(1, results.filterIsInstance<Ok<Unit>>().size)
                assertEquals(1, patch.getFixtures().size)
                patch.removeFixtures(listOf(exampleFixture.uuid))
            }
        )
    }

    @Test
    // This test is janky and sometimes needs lots of iteration to fail or multiple restarts
    // maybe remove more than one fixture or interleave with getters like in the getter tests
    fun removingFixtureFromMultipleThreadsOnlyWorksOnce() {
        val removeList = listOf(exampleFixture.uuid)
        patch.addFixtureTypes(listOf(exampleFixtureType))
        patch.addFixtures(listOf(exampleFixture))
        singleActionTest(
            task = Callable {
                barrier.await(2, TimeUnit.SECONDS)
                patch.removeFixtures(removeList)
            },
            assertAndClean = { results ->
                assertEquals(1, results.filterIsInstance<Ok<Unit>>().size)
                assertEquals(0, patch.getFixtures().size)
                patch.addFixtures(listOf(exampleFixture))
            }
        )
    }

    @Test
    fun addingSameFixtureTypeFromMultipleThreadsOnlyWorksOnce() = singleActionTest(
            task = Callable {
                barrier.await(2, TimeUnit.SECONDS)
                patch.addFixtureTypes(listOf(exampleFixtureType))
            },
            assertAndClean = { results ->
                assertEquals(1, results.filterIsInstance<Ok<Unit>>().size)
                assertEquals(1, patch.getFixtureTypes().size)
                patch.removeFixtureTypes(listOf(exampleFixtureType.fixtureTypeId))
            }
        )

    @Test
    // This test is janky and sometimes needs >10_000 iteration to fail or multiple restarts,
    // but it is partially also covered in ConcurrentPatchGetterTest
    // maybe we can have more fixture types
    fun removingSameFixtureTypeFromMultipleThreadsOnlyWorksOnce() {
        val removeList = listOf(exampleFixtureType.fixtureTypeId)
        fun setup() {
            patch.addFixtureTypes(listOf(exampleFixtureType))
            patch.addFixtures(listOf(exampleFixture))
        }
        setup()
        singleActionTest(
            task = Callable {
                barrier.await(2, TimeUnit.SECONDS)
                patch.removeFixtureTypes(removeList)
            },
            assertAndClean = { results ->
                assertEquals(1, results.filterIsInstance<Ok<Unit>>().size)
                assertEquals(0, patch.getFixtureTypes().size)
                assertEquals(0, patch.getFixtures().size)
                setup()
            }
        )
    }

    // janky
    @Test
    fun removingFixturesWhileUpdatingWorks() {
        patch.addFixtureTypes(listOf(exampleFixtureType))

        val fixtureList = mutableListOf(exampleFixture).apply {
            repeat(100) {this.add(exampleFixture.copy(uuid = UUID.randomUUID()))}
        }
        patch.addFixtures(fixtureList)

        val removeTask = Callable {
            barrier.await(2, TimeUnit.SECONDS)
            Thread.sleep(0, 5)
            patch.removeFixtures(fixtureList.map { it.uuid }) as Result<Unit, List<GlowError>>
        }

        val updateTask = Callable {
            val updates = fixtureList.map {
                PatchFixtureUpdate(it.uuid, fid = ThreadLocalRandom.current().nextInt())
            }
            barrier.await(2, TimeUnit.SECONDS)
            patch.updateFixtures(updates)
        }

        val taskList = mutableListOf(removeTask).apply {
            repeat(threadCount - 1) {this.add(updateTask)}
        }

        repeat(iterationCount) { iteration ->
            val futures = pool.invokeAll(taskList)

            val results = futures.map { it.get() }

            try {
                assertEquals(0, patch.getFixtures().size)
            } catch (error: Throwable) {
                println("failing in iteration $iteration")
                println("failing list of Results: $results")
                println("failing with ${patch.getFixtures().size} fixtures: ${patch.getFixtures()}")
                println("failing with ${patch.getFixtureTypes().size} fixture types: ${patch.getFixtureTypes()}")
                throw error
            }
        }
    }
}
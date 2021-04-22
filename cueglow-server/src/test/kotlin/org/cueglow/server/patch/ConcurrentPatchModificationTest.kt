package org.cueglow.server.patch

import com.github.michaelbull.result.Ok
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.locks.ReentrantLock

class ConcurrentPatchModificationTest {
    val patch = Patch(LinkedBlockingQueue(), ReentrantLock())
    val threadCount = 4
    val iterationCount = 100
    val barrier = CyclicBarrier(threadCount)
    val pool = Executors.newFixedThreadPool(threadCount)!!

    val exampleFixtureType = ExampleFixtureType.esprite
    val exampleFixtureType2 = ExampleFixtureType.channelLayoutTestGdtf
    val exampleFixture = ExampleFixtureType.esprite_fixture

    private fun generateExampleFixtures(count: Int): List<PatchFixture> {
        return mutableListOf(exampleFixture).apply {
            repeat(count - 1) { this.add(exampleFixture.copy(uuid = UUID.randomUUID())) }
        }
    }

    fun <T> taskListTest(taskList: List<Callable<T>>, assertAndClean: (List<T>) -> Unit) {
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

    fun <T> singleTaskTest(task: Callable<T>, assertAndClean: (List<T>) -> Unit) {
        val taskList = mutableListOf(task).apply {
            repeat(threadCount - 1) {this.add(task)}
        }
        taskListTest(taskList, assertAndClean)
    }

    @Test
    fun addingAndRemovingFixtureConcurrently() {
        patch.addFixtureTypes(listOf(exampleFixtureType))

        singleTaskTest(
            task = {
                barrier.await(2, TimeUnit.SECONDS)
                val addResult = patch.addFixtures(listOf(exampleFixture))
                barrier.await(2, TimeUnit.SECONDS)
                val removeResult = patch.removeFixtures(listOf(exampleFixture.uuid))
                Pair(addResult, removeResult)
            },
            assertAndClean = { results ->
                assertEquals(1, results.map{it.first}.filterIsInstance<Ok<Unit>>().size)
                assertEquals(1, results.map{it.first}.filterIsInstance<Ok<Unit>>().size)
                assertEquals(0, patch.getFixtures().size)
                patch.removeFixtures(listOf(exampleFixture.uuid))
            }
        )
    }

    @Test
    fun addingAndRemovingFixtureTypesConcurrently() = singleTaskTest(
            task = {
                barrier.await(2, TimeUnit.SECONDS)
                val addResult = patch.addFixtureTypes(listOf(exampleFixtureType, exampleFixtureType2))
                barrier.await(2, TimeUnit.SECONDS)
                val removeResult = patch.removeFixtureTypes(listOf(exampleFixtureType, exampleFixtureType2).map{it.fixtureTypeId})
                Pair(addResult, removeResult)
            },
            assertAndClean = { results ->
                // only one of the threads should have received an Ok
                assertEquals(1, results.map{it.first}.filterIsInstance<Ok<Unit>>().size)
                assertEquals(1, results.map{it.second}.filterIsInstance<Ok<Unit>>().size)
                // remove should have worked
                assertEquals(0, patch.getFixtureTypes().size)
            }
        )

    @Test
    fun updateAndRemoveFixtures() {
        patch.addFixtureTypes(listOf(exampleFixtureType))

        val fixtureList = generateExampleFixtures(10)
        patch.addFixtures(fixtureList)

        val removeTask = Callable {
            fixtureList.map{it.uuid}.forEach {
                barrier.await(2, TimeUnit.SECONDS)
                patch.removeFixtures(listOf(it))
            }
        }

        val updateTask = Callable {
            fixtureList.map{it.uuid}.forEach {
                val update = PatchFixtureUpdate(it, fid = ThreadLocalRandom.current().nextInt())
                barrier.await(2, TimeUnit.SECONDS)
                patch.updateFixtures(listOf(update))
            }
        }

        val taskList = mutableListOf(removeTask).apply {
            repeat(threadCount - 1) {this.add(updateTask)}
        }

        taskListTest(taskList) {
            assertEquals(0, patch.getFixtures().size)
        }
    }
}
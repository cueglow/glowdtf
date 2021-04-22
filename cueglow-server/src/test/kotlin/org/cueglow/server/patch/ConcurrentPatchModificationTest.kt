package org.cueglow.server.patch

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.getOrElse
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.*
import java.util.concurrent.locks.ReentrantLock

// TODO for concurrent patch testing
// multi-method interactions
// TODO update fixture while deleting it -> could not be deleted in the end
// TODO Removing Fixture Type while adding fixtures for that fixture type should not leave any fixtures behind
// TODO while removing a fixture type, fixtures where the associated fixture type is already removed should not be observable in the fixtures map
// TODO do a test involving removeFixtures

class ConcurrentPatchModificationTest {
    val patch = Patch(LinkedBlockingQueue(), ReentrantLock())
    val threadCount = 4
    val iterationCount = 60
    val barrier = CyclicBarrier(threadCount)
    val pool = Executors.newFixedThreadPool(threadCount)

    val exampleFixture = ExampleFixtureType.esprite_fixture
    val exampleFixtureType = ExampleFixtureType.esprite

    @Test
    fun addingSameFixtureFromMultipleThreadOnlyWorksOnce() {
        patch.addFixtureTypes(listOf(exampleFixtureType))

        val task = Callable {
            barrier.await(2, TimeUnit.SECONDS)
            patch.addFixtures(listOf(exampleFixture))
        }

        val taskList = mutableListOf(task).apply {
            repeat(threadCount - 1) {this.add(task)}
        }

        repeat(iterationCount) { iteration ->
            val futures = pool.invokeAll(taskList)

            val results = futures.map { it.get() }

            try {
                assertEquals(1, results.filterIsInstance<Ok<Unit>>().size)
                assertEquals(1, patch.getFixtures().size)
            } catch (error: Throwable) {
                println("failing in iteration $iteration")
                println("failing list of Results: $results")
                println("failing fixture list: ${patch.getFixtures()}")
                println("failing fixture list length: ${patch.getFixtures().size}")
                throw error
            }

            // clean up
            patch.removeFixtures(listOf(exampleFixture.uuid))
        }
    }

    @Test
    fun addingSameFixtureTypeFromMultipleThreadsOnlyWorksOnce() {
        val task = Callable {
            barrier.await(2, TimeUnit.SECONDS)
            patch.addFixtureTypes(listOf(exampleFixtureType))
        }

        val taskList = mutableListOf(task).apply {
            repeat(threadCount - 1) {this.add(task)}
        }

        repeat(iterationCount) { iteration ->
            val futures = pool.invokeAll(taskList)

            val results = futures.map { it.get() }

            try {
                assertEquals(1, results.filterIsInstance<Ok<Unit>>().size)
                assertEquals(1, patch.getFixtureTypes().size)
            } catch (error: Throwable) {
                println("failing in iteration $iteration")
                println("failing list of Results: $results")
                println("failing fixture type list: ${patch.getFixtureTypes()}")
                println("failing fixture list length: ${patch.getFixtureTypes().size}")
                throw error
            }

            // clean up
            patch.removeFixtureTypes(listOf(exampleFixtureType.fixtureTypeId))
        }
    }
}
package org.cueglow.server.patch

import com.github.michaelbull.result.Ok
import com.google.common.truth.Truth.assertThat
import org.cueglow.server.test_utilities.ExampleFixtureType
import org.cueglow.server.test_utilities.concurrentTaskListTest
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.locks.ReentrantLock

class ConcurrentPatchTest {
    val patch = Patch(LinkedBlockingQueue(), ReentrantLock())

    private val exampleFixtureTypeList = listOf(
        ExampleFixtureType.esprite,
        ExampleFixtureType.channelLayoutTestGdtf
    )

    private val threadCount = 4
    private val iterationCount = 100

    private fun printPatchStateAfterException() {
        println("failing with ${patch.getFixtures().size} fixtures: ${patch.getFixtures()}")
        println("failing with ${patch.getFixtureTypes().size} fixture types: ${patch.getFixtureTypes()}")
    }

    /**
     * wrapper around [concurrentTaskListTest] from utilites to always use the same [iterationCount] and
     * [printPatchStateAfterException].
     */
    private fun <T> concurrentTaskListTest(concurrentTaskList: List<(CyclicBarrier) -> T>, afterConcurrentTasks: (List<T>) -> Unit) {
        concurrentTaskListTest(iterationCount, concurrentTaskList, afterConcurrentTasks, ::printPatchStateAfterException)
    }

    @Test
    fun updateAndRemoveFixtures() {
        patch.addFixtureTypes(listOf(ExampleFixtureType.esprite))

        val fixtureList = List(10) {
            ExampleFixtureType.esprite_fixture.copy(uuid = UUID.randomUUID())
        }
        patch.addFixtures(fixtureList)

        val removeTask = { barrier: CyclicBarrier ->
            fixtureList.map { it.uuid }.forEach {
                barrier.await(2, TimeUnit.SECONDS)
                patch.removeFixtures(listOf(it))
            }
        }

        val updateTask = { barrier: CyclicBarrier ->
            fixtureList.map { it.uuid }.forEach {
                val update = PatchFixtureUpdate(it, fid = ThreadLocalRandom.current().nextInt())
                barrier.await(2, TimeUnit.SECONDS)
                patch.updateFixtures(listOf(update))
            }
        }

        // one threads removes, others update
        val taskList = mutableListOf(removeTask).apply {
            repeat(threadCount - 1) { this.add(updateTask) }
        }

        concurrentTaskListTest(taskList) {
            assertThat(patch.getFixtures()).hasSize(0)
        }
    }

    private fun <T> singleTaskConcurrentTest(task: (CyclicBarrier) -> T, afterConcurrentTest: (List<T>) -> Unit) {
        org.cueglow.server.test_utilities.singleTaskConcurrentTest(threadCount, iterationCount, task, afterConcurrentTest, ::printPatchStateAfterException)
    }

    @Test
    fun addingAndRemovingFixtureConcurrently() {
        patch.addFixtureTypes(listOf(ExampleFixtureType.esprite))

        singleTaskConcurrentTest(
            task = { barrier ->
                barrier.await(2, TimeUnit.SECONDS)
                val addResult = patch.addFixtures(listOf(ExampleFixtureType.esprite_fixture))
                barrier.await(2, TimeUnit.SECONDS)
                val removeResult = patch.removeFixtures(listOf(ExampleFixtureType.esprite_fixture.uuid))
                Pair(addResult, removeResult)
            },
            afterConcurrentTest = { results ->
                val (addResults, removeResults) = results.unzip()
                assertThat(addResults.filterIsInstance<Ok<Unit>>()).hasSize(1)
                assertThat(removeResults.filterIsInstance<Ok<Unit>>()).hasSize(1)
                assertThat(patch.getFixtures()).hasSize(0)
            }
        )
    }

    @Test
    fun addingAndRemovingFixtureTypesConcurrently() = singleTaskConcurrentTest(
        task = { barrier ->
            barrier.await(2, TimeUnit.SECONDS)
            val addResult = patch.addFixtureTypes(exampleFixtureTypeList)
            barrier.await(2, TimeUnit.SECONDS)
            val removeResult = patch.removeFixtureTypes(exampleFixtureTypeList.map{it.fixtureTypeId})
            Pair(addResult, removeResult)
        },
        afterConcurrentTest = { results ->
            val (addResults, removeResults) = results.unzip()
            assertThat(addResults.filterIsInstance<Ok<Unit>>()).hasSize(1)
            assertThat(removeResults.filterIsInstance<Ok<Unit>>()).hasSize(1)
            assertThat(patch.getFixtureTypes()).hasSize(0)
        }
    )

    private fun setupExampleFixtures() {
        patch.addFixtureTypes(exampleFixtureTypeList)
        patch.addFixtures(listOf(ExampleFixtureType.esprite_fixture, ExampleFixtureType.esprite_fixture2, ExampleFixtureType.channelLayoutTestGdtfFixture))
    }

    private fun testGettersConcurrentlyWhileRemoving(getterLambda: (CyclicBarrier) -> Unit) {
        val removeTask = { barrier: CyclicBarrier ->
            barrier.await(2, TimeUnit.SECONDS)
            patch.removeFixtureTypes(exampleFixtureTypeList.map{it.fixtureTypeId})
            Unit
        }

        val taskList = mutableListOf(removeTask).apply {
            repeat(threadCount - 1) {this.add(getterLambda)}
        }

        setupExampleFixtures()

        concurrentTaskListTest(taskList) {
            setupExampleFixtures()
        }
    }

    @Test
    fun getFixturesReturnsCoherentStateDuringRemove() {
        testGettersConcurrentlyWhileRemoving{ barrier ->
            barrier.await(2, TimeUnit.SECONDS)
            val returnedFixtures = patch.getFixtures()
            assertThat(returnedFixtures.size).isAnyOf(0, 3)
        }
    }

    @Test
    fun getFixtureTypesReturnsCoherentStateDuringRemove() {
        testGettersConcurrentlyWhileRemoving{ barrier ->
            barrier.await(2, TimeUnit.SECONDS)
            val returnedFixtureTypes = patch.getFixtureTypes()
            assertThat(returnedFixtureTypes.size).isAnyOf(0, 2)
        }
    }

    @Test
    fun getGlowPatchReturnsCoherentStateDuringRemove() {
        testGettersConcurrentlyWhileRemoving{ barrier ->
            barrier.await(2, TimeUnit.SECONDS)
            val returnedGlowPatch = patch.getGlowPatch()
            assertThat(returnedGlowPatch.fixtures.size).isAnyOf(0, 3)
            assertThat(returnedGlowPatch.fixtureTypes.size).isAnyOf(0, 2)
        }
    }
}
package org.cueglow.server.test_utilities

import java.util.concurrent.Callable
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors

/**
 * Takes a list of tasks and executes each on a separate Thread.
 * To synchronize, the tasks can take a [CyclicBarrier] that resets after all threads awaited it.
 * Then, [afterConcurrentTasks] can be used to assert on the results returned by the tasks.
 * If an exception is thrown during execution of the tasks or during [afterConcurrentTasks], debug information
 * can be printed in [afterException].
 * All of the above is repeated [repeat] times.
 */
fun <T> concurrentTaskListTest(
    repeat: Int,
    concurrentTaskList: List<(CyclicBarrier) -> T>,
    afterConcurrentTasks: (List<T>) -> Unit = {},
    afterException: () -> Unit = {},
    ) {
    val threadCount = concurrentTaskList.size
    val barrier = CyclicBarrier(threadCount)
    val pool = Executors.newFixedThreadPool(threadCount)!!

    val callableList = concurrentTaskList.map {
        Callable {
            it(barrier)
        }
    }

    fun printExceptionInfo(iteration: Int) {
        println("failing in iteration $iteration")
        afterException()
    }

    repeat(repeat) { iteration ->
        val futures = pool.invokeAll(callableList)

        val results = try {
            futures.map { it.get() }
        } catch (error: Throwable) {
            printExceptionInfo(iteration)
            throw Error("Error during execution of concurrent tasks", error)
        }

        try {
            afterConcurrentTasks(results)
        } catch (error: Throwable) {
            printExceptionInfo(iteration)
            println("failing list of Results: $results")
            throw Error("Error during afterConcurrentTest", error)
        }
    }
}

/**
 * Wrapper around [concurrentTaskListTest] that runs a single task on a [threadCount] threads.
 *
 * For details, see [concurrentTaskListTest].
 */
fun <T> singleTaskConcurrentTest(
    threadCount: Int,
    repeat: Int,
    task: (CyclicBarrier) -> T,
    afterConcurrentTasks: (List<T>) -> Unit,
    afterException: () -> Unit,
) {
    val taskList = List(threadCount) {task}
    concurrentTaskListTest(repeat, taskList, afterConcurrentTasks, afterException)
}
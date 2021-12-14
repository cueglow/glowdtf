package org.cueglow.server.artnet

import java.time.Duration

// using System.nanoTime
// all times in Nanoseconds
// time.Instant only gets updated every ms -> too slow
class RateLimiter(frameTime: Duration) {
    private var scheduledFinish = System.nanoTime()
    private val frameTime = frameTime.toNanos()

    fun limitRate() {
        val current = System.nanoTime()
        val lastScheduled = scheduledFinish
        val periodicScheduleCandidate = lastScheduled + frameTime
        if (current > periodicScheduleCandidate) {
            // too late
            // already over the periodic schedule candidate

            // instead of scheduling the next send in the past, we schedule it now
            scheduledFinish = current

            // and don't sleep here
        } else {
            // Periodic, normal case
            // We try to meet periodic schedule candidate
            scheduledFinish = periodicScheduleCandidate
            smartSleepUntil(scheduledFinish)
        }
    }

    fun smartSleepUntil(t: Long) {
        val smartSleepFor = t - System.nanoTime()

        // magic number - must be benchmarked
        // to determine run Wireshark with Art-Net filter (udp port 6454), then go to Statistics -> I/O Graph.
        // use udp.time_delta as Y_FIELD and display MAX, AVG and MIN. Choose 100 ms window size.
        // Target is less then +- 200 us jitter.
        val spinForMax: Long = 4_000_000
        val sleepFor = smartSleepFor - spinForMax
        if (sleepFor > 0) {
            val sleepForMillis = sleepFor / 1_000_000
            val sleepForNanosPart = (sleepFor - sleepForMillis * 1_000_000).toInt()
            Thread.sleep(sleepForMillis, sleepForNanosPart)
        }
        while (System.nanoTime() < t) {
            // spin
        }
    }
}
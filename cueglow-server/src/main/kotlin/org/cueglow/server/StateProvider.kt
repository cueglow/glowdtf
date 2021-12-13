package org.cueglow.server

import org.cueglow.server.json.JsonHandler
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.patch.Patch
import org.cueglow.server.rig.FixtureState
import org.cueglow.server.rig.RigState
import java.util.concurrent.BlockingQueue
import java.util.concurrent.locks.ReentrantLock

/**
 * Provides a collection of state objects
 *
 * The StateProvider is initialized by the main process and passed to e.g. a [JsonHandler] for mutation.
 */
class StateProvider(val outEventQueue: BlockingQueue<GlowMessage>) {
    val lock = ReentrantLock()
    val rigState: RigState = mutableListOf()
    val patch = Patch(outEventQueue, lock, rigState)
}
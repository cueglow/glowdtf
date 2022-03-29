package org.cueglow.server

import org.cueglow.server.json.JsonHandler
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.patch.Patch
import org.cueglow.server.rig.RigStateMap
import java.util.concurrent.BlockingQueue
import java.util.concurrent.locks.ReentrantLock

/**
 * Provides a collection of state objects
 *
 * The StateProvider is initialized by the main process and passed to e.g. a [JsonHandler] for mutation.
 */
class StateProvider(val outEventQueue: BlockingQueue<GlowMessage>) {
    val lock = ReentrantLock()
    val rigStateContainer = RigStateContainer()
    val patch = Patch(outEventQueue, lock, rigStateContainer)
}

/* Allows us to pass rigState into Patch by reference that will update when we assign new values to rigState */
class RigStateContainer() {
    var rigState: RigStateMap = mapOf()
}
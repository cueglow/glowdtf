package org.cueglow.server

import org.cueglow.server.json.JsonHandler
import org.cueglow.server.objects.messages.GlowMessage
import org.cueglow.server.patch.Patch
import java.util.concurrent.BlockingQueue

/**
 * Provides a collection of state objects
 *
 * The StateProvider is initialized by the main process and passed to e.g. a [JsonHandler] for mutation.
 */
class StateProvider(outEventQueue: BlockingQueue<GlowMessage>) {
    val patch = Patch(outEventQueue)
}
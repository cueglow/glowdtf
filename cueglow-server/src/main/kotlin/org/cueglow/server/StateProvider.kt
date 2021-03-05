package org.cueglow.server

import org.cueglow.server.json.JsonHandler
import org.cueglow.server.patch.Patch

/**
 * Provides a collection of state objects
 *
 * The StateProvider is initialized by the main process and passed to e.g. a [JsonHandler] for mutation.
 */
class StateProvider {
    val patch = Patch()
}
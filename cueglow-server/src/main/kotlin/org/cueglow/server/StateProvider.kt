package org.cueglow.server

import org.cueglow.server.patch.Patch

/**
 * Provides a collection of state objects
 *
 * The StateProvider is initialized by the main process and passed to e.g. the [InEventHandler] to mutate.
 */
class StateProvider {
    val patch = Patch()
}
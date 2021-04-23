package org.cueglow.server

import org.apache.logging.log4j.kotlin.Logging
import org.cueglow.server.objects.messages.GlowMessage
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

/**
 * Starts a thread that takes GlowMessages from the [queue] of OutEvents and passes them to the registered receivers.
 */
class OutEventHandler(receivers: Iterable<OutEventReceiver>): Logging {
    val queue = LinkedBlockingQueue<GlowMessage>()

    init {
        // TODO what happens if there is an exception in this thread?
        Executors.newSingleThreadExecutor().submit {
            while (true) {
                val glowMessage = queue.take()
                logger.info("Handling OutEvent: $glowMessage")
                receivers.forEach { it.receive(glowMessage) }
            }
        }
    }
}

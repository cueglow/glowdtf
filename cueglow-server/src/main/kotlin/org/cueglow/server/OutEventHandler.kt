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
        Executors.newSingleThreadExecutor().submit {
            while (true) {
                try {
                    val glowMessage = queue.take()
                    logger.debug("Handling OutEvent: $glowMessage")
                    receivers.forEach { it.receive(glowMessage) }
                } catch (e: Throwable) {
                    logger.error(e)
                }
            }
        }
    }
}

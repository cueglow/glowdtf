package org.cueglow.server.json

/**
 * Represents a Client of the Server that can be sent a String message asynchronously (i.e. at any time)
 */
interface AsyncClient {
    fun send(message: String)
}
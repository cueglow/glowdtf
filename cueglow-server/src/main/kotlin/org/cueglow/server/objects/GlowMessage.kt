package org.cueglow.server.objects

import kotlin.properties.Delegates

class GlowMessage {

    lateinit var glowEvent: GlowEvent;
    lateinit var data: String; // TODO Change to JSON Object
    var messageId by Delegates.notNull<Int>();

    /**
     * Create a new GlowMessage and parse the values from the [jsonString].
     */
    constructor(jsonString: String) {
        // TODO Convert JSON Message to parsed objects

        println("Created Message from jsonString: $jsonString")
    }

    init {
        println("Created new empty GlowMessage")
    }

}
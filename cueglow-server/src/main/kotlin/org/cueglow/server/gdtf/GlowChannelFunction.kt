package org.cueglow.server.gdtf

import com.beust.klaxon.Json
import org.cueglow.gdtf.ChannelFunction

data class GlowChannelFunction(
    val name: String,

    // specified with the same byte number as the parent MultiByteChannel
    val dmxFrom: Long, // support up to 7 bytes
    val dmxTo: Long, // support up to 7 bytes

    val multiByteChannelInd: Int,
    val logicalChannel: String?, // either name of Logical Channel or if null indicates raw DMX Channel Function

    // TODO this is only needed during construction of the DMX mode - move to some sort of context object?
    @Json(ignored = true)
    val originalChannelFunction: ChannelFunction?, // null when raw DMX ChF

    @Json(ignored = true)
    val defaultValue: Long?, // null when raw DMX ChF

    val attribute: String?, // null when raw DMX ChF

    val featureGroup: String, // Should be "Raw DMX" for raw channel functions
)

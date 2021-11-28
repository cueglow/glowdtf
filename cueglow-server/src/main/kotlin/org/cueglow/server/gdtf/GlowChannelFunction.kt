package org.cueglow.server.gdtf

data class GlowChannelFunction (
    val name: String,

    // specified with the same byte number as the parent MultiByteChannel
    val dmxFrom: Long, // support up to 7 bytes
    val dmxTo: Long, // support up to 7 bytes

    val multiByteChannelInd: Int,

    val raw: Boolean = false,

    // TODO controlled geometry and attribute (not in name!)
)

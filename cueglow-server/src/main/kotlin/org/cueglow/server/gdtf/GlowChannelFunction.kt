package org.cueglow.server.gdtf

data class GlowChannelFunction (
    val name: String,
    // TODO what name do we give to the RawDmx channel function? How to avoid collisions?
    // TODO how to uniquely identify each channel function in a certain DmxMode?

    // specified with the same byte number as the parent MultiByteChannel
    val dmxFrom: Long, // support up to 7 bytes
    val dmxTo: Long, // support up to 7 bytes

    val multiByteChannelInd: Int,

    // TODO controlled geometry and attribute (or is that in name?)
)

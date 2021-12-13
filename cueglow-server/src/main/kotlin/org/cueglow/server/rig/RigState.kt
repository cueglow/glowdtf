package org.cueglow.server.rig

typealias RigState = MutableList<FixtureState>

data class FixtureState(
    val chValues: MutableList<Long>, // size = multiByteChannels.size
    val chFDisabled: MutableList<String?>, // size = channelFunctions.size
    // modesDisabled is null if enabled or string with reason if disabled
)

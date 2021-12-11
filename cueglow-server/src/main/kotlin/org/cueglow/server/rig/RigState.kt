package org.cueglow.server.rig

typealias RigState = MutableList<FixtureState>

typealias FixtureState = MutableList<ChannelFunctionState>

data class ChannelFunctionState(
    var value: Long,
    var outOfRange: Boolean,
    var modeDisabled: String?,
)

fun ChannelFunctionState.active(): Boolean =
    this.modeDisabled == null && !this.outOfRange

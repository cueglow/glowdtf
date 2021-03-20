package org.cueglow.server.objects.messages

import com.beust.klaxon.Json
import java.util.*

// TODO remove at later stages after moving content to GlowMessage Polymorphism
/**
 * Different types for the data property in [GlowMessage]. Which specific subtype to use depends on the event property.
 */
sealed class GlowData {
    data class Subscribe(val stream: String) : GlowData()

    data class FixtureTypeAdded(val fixtureTypeId : UUID): GlowData()
}










package org.cueglow.server.gdtf

import org.cueglow.gdtf.GDTF
import java.util.*

/**
 * Immutable wrapper around GDTF
 */
class GdtfWrapper(val gdtf: GDTF) {
    val name: String = gdtf.fixtureType.name
    val manufacturer: String = gdtf.fixtureType.manufacturer
    val fixtureTypeId: UUID = UUID.fromString(gdtf.fixtureType.fixtureTypeID)
    val modes: List<GlowDmxMode> = run {
        val abstractGeometries = findAbstractGeometries(gdtf.fixtureType.geometries)
        // create each mode
        gdtf.fixtureType.dmxModes.dmxMode.map { GlowDmxMode(it, abstractGeometries) }
    }
}



package org.cueglow.server.gdtf

import com.gdtf_share.schemas.device.GDTF
import java.util.*

/**
 * Immutable wrapper around GDTF
 */
class GdtfWrapper(private val gdtf: GDTF) {
    val name = gdtf.fixtureType.name
    val manufacturer = gdtf.fixtureType.manufacturer
    val fixtureTypeId = UUID.fromString(gdtf.fixtureType.fixtureTypeID)
    val modes: List<DmxMode> = run {
        gdtf.fixtureType.attributeDefinitionsOrWheelsOrPhysicalDescriptions.
    }
}

data class DmxMode(val name: String, val channelCount: Int)
package org.cueglow.server.gdtf

import com.beust.klaxon.Json
import org.apache.logging.log4j.LogManager
import org.cueglow.gdtf.GDTF
import java.util.*
import java.time.LocalDateTime

// logger for gdtf package
val logger = LogManager.getLogger()!!

/**
 * Immutable wrapper around GDTF
 */
class GdtfWrapper(@Json(ignored = true) val gdtf: GDTF) {
    val name: String = gdtf.fixtureType.name ?: ""
    val manufacturer: String = gdtf.fixtureType.manufacturer ?: ""
    val fixtureTypeId: UUID = UUID.fromString(gdtf.fixtureType.fixtureTypeID)
    val shortName: String = gdtf.fixtureType.shortName ?: ""
    val longName: String = gdtf.fixtureType.longName ?: ""
    val description: String = gdtf.fixtureType.description ?: ""
    val refFT: UUID? = if (gdtf.fixtureType.refFT != "" && gdtf.fixtureType.refFT != null) {
        logger.info("refFt from string", gdtf.fixtureType.refFT)
        UUID.fromString(gdtf.fixtureType.refFT)
    } else {
        null
    }
    val revisions: List<GlowRevision> = gdtf.fixtureType.revisions.revision.map {
        GlowRevision(
            LocalDateTime.of(it.date.year, it.date.month, it.date.day, it.date.hour, it.date.minute, it.date.second).toString(),
            it.text ?: "",
            it.userID.toInt(),
        )
    }
    val modes: List<GlowDmxMode> = run {
        logger.info("Parsing GDTF: $manufacturer $name")
        val abstractGeometries = findAbstractGeometries(gdtf.fixtureType.geometries)
        // create each mode
        gdtf.fixtureType.dmxModes.dmxMode.map {
            GlowDmxMode(
                it,
                abstractGeometries,
                gdtf.fixtureType.attributeDefinitions.attributes.attribute
            )
        }
    }
}

data class GlowRevision(val date: String, val text: String, val userId: Int)



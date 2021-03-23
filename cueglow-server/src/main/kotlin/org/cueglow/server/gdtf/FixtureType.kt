package org.cueglow.server.gdtf

import org.cueglow.gdtf.*
import java.util.*
import javax.xml.bind.JAXBElement

/**
 * Immutable wrapper around GDTF
 *
 * Currently only provides the most basic properties (dmxMode channelCount is also wrong, see comments).
 */
class FixtureType(val gdtf: GDTF) {
    val name: String = gdtf.fixtureType.name
    val manufacturer: String = gdtf.fixtureType.manufacturer
    val fixtureTypeId: UUID = UUID.fromString(gdtf.fixtureType.fixtureTypeID)
    val modes: List<DmxMode> = createModes(gdtf)
}

data class AbstractGeometry(val name: String, val referencedBy: MutableList<GeometryReference>)

fun createModes(gdtf: GDTF): List<DmxMode> {
    val abstractGeometries: List<AbstractGeometry> = findAbstractGeometries(gdtf.fixtureType.geometries)
    return gdtf.fixtureType.dmxModes.dmxMode.map { createMode(it, abstractGeometries) }
}

fun findAbstractGeometries(geometries: Geometries): List<AbstractGeometry> {
    val abstractGeometries: MutableList<AbstractGeometry> = mutableListOf()
    addToListOrDescend(geometries.geometryOrAxisOrFilterBeam, abstractGeometries)
    return abstractGeometries
}

fun addToListOrDescend(geometryList: List<JAXBElement<out BasicGeometryAttributes>>, abstractGeometries: MutableList<AbstractGeometry>) {
    geometryList.forEach { jaxbElementGeometry ->
        // if node is GeometryReference, add referenced Geometry to Set
        if(jaxbElementGeometry.declaredType == GeometryReference::class.java) {
            val currentGeometryReference = jaxbElementGeometry.value as GeometryReference
            val referenced = currentGeometryReference.geometry
            val abstractGeometry = abstractGeometries.find { it.name == referenced } ?: run {
                val newAbstractGeometry = AbstractGeometry(referenced, mutableListOf())
                abstractGeometries.add(newAbstractGeometry)
                newAbstractGeometry
            }
            abstractGeometry.referencedBy.add(currentGeometryReference)
        } else {
            // node is not a GeometryReference, so it can have children and is therefore castable to BasicGeometryType
            // therefore we can descend and recurse
            val children = (jaxbElementGeometry.value as BasicGeometryType).geometryOrAxisOrFilterBeam
            addToListOrDescend(children, abstractGeometries)
        }
    }
}

fun createMode(mode: DMXMode, abstractGeometries: List<AbstractGeometry>): DmxMode {
    val modeName = mode.name
    val channels = mode.dmxChannels.dmxChannel
    val channelLayout: MutableList<MutableList<String?>> = mutableListOf(mutableListOf())
    channels.forEach { channel ->
        val abstractGeometry = abstractGeometries.find { it.name == channel.geometry }
        if (abstractGeometry != null) {
            // channel is instantiated by references
            val namePrototype = channelNamePrototype(channel)
            abstractGeometry.referencedBy.forEach { geometryReference ->
                val nameWithoutByteNumber = "${geometryReference.name} -> $namePrototype"
                // calculate break
                val (dmxBreak, refOffset) = if (channel.dmxBreak == "Overwrite") {
                    // use last Break element in geometry reference as override element
                    val lastBreakMap = geometryReference.`break`.last()
                    val dmxBreak = lastBreakMap.dmxBreak.toInt()
                    val refOffset = lastBreakMap.dmxOffset
                    Pair(dmxBreak, refOffset)
                } else {
                    // use first Break element in geometry reference that matches break of channel
                    val dmxBreak = channel.dmxBreak.toInt()
                    val refOffset = geometryReference.`break`.find { it.dmxBreak.toInt() == dmxBreak }?.dmxOffset ?:
                    throw java.lang.IllegalArgumentException("The Geometry Reference ${geometryReference.name} does not " +
                            "provide an offset for the break $dmxBreak as required by the channel $namePrototype in " +
                            "DMX Mode $modeName")
                    Pair(dmxBreak, refOffset)
                }
                val offsets: List<Int> = channel.offset.split(",").map { it.toInt() }
                val byteNumber = offsets.size
                offsets.forEachIndexed { offsetIndex, originalOffset ->
                    val offset = originalOffset+refOffset-1
                    val breakList = channelLayout.elementAtOrFillWith(dmxBreak - 1, mutableListOf())
                    val nameAtWriteIndex = breakList.elementAtOrFillWith(offset - 1, null)
                    val nameToWrite = if (byteNumber == 1) {
                        nameWithoutByteNumber
                    } else {
                        "$nameWithoutByteNumber (${offsetIndex+1}/$byteNumber)"
                    }
                    if (nameAtWriteIndex != null) {
                        // channel name already set -> there is a collision between two channels
                        throw IllegalArgumentException("GDTF file produces DMX Channel collision at break $dmxBreak and offset " +
                                "$offset between $nameAtWriteIndex and $nameToWrite (in Mode $modeName)")
                    } else {
                        breakList[offset - 1] = nameToWrite
                    }
                }
            }
        } else {
            // channel is what it is
            val dmxBreak: Int = channel.dmxBreak.toInt()
            val offsets: List<Int> = channel.offset.split(",").map { it.toInt() }
            val byteNumber = offsets.size
            val namePrototype = channelNamePrototype(channel)
            offsets.forEachIndexed { offsetIndex, offset ->
                val breakList = channelLayout.elementAtOrFillWith(dmxBreak - 1, mutableListOf())
                val nameAtWriteIndex = breakList.elementAtOrFillWith(offset - 1, null)
                val nameToWrite = if (byteNumber == 1) {
                    namePrototype
                } else {
                    "$namePrototype (${offsetIndex+1}/$byteNumber)"
                }
                if (nameAtWriteIndex != null) {
                    // channel name already set -> there is a collision between two channels
                    throw IllegalArgumentException("GDTF file produces DMX Channel collision at break $dmxBreak and offset " +
                    "$offset between $nameAtWriteIndex and $nameToWrite (in Mode $modeName)")
                } else {
                    breakList[offset - 1] = nameToWrite
                }
            }
        }
    }
    val channelCount: Int = channelLayout.sumBy { it.size }
    return DmxMode(modeName, channelCount, channelLayout)
}

/** Name Prototype where reference name has to be prepended and byte-number appended. */
fun channelNamePrototype(channel: DMXChannel): String {
    val geometry = channel.geometry
    val attribute = channel.logicalChannel[0].attribute
    return "${geometry}_${attribute}"
}

fun <T> MutableList<T>.elementAtOrFillWith(index: Int, fillWith: T): T {
    return this.elementAtOrElse(index) {
        while(this.size < index + 1) {
            this.add(fillWith)
        }
        this[index]
    }
}

/**
 * @property channelLayout A list of DMX breaks, each of which is a list of channel name strings. If the channel name is
 *     null, it means this specific DMX offset is empty.
 *     Example for a channel name: "LED1 -> GenericLED_Dimmer (1/2)"
 *     where the channel references the Geometry GenericLED, the channel is instantiated by the Geometry Reference LED1,
 *     the controlled Attribute is Dimmer and it is the coarse channel of two channels controlling the dimmer with
 *     16 bits.
 *     If the channel is only 8-bit, the " (1/1)" at the end is omitted. If the channel does not come from a
 *     referenced Geometry, the "LED1 -> " is omitted.
 */
data class DmxMode(val name: String, val channelCount: Int, val channelLayout: List<List<String?>>)
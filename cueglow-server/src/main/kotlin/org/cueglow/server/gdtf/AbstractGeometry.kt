package org.cueglow.server.gdtf

import org.cueglow.gdtf.BasicGeometryAttributes
import org.cueglow.gdtf.BasicGeometryType
import org.cueglow.gdtf.Geometries
import org.cueglow.gdtf.GeometryReference
import jakarta.xml.bind.JAXBElement

/**
 * Represents a top-level geometry that is referenced by a GeometryReference. It is "abstract" because the
 * channels that reference it are only instantiated by the geometries referencing it.
 */
data class AbstractGeometry(val name: String, val referencedBy: MutableList<GeometryReference>)

fun findAbstractGeometries(geometries: Geometries): List<AbstractGeometry> {
    val abstractGeometries: MutableList<AbstractGeometry> = mutableListOf()
    addToListOrDescend(geometries.geometryOrAxisOrFilterBeam, abstractGeometries)
    return abstractGeometries
}

/** Recursive Function which adds a geometry to the list of abstract geometries if it is referenced */
private fun addToListOrDescend(
    geometryList: List<JAXBElement<out BasicGeometryAttributes>>,
    abstractGeometries: MutableList<AbstractGeometry>
) {
    geometryList.forEach { jaxbElementGeometry ->
        // if node is GeometryReference, add referenced Geometry to Set
        if (jaxbElementGeometry.declaredType == GeometryReference::class.java) {
            val currentGeometryReference = jaxbElementGeometry.value as GeometryReference
            val referenced = currentGeometryReference.geometry
            val abstractGeometry = abstractGeometries.find { it.name == referenced } ?: run {
                val newAbstractGeometry = AbstractGeometry(referenced, mutableListOf())
                abstractGeometries.add(newAbstractGeometry)
                newAbstractGeometry
            }
            abstractGeometry.referencedBy.add(currentGeometryReference)
            // no need to recurse here since GeometryReference cannot have Geometry children
        } else {
            // node is not a GeometryReference, so it can have children and is therefore castable to BasicGeometryType
            // therefore we can descend and recurse
            val children = (jaxbElementGeometry.value as BasicGeometryType).geometryOrAxisOrFilterBeam
            addToListOrDescend(children, abstractGeometries)
        }
    }
}
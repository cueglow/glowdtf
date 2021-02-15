//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.15 at 11:52:05 PM CET 
//


package com.gdtf_share.schemas.device;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for Geometries complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Geometries"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;group ref="{}GeometryChildren"/&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Geometries", propOrder = {
    "geometryOrAxisOrFilterBeam"
})
public class Geometries {

    @XmlElementRefs({
        @XmlElementRef(name = "Geometry", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Axis", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "FilterBeam", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "FilterColor", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "FilterGobo", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "FilterShaper", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Beam", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "MediaServerLayer", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "MediaServerCamera", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "MediaServerMaster", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Display", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "GeometryReference", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<? extends BasicGeometryAttributes>> geometryOrAxisOrFilterBeam;

    /**
     * Gets the value of the geometryOrAxisOrFilterBeam property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the geometryOrAxisOrFilterBeam property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGeometryOrAxisOrFilterBeam().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link BasicGeometryType }{@code >}
     * {@link JAXBElement }{@code <}{@link BasicGeometryType }{@code >}
     * {@link JAXBElement }{@code <}{@link BasicGeometryType }{@code >}
     * {@link JAXBElement }{@code <}{@link BasicGeometryType }{@code >}
     * {@link JAXBElement }{@code <}{@link BasicGeometryType }{@code >}
     * {@link JAXBElement }{@code <}{@link BasicGeometryType }{@code >}
     * {@link JAXBElement }{@code <}{@link Beam }{@code >}
     * {@link JAXBElement }{@code <}{@link BasicGeometryType }{@code >}
     * {@link JAXBElement }{@code <}{@link BasicGeometryType }{@code >}
     * {@link JAXBElement }{@code <}{@link BasicGeometryType }{@code >}
     * {@link JAXBElement }{@code <}{@link Display }{@code >}
     * {@link JAXBElement }{@code <}{@link GeometryReference }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends BasicGeometryAttributes>> getGeometryOrAxisOrFilterBeam() {
        if (geometryOrAxisOrFilterBeam == null) {
            geometryOrAxisOrFilterBeam = new ArrayList<JAXBElement<? extends BasicGeometryAttributes>>();
        }
        return this.geometryOrAxisOrFilterBeam;
    }

}

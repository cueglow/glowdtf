//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java-Klasse für anonymous complex type.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Geometry" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Axis" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FilterBeam" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FilterColor" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FilterGobo" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FilterShaper" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Beam" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}GeometryReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "geometryAndAxisAndFilterBeam"
})
@XmlRootElement(name = "Geometries")
public class Geometries {

    @XmlElements({
        @XmlElement(name = "Geometry", type = Geometry.class),
        @XmlElement(name = "Axis", type = Axis.class),
        @XmlElement(name = "FilterBeam", type = FilterBeam.class),
        @XmlElement(name = "FilterColor", type = FilterColor.class),
        @XmlElement(name = "FilterGobo", type = FilterGobo.class),
        @XmlElement(name = "FilterShaper", type = FilterShaper.class),
        @XmlElement(name = "Beam", type = Beam.class),
        @XmlElement(name = "GeometryReference", type = GeometryReference.class)
    })
    protected List<Object> geometryAndAxisAndFilterBeam;

    /**
     * Gets the value of the geometryAndAxisAndFilterBeam property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the geometryAndAxisAndFilterBeam property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGeometryAndAxisAndFilterBeam().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link Geometry }
     * {@link Axis }
     * {@link FilterBeam }
     * {@link FilterColor }
     * {@link FilterGobo }
     * {@link FilterShaper }
     * {@link Beam }
     * {@link GeometryReference }
     * 
     * 
     */
    public List<Object> getGeometryAndAxisAndFilterBeam() {
        if (geometryAndAxisAndFilterBeam == null) {
            geometryAndAxisAndFilterBeam = new ArrayList<Object>();
        }
        return this.geometryAndAxisAndFilterBeam;
    }

}

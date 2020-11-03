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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}MeasurementPoint"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="Physical" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="LuminousIntensity" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="Transmission" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="InterpolationTo" type="{http://schemas.gdtf-share.com/device}InterpolationToEnum" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "measurementPoint"
})
@XmlRootElement(name = "Measurement")
public class Measurement {

    @XmlElement(name = "MeasurementPoint")
    protected List<MeasurementPoint> measurementPoint;
    @XmlAttribute(name = "Physical")
    protected Float physical;
    @XmlAttribute(name = "LuminousIntensity")
    protected Float luminousIntensity;
    @XmlAttribute(name = "Transmission")
    protected Float transmission;
    @XmlAttribute(name = "InterpolationTo")
    protected InterpolationToEnum interpolationTo;

    /**
     * Gets the value of the measurementPoint property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the measurementPoint property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getMeasurementPoint().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link MeasurementPoint }
     * 
     * 
     */
    public List<MeasurementPoint> getMeasurementPoint() {
        if (measurementPoint == null) {
            measurementPoint = new ArrayList<MeasurementPoint>();
        }
        return this.measurementPoint;
    }

    /**
     * Ruft den Wert der physical-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPhysical() {
        return physical;
    }

    /**
     * Legt den Wert der physical-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPhysical(Float value) {
        this.physical = value;
    }

    /**
     * Ruft den Wert der luminousIntensity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getLuminousIntensity() {
        return luminousIntensity;
    }

    /**
     * Legt den Wert der luminousIntensity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setLuminousIntensity(Float value) {
        this.luminousIntensity = value;
    }

    /**
     * Ruft den Wert der transmission-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getTransmission() {
        return transmission;
    }

    /**
     * Legt den Wert der transmission-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTransmission(Float value) {
        this.transmission = value;
    }

    /**
     * Ruft den Wert der interpolationTo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link InterpolationToEnum }
     *     
     */
    public InterpolationToEnum getInterpolationTo() {
        return interpolationTo;
    }

    /**
     * Legt den Wert der interpolationTo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link InterpolationToEnum }
     *     
     */
    public void setInterpolationTo(InterpolationToEnum value) {
        this.interpolationTo = value;
    }

}

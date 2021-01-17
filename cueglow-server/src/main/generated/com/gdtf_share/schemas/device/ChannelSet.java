//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.17 at 10:55:10 PM CET 
//


package com.gdtf_share.schemas.device;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for anonymous complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="DMXFrom" type="{http://schemas.gdtf-share.com/device}dmxtype" /&amp;gt;
 *       &amp;lt;attribute name="DMXTo" type="{http://schemas.gdtf-share.com/device}dmxtype" /&amp;gt;
 *       &amp;lt;attribute name="PhysicalFrom" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="PhysicalTo" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="WheelSlotIndex" type="{http://www.w3.org/2001/XMLSchema}integer" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "ChannelSet")
public class ChannelSet {

    @XmlAttribute(name = "Name")
    protected String name;
    @XmlAttribute(name = "DMXFrom")
    protected String dmxFrom;
    @XmlAttribute(name = "DMXTo")
    protected String dmxTo;
    @XmlAttribute(name = "PhysicalFrom")
    protected Float physicalFrom;
    @XmlAttribute(name = "PhysicalTo")
    protected Float physicalTo;
    @XmlAttribute(name = "WheelSlotIndex")
    protected BigInteger wheelSlotIndex;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the dmxFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDMXFrom() {
        return dmxFrom;
    }

    /**
     * Sets the value of the dmxFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDMXFrom(String value) {
        this.dmxFrom = value;
    }

    /**
     * Gets the value of the dmxTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDMXTo() {
        return dmxTo;
    }

    /**
     * Sets the value of the dmxTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDMXTo(String value) {
        this.dmxTo = value;
    }

    /**
     * Gets the value of the physicalFrom property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPhysicalFrom() {
        return physicalFrom;
    }

    /**
     * Sets the value of the physicalFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPhysicalFrom(Float value) {
        this.physicalFrom = value;
    }

    /**
     * Gets the value of the physicalTo property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPhysicalTo() {
        return physicalTo;
    }

    /**
     * Sets the value of the physicalTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPhysicalTo(Float value) {
        this.physicalTo = value;
    }

    /**
     * Gets the value of the wheelSlotIndex property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWheelSlotIndex() {
        return wheelSlotIndex;
    }

    /**
     * Sets the value of the wheelSlotIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWheelSlotIndex(BigInteger value) {
        this.wheelSlotIndex = value;
    }

}

//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
     * Ruft den Wert der name-Eigenschaft ab.
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
     * Legt den Wert der name-Eigenschaft fest.
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
     * Ruft den Wert der dmxFrom-Eigenschaft ab.
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
     * Legt den Wert der dmxFrom-Eigenschaft fest.
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
     * Ruft den Wert der dmxTo-Eigenschaft ab.
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
     * Legt den Wert der dmxTo-Eigenschaft fest.
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
     * Ruft den Wert der physicalFrom-Eigenschaft ab.
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
     * Legt den Wert der physicalFrom-Eigenschaft fest.
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
     * Ruft den Wert der physicalTo-Eigenschaft ab.
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
     * Legt den Wert der physicalTo-Eigenschaft fest.
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
     * Ruft den Wert der wheelSlotIndex-Eigenschaft ab.
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
     * Legt den Wert der wheelSlotIndex-Eigenschaft fest.
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

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
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}ChannelSet"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="Attribute" use="required" type="{http://schemas.gdtf-share.com/device}AttributeEnum" /&amp;gt;
 *       &amp;lt;attribute name="OriginalAttribute" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="DMXFrom" type="{http://schemas.gdtf-share.com/device}dmxtype" /&amp;gt;
 *       &amp;lt;attribute name="PhysicalFrom" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="PhysicalTo" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="RealFade" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="Wheel" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="Emitter" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="Filter" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="ModeMaster" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="ModeFrom" type="{http://schemas.gdtf-share.com/device}dmxtype" default="0/1" /&amp;gt;
 *       &amp;lt;attribute name="ModeTo" type="{http://schemas.gdtf-share.com/device}dmxtype" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "channelSet"
})
@XmlRootElement(name = "ChannelFunction")
public class ChannelFunction {

    @XmlElement(name = "ChannelSet")
    protected List<ChannelSet> channelSet;
    @XmlAttribute(name = "Name")
    protected String name;
    @XmlAttribute(name = "Attribute", required = true)
    protected AttributeEnum attribute;
    @XmlAttribute(name = "OriginalAttribute")
    protected String originalAttribute;
    @XmlAttribute(name = "DMXFrom")
    protected String dmxFrom;
    @XmlAttribute(name = "PhysicalFrom")
    protected Float physicalFrom;
    @XmlAttribute(name = "PhysicalTo")
    protected Float physicalTo;
    @XmlAttribute(name = "RealFade")
    protected Float realFade;
    @XmlAttribute(name = "Wheel")
    protected String wheel;
    @XmlAttribute(name = "Emitter")
    protected String emitter;
    @XmlAttribute(name = "Filter")
    protected String filter;
    @XmlAttribute(name = "ModeMaster")
    protected String modeMaster;
    @XmlAttribute(name = "ModeFrom")
    protected String modeFrom;
    @XmlAttribute(name = "ModeTo")
    protected String modeTo;

    /**
     * Gets the value of the channelSet property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the channelSet property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getChannelSet().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ChannelSet }
     * 
     * 
     */
    public List<ChannelSet> getChannelSet() {
        if (channelSet == null) {
            channelSet = new ArrayList<ChannelSet>();
        }
        return this.channelSet;
    }

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
     * Ruft den Wert der attribute-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AttributeEnum }
     *     
     */
    public AttributeEnum getAttribute() {
        return attribute;
    }

    /**
     * Legt den Wert der attribute-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeEnum }
     *     
     */
    public void setAttribute(AttributeEnum value) {
        this.attribute = value;
    }

    /**
     * Ruft den Wert der originalAttribute-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalAttribute() {
        return originalAttribute;
    }

    /**
     * Legt den Wert der originalAttribute-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalAttribute(String value) {
        this.originalAttribute = value;
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
     * Ruft den Wert der realFade-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getRealFade() {
        return realFade;
    }

    /**
     * Legt den Wert der realFade-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setRealFade(Float value) {
        this.realFade = value;
    }

    /**
     * Ruft den Wert der wheel-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWheel() {
        return wheel;
    }

    /**
     * Legt den Wert der wheel-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWheel(String value) {
        this.wheel = value;
    }

    /**
     * Ruft den Wert der emitter-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmitter() {
        return emitter;
    }

    /**
     * Legt den Wert der emitter-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmitter(String value) {
        this.emitter = value;
    }

    /**
     * Ruft den Wert der filter-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Legt den Wert der filter-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilter(String value) {
        this.filter = value;
    }

    /**
     * Ruft den Wert der modeMaster-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeMaster() {
        return modeMaster;
    }

    /**
     * Legt den Wert der modeMaster-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeMaster(String value) {
        this.modeMaster = value;
    }

    /**
     * Ruft den Wert der modeFrom-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeFrom() {
        if (modeFrom == null) {
            return "0/1";
        } else {
            return modeFrom;
        }
    }

    /**
     * Legt den Wert der modeFrom-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeFrom(String value) {
        this.modeFrom = value;
    }

    /**
     * Ruft den Wert der modeTo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeTo() {
        return modeTo;
    }

    /**
     * Legt den Wert der modeTo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeTo(String value) {
        this.modeTo = value;
    }

}

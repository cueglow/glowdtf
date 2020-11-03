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
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}LogicalChannel"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="Name" type="{http://schemas.gdtf-share.com/device}nametype" /&amp;gt;
 *       &amp;lt;attribute name="DMXBreak" type="{http://www.w3.org/2001/XMLSchema}integer" /&amp;gt;
 *       &amp;lt;attribute name="Offset" type="{http://schemas.gdtf-share.com/device}offsettype" /&amp;gt;
 *       &amp;lt;attribute name="Default" type="{http://schemas.gdtf-share.com/device}dmxtype" /&amp;gt;
 *       &amp;lt;attribute name="Highlight" type="{http://schemas.gdtf-share.com/device}dmxtype" /&amp;gt;
 *       &amp;lt;attribute name="Geometry" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "logicalChannel"
})
@XmlRootElement(name = "DMXChannel")
public class DMXChannel {

    @XmlElement(name = "LogicalChannel", required = true)
    protected LogicalChannel logicalChannel;
    @XmlAttribute(name = "Name")
    protected String name;
    @XmlAttribute(name = "DMXBreak")
    protected BigInteger dmxBreak;
    @XmlAttribute(name = "Offset")
    protected String offset;
    @XmlAttribute(name = "Default")
    protected String _default;
    @XmlAttribute(name = "Highlight")
    protected String highlight;
    @XmlAttribute(name = "Geometry")
    protected String geometry;

    /**
     * Ruft den Wert der logicalChannel-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LogicalChannel }
     *     
     */
    public LogicalChannel getLogicalChannel() {
        return logicalChannel;
    }

    /**
     * Legt den Wert der logicalChannel-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LogicalChannel }
     *     
     */
    public void setLogicalChannel(LogicalChannel value) {
        this.logicalChannel = value;
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
     * Ruft den Wert der dmxBreak-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDMXBreak() {
        return dmxBreak;
    }

    /**
     * Legt den Wert der dmxBreak-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDMXBreak(BigInteger value) {
        this.dmxBreak = value;
    }

    /**
     * Ruft den Wert der offset-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOffset() {
        return offset;
    }

    /**
     * Legt den Wert der offset-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOffset(String value) {
        this.offset = value;
    }

    /**
     * Ruft den Wert der default-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefault() {
        return _default;
    }

    /**
     * Legt den Wert der default-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefault(String value) {
        this._default = value;
    }

    /**
     * Ruft den Wert der highlight-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHighlight() {
        return highlight;
    }

    /**
     * Legt den Wert der highlight-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHighlight(String value) {
        this.highlight = value;
    }

    /**
     * Ruft den Wert der geometry-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeometry() {
        return geometry;
    }

    /**
     * Legt den Wert der geometry-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeometry(String value) {
        this.geometry = value;
    }

}

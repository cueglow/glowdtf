//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

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
 *       &amp;lt;attribute name="ColorSpace" type="{http://schemas.gdtf-share.com/device}ColorSpaceEnum" /&amp;gt;
 *       &amp;lt;attribute name="Red" type="{http://schemas.gdtf-share.com/device}colorcietype" /&amp;gt;
 *       &amp;lt;attribute name="Green" type="{http://schemas.gdtf-share.com/device}colorcietype" /&amp;gt;
 *       &amp;lt;attribute name="Blue" type="{http://schemas.gdtf-share.com/device}colorcietype" /&amp;gt;
 *       &amp;lt;attribute name="WhitePoint" type="{http://schemas.gdtf-share.com/device}colorcietype" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "ColorSpace")
public class ColorSpace {

    @XmlAttribute(name = "ColorSpace")
    protected ColorSpaceEnum colorSpace;
    @XmlAttribute(name = "Red")
    protected String red;
    @XmlAttribute(name = "Green")
    protected String green;
    @XmlAttribute(name = "Blue")
    protected String blue;
    @XmlAttribute(name = "WhitePoint")
    protected String whitePoint;

    /**
     * Ruft den Wert der colorSpace-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ColorSpaceEnum }
     *     
     */
    public ColorSpaceEnum getColorSpace() {
        return colorSpace;
    }

    /**
     * Legt den Wert der colorSpace-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ColorSpaceEnum }
     *     
     */
    public void setColorSpace(ColorSpaceEnum value) {
        this.colorSpace = value;
    }

    /**
     * Ruft den Wert der red-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRed() {
        return red;
    }

    /**
     * Legt den Wert der red-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRed(String value) {
        this.red = value;
    }

    /**
     * Ruft den Wert der green-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGreen() {
        return green;
    }

    /**
     * Legt den Wert der green-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGreen(String value) {
        this.green = value;
    }

    /**
     * Ruft den Wert der blue-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlue() {
        return blue;
    }

    /**
     * Legt den Wert der blue-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlue(String value) {
        this.blue = value;
    }

    /**
     * Ruft den Wert der whitePoint-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhitePoint() {
        return whitePoint;
    }

    /**
     * Legt den Wert der whitePoint-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhitePoint(String value) {
        this.whitePoint = value;
    }

}

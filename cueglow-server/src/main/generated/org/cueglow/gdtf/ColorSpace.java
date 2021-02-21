//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.16 at 04:23:25 PM CET 
//


package org.cueglow.gdtf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for ColorSpace complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ColorSpace"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;attribute name="Mode" type="{}ColorSpaceEnum" default="sRGB" /&amp;gt;
 *       &amp;lt;attribute name="Red" type="{}colorcietype" /&amp;gt;
 *       &amp;lt;attribute name="Green" type="{}colorcietype" /&amp;gt;
 *       &amp;lt;attribute name="Blue" type="{}colorcietype" /&amp;gt;
 *       &amp;lt;attribute name="WhitePoint" type="{}colorcietype" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ColorSpace")
public class ColorSpace {

    @XmlAttribute(name = "Mode")
    protected ColorSpaceEnum mode;
    @XmlAttribute(name = "Red")
    protected String red;
    @XmlAttribute(name = "Green")
    protected String green;
    @XmlAttribute(name = "Blue")
    protected String blue;
    @XmlAttribute(name = "WhitePoint")
    protected String whitePoint;

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link ColorSpaceEnum }
     *     
     */
    public ColorSpaceEnum getMode() {
        if (mode == null) {
            return ColorSpaceEnum.S_RGB;
        } else {
            return mode;
        }
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ColorSpaceEnum }
     *     
     */
    public void setMode(ColorSpaceEnum value) {
        this.mode = value;
    }

    /**
     * Gets the value of the red property.
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
     * Sets the value of the red property.
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
     * Gets the value of the green property.
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
     * Sets the value of the green property.
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
     * Gets the value of the blue property.
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
     * Sets the value of the blue property.
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
     * Gets the value of the whitePoint property.
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
     * Sets the value of the whitePoint property.
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
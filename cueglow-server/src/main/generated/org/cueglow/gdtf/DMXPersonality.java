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
 * &lt;p&gt;Java class for DMXPersonality complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="DMXPersonality"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;attribute name="Value" type="{}hextype" /&amp;gt;
 *       &amp;lt;attribute name="DMXMode" type="{}nametype" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DMXPersonality")
public class DMXPersonality {

    @XmlAttribute(name = "Value")
    protected String value;
    @XmlAttribute(name = "DMXMode")
    protected String dmxMode;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the dmxMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDMXMode() {
        return dmxMode;
    }

    /**
     * Sets the value of the dmxMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDMXMode(String value) {
        this.dmxMode = value;
    }

}
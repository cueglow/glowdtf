//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.15 at 11:52:05 PM CET 
//


package com.gdtf_share.schemas.device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="FixtureType" type="{}FixtureType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="DataVersion" use="required" type="{}dataversion" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fixtureType"
})
@XmlRootElement(name = "GDTF")
public class GDTF {

    @XmlElement(name = "FixtureType", required = true)
    protected FixtureType fixtureType;
    @XmlAttribute(name = "DataVersion", required = true)
    protected float dataVersion;

    /**
     * Gets the value of the fixtureType property.
     * 
     * @return
     *     possible object is
     *     {@link FixtureType }
     *     
     */
    public FixtureType getFixtureType() {
        return fixtureType;
    }

    /**
     * Sets the value of the fixtureType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FixtureType }
     *     
     */
    public void setFixtureType(FixtureType value) {
        this.fixtureType = value;
    }

    /**
     * Gets the value of the dataVersion property.
     * 
     */
    public float getDataVersion() {
        return dataVersion;
    }

    /**
     * Sets the value of the dataVersion property.
     * 
     */
    public void setDataVersion(float value) {
        this.dataVersion = value;
    }

}

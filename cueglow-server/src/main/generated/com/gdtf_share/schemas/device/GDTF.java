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
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FixtureType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="DataVersion" use="required" type="{http://schemas.gdtf-share.com/device}dataversion" /&amp;gt;
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
     * Ruft den Wert der fixtureType-Eigenschaft ab.
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
     * Legt den Wert der fixtureType-Eigenschaft fest.
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
     * Ruft den Wert der dataVersion-Eigenschaft ab.
     * 
     */
    public float getDataVersion() {
        return dataVersion;
    }

    /**
     * Legt den Wert der dataVersion-Eigenschaft fest.
     * 
     */
    public void setDataVersion(float value) {
        this.dataVersion = value;
    }

}

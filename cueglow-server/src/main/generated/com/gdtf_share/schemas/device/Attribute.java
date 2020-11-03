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
 *       &amp;lt;attribute name="Name" use="required" type="{http://schemas.gdtf-share.com/device}AttributeEnum" /&amp;gt;
 *       &amp;lt;attribute name="Pretty" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="ActivationGroup" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="PhysicalUnit" type="{http://schemas.gdtf-share.com/device}PhysicalUnitEnum" /&amp;gt;
 *       &amp;lt;attribute name="Color" type="{http://schemas.gdtf-share.com/device}colorcietype" /&amp;gt;
 *       &amp;lt;attribute name="Feature" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="MainAttribute" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Attribute")
public class Attribute {

    @XmlAttribute(name = "Name", required = true)
    protected AttributeEnum name;
    @XmlAttribute(name = "Pretty", required = true)
    protected String pretty;
    @XmlAttribute(name = "ActivationGroup")
    protected String activationGroup;
    @XmlAttribute(name = "PhysicalUnit")
    protected PhysicalUnitEnum physicalUnit;
    @XmlAttribute(name = "Color")
    protected String color;
    @XmlAttribute(name = "Feature")
    protected String feature;
    @XmlAttribute(name = "MainAttribute")
    protected String mainAttribute;

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AttributeEnum }
     *     
     */
    public AttributeEnum getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeEnum }
     *     
     */
    public void setName(AttributeEnum value) {
        this.name = value;
    }

    /**
     * Ruft den Wert der pretty-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPretty() {
        return pretty;
    }

    /**
     * Legt den Wert der pretty-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPretty(String value) {
        this.pretty = value;
    }

    /**
     * Ruft den Wert der activationGroup-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivationGroup() {
        return activationGroup;
    }

    /**
     * Legt den Wert der activationGroup-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivationGroup(String value) {
        this.activationGroup = value;
    }

    /**
     * Ruft den Wert der physicalUnit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PhysicalUnitEnum }
     *     
     */
    public PhysicalUnitEnum getPhysicalUnit() {
        return physicalUnit;
    }

    /**
     * Legt den Wert der physicalUnit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicalUnitEnum }
     *     
     */
    public void setPhysicalUnit(PhysicalUnitEnum value) {
        this.physicalUnit = value;
    }

    /**
     * Ruft den Wert der color-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * Legt den Wert der color-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * Ruft den Wert der feature-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeature() {
        return feature;
    }

    /**
     * Legt den Wert der feature-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeature(String value) {
        this.feature = value;
    }

    /**
     * Ruft den Wert der mainAttribute-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainAttribute() {
        return mainAttribute;
    }

    /**
     * Legt den Wert der mainAttribute-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainAttribute(String value) {
        this.mainAttribute = value;
    }

}

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
import javax.xml.bind.annotation.XmlElements;
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
 *       &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}AttributeDefinitions"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Wheels"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}PhysicalDescriptions"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Models"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Geometries"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}DMXModes"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Revisions"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FTPresets"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Protocols"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="Name" use="required" type="{http://schemas.gdtf-share.com/device}nametype" /&amp;gt;
 *       &amp;lt;attribute name="ShortName" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="LongName" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="Manufacturer" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="FixtureTypeID" use="required" type="{http://schemas.gdtf-share.com/device}guidtype" /&amp;gt;
 *       &amp;lt;attribute name="Thumbnail" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="RefFT" type="{http://schemas.gdtf-share.com/device}guidtype" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "attributeDefinitionsOrWheelsOrPhysicalDescriptions"
})
@XmlRootElement(name = "FixtureType")
public class FixtureType {

    @XmlElements({
        @XmlElement(name = "AttributeDefinitions", type = AttributeDefinitions.class),
        @XmlElement(name = "Wheels", type = Wheels.class),
        @XmlElement(name = "PhysicalDescriptions", type = PhysicalDescriptions.class),
        @XmlElement(name = "Models", type = Models.class),
        @XmlElement(name = "Geometries", type = Geometries.class),
        @XmlElement(name = "DMXModes", type = DMXModes.class),
        @XmlElement(name = "Revisions", type = Revisions.class),
        @XmlElement(name = "FTPresets", type = FTPresets.class),
        @XmlElement(name = "Protocols", type = Protocols.class)
    })
    protected List<Object> attributeDefinitionsOrWheelsOrPhysicalDescriptions;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "ShortName")
    protected String shortName;
    @XmlAttribute(name = "LongName")
    protected String longName;
    @XmlAttribute(name = "Manufacturer", required = true)
    protected String manufacturer;
    @XmlAttribute(name = "Description", required = true)
    protected String description;
    @XmlAttribute(name = "FixtureTypeID", required = true)
    protected String fixtureTypeID;
    @XmlAttribute(name = "Thumbnail")
    protected String thumbnail;
    @XmlAttribute(name = "RefFT")
    protected String refFT;

    /**
     * Gets the value of the attributeDefinitionsOrWheelsOrPhysicalDescriptions property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the attributeDefinitionsOrWheelsOrPhysicalDescriptions property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAttributeDefinitionsOrWheelsOrPhysicalDescriptions().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeDefinitions }
     * {@link Wheels }
     * {@link PhysicalDescriptions }
     * {@link Models }
     * {@link Geometries }
     * {@link DMXModes }
     * {@link Revisions }
     * {@link FTPresets }
     * {@link Protocols }
     * 
     * 
     */
    public List<Object> getAttributeDefinitionsOrWheelsOrPhysicalDescriptions() {
        if (attributeDefinitionsOrWheelsOrPhysicalDescriptions == null) {
            attributeDefinitionsOrWheelsOrPhysicalDescriptions = new ArrayList<Object>();
        }
        return this.attributeDefinitionsOrWheelsOrPhysicalDescriptions;
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
     * Ruft den Wert der shortName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Legt den Wert der shortName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortName(String value) {
        this.shortName = value;
    }

    /**
     * Ruft den Wert der longName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Legt den Wert der longName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongName(String value) {
        this.longName = value;
    }

    /**
     * Ruft den Wert der manufacturer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Legt den Wert der manufacturer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturer(String value) {
        this.manufacturer = value;
    }

    /**
     * Ruft den Wert der description-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Legt den Wert der description-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Ruft den Wert der fixtureTypeID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFixtureTypeID() {
        return fixtureTypeID;
    }

    /**
     * Legt den Wert der fixtureTypeID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFixtureTypeID(String value) {
        this.fixtureTypeID = value;
    }

    /**
     * Ruft den Wert der thumbnail-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Legt den Wert der thumbnail-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumbnail(String value) {
        this.thumbnail = value;
    }

    /**
     * Ruft den Wert der refFT-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefFT() {
        return refFT;
    }

    /**
     * Legt den Wert der refFT-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefFT(String value) {
        this.refFT = value;
    }

}

//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import java.math.BigInteger;
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
 *       &amp;lt;sequence maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Geometry" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Axis" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FilterBeam" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FilterColor" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FilterGobo" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FilterShaper" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Beam" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}GeometryReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="Name" use="required" type="{http://schemas.gdtf-share.com/device}nametype" /&amp;gt;
 *       &amp;lt;attribute name="Model" use="required" type="{http://schemas.gdtf-share.com/device}nametype" /&amp;gt;
 *       &amp;lt;attribute name="Position" use="required" type="{http://schemas.gdtf-share.com/device}matrixtype" /&amp;gt;
 *       &amp;lt;attribute name="LampType" type="{http://schemas.gdtf-share.com/device}LampTypeEnum" /&amp;gt;
 *       &amp;lt;attribute name="PowerConsumption" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="LuminousFlux" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="ColorTemperature" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="BeamAngle" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="FieldAngle" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="BeamRadius" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="BeamType" type="{http://schemas.gdtf-share.com/device}BeamTypeEnum" /&amp;gt;
 *       &amp;lt;attribute name="ColorRenderingIndex" type="{http://www.w3.org/2001/XMLSchema}integer" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "geometryAndAxisAndFilterBeam"
})
@XmlRootElement(name = "Beam")
public class Beam {

    @XmlElements({
        @XmlElement(name = "Geometry", type = Geometry.class),
        @XmlElement(name = "Axis", type = Axis.class),
        @XmlElement(name = "FilterBeam", type = FilterBeam.class),
        @XmlElement(name = "FilterColor", type = FilterColor.class),
        @XmlElement(name = "FilterGobo", type = FilterGobo.class),
        @XmlElement(name = "FilterShaper", type = FilterShaper.class),
        @XmlElement(name = "Beam", type = Beam.class),
        @XmlElement(name = "GeometryReference", type = GeometryReference.class)
    })
    protected List<Object> geometryAndAxisAndFilterBeam;
    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "Model", required = true)
    protected String model;
    @XmlAttribute(name = "Position", required = true)
    protected String position;
    @XmlAttribute(name = "LampType")
    protected LampTypeEnum lampType;
    @XmlAttribute(name = "PowerConsumption")
    protected Float powerConsumption;
    @XmlAttribute(name = "LuminousFlux")
    protected Float luminousFlux;
    @XmlAttribute(name = "ColorTemperature")
    protected Float colorTemperature;
    @XmlAttribute(name = "BeamAngle")
    protected Float beamAngle;
    @XmlAttribute(name = "FieldAngle")
    protected Float fieldAngle;
    @XmlAttribute(name = "BeamRadius")
    protected Float beamRadius;
    @XmlAttribute(name = "BeamType")
    protected BeamTypeEnum beamType;
    @XmlAttribute(name = "ColorRenderingIndex")
    protected BigInteger colorRenderingIndex;

    /**
     * Gets the value of the geometryAndAxisAndFilterBeam property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the geometryAndAxisAndFilterBeam property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGeometryAndAxisAndFilterBeam().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link Geometry }
     * {@link Axis }
     * {@link FilterBeam }
     * {@link FilterColor }
     * {@link FilterGobo }
     * {@link FilterShaper }
     * {@link Beam }
     * {@link GeometryReference }
     * 
     * 
     */
    public List<Object> getGeometryAndAxisAndFilterBeam() {
        if (geometryAndAxisAndFilterBeam == null) {
            geometryAndAxisAndFilterBeam = new ArrayList<Object>();
        }
        return this.geometryAndAxisAndFilterBeam;
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
     * Ruft den Wert der model-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Legt den Wert der model-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Ruft den Wert der position-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Legt den Wert der position-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
    }

    /**
     * Ruft den Wert der lampType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LampTypeEnum }
     *     
     */
    public LampTypeEnum getLampType() {
        return lampType;
    }

    /**
     * Legt den Wert der lampType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LampTypeEnum }
     *     
     */
    public void setLampType(LampTypeEnum value) {
        this.lampType = value;
    }

    /**
     * Ruft den Wert der powerConsumption-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPowerConsumption() {
        return powerConsumption;
    }

    /**
     * Legt den Wert der powerConsumption-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPowerConsumption(Float value) {
        this.powerConsumption = value;
    }

    /**
     * Ruft den Wert der luminousFlux-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getLuminousFlux() {
        return luminousFlux;
    }

    /**
     * Legt den Wert der luminousFlux-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setLuminousFlux(Float value) {
        this.luminousFlux = value;
    }

    /**
     * Ruft den Wert der colorTemperature-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getColorTemperature() {
        return colorTemperature;
    }

    /**
     * Legt den Wert der colorTemperature-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setColorTemperature(Float value) {
        this.colorTemperature = value;
    }

    /**
     * Ruft den Wert der beamAngle-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getBeamAngle() {
        return beamAngle;
    }

    /**
     * Legt den Wert der beamAngle-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setBeamAngle(Float value) {
        this.beamAngle = value;
    }

    /**
     * Ruft den Wert der fieldAngle-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFieldAngle() {
        return fieldAngle;
    }

    /**
     * Legt den Wert der fieldAngle-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFieldAngle(Float value) {
        this.fieldAngle = value;
    }

    /**
     * Ruft den Wert der beamRadius-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getBeamRadius() {
        return beamRadius;
    }

    /**
     * Legt den Wert der beamRadius-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setBeamRadius(Float value) {
        this.beamRadius = value;
    }

    /**
     * Ruft den Wert der beamType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BeamTypeEnum }
     *     
     */
    public BeamTypeEnum getBeamType() {
        return beamType;
    }

    /**
     * Legt den Wert der beamType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BeamTypeEnum }
     *     
     */
    public void setBeamType(BeamTypeEnum value) {
        this.beamType = value;
    }

    /**
     * Ruft den Wert der colorRenderingIndex-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getColorRenderingIndex() {
        return colorRenderingIndex;
    }

    /**
     * Legt den Wert der colorRenderingIndex-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setColorRenderingIndex(BigInteger value) {
        this.colorRenderingIndex = value;
    }

}

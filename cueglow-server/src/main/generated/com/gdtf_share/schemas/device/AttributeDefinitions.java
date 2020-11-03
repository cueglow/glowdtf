//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}ActivationGroups" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}FeatureGroups"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Attributes"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "activationGroups",
    "featureGroups",
    "attributes"
})
@XmlRootElement(name = "AttributeDefinitions")
public class AttributeDefinitions {

    @XmlElement(name = "ActivationGroups")
    protected ActivationGroups activationGroups;
    @XmlElement(name = "FeatureGroups", required = true)
    protected FeatureGroups featureGroups;
    @XmlElement(name = "Attributes", required = true)
    protected Attributes attributes;

    /**
     * Ruft den Wert der activationGroups-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ActivationGroups }
     *     
     */
    public ActivationGroups getActivationGroups() {
        return activationGroups;
    }

    /**
     * Legt den Wert der activationGroups-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivationGroups }
     *     
     */
    public void setActivationGroups(ActivationGroups value) {
        this.activationGroups = value;
    }

    /**
     * Ruft den Wert der featureGroups-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link FeatureGroups }
     *     
     */
    public FeatureGroups getFeatureGroups() {
        return featureGroups;
    }

    /**
     * Legt den Wert der featureGroups-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link FeatureGroups }
     *     
     */
    public void setFeatureGroups(FeatureGroups value) {
        this.featureGroups = value;
    }

    /**
     * Ruft den Wert der attributes-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Attributes }
     *     
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * Legt den Wert der attributes-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributes }
     *     
     */
    public void setAttributes(Attributes value) {
        this.attributes = value;
    }

}

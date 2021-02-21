//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.16 at 04:23:25 PM CET 
//


package org.cueglow.gdtf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for AttributeDefinitions complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="AttributeDefinitions"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;all&amp;gt;
 *         &amp;lt;element name="ActivationGroups" type="{}ActivationGroups" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="FeatureGroups" type="{}FeatureGroups"/&amp;gt;
 *         &amp;lt;element name="Attributes" type="{}Attributes"/&amp;gt;
 *       &amp;lt;/all&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeDefinitions", propOrder = {

})
public class AttributeDefinitions {

    @XmlElement(name = "ActivationGroups")
    protected ActivationGroups activationGroups;
    @XmlElement(name = "FeatureGroups", required = true)
    protected FeatureGroups featureGroups;
    @XmlElement(name = "Attributes", required = true)
    protected Attributes attributes;

    /**
     * Gets the value of the activationGroups property.
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
     * Sets the value of the activationGroups property.
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
     * Gets the value of the featureGroups property.
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
     * Sets the value of the featureGroups property.
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
     * Gets the value of the attributes property.
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
     * Sets the value of the attributes property.
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
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
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for Relation complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Relation"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="Master" use="required" type="{}nodetype" /&amp;gt;
 *       &amp;lt;attribute name="Follower" use="required" type="{}nodetype" /&amp;gt;
 *       &amp;lt;attribute name="Type" use="required" type="{}RelationTypesEnum" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Relation")
public class Relation {

    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "Master", required = true)
    protected String master;
    @XmlAttribute(name = "Follower", required = true)
    protected String follower;
    @XmlAttribute(name = "Type", required = true)
    protected RelationTypesEnum type;

    /**
     * Gets the value of the name property.
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
     * Sets the value of the name property.
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
     * Gets the value of the master property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaster() {
        return master;
    }

    /**
     * Sets the value of the master property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaster(String value) {
        this.master = value;
    }

    /**
     * Gets the value of the follower property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFollower() {
        return follower;
    }

    /**
     * Sets the value of the follower property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFollower(String value) {
        this.follower = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link RelationTypesEnum }
     *     
     */
    public RelationTypesEnum getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationTypesEnum }
     *     
     */
    public void setType(RelationTypesEnum value) {
        this.type = value;
    }

}

//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.15 at 11:52:05 PM CET 
//


package com.gdtf_share.schemas.device;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for LogicalChannel complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="LogicalChannel"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *         &amp;lt;element name="ChannelFunction" type="{}ChannelFunction"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="Attribute" use="required" type="{}nametype" /&amp;gt;
 *       &amp;lt;attribute name="Snap" type="{}SnapEnum" default="No" /&amp;gt;
 *       &amp;lt;attribute name="Master" type="{}MasterEnum" default="None" /&amp;gt;
 *       &amp;lt;attribute name="MibFade" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *       &amp;lt;attribute name="DMXChangeTimeLimit" type="{http://www.w3.org/2001/XMLSchema}float" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogicalChannel", propOrder = {
    "channelFunction"
})
public class LogicalChannel {

    @XmlElement(name = "ChannelFunction")
    protected List<ChannelFunction> channelFunction;
    @XmlAttribute(name = "Attribute", required = true)
    protected String attribute;
    @XmlAttribute(name = "Snap")
    protected SnapEnum snap;
    @XmlAttribute(name = "Master")
    protected MasterEnum master;
    @XmlAttribute(name = "MibFade")
    protected Float mibFade;
    @XmlAttribute(name = "DMXChangeTimeLimit")
    protected Float dmxChangeTimeLimit;

    /**
     * Gets the value of the channelFunction property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the channelFunction property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getChannelFunction().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ChannelFunction }
     * 
     * 
     */
    public List<ChannelFunction> getChannelFunction() {
        if (channelFunction == null) {
            channelFunction = new ArrayList<ChannelFunction>();
        }
        return this.channelFunction;
    }

    /**
     * Gets the value of the attribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Sets the value of the attribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute(String value) {
        this.attribute = value;
    }

    /**
     * Gets the value of the snap property.
     * 
     * @return
     *     possible object is
     *     {@link SnapEnum }
     *     
     */
    public SnapEnum getSnap() {
        if (snap == null) {
            return SnapEnum.NO;
        } else {
            return snap;
        }
    }

    /**
     * Sets the value of the snap property.
     * 
     * @param value
     *     allowed object is
     *     {@link SnapEnum }
     *     
     */
    public void setSnap(SnapEnum value) {
        this.snap = value;
    }

    /**
     * Gets the value of the master property.
     * 
     * @return
     *     possible object is
     *     {@link MasterEnum }
     *     
     */
    public MasterEnum getMaster() {
        if (master == null) {
            return MasterEnum.NONE;
        } else {
            return master;
        }
    }

    /**
     * Sets the value of the master property.
     * 
     * @param value
     *     allowed object is
     *     {@link MasterEnum }
     *     
     */
    public void setMaster(MasterEnum value) {
        this.master = value;
    }

    /**
     * Gets the value of the mibFade property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getMibFade() {
        return mibFade;
    }

    /**
     * Sets the value of the mibFade property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setMibFade(Float value) {
        this.mibFade = value;
    }

    /**
     * Gets the value of the dmxChangeTimeLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getDMXChangeTimeLimit() {
        return dmxChangeTimeLimit;
    }

    /**
     * Sets the value of the dmxChangeTimeLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setDMXChangeTimeLimit(Float value) {
        this.dmxChangeTimeLimit = value;
    }

}

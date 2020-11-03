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
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}ChannelFunction"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="Name" type="{http://schemas.gdtf-share.com/device}nametype" /&amp;gt;
 *       &amp;lt;attribute name="Attribute" use="required" type="{http://schemas.gdtf-share.com/device}AttributeEnum" /&amp;gt;
 *       &amp;lt;attribute name="Snap" type="{http://schemas.gdtf-share.com/device}SnapEnum" /&amp;gt;
 *       &amp;lt;attribute name="Master" type="{http://schemas.gdtf-share.com/device}MasterEnum" /&amp;gt;
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
@XmlType(name = "", propOrder = {
    "channelFunction"
})
@XmlRootElement(name = "LogicalChannel")
public class LogicalChannel {

    @XmlElement(name = "ChannelFunction")
    protected List<ChannelFunction> channelFunction;
    @XmlAttribute(name = "Name")
    protected String name;
    @XmlAttribute(name = "Attribute", required = true)
    protected AttributeEnum attribute;
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
     * Ruft den Wert der attribute-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AttributeEnum }
     *     
     */
    public AttributeEnum getAttribute() {
        return attribute;
    }

    /**
     * Legt den Wert der attribute-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeEnum }
     *     
     */
    public void setAttribute(AttributeEnum value) {
        this.attribute = value;
    }

    /**
     * Ruft den Wert der snap-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SnapEnum }
     *     
     */
    public SnapEnum getSnap() {
        return snap;
    }

    /**
     * Legt den Wert der snap-Eigenschaft fest.
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
     * Ruft den Wert der master-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MasterEnum }
     *     
     */
    public MasterEnum getMaster() {
        return master;
    }

    /**
     * Legt den Wert der master-Eigenschaft fest.
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
     * Ruft den Wert der mibFade-Eigenschaft ab.
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
     * Legt den Wert der mibFade-Eigenschaft fest.
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
     * Ruft den Wert der dmxChangeTimeLimit-Eigenschaft ab.
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
     * Legt den Wert der dmxChangeTimeLimit-Eigenschaft fest.
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

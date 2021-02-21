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
 * &lt;p&gt;Java class for Protocols complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="Protocols"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;all&amp;gt;
 *         &amp;lt;element name="FTRDM" type="{}FTRDM" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Art-Net" type="{}Art-Net" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="sACN" type="{}sACN" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="PosiStageNet" type="{}PosiStageNet" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="OpenSoundControl" type="{}OpenSoundControl" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="CITP" type="{}CITP" minOccurs="0"/&amp;gt;
 *       &amp;lt;/all&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Protocols", propOrder = {

})
public class Protocols {

    @XmlElement(name = "FTRDM")
    protected FTRDM ftrdm;
    @XmlElement(name = "Art-Net")
    protected ArtNet artNet;
    @XmlElement(name = "sACN")
    protected SACN sacn;
    @XmlElement(name = "PosiStageNet")
    protected PosiStageNet posiStageNet;
    @XmlElement(name = "OpenSoundControl")
    protected OpenSoundControl openSoundControl;
    @XmlElement(name = "CITP")
    protected CITP citp;

    /**
     * Gets the value of the ftrdm property.
     * 
     * @return
     *     possible object is
     *     {@link FTRDM }
     *     
     */
    public FTRDM getFTRDM() {
        return ftrdm;
    }

    /**
     * Sets the value of the ftrdm property.
     * 
     * @param value
     *     allowed object is
     *     {@link FTRDM }
     *     
     */
    public void setFTRDM(FTRDM value) {
        this.ftrdm = value;
    }

    /**
     * Gets the value of the artNet property.
     * 
     * @return
     *     possible object is
     *     {@link ArtNet }
     *     
     */
    public ArtNet getArtNet() {
        return artNet;
    }

    /**
     * Sets the value of the artNet property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArtNet }
     *     
     */
    public void setArtNet(ArtNet value) {
        this.artNet = value;
    }

    /**
     * Gets the value of the sacn property.
     * 
     * @return
     *     possible object is
     *     {@link SACN }
     *     
     */
    public SACN getSACN() {
        return sacn;
    }

    /**
     * Sets the value of the sacn property.
     * 
     * @param value
     *     allowed object is
     *     {@link SACN }
     *     
     */
    public void setSACN(SACN value) {
        this.sacn = value;
    }

    /**
     * Gets the value of the posiStageNet property.
     * 
     * @return
     *     possible object is
     *     {@link PosiStageNet }
     *     
     */
    public PosiStageNet getPosiStageNet() {
        return posiStageNet;
    }

    /**
     * Sets the value of the posiStageNet property.
     * 
     * @param value
     *     allowed object is
     *     {@link PosiStageNet }
     *     
     */
    public void setPosiStageNet(PosiStageNet value) {
        this.posiStageNet = value;
    }

    /**
     * Gets the value of the openSoundControl property.
     * 
     * @return
     *     possible object is
     *     {@link OpenSoundControl }
     *     
     */
    public OpenSoundControl getOpenSoundControl() {
        return openSoundControl;
    }

    /**
     * Sets the value of the openSoundControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link OpenSoundControl }
     *     
     */
    public void setOpenSoundControl(OpenSoundControl value) {
        this.openSoundControl = value;
    }

    /**
     * Gets the value of the citp property.
     * 
     * @return
     *     possible object is
     *     {@link CITP }
     *     
     */
    public CITP getCITP() {
        return citp;
    }

    /**
     * Sets the value of the citp property.
     * 
     * @param value
     *     allowed object is
     *     {@link CITP }
     *     
     */
    public void setCITP(CITP value) {
        this.citp = value;
    }

}
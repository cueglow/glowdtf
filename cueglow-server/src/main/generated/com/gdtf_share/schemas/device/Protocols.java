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
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}RDM" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Art-Net" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}sACN" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}KiNET" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}PosiStageNet" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}OpenSoundControl" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}CITP" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
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
    "rdm",
    "artNet",
    "sacn",
    "kiNET",
    "posiStageNet",
    "openSoundControl",
    "citp"
})
@XmlRootElement(name = "Protocols")
public class Protocols {

    @XmlElement(name = "RDM")
    protected List<RDM> rdm;
    @XmlElement(name = "Art-Net")
    protected List<ArtNet> artNet;
    @XmlElement(name = "sACN")
    protected List<SACN> sacn;
    @XmlElement(name = "KiNET")
    protected List<KiNET> kiNET;
    @XmlElement(name = "PosiStageNet")
    protected List<PosiStageNet> posiStageNet;
    @XmlElement(name = "OpenSoundControl")
    protected List<OpenSoundControl> openSoundControl;
    @XmlElement(name = "CITP")
    protected List<CITP> citp;

    /**
     * Gets the value of the rdm property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the rdm property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRDM().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RDM }
     * 
     * 
     */
    public List<RDM> getRDM() {
        if (rdm == null) {
            rdm = new ArrayList<RDM>();
        }
        return this.rdm;
    }

    /**
     * Gets the value of the artNet property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the artNet property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getArtNet().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ArtNet }
     * 
     * 
     */
    public List<ArtNet> getArtNet() {
        if (artNet == null) {
            artNet = new ArrayList<ArtNet>();
        }
        return this.artNet;
    }

    /**
     * Gets the value of the sacn property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the sacn property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getSACN().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link SACN }
     * 
     * 
     */
    public List<SACN> getSACN() {
        if (sacn == null) {
            sacn = new ArrayList<SACN>();
        }
        return this.sacn;
    }

    /**
     * Gets the value of the kiNET property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the kiNET property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getKiNET().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link KiNET }
     * 
     * 
     */
    public List<KiNET> getKiNET() {
        if (kiNET == null) {
            kiNET = new ArrayList<KiNET>();
        }
        return this.kiNET;
    }

    /**
     * Gets the value of the posiStageNet property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the posiStageNet property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPosiStageNet().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PosiStageNet }
     * 
     * 
     */
    public List<PosiStageNet> getPosiStageNet() {
        if (posiStageNet == null) {
            posiStageNet = new ArrayList<PosiStageNet>();
        }
        return this.posiStageNet;
    }

    /**
     * Gets the value of the openSoundControl property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the openSoundControl property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getOpenSoundControl().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link OpenSoundControl }
     * 
     * 
     */
    public List<OpenSoundControl> getOpenSoundControl() {
        if (openSoundControl == null) {
            openSoundControl = new ArrayList<OpenSoundControl>();
        }
        return this.openSoundControl;
    }

    /**
     * Gets the value of the citp property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the citp property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCITP().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CITP }
     * 
     * 
     */
    public List<CITP> getCITP() {
        if (citp == null) {
            citp = new ArrayList<CITP>();
        }
        return this.citp;
    }

}

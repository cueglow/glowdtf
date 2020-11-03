//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import java.math.BigInteger;
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
 *       &amp;lt;attribute name="CES" type="{http://schemas.gdtf-share.com/device}CESEnum" /&amp;gt;
 *       &amp;lt;attribute name="ColorRenderingIndex" type="{http://www.w3.org/2001/XMLSchema}integer" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "CRI")
public class CRI {

    @XmlAttribute(name = "CES")
    protected CESEnum ces;
    @XmlAttribute(name = "ColorRenderingIndex")
    protected BigInteger colorRenderingIndex;

    /**
     * Ruft den Wert der ces-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CESEnum }
     *     
     */
    public CESEnum getCES() {
        return ces;
    }

    /**
     * Legt den Wert der ces-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CESEnum }
     *     
     */
    public void setCES(CESEnum value) {
        this.ces = value;
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

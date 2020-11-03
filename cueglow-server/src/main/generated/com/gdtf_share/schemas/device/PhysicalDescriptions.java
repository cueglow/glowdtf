//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java-Klasse f�r anonymous complex type.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Emitters"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}Filters"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}ColorSpace"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}DMXProfiles"/&amp;gt;
 *         &amp;lt;element ref="{http://schemas.gdtf-share.com/device}CRIs"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "emittersOrFiltersOrColorSpace"
})
@XmlRootElement(name = "PhysicalDescriptions")
public class PhysicalDescriptions {

    @XmlElements({
        @XmlElement(name = "Emitters", type = Emitters.class),
        @XmlElement(name = "Filters", type = Filters.class),
        @XmlElement(name = "ColorSpace", type = ColorSpace.class),
        @XmlElement(name = "DMXProfiles", type = DMXProfiles.class),
        @XmlElement(name = "CRIs", type = CRIs.class)
    })
    protected List<Object> emittersOrFiltersOrColorSpace;

    /**
     * Gets the value of the emittersOrFiltersOrColorSpace property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the emittersOrFiltersOrColorSpace property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getEmittersOrFiltersOrColorSpace().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link Emitters }
     * {@link Filters }
     * {@link ColorSpace }
     * {@link DMXProfiles }
     * {@link CRIs }
     * 
     * 
     */
    public List<Object> getEmittersOrFiltersOrColorSpace() {
        if (emittersOrFiltersOrColorSpace == null) {
            emittersOrFiltersOrColorSpace = new ArrayList<Object>();
        }
        return this.emittersOrFiltersOrColorSpace;
    }

}

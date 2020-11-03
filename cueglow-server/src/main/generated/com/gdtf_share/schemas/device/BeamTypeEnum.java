//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java-Klasse für BeamTypeEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="BeamTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Wash"/&amp;gt;
 *     &amp;lt;enumeration value="Spot"/&amp;gt;
 *     &amp;lt;enumeration value="None"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "BeamTypeEnum")
@XmlEnum
public enum BeamTypeEnum {

    @XmlEnumValue("Wash")
    WASH("Wash"),
    @XmlEnumValue("Spot")
    SPOT("Spot"),
    @XmlEnumValue("None")
    NONE("None");
    private final String value;

    BeamTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BeamTypeEnum fromValue(String v) {
        for (BeamTypeEnum c: BeamTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

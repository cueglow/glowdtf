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
 * &lt;p&gt;Java-Klasse für InterpolationToEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="InterpolationToEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Linear"/&amp;gt;
 *     &amp;lt;enumeration value="Step"/&amp;gt;
 *     &amp;lt;enumeration value="Log"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "InterpolationToEnum")
@XmlEnum
public enum InterpolationToEnum {

    @XmlEnumValue("Linear")
    LINEAR("Linear"),
    @XmlEnumValue("Step")
    STEP("Step"),
    @XmlEnumValue("Log")
    LOG("Log");
    private final String value;

    InterpolationToEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InterpolationToEnum fromValue(String v) {
        for (InterpolationToEnum c: InterpolationToEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

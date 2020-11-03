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
 * &lt;p&gt;Java-Klasse für ThresholdOperatorEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="ThresholdOperatorEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Is"/&amp;gt;
 *     &amp;lt;enumeration value="IsNot"/&amp;gt;
 *     &amp;lt;enumeration value="Greater"/&amp;gt;
 *     &amp;lt;enumeration value="Less"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "ThresholdOperatorEnum")
@XmlEnum
public enum ThresholdOperatorEnum {

    @XmlEnumValue("Is")
    IS("Is"),
    @XmlEnumValue("IsNot")
    IS_NOT("IsNot"),
    @XmlEnumValue("Greater")
    GREATER("Greater"),
    @XmlEnumValue("Less")
    LESS("Less");
    private final String value;

    ThresholdOperatorEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ThresholdOperatorEnum fromValue(String v) {
        for (ThresholdOperatorEnum c: ThresholdOperatorEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

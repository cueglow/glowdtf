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
 * &lt;p&gt;Java-Klasse für RDMParameterCommandEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="RDMParameterCommandEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="None"/&amp;gt;
 *     &amp;lt;enumeration value="CC_GET"/&amp;gt;
 *     &amp;lt;enumeration value="CC_SET"/&amp;gt;
 *     &amp;lt;enumeration value="CC_GET_SET"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "RDMParameterCommandEnum")
@XmlEnum
public enum RDMParameterCommandEnum {

    @XmlEnumValue("None")
    NONE("None"),
    CC_GET("CC_GET"),
    CC_SET("CC_SET"),
    CC_GET_SET("CC_GET_SET");
    private final String value;

    RDMParameterCommandEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RDMParameterCommandEnum fromValue(String v) {
        for (RDMParameterCommandEnum c: RDMParameterCommandEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

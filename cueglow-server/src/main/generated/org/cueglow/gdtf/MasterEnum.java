//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.16 at 04:23:25 PM CET 
//


package org.cueglow.gdtf;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for MasterEnum.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="MasterEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="None"/&amp;gt;
 *     &amp;lt;enumeration value="Grand"/&amp;gt;
 *     &amp;lt;enumeration value="Group"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "MasterEnum")
@XmlEnum
public enum MasterEnum {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Grand")
    GRAND("Grand"),
    @XmlEnumValue("Group")
    GROUP("Group");
    private final String value;

    MasterEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MasterEnum fromValue(String v) {
        for (MasterEnum c: MasterEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
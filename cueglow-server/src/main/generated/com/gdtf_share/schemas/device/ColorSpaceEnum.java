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
 * &lt;p&gt;Java-Klasse für ColorSpaceEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="ColorSpaceEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Custom"/&amp;gt;
 *     &amp;lt;enumeration value="sRGB"/&amp;gt;
 *     &amp;lt;enumeration value="ProPhoto"/&amp;gt;
 *     &amp;lt;enumeration value="ANSI"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "ColorSpaceEnum")
@XmlEnum
public enum ColorSpaceEnum {

    @XmlEnumValue("Custom")
    CUSTOM("Custom"),
    @XmlEnumValue("sRGB")
    S_RGB("sRGB"),
    @XmlEnumValue("ProPhoto")
    PRO_PHOTO("ProPhoto"),
    ANSI("ANSI");
    private final String value;

    ColorSpaceEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ColorSpaceEnum fromValue(String v) {
        for (ColorSpaceEnum c: ColorSpaceEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

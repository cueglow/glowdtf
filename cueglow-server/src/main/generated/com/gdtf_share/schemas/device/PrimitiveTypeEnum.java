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
 * &lt;p&gt;Java-Klasse für PrimitiveTypeEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="PrimitiveTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="Undefined"/&amp;gt;
 *     &amp;lt;enumeration value="Cube"/&amp;gt;
 *     &amp;lt;enumeration value="Cylinder"/&amp;gt;
 *     &amp;lt;enumeration value="Sphere"/&amp;gt;
 *     &amp;lt;enumeration value="Base"/&amp;gt;
 *     &amp;lt;enumeration value="Yoke"/&amp;gt;
 *     &amp;lt;enumeration value="Head"/&amp;gt;
 *     &amp;lt;enumeration value="Scanner"/&amp;gt;
 *     &amp;lt;enumeration value="Conventional"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "PrimitiveTypeEnum")
@XmlEnum
public enum PrimitiveTypeEnum {

    @XmlEnumValue("Undefined")
    UNDEFINED("Undefined"),
    @XmlEnumValue("Cube")
    CUBE("Cube"),
    @XmlEnumValue("Cylinder")
    CYLINDER("Cylinder"),
    @XmlEnumValue("Sphere")
    SPHERE("Sphere"),
    @XmlEnumValue("Base")
    BASE("Base"),
    @XmlEnumValue("Yoke")
    YOKE("Yoke"),
    @XmlEnumValue("Head")
    HEAD("Head"),
    @XmlEnumValue("Scanner")
    SCANNER("Scanner"),
    @XmlEnumValue("Conventional")
    CONVENTIONAL("Conventional");
    private final String value;

    PrimitiveTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PrimitiveTypeEnum fromValue(String v) {
        for (PrimitiveTypeEnum c: PrimitiveTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

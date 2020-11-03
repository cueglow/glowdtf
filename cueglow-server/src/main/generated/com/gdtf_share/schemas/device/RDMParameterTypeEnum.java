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
 * &lt;p&gt;Java-Klasse für RDMParameterTypeEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="RDMParameterTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="RDM"/&amp;gt;
 *     &amp;lt;enumeration value="FixtureType"/&amp;gt;
 *     &amp;lt;enumeration value="Fixture"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "RDMParameterTypeEnum")
@XmlEnum
public enum RDMParameterTypeEnum {

    RDM("RDM"),
    @XmlEnumValue("FixtureType")
    FIXTURE_TYPE("FixtureType"),
    @XmlEnumValue("Fixture")
    FIXTURE("Fixture");
    private final String value;

    RDMParameterTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RDMParameterTypeEnum fromValue(String v) {
        for (RDMParameterTypeEnum c: RDMParameterTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

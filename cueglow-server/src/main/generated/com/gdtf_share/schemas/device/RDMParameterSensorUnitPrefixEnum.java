//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java-Klasse f�r RDMParameterSensorUnitPrefixEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="RDMParameterSensorUnitPrefixEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_NONE"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_DECI"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_CENTI"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_MILLI"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_MICRO"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_NANO"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_PICO"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_FEMPTO"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_ATTO"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_ZEPTO"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_YOCTO"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_DECA"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_HECTO"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_KILO"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_MEGA"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_GIGA"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_TERRA"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_PETA"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_EXA"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_ZETTA"/&amp;gt;
 *     &amp;lt;enumeration value="PREFIX_YOTTA"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "RDMParameterSensorUnitPrefixEnum")
@XmlEnum
public enum RDMParameterSensorUnitPrefixEnum {

    PREFIX_NONE,
    PREFIX_DECI,
    PREFIX_CENTI,
    PREFIX_MILLI,
    PREFIX_MICRO,
    PREFIX_NANO,
    PREFIX_PICO,
    PREFIX_FEMPTO,
    PREFIX_ATTO,
    PREFIX_ZEPTO,
    PREFIX_YOCTO,
    PREFIX_DECA,
    PREFIX_HECTO,
    PREFIX_KILO,
    PREFIX_MEGA,
    PREFIX_GIGA,
    PREFIX_TERRA,
    PREFIX_PETA,
    PREFIX_EXA,
    PREFIX_ZETTA,
    PREFIX_YOTTA;

    public String value() {
        return name();
    }

    public static RDMParameterSensorUnitPrefixEnum fromValue(String v) {
        return valueOf(v);
    }

}

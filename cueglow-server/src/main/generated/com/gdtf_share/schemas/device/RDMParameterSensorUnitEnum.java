//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java-Klasse für RDMParameterSensorUnitEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="RDMParameterSensorUnitEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="UNITS_NONE"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_CENTIGRADE"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_VOLTS_DC"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_VOLTS_AC_PEAK"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_VOLTS_AC_RMS"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_AMPERE_DC"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_AMPERE_AC_PEAK"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_AMPERE_AC_RMS"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_HERTZ"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_OHM"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_WATT"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_KILOGRAM"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_METERS"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_METERS_SQUARED"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_METERS_CUBED"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_KILOGRAMMES_PER_METER_CUBED"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_METERS_PER_SECOND"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_METERS_PER_SECOND_SQUARED"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_NEWTON"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_JOULE"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_PASCAL"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_SECOND"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_DEGREE"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_STERADIAN"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_CANDELA"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_LUMEN"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_LUX"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_IRE"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_BYTE"/&amp;gt;
 *     &amp;lt;enumeration value="UNITS_MS"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "RDMParameterSensorUnitEnum")
@XmlEnum
public enum RDMParameterSensorUnitEnum {

    UNITS_NONE,
    UNITS_CENTIGRADE,
    UNITS_VOLTS_DC,
    UNITS_VOLTS_AC_PEAK,
    UNITS_VOLTS_AC_RMS,
    UNITS_AMPERE_DC,
    UNITS_AMPERE_AC_PEAK,
    UNITS_AMPERE_AC_RMS,
    UNITS_HERTZ,
    UNITS_OHM,
    UNITS_WATT,
    UNITS_KILOGRAM,
    UNITS_METERS,
    UNITS_METERS_SQUARED,
    UNITS_METERS_CUBED,
    UNITS_KILOGRAMMES_PER_METER_CUBED,
    UNITS_METERS_PER_SECOND,
    UNITS_METERS_PER_SECOND_SQUARED,
    UNITS_NEWTON,
    UNITS_JOULE,
    UNITS_PASCAL,
    UNITS_SECOND,
    UNITS_DEGREE,
    UNITS_STERADIAN,
    UNITS_CANDELA,
    UNITS_LUMEN,
    UNITS_LUX,
    UNITS_IRE,
    UNITS_BYTE,
    UNITS_MS;

    public String value() {
        return name();
    }

    public static RDMParameterSensorUnitEnum fromValue(String v) {
        return valueOf(v);
    }

}

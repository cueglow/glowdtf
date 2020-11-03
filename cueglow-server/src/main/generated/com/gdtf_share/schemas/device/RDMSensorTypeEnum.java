//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java-Klasse f�r RDMSensorTypeEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="RDMSensorTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="SENS_VOLTAGE"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_CURRENT"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_FREQUENCY"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_RESISTANCE"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_POWER"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_MASS"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_LENGTH"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_AREA"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_VOLUME"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_DENSITY"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_VELOCITY"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_ACCELERATION"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_FORCE"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_ENERGY"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_PRESSURE"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_TIME"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_ANGLE"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_POSITION_X"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_POSITION_Y"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_POSITION_Z"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_ANGULAR_VELOCITY"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_LUMINOUS_INTENSITY"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_LUMINOUS_FLUX"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_ILLUMINANCE"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_CHROMINANCE_RED"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_CHROMINANCE_GREEN"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_CHROMINANCE_BLUE"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_CONTACTS"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_MEMORY"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_ITEMS"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_HUMIDITY"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_COUNTER_16BIT"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_OTHER"/&amp;gt;
 *     &amp;lt;enumeration value="SENS_MS"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "RDMSensorTypeEnum")
@XmlEnum
public enum RDMSensorTypeEnum {

    SENS_VOLTAGE("SENS_VOLTAGE"),
    SENS_CURRENT("SENS_CURRENT"),
    SENS_FREQUENCY("SENS_FREQUENCY"),
    SENS_RESISTANCE("SENS_RESISTANCE"),
    SENS_POWER("SENS_POWER"),
    SENS_MASS("SENS_MASS"),
    SENS_LENGTH("SENS_LENGTH"),
    SENS_AREA("SENS_AREA"),
    SENS_VOLUME("SENS_VOLUME"),
    SENS_DENSITY("SENS_DENSITY"),
    SENS_VELOCITY("SENS_VELOCITY"),
    SENS_ACCELERATION("SENS_ACCELERATION"),
    SENS_FORCE("SENS_FORCE"),
    SENS_ENERGY("SENS_ENERGY"),
    SENS_PRESSURE("SENS_PRESSURE"),
    SENS_TIME("SENS_TIME"),
    SENS_ANGLE("SENS_ANGLE"),
    SENS_POSITION_X("SENS_POSITION_X"),
    SENS_POSITION_Y("SENS_POSITION_Y"),
    SENS_POSITION_Z("SENS_POSITION_Z"),
    SENS_ANGULAR_VELOCITY("SENS_ANGULAR_VELOCITY"),
    SENS_LUMINOUS_INTENSITY("SENS_LUMINOUS_INTENSITY"),
    SENS_LUMINOUS_FLUX("SENS_LUMINOUS_FLUX"),
    SENS_ILLUMINANCE("SENS_ILLUMINANCE"),
    SENS_CHROMINANCE_RED("SENS_CHROMINANCE_RED"),
    SENS_CHROMINANCE_GREEN("SENS_CHROMINANCE_GREEN"),
    SENS_CHROMINANCE_BLUE("SENS_CHROMINANCE_BLUE"),
    SENS_CONTACTS("SENS_CONTACTS"),
    SENS_MEMORY("SENS_MEMORY"),
    SENS_ITEMS("SENS_ITEMS"),
    SENS_HUMIDITY("SENS_HUMIDITY"),
    @XmlEnumValue("SENS_COUNTER_16BIT")
    SENS_COUNTER_16_BIT("SENS_COUNTER_16BIT"),
    SENS_OTHER("SENS_OTHER"),
    SENS_MS("SENS_MS");
    private final String value;

    RDMSensorTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RDMSensorTypeEnum fromValue(String v) {
        for (RDMSensorTypeEnum c: RDMSensorTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

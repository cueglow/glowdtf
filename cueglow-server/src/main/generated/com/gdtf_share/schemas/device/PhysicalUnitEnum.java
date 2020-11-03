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
 * &lt;p&gt;Java-Klasse für PhysicalUnitEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="PhysicalUnitEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="None"/&amp;gt;
 *     &amp;lt;enumeration value="Percent"/&amp;gt;
 *     &amp;lt;enumeration value="Length"/&amp;gt;
 *     &amp;lt;enumeration value="Mass"/&amp;gt;
 *     &amp;lt;enumeration value="Time"/&amp;gt;
 *     &amp;lt;enumeration value="Temperature"/&amp;gt;
 *     &amp;lt;enumeration value="LuminousIntensity"/&amp;gt;
 *     &amp;lt;enumeration value="Angle"/&amp;gt;
 *     &amp;lt;enumeration value="Force"/&amp;gt;
 *     &amp;lt;enumeration value="Frequency"/&amp;gt;
 *     &amp;lt;enumeration value="Current"/&amp;gt;
 *     &amp;lt;enumeration value="Voltage"/&amp;gt;
 *     &amp;lt;enumeration value="Power"/&amp;gt;
 *     &amp;lt;enumeration value="Energy"/&amp;gt;
 *     &amp;lt;enumeration value="Area"/&amp;gt;
 *     &amp;lt;enumeration value="Volume"/&amp;gt;
 *     &amp;lt;enumeration value="Speed"/&amp;gt;
 *     &amp;lt;enumeration value="Acceleration"/&amp;gt;
 *     &amp;lt;enumeration value="AngularSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="AngularAccc"/&amp;gt;
 *     &amp;lt;enumeration value="WaveLength"/&amp;gt;
 *     &amp;lt;enumeration value="ColorComponent"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "PhysicalUnitEnum")
@XmlEnum
public enum PhysicalUnitEnum {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Percent")
    PERCENT("Percent"),
    @XmlEnumValue("Length")
    LENGTH("Length"),
    @XmlEnumValue("Mass")
    MASS("Mass"),
    @XmlEnumValue("Time")
    TIME("Time"),
    @XmlEnumValue("Temperature")
    TEMPERATURE("Temperature"),
    @XmlEnumValue("LuminousIntensity")
    LUMINOUS_INTENSITY("LuminousIntensity"),
    @XmlEnumValue("Angle")
    ANGLE("Angle"),
    @XmlEnumValue("Force")
    FORCE("Force"),
    @XmlEnumValue("Frequency")
    FREQUENCY("Frequency"),
    @XmlEnumValue("Current")
    CURRENT("Current"),
    @XmlEnumValue("Voltage")
    VOLTAGE("Voltage"),
    @XmlEnumValue("Power")
    POWER("Power"),
    @XmlEnumValue("Energy")
    ENERGY("Energy"),
    @XmlEnumValue("Area")
    AREA("Area"),
    @XmlEnumValue("Volume")
    VOLUME("Volume"),
    @XmlEnumValue("Speed")
    SPEED("Speed"),
    @XmlEnumValue("Acceleration")
    ACCELERATION("Acceleration"),
    @XmlEnumValue("AngularSpeed")
    ANGULAR_SPEED("AngularSpeed"),
    @XmlEnumValue("AngularAccc")
    ANGULAR_ACCC("AngularAccc"),
    @XmlEnumValue("WaveLength")
    WAVE_LENGTH("WaveLength"),
    @XmlEnumValue("ColorComponent")
    COLOR_COMPONENT("ColorComponent");
    private final String value;

    PhysicalUnitEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PhysicalUnitEnum fromValue(String v) {
        for (PhysicalUnitEnum c: PhysicalUnitEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

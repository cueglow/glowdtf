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
 * &lt;p&gt;Java-Klasse für RDMSlotLabelIDEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="RDMSlotLabelIDEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="SD_INTENSITY"/&amp;gt;
 *     &amp;lt;enumeration value="SD_INTENSITY_MASTER"/&amp;gt;
 *     &amp;lt;enumeration value="SD_PAN"/&amp;gt;
 *     &amp;lt;enumeration value="SD_TILT"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_WHEEL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_SUB_CYAN"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_SUB_YELLOW"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_SUB_MAGENTA"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_ADD_RED"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_ADD_GREEN"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_ADD_BLUE"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_CORRECTION"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_SCROLL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_SEMAPHORE"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_ADD_AMBER"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_ADD_WHITE"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_ADD_W ARM_WHITE"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_ADD_COOL_WHITE"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_SUB_UV"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_HUE"/&amp;gt;
 *     &amp;lt;enumeration value="SD_COLOR_SATURATION"/&amp;gt;
 *     &amp;lt;enumeration value="SD_STATIC_GOBO_WHEEL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_ROTO_GOBO_WHEEL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_PRISM_WHEEL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_EFFECTS_WHEEL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_BEAM_SIZE_IRIS"/&amp;gt;
 *     &amp;lt;enumeration value="SD_EDGE"/&amp;gt;
 *     &amp;lt;enumeration value="SD_FROST"/&amp;gt;
 *     &amp;lt;enumeration value="SD_STROBE"/&amp;gt;
 *     &amp;lt;enumeration value="SD_ZOOM"/&amp;gt;
 *     &amp;lt;enumeration value="SD_FRAMING_SHUTTER"/&amp;gt;
 *     &amp;lt;enumeration value="SD_SHUTTER_ROTATE"/&amp;gt;
 *     &amp;lt;enumeration value="SD_DOUSER"/&amp;gt;
 *     &amp;lt;enumeration value="SD_BARN_DOOR"/&amp;gt;
 *     &amp;lt;enumeration value="SD_LAMP_CONTROL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_FIXTURE_CONTROL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_FIXTURE_SPEED"/&amp;gt;
 *     &amp;lt;enumeration value="SD_MACRO"/&amp;gt;
 *     &amp;lt;enumeration value="SD_POWER_CONTROL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_FAN_CONTROL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_HEATER_CONTROL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_FOUNTAIN_CONTROL"/&amp;gt;
 *     &amp;lt;enumeration value="SD_UNDEFINED"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "RDMSlotLabelIDEnum")
@XmlEnum
public enum RDMSlotLabelIDEnum {

    SD_INTENSITY("SD_INTENSITY"),
    SD_INTENSITY_MASTER("SD_INTENSITY_MASTER"),
    SD_PAN("SD_PAN"),
    SD_TILT("SD_TILT"),
    SD_COLOR_WHEEL("SD_COLOR_WHEEL"),
    SD_COLOR_SUB_CYAN("SD_COLOR_SUB_CYAN"),
    SD_COLOR_SUB_YELLOW("SD_COLOR_SUB_YELLOW"),
    SD_COLOR_SUB_MAGENTA("SD_COLOR_SUB_MAGENTA"),
    SD_COLOR_ADD_RED("SD_COLOR_ADD_RED"),
    SD_COLOR_ADD_GREEN("SD_COLOR_ADD_GREEN"),
    SD_COLOR_ADD_BLUE("SD_COLOR_ADD_BLUE"),
    SD_COLOR_CORRECTION("SD_COLOR_CORRECTION"),
    SD_COLOR_SCROLL("SD_COLOR_SCROLL"),
    SD_COLOR_SEMAPHORE("SD_COLOR_SEMAPHORE"),
    SD_COLOR_ADD_AMBER("SD_COLOR_ADD_AMBER"),
    SD_COLOR_ADD_WHITE("SD_COLOR_ADD_WHITE"),
    @XmlEnumValue("SD_COLOR_ADD_W ARM_WHITE")
    SD_COLOR_ADD_W_ARM_WHITE("SD_COLOR_ADD_W ARM_WHITE"),
    SD_COLOR_ADD_COOL_WHITE("SD_COLOR_ADD_COOL_WHITE"),
    SD_COLOR_SUB_UV("SD_COLOR_SUB_UV"),
    SD_COLOR_HUE("SD_COLOR_HUE"),
    SD_COLOR_SATURATION("SD_COLOR_SATURATION"),
    SD_STATIC_GOBO_WHEEL("SD_STATIC_GOBO_WHEEL"),
    SD_ROTO_GOBO_WHEEL("SD_ROTO_GOBO_WHEEL"),
    SD_PRISM_WHEEL("SD_PRISM_WHEEL"),
    SD_EFFECTS_WHEEL("SD_EFFECTS_WHEEL"),
    SD_BEAM_SIZE_IRIS("SD_BEAM_SIZE_IRIS"),
    SD_EDGE("SD_EDGE"),
    SD_FROST("SD_FROST"),
    SD_STROBE("SD_STROBE"),
    SD_ZOOM("SD_ZOOM"),
    SD_FRAMING_SHUTTER("SD_FRAMING_SHUTTER"),
    SD_SHUTTER_ROTATE("SD_SHUTTER_ROTATE"),
    SD_DOUSER("SD_DOUSER"),
    SD_BARN_DOOR("SD_BARN_DOOR"),
    SD_LAMP_CONTROL("SD_LAMP_CONTROL"),
    SD_FIXTURE_CONTROL("SD_FIXTURE_CONTROL"),
    SD_FIXTURE_SPEED("SD_FIXTURE_SPEED"),
    SD_MACRO("SD_MACRO"),
    SD_POWER_CONTROL("SD_POWER_CONTROL"),
    SD_FAN_CONTROL("SD_FAN_CONTROL"),
    SD_HEATER_CONTROL("SD_HEATER_CONTROL"),
    SD_FOUNTAIN_CONTROL("SD_FOUNTAIN_CONTROL"),
    SD_UNDEFINED("SD_UNDEFINED");
    private final String value;

    RDMSlotLabelIDEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RDMSlotLabelIDEnum fromValue(String v) {
        for (RDMSlotLabelIDEnum c: RDMSlotLabelIDEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

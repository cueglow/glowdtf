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
 * &lt;p&gt;Java-Klasse f�r RDMSlotInfoTypeEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="RDMSlotInfoTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="ST_PRIMARY"/&amp;gt;
 *     &amp;lt;enumeration value="ST_SEC_FINE"/&amp;gt;
 *     &amp;lt;enumeration value="ST_SEC_TIMING"/&amp;gt;
 *     &amp;lt;enumeration value="ST_SEC_SPEED"/&amp;gt;
 *     &amp;lt;enumeration value="ST_SEC_CONTROL"/&amp;gt;
 *     &amp;lt;enumeration value="ST_SEC_INDEX"/&amp;gt;
 *     &amp;lt;enumeration value="ST_SEC_ROTATION"/&amp;gt;
 *     &amp;lt;enumeration value="ST_SEC_INDEX_ROTATE"/&amp;gt;
 *     &amp;lt;enumeration value="ST_SEC_UNDEFINED"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "RDMSlotInfoTypeEnum")
@XmlEnum
public enum RDMSlotInfoTypeEnum {

    ST_PRIMARY,
    ST_SEC_FINE,
    ST_SEC_TIMING,
    ST_SEC_SPEED,
    ST_SEC_CONTROL,
    ST_SEC_INDEX,
    ST_SEC_ROTATION,
    ST_SEC_INDEX_ROTATE,
    ST_SEC_UNDEFINED;

    public String value() {
        return name();
    }

    public static RDMSlotInfoTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}

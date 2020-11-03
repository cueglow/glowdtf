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
 * &lt;p&gt;Java-Klasse für RDMParameterDataTypeEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="RDMParameterDataTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="DS_NOT_DEFINED"/&amp;gt;
 *     &amp;lt;enumeration value="DS_BIT_FIELD"/&amp;gt;
 *     &amp;lt;enumeration value="DS_ASCII"/&amp;gt;
 *     &amp;lt;enumeration value="DS_UNSIGNED_BYTE"/&amp;gt;
 *     &amp;lt;enumeration value="DS_SIGNED_BYTE"/&amp;gt;
 *     &amp;lt;enumeration value="DS_UNSIGNED_WORD"/&amp;gt;
 *     &amp;lt;enumeration value="DS_SIGNED_WORD"/&amp;gt;
 *     &amp;lt;enumeration value="DS_UNSIGNED_DWORD"/&amp;gt;
 *     &amp;lt;enumeration value="DS_SIGNED_DWORD"/&amp;gt;
 *     &amp;lt;enumeration value="DS_MS"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "RDMParameterDataTypeEnum")
@XmlEnum
public enum RDMParameterDataTypeEnum {

    DS_NOT_DEFINED,
    DS_BIT_FIELD,
    DS_ASCII,
    DS_UNSIGNED_BYTE,
    DS_SIGNED_BYTE,
    DS_UNSIGNED_WORD,
    DS_SIGNED_WORD,
    DS_UNSIGNED_DWORD,
    DS_SIGNED_DWORD,
    DS_MS;

    public String value() {
        return name();
    }

    public static RDMParameterDataTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}

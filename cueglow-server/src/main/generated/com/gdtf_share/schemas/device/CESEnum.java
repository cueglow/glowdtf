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
 * &lt;p&gt;Java-Klasse f�r CESEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="CESEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="CES01"/&amp;gt;
 *     &amp;lt;enumeration value="CES02"/&amp;gt;
 *     &amp;lt;enumeration value="CES03"/&amp;gt;
 *     &amp;lt;enumeration value="CES04"/&amp;gt;
 *     &amp;lt;enumeration value="CES05"/&amp;gt;
 *     &amp;lt;enumeration value="CES06"/&amp;gt;
 *     &amp;lt;enumeration value="CES07"/&amp;gt;
 *     &amp;lt;enumeration value="CES08"/&amp;gt;
 *     &amp;lt;enumeration value="CES09"/&amp;gt;
 *     &amp;lt;enumeration value="CES10"/&amp;gt;
 *     &amp;lt;enumeration value="CES11"/&amp;gt;
 *     &amp;lt;enumeration value="CES12"/&amp;gt;
 *     &amp;lt;enumeration value="CES13"/&amp;gt;
 *     &amp;lt;enumeration value="CES14"/&amp;gt;
 *     &amp;lt;enumeration value="CES15"/&amp;gt;
 *     &amp;lt;enumeration value="CES16"/&amp;gt;
 *     &amp;lt;enumeration value="CES17"/&amp;gt;
 *     &amp;lt;enumeration value="CES18"/&amp;gt;
 *     &amp;lt;enumeration value="CES19"/&amp;gt;
 *     &amp;lt;enumeration value="CES20"/&amp;gt;
 *     &amp;lt;enumeration value="CES21"/&amp;gt;
 *     &amp;lt;enumeration value="CES22"/&amp;gt;
 *     &amp;lt;enumeration value="CES23"/&amp;gt;
 *     &amp;lt;enumeration value="CES24"/&amp;gt;
 *     &amp;lt;enumeration value="CES25"/&amp;gt;
 *     &amp;lt;enumeration value="CES26"/&amp;gt;
 *     &amp;lt;enumeration value="CES27"/&amp;gt;
 *     &amp;lt;enumeration value="CES28"/&amp;gt;
 *     &amp;lt;enumeration value="CES29"/&amp;gt;
 *     &amp;lt;enumeration value="CES30"/&amp;gt;
 *     &amp;lt;enumeration value="CES31"/&amp;gt;
 *     &amp;lt;enumeration value="CES32"/&amp;gt;
 *     &amp;lt;enumeration value="CES33"/&amp;gt;
 *     &amp;lt;enumeration value="CES34"/&amp;gt;
 *     &amp;lt;enumeration value="CES35"/&amp;gt;
 *     &amp;lt;enumeration value="CES36"/&amp;gt;
 *     &amp;lt;enumeration value="CES37"/&amp;gt;
 *     &amp;lt;enumeration value="CES38"/&amp;gt;
 *     &amp;lt;enumeration value="CES39"/&amp;gt;
 *     &amp;lt;enumeration value="CES40"/&amp;gt;
 *     &amp;lt;enumeration value="CES41"/&amp;gt;
 *     &amp;lt;enumeration value="CES42"/&amp;gt;
 *     &amp;lt;enumeration value="CES43"/&amp;gt;
 *     &amp;lt;enumeration value="CES44"/&amp;gt;
 *     &amp;lt;enumeration value="CES45"/&amp;gt;
 *     &amp;lt;enumeration value="CES46"/&amp;gt;
 *     &amp;lt;enumeration value="CES47"/&amp;gt;
 *     &amp;lt;enumeration value="CES48"/&amp;gt;
 *     &amp;lt;enumeration value="CES49"/&amp;gt;
 *     &amp;lt;enumeration value="CES50"/&amp;gt;
 *     &amp;lt;enumeration value="CES51"/&amp;gt;
 *     &amp;lt;enumeration value="CES52"/&amp;gt;
 *     &amp;lt;enumeration value="CES53"/&amp;gt;
 *     &amp;lt;enumeration value="CES54"/&amp;gt;
 *     &amp;lt;enumeration value="CES55"/&amp;gt;
 *     &amp;lt;enumeration value="CES56"/&amp;gt;
 *     &amp;lt;enumeration value="CES57"/&amp;gt;
 *     &amp;lt;enumeration value="CES58"/&amp;gt;
 *     &amp;lt;enumeration value="CES59"/&amp;gt;
 *     &amp;lt;enumeration value="CES60"/&amp;gt;
 *     &amp;lt;enumeration value="CES61"/&amp;gt;
 *     &amp;lt;enumeration value="CES62"/&amp;gt;
 *     &amp;lt;enumeration value="CES63"/&amp;gt;
 *     &amp;lt;enumeration value="CES64"/&amp;gt;
 *     &amp;lt;enumeration value="CES65"/&amp;gt;
 *     &amp;lt;enumeration value="CES66"/&amp;gt;
 *     &amp;lt;enumeration value="CES67"/&amp;gt;
 *     &amp;lt;enumeration value="CES68"/&amp;gt;
 *     &amp;lt;enumeration value="CES69"/&amp;gt;
 *     &amp;lt;enumeration value="CES70"/&amp;gt;
 *     &amp;lt;enumeration value="CES71"/&amp;gt;
 *     &amp;lt;enumeration value="CES72"/&amp;gt;
 *     &amp;lt;enumeration value="CES73"/&amp;gt;
 *     &amp;lt;enumeration value="CES74"/&amp;gt;
 *     &amp;lt;enumeration value="CES75"/&amp;gt;
 *     &amp;lt;enumeration value="CES76"/&amp;gt;
 *     &amp;lt;enumeration value="CES77"/&amp;gt;
 *     &amp;lt;enumeration value="CES78"/&amp;gt;
 *     &amp;lt;enumeration value="CES79"/&amp;gt;
 *     &amp;lt;enumeration value="CES80"/&amp;gt;
 *     &amp;lt;enumeration value="CES81"/&amp;gt;
 *     &amp;lt;enumeration value="CES82"/&amp;gt;
 *     &amp;lt;enumeration value="CES83"/&amp;gt;
 *     &amp;lt;enumeration value="CES84"/&amp;gt;
 *     &amp;lt;enumeration value="CES85"/&amp;gt;
 *     &amp;lt;enumeration value="CES86"/&amp;gt;
 *     &amp;lt;enumeration value="CES87"/&amp;gt;
 *     &amp;lt;enumeration value="CES88"/&amp;gt;
 *     &amp;lt;enumeration value="CES89"/&amp;gt;
 *     &amp;lt;enumeration value="CES90"/&amp;gt;
 *     &amp;lt;enumeration value="CES91"/&amp;gt;
 *     &amp;lt;enumeration value="CES92"/&amp;gt;
 *     &amp;lt;enumeration value="CES93"/&amp;gt;
 *     &amp;lt;enumeration value="CES94"/&amp;gt;
 *     &amp;lt;enumeration value="CES95"/&amp;gt;
 *     &amp;lt;enumeration value="CES96"/&amp;gt;
 *     &amp;lt;enumeration value="CES97"/&amp;gt;
 *     &amp;lt;enumeration value="CES98"/&amp;gt;
 *     &amp;lt;enumeration value="CES99"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "CESEnum")
@XmlEnum
public enum CESEnum {

    @XmlEnumValue("CES01")
    CES_01("CES01"),
    @XmlEnumValue("CES02")
    CES_02("CES02"),
    @XmlEnumValue("CES03")
    CES_03("CES03"),
    @XmlEnumValue("CES04")
    CES_04("CES04"),
    @XmlEnumValue("CES05")
    CES_05("CES05"),
    @XmlEnumValue("CES06")
    CES_06("CES06"),
    @XmlEnumValue("CES07")
    CES_07("CES07"),
    @XmlEnumValue("CES08")
    CES_08("CES08"),
    @XmlEnumValue("CES09")
    CES_09("CES09"),
    @XmlEnumValue("CES10")
    CES_10("CES10"),
    @XmlEnumValue("CES11")
    CES_11("CES11"),
    @XmlEnumValue("CES12")
    CES_12("CES12"),
    @XmlEnumValue("CES13")
    CES_13("CES13"),
    @XmlEnumValue("CES14")
    CES_14("CES14"),
    @XmlEnumValue("CES15")
    CES_15("CES15"),
    @XmlEnumValue("CES16")
    CES_16("CES16"),
    @XmlEnumValue("CES17")
    CES_17("CES17"),
    @XmlEnumValue("CES18")
    CES_18("CES18"),
    @XmlEnumValue("CES19")
    CES_19("CES19"),
    @XmlEnumValue("CES20")
    CES_20("CES20"),
    @XmlEnumValue("CES21")
    CES_21("CES21"),
    @XmlEnumValue("CES22")
    CES_22("CES22"),
    @XmlEnumValue("CES23")
    CES_23("CES23"),
    @XmlEnumValue("CES24")
    CES_24("CES24"),
    @XmlEnumValue("CES25")
    CES_25("CES25"),
    @XmlEnumValue("CES26")
    CES_26("CES26"),
    @XmlEnumValue("CES27")
    CES_27("CES27"),
    @XmlEnumValue("CES28")
    CES_28("CES28"),
    @XmlEnumValue("CES29")
    CES_29("CES29"),
    @XmlEnumValue("CES30")
    CES_30("CES30"),
    @XmlEnumValue("CES31")
    CES_31("CES31"),
    @XmlEnumValue("CES32")
    CES_32("CES32"),
    @XmlEnumValue("CES33")
    CES_33("CES33"),
    @XmlEnumValue("CES34")
    CES_34("CES34"),
    @XmlEnumValue("CES35")
    CES_35("CES35"),
    @XmlEnumValue("CES36")
    CES_36("CES36"),
    @XmlEnumValue("CES37")
    CES_37("CES37"),
    @XmlEnumValue("CES38")
    CES_38("CES38"),
    @XmlEnumValue("CES39")
    CES_39("CES39"),
    @XmlEnumValue("CES40")
    CES_40("CES40"),
    @XmlEnumValue("CES41")
    CES_41("CES41"),
    @XmlEnumValue("CES42")
    CES_42("CES42"),
    @XmlEnumValue("CES43")
    CES_43("CES43"),
    @XmlEnumValue("CES44")
    CES_44("CES44"),
    @XmlEnumValue("CES45")
    CES_45("CES45"),
    @XmlEnumValue("CES46")
    CES_46("CES46"),
    @XmlEnumValue("CES47")
    CES_47("CES47"),
    @XmlEnumValue("CES48")
    CES_48("CES48"),
    @XmlEnumValue("CES49")
    CES_49("CES49"),
    @XmlEnumValue("CES50")
    CES_50("CES50"),
    @XmlEnumValue("CES51")
    CES_51("CES51"),
    @XmlEnumValue("CES52")
    CES_52("CES52"),
    @XmlEnumValue("CES53")
    CES_53("CES53"),
    @XmlEnumValue("CES54")
    CES_54("CES54"),
    @XmlEnumValue("CES55")
    CES_55("CES55"),
    @XmlEnumValue("CES56")
    CES_56("CES56"),
    @XmlEnumValue("CES57")
    CES_57("CES57"),
    @XmlEnumValue("CES58")
    CES_58("CES58"),
    @XmlEnumValue("CES59")
    CES_59("CES59"),
    @XmlEnumValue("CES60")
    CES_60("CES60"),
    @XmlEnumValue("CES61")
    CES_61("CES61"),
    @XmlEnumValue("CES62")
    CES_62("CES62"),
    @XmlEnumValue("CES63")
    CES_63("CES63"),
    @XmlEnumValue("CES64")
    CES_64("CES64"),
    @XmlEnumValue("CES65")
    CES_65("CES65"),
    @XmlEnumValue("CES66")
    CES_66("CES66"),
    @XmlEnumValue("CES67")
    CES_67("CES67"),
    @XmlEnumValue("CES68")
    CES_68("CES68"),
    @XmlEnumValue("CES69")
    CES_69("CES69"),
    @XmlEnumValue("CES70")
    CES_70("CES70"),
    @XmlEnumValue("CES71")
    CES_71("CES71"),
    @XmlEnumValue("CES72")
    CES_72("CES72"),
    @XmlEnumValue("CES73")
    CES_73("CES73"),
    @XmlEnumValue("CES74")
    CES_74("CES74"),
    @XmlEnumValue("CES75")
    CES_75("CES75"),
    @XmlEnumValue("CES76")
    CES_76("CES76"),
    @XmlEnumValue("CES77")
    CES_77("CES77"),
    @XmlEnumValue("CES78")
    CES_78("CES78"),
    @XmlEnumValue("CES79")
    CES_79("CES79"),
    @XmlEnumValue("CES80")
    CES_80("CES80"),
    @XmlEnumValue("CES81")
    CES_81("CES81"),
    @XmlEnumValue("CES82")
    CES_82("CES82"),
    @XmlEnumValue("CES83")
    CES_83("CES83"),
    @XmlEnumValue("CES84")
    CES_84("CES84"),
    @XmlEnumValue("CES85")
    CES_85("CES85"),
    @XmlEnumValue("CES86")
    CES_86("CES86"),
    @XmlEnumValue("CES87")
    CES_87("CES87"),
    @XmlEnumValue("CES88")
    CES_88("CES88"),
    @XmlEnumValue("CES89")
    CES_89("CES89"),
    @XmlEnumValue("CES90")
    CES_90("CES90"),
    @XmlEnumValue("CES91")
    CES_91("CES91"),
    @XmlEnumValue("CES92")
    CES_92("CES92"),
    @XmlEnumValue("CES93")
    CES_93("CES93"),
    @XmlEnumValue("CES94")
    CES_94("CES94"),
    @XmlEnumValue("CES95")
    CES_95("CES95"),
    @XmlEnumValue("CES96")
    CES_96("CES96"),
    @XmlEnumValue("CES97")
    CES_97("CES97"),
    @XmlEnumValue("CES98")
    CES_98("CES98"),
    @XmlEnumValue("CES99")
    CES_99("CES99");
    private final String value;

    CESEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CESEnum fromValue(String v) {
        for (CESEnum c: CESEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

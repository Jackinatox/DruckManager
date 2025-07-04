
package de.fernausoft.druckmanager.xml.schema;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Gibt die Präferenz bei Dubletten an (nur wenn continueOnHit==true)
 *                 override: Nachfolgende Dubletten überschreiben die Vorgänger
 *                 keep:     Nachfolgende Dubletten werden ignoriert
 * 
 * <p>Java-Klasse für duplicateDealingType.</p>
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.</p>
 * <pre>{@code
 * <simpleType name="duplicateDealingType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="override"/>
 *     <enumeration value="keep"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "duplicateDealingType")
@XmlEnum
public enum DuplicateDealingType {

    @XmlEnumValue("override")
    OVERRIDE("override"),
    @XmlEnumValue("keep")
    KEEP("keep");
    private final String value;

    DuplicateDealingType(String v) {
        value = v;
    }

    /**
     * Gets the value associated to the enum constant.
     * 
     * @return
     *     The value linked to the enum.
     */
    public String value() {
        return value;
    }

    /**
     * Gets the enum associated to the value passed as parameter.
     * 
     * @param v
     *     The value to get the enum from.
     * @return
     *     The enum which corresponds to the value, if it exists.
     * @throws IllegalArgumentException
     *     If no value matches in the enum declaration.
     */
    public static DuplicateDealingType fromValue(String v) {
        for (DuplicateDealingType c: DuplicateDealingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

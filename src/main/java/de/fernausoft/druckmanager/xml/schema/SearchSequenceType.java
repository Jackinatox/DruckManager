
package de.fernausoft.druckmanager.xml.schema;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Gibt die Suchreihenfolge an
 *                 fullThenUser: Erst den vollen Treffer versuchen. Anschließend mit dem User fortfahren. Als letztes wird der Host verglichen
 *                 fullThenHost: Erst den vollen Treffer versuchen. Anschließend mit dem Host fortfahren. Als letztes wird der User verglichen
 * 
 * <p>Java-Klasse für searchSequenceType.</p>
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.</p>
 * <pre>{@code
 * <simpleType name="searchSequenceType">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="fullThenUser"/>
 *     <enumeration value="fullThenHost"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "searchSequenceType")
@XmlEnum
public enum SearchSequenceType {

    @XmlEnumValue("fullThenUser")
    FULL_THEN_USER("fullThenUser"),
    @XmlEnumValue("fullThenHost")
    FULL_THEN_HOST("fullThenHost");
    private final String value;

    SearchSequenceType(String v) {
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
    public static SearchSequenceType fromValue(String v) {
        for (SearchSequenceType c: SearchSequenceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

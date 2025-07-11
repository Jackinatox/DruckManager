
package de.fernausoft.druckmanager.xml.schema;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für keyvalue_def complex type.</p>
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.</p>
 * 
 * <pre>{@code
 * <complexType name="keyvalue_def">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <attribute name="env" use="required" type="{http://www.kfz3000plus.de/client/printerconfig}nonEmptyString" />
 *       <attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       <attribute name="ref" type="{http://www.kfz3000plus.de/client/printerconfig}nonEmptyString" />
 *       <attribute name="printer-dialog" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       <attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "keyvalue_def")
public class KeyvalueDef {

    @XmlAttribute(name = "env", required = true)
    protected String env;
    @XmlAttribute(name = "value")
    protected String value;
    @XmlAttribute(name = "ref")
    protected String ref;
    /**
     * Wird dieser Parameter auf true gesetzt, wird der User nach einem Drucker gefragt
     * 
     */
    @XmlAttribute(name = "printer-dialog")
    protected Boolean printerDialog;
    /**
     * Bei false wird dieser Eintrag bei der Interpretation der Einstellungen ignoriert
     * 
     */
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;

    /**
     * Ruft den Wert der env-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnv() {
        return env;
    }

    /**
     * Legt den Wert der env-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnv(String value) {
        this.env = value;
    }

    /**
     * Ruft den Wert der value-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Legt den Wert der value-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Ruft den Wert der ref-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRef() {
        return ref;
    }

    /**
     * Legt den Wert der ref-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRef(String value) {
        this.ref = value;
    }

    /**
     * Wird dieser Parameter auf true gesetzt, wird der User nach einem Drucker gefragt
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPrinterDialog() {
        if (printerDialog == null) {
            return false;
        } else {
            return printerDialog;
        }
    }

    /**
     * Legt den Wert der printerDialog-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isPrinterDialog()
     */
    public void setPrinterDialog(Boolean value) {
        this.printerDialog = value;
    }

    /**
     * Bei false wird dieser Eintrag bei der Interpretation der Einstellungen ignoriert
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEnabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    /**
     * Legt den Wert der enabled-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isEnabled()
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

}

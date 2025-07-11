
package de.fernausoft.druckmanager.xml.schema;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für printerconfig_def complex type.</p>
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.</p>
 * 
 * <pre>{@code
 * <complexType name="printerconfig_def">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="printers" type="{http://www.kfz3000plus.de/client/printerconfig}printers_def" minOccurs="0"/>
 *         <element name="targets" type="{http://www.kfz3000plus.de/client/printerconfig}targets_def" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "printerconfig_def", propOrder = {
    "printers",
    "targets"
})
public class PrinterconfigDef {

    protected PrintersDef printers;
    protected TargetsDef targets;

    /**
     * Ruft den Wert der printers-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PrintersDef }
     *     
     */
    public PrintersDef getPrinters() {
        return printers;
    }

    /**
     * Legt den Wert der printers-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PrintersDef }
     *     
     */
    public void setPrinters(PrintersDef value) {
        this.printers = value;
    }

    /**
     * Ruft den Wert der targets-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TargetsDef }
     *     
     */
    public TargetsDef getTargets() {
        return targets;
    }

    /**
     * Legt den Wert der targets-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TargetsDef }
     *     
     */
    public void setTargets(TargetsDef value) {
        this.targets = value;
    }

}

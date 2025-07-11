
package de.fernausoft.druckmanager.xml.schema;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für targets_def complex type.</p>
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.</p>
 * 
 * <pre>{@code
 * <complexType name="targets_def">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="target" type="{http://www.kfz3000plus.de/client/printerconfig}target_def" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="continueOnHit" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       <attribute name="searchSequence" type="{http://www.kfz3000plus.de/client/printerconfig}searchSequenceType" default="fullThenHost" />
 *       <attribute name="duplicateDealing" type="{http://www.kfz3000plus.de/client/printerconfig}duplicateDealingType" default="keep" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "targets_def", propOrder = {
    "target"
})
public class TargetsDef {

    protected List<TargetDef> target;
    @XmlAttribute(name = "continueOnHit")
    protected Boolean continueOnHit;
    @XmlAttribute(name = "searchSequence")
    protected SearchSequenceType searchSequence;
    @XmlAttribute(name = "duplicateDealing")
    protected DuplicateDealingType duplicateDealing;

    /**
     * Gets the value of the target property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the target property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getTarget().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TargetDef }
     * </p>
     * 
     * 
     * @return
     *     The value of the target property.
     */
    public List<TargetDef> getTarget() {
        if (target == null) {
            target = new ArrayList<>();
        }
        return this.target;
    }

    /**
     * Ruft den Wert der continueOnHit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isContinueOnHit() {
        if (continueOnHit == null) {
            return false;
        } else {
            return continueOnHit;
        }
    }

    /**
     * Legt den Wert der continueOnHit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setContinueOnHit(Boolean value) {
        this.continueOnHit = value;
    }

    /**
     * Ruft den Wert der searchSequence-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SearchSequenceType }
     *     
     */
    public SearchSequenceType getSearchSequence() {
        if (searchSequence == null) {
            return SearchSequenceType.FULL_THEN_HOST;
        } else {
            return searchSequence;
        }
    }

    /**
     * Legt den Wert der searchSequence-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchSequenceType }
     *     
     */
    public void setSearchSequence(SearchSequenceType value) {
        this.searchSequence = value;
    }

    /**
     * Ruft den Wert der duplicateDealing-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link DuplicateDealingType }
     *     
     */
    public DuplicateDealingType getDuplicateDealing() {
        if (duplicateDealing == null) {
            return DuplicateDealingType.KEEP;
        } else {
            return duplicateDealing;
        }
    }

    /**
     * Legt den Wert der duplicateDealing-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link DuplicateDealingType }
     *     
     */
    public void setDuplicateDealing(DuplicateDealingType value) {
        this.duplicateDealing = value;
    }

}


package de.fernausoft.druckmanager.xml.schema;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Angabe eines Ziels mittels Benutzer- und Hostname
 *                 - Der Username ist der in Windows eingeloggte Benutzer
 *                 - Der Hostname spiegelt den Rechner wieder an welchem man eingeloggt ist.
 *                   Hinweis: In Terminalserverumgebungen wird statt der HOSTNAME
 *                   Variable der CLIENTNAME abgefragt. Erkennen tue ich das daran,
 *                   dass eine CLIENTNAME-Umgebungsvariable existiert und nicht leer
 *                   ist. Diese Variable ist immer dann gesetzt, wenn man sich 
 *                   Remote an einen Rechner angemeldet hat.
 * 
 * <p>Java-Klasse für target_def complex type.</p>
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.</p>
 * 
 * <pre>{@code
 * <complexType name="target_def">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="env" type="{http://www.kfz3000plus.de/client/printerconfig}keyvalue_def" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="username" type="{http://www.kfz3000plus.de/client/printerconfig}nonEmptyString" />
 *       <attribute name="hostname" type="{http://www.kfz3000plus.de/client/printerconfig}nonEmptyString" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "target_def", propOrder = {
    "env"
})
public class TargetDef {

    protected List<KeyvalueDef> env;
    @XmlAttribute(name = "username")
    protected String username;
    @XmlAttribute(name = "hostname")
    protected String hostname;

    /**
     * Gets the value of the env property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the env property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getEnv().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyvalueDef }
     * </p>
     * 
     * 
     * @return
     *     The value of the env property.
     */
    public List<KeyvalueDef> getEnv() {
        if (env == null) {
            env = new ArrayList<>();
        }
        return this.env;
    }

    /**
     * Ruft den Wert der username-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Legt den Wert der username-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Ruft den Wert der hostname-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Legt den Wert der hostname-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostname(String value) {
        this.hostname = value;
    }

}


package de.fernausoft.druckmanager.xml.schema;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.fernausoft.druckmanager.xml.schema package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _Printerconfig_QNAME = new QName("http://www.kfz3000plus.de/client/printerconfig", "printerconfig");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.fernausoft.druckmanager.xml.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PrinterconfigDef }
     * 
     * @return
     *     the new instance of {@link PrinterconfigDef }
     */
    public PrinterconfigDef createPrinterconfigDef() {
        return new PrinterconfigDef();
    }

    /**
     * Create an instance of {@link PrintersDef }
     * 
     * @return
     *     the new instance of {@link PrintersDef }
     */
    public PrintersDef createPrintersDef() {
        return new PrintersDef();
    }

    /**
     * Create an instance of {@link PrinterDef }
     * 
     * @return
     *     the new instance of {@link PrinterDef }
     */
    public PrinterDef createPrinterDef() {
        return new PrinterDef();
    }

    /**
     * Create an instance of {@link TargetsDef }
     * 
     * @return
     *     the new instance of {@link TargetsDef }
     */
    public TargetsDef createTargetsDef() {
        return new TargetsDef();
    }

    /**
     * Create an instance of {@link TargetDef }
     * 
     * @return
     *     the new instance of {@link TargetDef }
     */
    public TargetDef createTargetDef() {
        return new TargetDef();
    }

    /**
     * Create an instance of {@link KeyvalueDef }
     * 
     * @return
     *     the new instance of {@link KeyvalueDef }
     */
    public KeyvalueDef createKeyvalueDef() {
        return new KeyvalueDef();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrinterconfigDef }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PrinterconfigDef }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.kfz3000plus.de/client/printerconfig", name = "printerconfig")
    public JAXBElement<PrinterconfigDef> createPrinterconfig(PrinterconfigDef value) {
        return new JAXBElement<>(_Printerconfig_QNAME, PrinterconfigDef.class, null, value);
    }

}

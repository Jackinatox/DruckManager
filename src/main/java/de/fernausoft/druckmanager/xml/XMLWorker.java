package de.fernausoft.druckmanager.xml;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.PrinterconfigDef;
import de.fernausoft.druckmanager.xml.schema.PrintersDef;
import de.fernausoft.druckmanager.xml.schema.TargetDef;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Unmarshaller;

public class XMLWorker {
    // private static final String JAXB_PACKAGE =
    // "de.fernausoft.druckmanager.xml.schema";
    private static final Logger logger = LogManager.getLogger(XMLWorker.class);

    private PrinterconfigDef printerConfig;
    private Map<String, PrinterDef> printerLookup = new java.util.HashMap<>();

    public XMLWorker(String pathToFile) {
        JAXBContext jaxbContext;
        try {
            InputStream xsdStream = getClass().getResourceAsStream("/clientprinterconfig.xsd");

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            javax.xml.validation.Schema schema = sf.newSchema(new StreamSource(xsdStream));
            jaxbContext = JAXBContext.newInstance("de.fernausoft.druckmanager.xml.schema");

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);

            File xmlFile = new File(pathToFile); // Use an XML file that matches the generated schema

            Object unmarshalledObject = unmarshaller.unmarshal(xmlFile);

            JAXBElement<?> jaxbElement = (JAXBElement<?>) unmarshalledObject;
            if (jaxbElement.getValue() instanceof PrinterconfigDef) {
                printerConfig = (PrinterconfigDef) jaxbElement.getValue();
            } else {
                logger.warn("Root element is JAXBElement, but value is not Printerconfig.");
                logger.warn("Type: " + jaxbElement.getDeclaredType().getName());
            }

            logger.info("XMLFile is an instance of: " + unmarshalledObject.getClass());

            for (PrinterDef printer : printerConfig.getPrinters().getPrinter()) {
                printerLookup.put(printer.getRef(), printer);
            }
            logger.info("Succesfully build PrinterLookup");

        } catch (Exception e) {
            logger.error(e);
        }

    }

    public List<PrinterDef> getAllPrinters() {
        List<PrinterDef> printerDefs = new ArrayList<>();
        PrintersDef allPrinters = printerConfig.getPrinters();
        if (allPrinters != null) {
            for (PrinterDef printer : allPrinters.getPrinter()) {
                printerDefs.add(printer);
            }
        }
        return printerDefs;
    }

    public List<TargetDef> getAllTargets() {
        List<TargetDef> targetDefs = new ArrayList<>();
        if (printerConfig.getTargets() != null) {
            List<TargetDef> targetList = printerConfig.getTargets().getTarget();
            if (targetList != null) {
                for (TargetDef target : targetList) {
                    targetDefs.add(target);
                    if (target == null || (target.getHostname() == null && target.getUsername() == null)) {
                        logger.warn(
                                "Target ohne Hostnamen und ohne User bei Element: " + (targetList.indexOf(target) + 1));
                    }
                }
            }
        }
        return targetDefs;
    }

    public PrinterDef printerLookup(String ref) {
        return printerLookup.get(ref);
    }

    public TargetDef forTesting() {
        return printerConfig.getTargets().getTarget().get(2);
    }
}

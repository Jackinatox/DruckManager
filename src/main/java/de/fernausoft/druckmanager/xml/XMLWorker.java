package de.fernausoft.druckmanager.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fernausoft.druckmanager.xml.schema.ObjectFactory;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.PrinterconfigDef;
import de.fernausoft.druckmanager.xml.schema.PrintersDef;

public class XMLWorker {
    // private static final String JAXB_PACKAGE =
    // "de.fernausoft.druckmanager.xml.schema";
    private static final Logger logger = LogManager.getLogger(XMLWorker.class);

    private PrinterconfigDef printerConfig;

    public XMLWorker(String pathToFile) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

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
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public List<PrinterDef> getAllPrinters() {
        List<PrinterDef> printerDefs = new ArrayList<>();
        PrintersDef allPrinters = printerConfig.getPrinters();
        if (allPrinters != null) {
            List<PrinterDef> printerList = allPrinters.getPrinter();
            if (printerList != null) {
                for (PrinterDef printer : printerList) {
                    if (printer != null && printer.getName() != null) {
                        printerDefs.add(printer);
                    }
                }
            }
        }
        return printerDefs;
    }

    public void something(){
        // var target = printerConfig.getTargets().getTarget();
        // target.get(0).getEnv().get
    }
}

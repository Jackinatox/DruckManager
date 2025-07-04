package de.fernausoft.druckmanager.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fernausoft.druckmanager.xml.schema.ObjectFactory;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.PrinterconfigDef;

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
                System.out.println("Root element is JAXBElement, but value is not Printerconfig.");
                System.out.println("Type: " + jaxbElement.getDeclaredType().getName());
            }

            logger.info("XMLFile is an instance of: " + unmarshalledObject.getClass());

            for (PrinterDef printer : printerConfig.getPrinters().getPrinter()) {
                // System.out.println(printer.getName());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}

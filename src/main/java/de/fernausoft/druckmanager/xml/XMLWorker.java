package de.fernausoft.druckmanager.xml;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fernausoft.druckmanager.ui.dialogs.DeletePrinterDialog;
import de.fernausoft.druckmanager.ui.dialogs.DeletePrinterDialog.DeletePrinterOption;
import de.fernausoft.druckmanager.ui.panels.Settings.ProgramType;
import de.fernausoft.druckmanager.ui.panels.Settings.ProgramTypeResolver;
import de.fernausoft.druckmanager.ui.panels.Settings.Target;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.BaseProgram;
import de.fernausoft.druckmanager.xml.schema.KeyvalueDef;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.PrinterconfigDef;
import de.fernausoft.druckmanager.xml.schema.PrintersDef;
import de.fernausoft.druckmanager.xml.schema.TargetDef;
import de.fernausoft.druckmanager.xml.schema.TargetsDef;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class XMLWorker {
    // private static final String JAXB_PACKAGE =
    // "de.fernausoft.druckmanager.xml.schema";
    private static final Logger logger = LogManager.getLogger(XMLWorker.class);

    private PrinterconfigDef printerConfig;
    private Map<String, PrinterDef> printerLookup = new java.util.HashMap<>();
    private PrinterDef askingPrinter;

    public XMLWorker(Path pathToFile) {
        JAXBContext jaxbContext;
        try {
            InputStream xsdStream = getClass().getResourceAsStream("/clientprinterconfig.xsd");

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            javax.xml.validation.Schema schema = sf.newSchema(new StreamSource(xsdStream));
            jaxbContext = JAXBContext.newInstance("de.fernausoft.druckmanager.xml.schema");

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);

            File xmlFile = pathToFile.toFile(); // Use an XML file that matches the generated schema

            Object unmarshalledObject = unmarshaller.unmarshal(xmlFile);

            JAXBElement<PrinterconfigDef> jaxbElement = (JAXBElement<PrinterconfigDef>) unmarshalledObject;
            printerConfig = jaxbElement.getValue();

            if (jaxbElement.getValue() instanceof PrinterconfigDef) {
                printerConfig = (PrinterconfigDef) jaxbElement.getValue();
            } else {
                logger.warn("Root element is JAXBElement, but value is not Printerconfig.");
                logger.warn("Type: " + jaxbElement.getDeclaredType().getName());
            }

            logger.info("XMLFile is an instance of: " + unmarshalledObject.getClass());

            askingPrinter = new PrinterDef();
            askingPrinter.setName("Dialog beim Drucken");

            printerConfig.getPrinters().getPrinter().add(askingPrinter);

            Collections.sort(printerConfig.getPrinters().getPrinter(), Comparator.comparing(PrinterDef::getName));
            Collections.sort(printerConfig.getTargets().getTarget(), Comparator.comparing(TargetDef::getHostname));

            for (PrinterDef printer : printerConfig.getPrinters().getPrinter()) {
                printerLookup.put(printer.getRef(), printer);
            }
            logger.info("Succesfully build PrinterLookup");

        } catch (Exception e) {
            logger.error(e);
        }

    }

    public PrintersDef getAllPrinters() {
        return printerConfig.getPrinters();
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

    public void rewriteXML(List<Target> myTargets) {
        // ObjectMapper mapper = new ObjectMapper();
        TargetsDef targetsDef = new TargetsDef();

        Collections.sort(printerConfig.getPrinters().getPrinter(), Comparator.comparing(PrinterDef::getName));

        int i = 1;

        printerConfig.getPrinters().getPrinter().remove(getAskingPrinter());

        for (PrinterDef printer : printerConfig.getPrinters().getPrinter()) {
            printer.setRef("Ref_" + i);
            i++;
        }

        for (Target target : myTargets) {
            TargetDef targetDef = new TargetDef();
            targetsDef.getTarget().add(targetDef);
            targetDef.setHostname(target.getHostname());
            targetDef.setUsername(target.getUsername());

            for (BaseProgram program : target.getPrograms()) {
                for (KeyvalueDef entry : program.buildEnvs()) {

                    targetDef.getEnv().add(entry);
                }
            }
        }

        printerConfig.getTargets().getTarget().clear();
        printerConfig.getTargets().getTarget().addAll(targetsDef.getTarget());

        try {
            JAXBContext context = JAXBContext.newInstance("de.fernausoft.druckmanager.xml.schema");
            File xmlFile = new File("newXML.xml");

            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            QName qName = new QName("http://www.kfz3000plus.de/client/printerconfig", "printerconfig");

            JAXBElement<PrinterconfigDef> rootElement = new JAXBElement<>(
                    qName,
                    PrinterconfigDef.class,
                    printerConfig);
            marshaller.marshal(rootElement, xmlFile);

            logger.info("Successfully wrote changes to XML file.");
        } catch (Exception e) {
            logger.error("Failed to write XML file", e);
        }
    }

    public void verifyRegex() {
        for (TargetDef target : printerConfig.getTargets().getTarget()) {
            for (KeyvalueDef env : target.getEnv()) {
                if (ProgramTypeResolver.resolveType(env.getEnv()) == ProgramType.UNBEKANNT) {
                    javax.swing.SwingUtilities.invokeLater(() -> javax.swing.JOptionPane.showMessageDialog(
                            null,
                            "Unbekannter ProgramType für Env: " + env.getEnv(),
                            "Info",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE));
                }
            }
        }
    }

    public PrinterDef newPrinter(String name) {
        boolean exists = printerConfig.getPrinters().getPrinter().stream()
                .anyMatch(p -> p.getName().equals(name.trim()));
        if (exists) {
            return null;
        }

        PrinterDef newPrinter = new PrinterDef();
        newPrinter.setName(name);
        newPrinter.setRef(name);

        printerConfig.getPrinters().getPrinter().add(newPrinter);
        Collections.sort(printerConfig.getPrinters().getPrinter(), Comparator.comparing(PrinterDef::getName));

        return newPrinter;
    }

    public void tryDeletePrinter(List<Target> myTargets, PrinterDef printerToDelete) {
        // ObjectMapper mapper = new ObjectMapper();
        List<PrinterWrapper> printers = new ArrayList<>();

        // Collections.sort(printerConfig.getPrinters().getPrinter(),
        // Comparator.comparing(PrinterDef::getName));

        for (Target target : myTargets) {

            for (BaseProgram program : target.getPrograms()) {
                for (var formularweg : program.getFormularwegList()) {
                    if (formularweg.getPrinter1() != null && !formularweg.getPrinter1().getAskDialog() && formularweg.getPrinter1().getPrinterDef() != null
                            && formularweg.getPrinter1().getPrinterDef().getRef().equals(printerToDelete.getRef())) {
                        printers.add(formularweg.getPrinter1());
                    }
                    if (formularweg.getPrinter2() != null && !formularweg.getPrinter2().getAskDialog() && formularweg.getPrinter2().getPrinterDef() != null
                            && formularweg.getPrinter2().getPrinterDef().getRef().equals(printerToDelete.getRef())) {

                        printers.add(formularweg.getPrinter2());
                    }
                    if (formularweg.getPrinter3() != null && !formularweg.getPrinter3().getAskDialog() && formularweg.getPrinter3().getPrinterDef() != null
                            && formularweg.getPrinter3().getPrinterDef().getRef().equals(printerToDelete.getRef())) {

                        printers.add(formularweg.getPrinter3());
                    }
                }
            }
        }

        if (!printers.isEmpty()) {
            DeletePrinterOption selection = DeletePrinterDialog.showDialog();

            switch (selection) {
                case SET_TO_ASK:
                    printers.forEach(printer -> {
                        // printerConfig.getPrinters().getPrinter().remove(printer.getPrinterDef());
                        printer.setAskDialog(true);
                        printer.setPrinter(null);
                    });
                    break;
                case DELETE_MAPPINGS:
                    printers.forEach(printer -> {
                        // printerConfig.getPrinters().getPrinter().remove(printer.getPrinterDef());
                        printer.setPrinter(null);

                        // printer.setAskDialog(false);
                    });
                    break;
                case CANCEL:
                    // User canceled the operation
                    return;
            }

        }
        printerConfig.getPrinters().getPrinter().remove(printerToDelete);
    }

    public PrinterDef getAskingPrinter() {
        return askingPrinter;
    }
}

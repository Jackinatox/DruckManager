package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.List;
import java.util.Map;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg3;
import de.fernausoft.druckmanager.xml.PrinterWrapper;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.KeyvalueDef;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class DefaultLayoutProgram extends BaseProgram {
    private Map<Character, Formularweg3> formularwegMap;
    // private String sufix;

    // This is only for teh ones with multiple FormularWege and multiple printer
    public DefaultLayoutProgram(String name, String prefix, String sufix, XMLWorker xmlWorker) {
        formularwegMap = new java.util.HashMap<>();
        this.name = name;
        this.prefix = prefix;
        // this.sufix = sufix;
        this.xmlWorker = xmlWorker;

        Formularweg3 weg;
        for (char c = '0'; c <= '8'; c++){
            weg = new Formularweg3("Formularweg", c, xmlWorker);
            formularwegMap.put(c, weg);
        }
    }

    @Override
    public String buildEnv() {
        throw new UnsupportedOperationException("Unimplemented method 'buildEnv'");
    }

    @Override
    public void addPrinter(KeyvalueDef env, PrinterDef printer) {
        Formularweg3 weg = formularwegMap.get(env.getEnv().charAt(6));
        PrinterWrapper printerWrapper = new PrinterWrapper(printer, env.isEnabled(), env.isPrinterDialog());

        char printerIndex = env.getEnv().charAt(10);
        weg.setPrinter(printerIndex, printerWrapper);
    }

    @Override
    public List<Formularweg> getFormularwegList() {
        return List.copyOf(formularwegMap.values());
    }

}

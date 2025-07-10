package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.List;
import java.util.Map;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg3;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class DefaultLayoutProgram extends BaseProgram {
    private Map<Character, Formularweg3> formularwegMap;
    private XMLWorker xmlWorker;

    // This is only for teh ones with multiple FormularWege and multiple printer
    public DefaultLayoutProgram(String name, String prefix, XMLWorker xmlWorker) {
        formularwegMap = new java.util.HashMap<>();
        this.name = name;
        this.prefix = prefix;
        this.xmlWorker = xmlWorker;
    }

    @Override
    public String buildEnv() {
        throw new UnsupportedOperationException("Unimplemented method 'buildEnv'");
    }

    @Override
    public void addPrinter(String env, PrinterDef printer) {
        var weg = formularwegMap.get(env.charAt(6));
        if (weg == null) {
            weg = new Formularweg3(name, env.charAt(6), xmlWorker);
            formularwegMap.put(env.charAt(6), weg);
        }
        char printerIndex = env.charAt(10);
        weg.setPrinter(printerIndex, printer);

    }

    @Override
    public List<Formularweg> getFormularwegList() {
        return List.copyOf(formularwegMap.values());
    }

}

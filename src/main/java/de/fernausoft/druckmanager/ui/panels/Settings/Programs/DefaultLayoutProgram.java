package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.Map;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg3;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class DefaultLayoutProgram extends BaseProgram {
    private Map<Character, Formularweg3> formularwegMap;

    // This is only for teh ones with multiple FormularWege and multiple printer
    public DefaultLayoutProgram(String name, String prefix) {
        formularwegMap = new java.util.HashMap<>();
        this.name = name;
        this.prefix = prefix;
    }

    @Override
    public String buildEnv() {
        throw new UnsupportedOperationException("Unimplemented method 'buildEnv'");
    }

    @Override
    public void addPrinter(String env, PrinterDef printer) {
        var weg = formularwegMap.get(env.charAt(6));
        if (weg == null) {
            weg = new Formularweg3(name, env.charAt(6));
            formularwegMap.put(env.charAt(6), weg);
        }
        char printerIndex = env.charAt(10);
        weg.addPrinter(printerIndex, printer);

    }

    public Map<Character, Formularweg3> getMap(){
        return this.formularwegMap;
    }

}

package de.fernausoft.druckmanager.ui.panels.Settings.Programs.Werkstatt;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.*;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class WerkstattFormularweg extends Formularweg3 {
    public WerkstattFormularweg(String env, PrinterDef printerDef) {
        super();
        addPrinter(env, printerDef);
    }

    public Formularweg3 addPrinter(String env, PrinterDef printerDef) {
        char printerId = env.charAt(10);
        super.addPrinter(printerId, printerDef);
        return this;
    }

    @Override
    protected String getName() {
        // return a completely random name for testing purposes
        return "Werkstatt Formularweg " + Math.random();
    }

}

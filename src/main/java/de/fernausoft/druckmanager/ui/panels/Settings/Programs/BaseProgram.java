package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.List;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public abstract class BaseProgram {
    protected String name;
    protected String prefix;
    protected XMLWorker xmlWorker;

    protected BaseProgram() {
    }

    public abstract void addPrinter(String env, PrinterDef printer);

    public abstract List<Formularweg> getFormularwegList();

    public String getName() {
        return this.name;
    }

    public abstract String buildEnv();

}
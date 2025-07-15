package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.List;
import java.util.Map;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.KeyvalueDef;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public abstract class BaseProgram {
    protected String name;
    protected String prefix;
    protected XMLWorker xmlWorker;

    protected BaseProgram() {
    }

    public abstract void addPrinter(KeyvalueDef env, PrinterDef printer);

    public abstract List<Formularweg> getFormularwegList();

    public String getName() {
        return this.name;
    }


    abstract public Map<KeyvalueDef, String> buildEnvs();
}
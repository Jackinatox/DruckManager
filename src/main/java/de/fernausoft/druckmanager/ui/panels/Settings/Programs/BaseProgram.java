package de.fernausoft.druckmanager.ui.panels.Settings.Programs;


import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public abstract class BaseProgram {
    protected String name;
    protected String prefix;

    protected BaseProgram() {
    }

    public abstract void addPrinter(String env, PrinterDef printer);

    public String getName() {
        return this.name;
    }

    public abstract String buildEnv();

}
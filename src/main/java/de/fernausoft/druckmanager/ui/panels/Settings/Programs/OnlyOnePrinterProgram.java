package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.List;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg1;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;


public class OnlyOnePrinterProgram extends BaseProgram {
    private Formularweg1 formularweg;
    
    public OnlyOnePrinterProgram(String name, String prefix, XMLWorker xmlWorker) {
        this.formularweg = new Formularweg1("Formularweg", '0', xmlWorker);
        this.name = name;
        this.prefix = prefix;
    }

    @Override
    public void addPrinter(String env, PrinterDef printer, Boolean enabled) {
        formularweg.addPrinter(env.charAt(10), printer, enabled);
    }

    @Override
    public String buildEnv() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildEnv'");
    }

    @Override
    public List<Formularweg> getFormularwegList() {
        return List.of(formularweg);
    }

}

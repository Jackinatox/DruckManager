package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.List;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg3;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class ThreePrintersProgram extends BaseProgram{
    private Formularweg3 formularweg;

    public ThreePrintersProgram(String name, String prefix, XMLWorker xmlWorker) {
        this.name = name;
        this.prefix = prefix;   
        this.xmlWorker = xmlWorker;
        this.formularweg = new Formularweg3("Formularweg", '0', xmlWorker);
    }

    @Override
    public void addPrinter(String env, PrinterDef printer, Boolean enabled) {
        formularweg.setPrinter(env.charAt(10), printer, enabled);
    }

    @Override
    public List<Formularweg> getFormularwegList() {
        return List.of(formularweg);
    }

    @Override
    public String buildEnv() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildEnv'");
    }
    
}

package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg3;
import de.fernausoft.druckmanager.xml.PrinterWrapper;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.KeyvalueDef;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class ThreePrintersProgram extends BaseProgram {
    private Formularweg3 formularweg;

    public ThreePrintersProgram(String name, String prefix, XMLWorker xmlWorker) {
        this.name = name;
        this.prefix = prefix;
        this.xmlWorker = xmlWorker;
        this.formularweg = new Formularweg3("Formularweg", '0', xmlWorker);
    }

    @Override
    public void addPrinter(KeyvalueDef env, PrinterDef printer) {
        PrinterWrapper printerWrapper = new PrinterWrapper(printer, env.isEnabled(), env.isPrinterDialog());

        formularweg.setPrinter(env.getEnv().charAt(10), printerWrapper);
    }

    @Override
    public List<Formularweg> getFormularwegList() {
        return List.of(formularweg);
    }

    @Override
    public List<KeyvalueDef> buildEnvs() {
        List<KeyvalueDef> envs = new ArrayList<>();

        Integer i = 1;
        for (PrinterWrapper printer : List.of(formularweg.getPrinter1(), formularweg.getPrinter2(),
                formularweg.getPrinter3())) {
            if (printer.getPrinterDef() != null) {
                KeyvalueDef def = new KeyvalueDef();
                def.setEnabled(printer.getEnabled());
                def.setPrinterDialog(printer.getAskDialog());
                def.setEnv(prefix + i);
                def.setRef(printer.getPrinterDef().getRef());
                envs.add(def);
            }
            i++;
        }

        return envs;
    }

    // public Map<String, String> buildEnvssw() {
    // Map<String, String> envs = new HashMap<>();

    // if (formularweg.getPrinter1().getPrinterDef() != null) {
    // envs.put(prefix + formularweg.getFWayChar() + "1",
    // formularweg.getPrinter1().getPrinterDef().getRef());
    // }
    // if (formularweg.getPrinter2().getPrinterDef() != null) {
    // envs.put(prefix + formularweg.getFWayChar() + "2",
    // formularweg.getPrinter2().getPrinterDef().getRef());
    // }
    // if (formularweg.getPrinter3().getPrinterDef() != null) {
    // envs.put(prefix + formularweg.getFWayChar() + "3",
    // formularweg.getPrinter3().getPrinterDef().getRef());
    // }

    // return envs;
    // }

}

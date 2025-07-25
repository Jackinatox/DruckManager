package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.ArrayList;
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
    private String sufix;

    // This is only for teh ones with multiple FormularWege and multiple printer
    public DefaultLayoutProgram(String name, String prefix, String sufix, XMLWorker xmlWorker) {
        formularwegMap = new java.util.HashMap<>();
        this.name = name;
        this.prefix = prefix;
        this.sufix = sufix;
        this.xmlWorker = xmlWorker;

        Formularweg3 weg;
        for (char c = '0'; c <= '8'; c++) {
            weg = new Formularweg3("Formularweg", c, xmlWorker);
            formularwegMap.put(c, weg);
        }
    }

    @Override
    public void addPrinter(KeyvalueDef env, PrinterDef printer) {
        Formularweg3 weg = formularwegMap.get(env.getEnv().charAt(6));
        PrinterWrapper printerWrapper = new PrinterWrapper(printer, env.isEnabled(),
                env.isPrinterDialog() || printer == null);

        char printerIndex = env.getEnv().charAt(10);
        weg.addPrinter(printerIndex, printerWrapper);
    }

    @Override
    public List<Formularweg> getFormularwegList() {
        return List.copyOf(formularwegMap.values());
    }

    @Override
    public List<KeyvalueDef> buildEnvs() {
        List<KeyvalueDef> envs = new ArrayList<>();

        for (Map.Entry<Character, Formularweg3> entry : formularwegMap.entrySet()) {
            Integer i = 1;
            Formularweg3 formularweg = entry.getValue();

            for (PrinterWrapper printer : List.of(formularweg.getPrinter1(), formularweg.getPrinter2(),
                    formularweg.getPrinter3())) {

                if (printer.getAskDialog() || printer.getPrinterDef() != null) {
                    KeyvalueDef def = new KeyvalueDef();

                    def.setEnv(prefix + formularweg.getFWayChar() + sufix + i);
                    def.setEnabled(printer.getEnabled());
                    def.setPrinterDialog(printer.getAskDialog());

                    def.setRef(printer.getPrinterDef() != null ? printer.getPrinterDef().getRef() : null);

                    envs.add(def);
                }
                i++;
            }
        }

        return envs;

    }

    /**
     * Indicates whether the current layout program has been edited.
     * <p>
     * This method overrides the corresponding method in the superclass.
     * </p>
     *
     * @return {@code true} if the layout program has been edited; {@code false} otherwise.
     * @see super#getEdited()
     */
    @Override
    public boolean getEdited() {
        for (Map.Entry<Character, Formularweg3> entry : formularwegMap.entrySet()) {
            if (entry.getValue().getEdited()) {
                return true;
            }
        }
        return false;
    }
}

package de.fernausoft.druckmanager.ui.panels.Settings.Programs.Werkstatt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.BaseProgram;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class WerkstattAuftrag extends BaseProgram {
    static Map<Character, Formularweg> map = new java.util.HashMap<>();

    // private static WerkstattAuftrag instance;

    // private WerkstattAuftrag(String env) {
    // super();
    // }

    // public static synchronized WerkstattAuftrag getInstance(String env) {
    // if (instance == null) {
    // instance = new WerkstattAuftrag(env);
    // }
    // return instance;
    // }

    public static void add(String env, PrinterDef printer) {
        char formularId = env.charAt(5);

        WerkstattFormularweg werkstattFormularweg = (WerkstattFormularweg) map.get(formularId);
        if (werkstattFormularweg == null) {
            map.put(formularId, new WerkstattFormularweg(env, printer));
        } else {
            werkstattFormularweg.addPrinter(env, printer);
        }
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Werkstatt Auftrag";
    }

    @Override
    public List<Formularweg> getFormularweg() {
        return new ArrayList<>(map.values());
    }
}

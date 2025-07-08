package de.fernausoft.druckmanager.ui.panels.Settings.Programs.Werkstatt;

import java.util.Map;

import de.fernausoft.druckmanager.ui.panels.Settings.Programs.BaseProgram;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class WerkstattAuftrag extends BaseProgram {

    // private static WerkstattAuftrag instance;
    static Map<Character, WerkstattFormularweg> map = new java.util.HashMap<>();

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
        
        WerkstattFormularweg werkstattFormularweg = map.get(formularId);
        if (werkstattFormularweg == null) {
            map.put(formularId, new WerkstattFormularweg(env, printer));
        } else {
            werkstattFormularweg.addPrinter(env, printer);
        }
    }
}

package de.fernausoft.druckmanager.ui.panels.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fernausoft.druckmanager.ui.panels.Settings.Programs.BaseProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.DefaultLayoutProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.OnlyOnePrinterProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.ThreePrintersProgram;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.KeyvalueDef;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.TargetDef;

public class Target {
    private TargetDef target;
    private Map<ProgramType, BaseProgram> programMap = new HashMap<>();
    private XMLWorker xmlWorker;

    private static final Logger logger = LogManager.getLogger(Target.class);

    public Target(TargetDef targetDef, XMLWorker xmlWorker) {
        super();
        this.target = targetDef;
        this.xmlWorker = xmlWorker;

        try {
            long startTime = System.currentTimeMillis();
            for (KeyvalueDef keyValue : target.getEnv()) {
                String env = keyValue.getEnv();
                PrinterDef printer = xmlWorker.printerLookup(keyValue.getRef());

                if (printer == null) {
                    String target = (targetDef.getUsername() != null && !targetDef.getUsername().isEmpty())
                            ? targetDef.getUsername()
                            : targetDef.getHostname();

                    logger.warn("Printer not found for target: " + target + " ENV: " + env);

                    continue;
                }
                var resolvedType = ProgramTypeResolver.resolveType(env);
                switch (resolvedType) {
                    case WERKSTATT_AUFTRAG:
                    case WERKSTATT_LIEFERSCHEIN:
                    case WERKSTATT_RECHNUNG:
                    case NEU_GEBRAUCHT_WAGEN_AUFTRAG:
                    case NEU_GEBRAUCHT_WAGEN_LIEFERSCHEIN:
                    case NEU_GEBRAUCHT_WAGEN_RECHNUNG:
                        handleDefaultLayoutProgram(resolvedType, env, printer, keyValue.isEnabled());
                        break;
                    case LAGER_ZU_ABGANGS_BUCHUNG:
                    case BESTELLUNGEN_PER_FAX:
                    case LAGERZUGANG_AUS_BESTELLUNG:
                    case ETIKETTENDRUCK_TEILE:
                    case BONDRUCK_TEILERECHNUNGEN:
                    case SAMMELRECHNUNGEN:
                    case GARANTIERUECKNAHME_GWE:
                    case NEUGEBRAUCHTWAGEN_ANGEBOTE_F0:
                    case NEUGEBRAUCHTWAGEN_ANGEBOTE_F1:
                    case FAHRZEUG_ANKAUF:
                    case SHOP_BARVERKAUF:
                    case BON_BOXENSTOP:
                    case LEIH_WAGEN_VERTRÄGE:
                    case LEIH_WAGEN_RECHNUNG:
                    case ETIKETTEN_REIFEN_EINLAGEERUNG:
                    case REIFEN_EINLAGERUNG:
                    case KUNDENKARTEN:
                    case LAGERENTNAHME_SCHEIN:
                        handleThreePrintersProgram(resolvedType, env, printer, keyValue.isEnabled());
                        break;
                    case DRUCK_AUS_KASSENABWICKLUNG:
                    case UEBERWEISUNGSTRAEGER:
                    case PICKERZETTEL_WERKSTATT:
                        handleOnlyOnePrinterProgram(resolvedType, env, printer, keyValue.isEnabled());
                        break;
                    case UNBEKANNT:
                    default:
                        logger.error("Unknown program type for environment: " + env);
                        break;

                }

            }
            long endTime = System.currentTimeMillis();
            logger.info("Target replica created successfully, Time: " + (endTime - startTime) + " ms");
        } catch (Exception e) {
            logger.error("creating Target replica failed: " + e.getMessage());
        }
    }

    private void handleThreePrintersProgram(ProgramType type, String env, PrinterDef printer, Boolean enabled) {
        ThreePrintersProgram threePrintersProgram = (ThreePrintersProgram) programMap.get(type);
        if (threePrintersProgram == null) {
            threePrintersProgram = new ThreePrintersProgram(type.toString(), env.substring(0, 8),
                    xmlWorker);
            programMap.put(type, threePrintersProgram);
        }

        threePrintersProgram.addPrinter(env, printer, enabled);
    }

    private void handleOnlyOnePrinterProgram(ProgramType type, String env, PrinterDef printer, Boolean enabled) {
        OnlyOnePrinterProgram onlyOnePrinterProgram = (OnlyOnePrinterProgram) programMap.get(type);
        if (onlyOnePrinterProgram == null) {
            onlyOnePrinterProgram = new OnlyOnePrinterProgram(type.toString(), env.substring(0, 9), xmlWorker);
            programMap.put(type, onlyOnePrinterProgram);
        } else {
            logger.warn(
                    "Doppelte Konfiguration gefunden für: " + type + " in " + target.getHostname() + " ENV: " + env);
        }

        onlyOnePrinterProgram.addPrinter(env, printer, enabled);
    }

    private void handleDefaultLayoutProgram(ProgramType type, String env, PrinterDef printer, Boolean enabled) {
        DefaultLayoutProgram defaultLayoutProgram = (DefaultLayoutProgram) programMap.get(type);
        if (defaultLayoutProgram == null) {
            defaultLayoutProgram = new DefaultLayoutProgram(type.toString(), env.substring(0, 5), env.substring(7, 8),
                    xmlWorker);
            programMap.put(type, defaultLayoutProgram);
        }

        defaultLayoutProgram.addPrinter(env, printer, enabled);
    }

    public String getHostname() {
        return target.getHostname();
    }

    public String getUsername() {
        return target.getUsername();
    }

    public List<BaseProgram> getPrograms() {
        return new ArrayList<>(programMap.values());
    }

}

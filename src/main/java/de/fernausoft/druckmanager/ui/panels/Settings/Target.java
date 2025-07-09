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
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.KeyvalueDef;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.TargetDef;

public class Target {
    private TargetDef target;
    private Map<ProgramType, BaseProgram> programMap = new HashMap<>();

    private static final Logger logger = LogManager.getLogger(Target.class);

    public Target(TargetDef targetDef, XMLWorker xmlWorker) {
        super();
        this.target = targetDef;

        try {
            long startTime = System.currentTimeMillis();
            for (KeyvalueDef keyValue : target.getEnv()) {
                String env = keyValue.getEnv();
                PrinterDef printer = xmlWorker.printerLookup(keyValue.getRef());

                if (printer == null) {
                    logger.warn("Printer not found for environment: " + env);
                    continue;
                }

                switch (ProgramTypeResolver.resolveType(env)) {
                    case WERKSTATT_AUFTRAG:
                        // DR_3030AU_1
                        handleDefaultLayoutProgram(ProgramType.WERKSTATT_AUFTRAG, env, printer);
                        break;
                    case BESTELLUNGEN_PER_FAX:
                        break;
                    case BONDRUCK:
                        break;
                    case BONDRUCK_TEILERECHNUNGEN:
                        break;
                    case ETIKETTEN:
                        break;
                    case ETIKETTENDRUCK_TEILE:
                        break;
                    case GARANTIERUECKNAHME_GWE:
                        break;
                    case KF1_ANGEBOTE:
                        break;
                    case KFZ_DOKUMENTE:
                        break;
                    case KUNDENKARTEN:
                        break;
                    case LAGERENTNAHME:
                        break;
                    case LAGERZUGANG_AUS_BESTELLUNG:
                        break;
                    case LAGER_ZU_ABGANGS_BUCHUNG:
                        break;
                    case NEU_GEBRAUCHT_WAGEN_AUFTRAG:
                        break;
                    case NEU_GEBRAUCHT_WAGEN_LIEFERSCHEIN:
                        break;
                    case NEU_GEBRAUCHT_WAGEN_RECHNUNG:
                        break;
                    case PICKERZETTEL_WERKSTATT:
                        break;
                    case SAMMELRECHNUNGEN:
                        break;
                    case UEBERWEISUNG:
                        break;
                    case UEBERWEISUNGSTRAEGER:
                        // DR_301UEB_1
                        handleOnlyOnePrinterProgram(ProgramType.UEBERWEISUNGSTRAEGER, env, printer);
                        break;
                    case UNBEKANNT:
                        break;
                    case WERKSTATT_LIEFERSCHEIN:
                        break;
                    case WERKSTATT_RECHNUNG:
                        break;
                    default:
                        break;

                }

                long endTime = System.currentTimeMillis();
                logger.info("Target replica created successfully, Time: " + (endTime - startTime) + " ms");
            }
        } catch (Exception e) {
            logger.error("creating Target replica failed: " + e.getMessage());
        }
    }

    private void handleOnlyOnePrinterProgram(ProgramType type, String env, PrinterDef printer) {
        OnlyOnePrinterProgram onlyOnePrinterProgram = (OnlyOnePrinterProgram) programMap.get(type);
        if (onlyOnePrinterProgram == null) {
            onlyOnePrinterProgram = new OnlyOnePrinterProgram(type.toString(), env.substring(0, 9));
            programMap.put(type, onlyOnePrinterProgram);
        } else {
            logger.warn("Doppelte Konfiguration gefunden für: " + type + " in " + target.getHostname() + " ENV: " + env);
        }

        onlyOnePrinterProgram.addPrinter(env, printer);
    }

    private void handleDefaultLayoutProgram(ProgramType type, String env, PrinterDef printer) {
        DefaultLayoutProgram defaultLayoutProgram = (DefaultLayoutProgram) programMap.get(type);
        if (defaultLayoutProgram == null) {
            defaultLayoutProgram = new DefaultLayoutProgram(type.toString(), env.substring(0, 6));
            programMap.put(type, defaultLayoutProgram);
        }

        defaultLayoutProgram.addPrinter(env, printer);
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

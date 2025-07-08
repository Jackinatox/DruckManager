package de.fernausoft.druckmanager.ui.panels.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg3;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.BaseProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.DefaultLayout;
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
                        Formularweg3 formularweg = new Formularweg3("Formularweg " + env.charAt(6), env.charAt(6), ProgramType.WERKSTATT_AUFTRAG);
                        formularweg.addPrinter(env, printer);

                        DefaultLayout defaultLayout = new DefaultLayout(ProgramType.WERKSTATT_AUFTRAG.name(),
                                env.substring(0, 6));

                        addEnv(ProgramType.WERKSTATT_AUFTRAG, defaultLayout, formularweg);

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

    private void addEnv(ProgramType type, BaseProgram base, Formularweg formularweg) {
        BaseProgram existingProgram;

        if (programMap.containsKey(type)) {
            existingProgram = programMap.get(type);
        } else {
            existingProgram = base;
        }

        existingProgram.addFormularweg(formularweg);

    }

}

package de.fernausoft.druckmanager.ui.panels.Settings;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fernausoft.druckmanager.ui.panels.Settings.Programs.Werkstatt.WerkstattAuftrag;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.KeyvalueDef;
import de.fernausoft.druckmanager.xml.schema.TargetDef;

public class Target {
    private TargetDef target;

    private static final Logger logger = LogManager.getLogger(Target.class);

    public Target(TargetDef targetDef, XMLWorker xmlWorker) {
        super();
        this.target = targetDef;
        // List<BaseProgram> programList = new ArrayList<>();

        try {
            long startTime = System.currentTimeMillis();
            for (KeyvalueDef keyValue : target.getEnv()) {
                String env = keyValue.getEnv();
                String ref = keyValue.getRef();

                switch(ProgramTypeResolver.resolveType(env)) {
                    case WERKSTATT_AUFTRAG:
                        WerkstattAuftrag.add(env, xmlWorker.printerLookup(ref));
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

}

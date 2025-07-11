package de.fernausoft.druckmanager.ui.panels.Settings;

import java.util.*;
import java.util.regex.*;

public class ProgramTypeResolver {
    private static final Map<String, ProgramType> exactMatches = new HashMap<>();
    private static final Map<Pattern, ProgramType> regexPatterns = new LinkedHashMap<>();

    static {
        // Exact matches      DR_301UEB_1
        exactMatches.put("DR_301UEB_1", ProgramType.UEBERWEISUNGSTRAEGER);

    
        // Regex-based patterns
        regexPatterns.put(Pattern.compile("DR_303\\dAU_\\d"), ProgramType.WERKSTATT_AUFTRAG);
        regexPatterns.put(Pattern.compile("DR_303\\dLI_\\d"), ProgramType.WERKSTATT_LIEFERSCHEIN);
        regexPatterns.put(Pattern.compile("DR_303\\dRE_\\d"), ProgramType.WERKSTATT_RECHNUNG);
        regexPatterns.put(Pattern.compile("DR_660\\dAU_\\d"), ProgramType.NEU_GEBRAUCHT_WAGEN_AUFTRAG);
        regexPatterns.put(Pattern.compile("DR_660\\dLI_\\d"), ProgramType.NEU_GEBRAUCHT_WAGEN_LIEFERSCHEIN);
        regexPatterns.put(Pattern.compile("DR_660\\dRE_\\d"), ProgramType.NEU_GEBRAUCHT_WAGEN_RECHNUNG);
        regexPatterns.put(Pattern.compile("DR_251KFZ_\\d"), ProgramType.LAGER_ZU_ABGANGS_BUCHUNG);
        regexPatterns.put(Pattern.compile("DR_256KFZ_\\d"), ProgramType.BESTELLUNGEN_PER_FAX);
        regexPatterns.put(Pattern.compile("DR_258KFZ_\\d"), ProgramType.LAGERZUGANG_AUS_BESTELLUNG);
        regexPatterns.put(Pattern.compile("DR_288KFZ_\\d"), ProgramType.ETIKETTENDRUCK_TEILE);
        regexPatterns.put(Pattern.compile("DR_303BON_\\d"), ProgramType.BONDRUCK_TEILERECHNUNGEN);
        regexPatterns.put(Pattern.compile("DR_304KFZ_\\d"), ProgramType.SAMMELRECHNUNGEN);
        regexPatterns.put(Pattern.compile("DR_518GWE_\\d"), ProgramType.GARANTIERUECKNAHME_GWE);
        regexPatterns.put(Pattern.compile("DR_677KFZ_\\d"), ProgramType.NEUGEBRAUCHTWAGEN_ANGEBOTE_F0);
        regexPatterns.put(Pattern.compile("DR_677KF1_\\d"), ProgramType.NEUGEBRAUCHTWAGEN_ANGEBOTE_F1); 
        regexPatterns.put(Pattern.compile("DR_701KFZ_\\d"), ProgramType.SHOP_BARVERKAUF); 
        regexPatterns.put(Pattern.compile("DR_721KFZ_\\d"), ProgramType.BON_BOXENSTOP); 
        regexPatterns.put(Pattern.compile("DR_806KFZ_\\d"), ProgramType.LEIH_WAGEN_VERTRÄGE); 
        regexPatterns.put(Pattern.compile("DR_811KFZ_\\d"), ProgramType.LEIH_WAGEN_RECHNUNG); 
        regexPatterns.put(Pattern.compile("DR_903KFZ_\\d"), ProgramType.REIFEN_EINLAGERUNG); 
        //DR_903KFZ_1
        regexPatterns.put(Pattern.compile("DR_KARTEN_\\d"), ProgramType.KUNDENKARTEN); 
        regexPatterns.put(Pattern.compile("DR_LENTNA_\\d"), ProgramType.LAGERENTNAHME_SCHEIN); 




    }

    public static ProgramType resolveType(String code) {
        // 1. Check exact matches
        if (exactMatches.containsKey(code)) {
            return exactMatches.get(code);
        }

        // 2. Regex pattern matches
        for (Map.Entry<Pattern, ProgramType> entry : regexPatterns.entrySet()) {
            if (entry.getKey().matcher(code).matches()) {
                return entry.getValue();
            }
        }

        // 3. Not found
        return ProgramType.UNBEKANNT;
    }

    // Gibt den Beschreibungs string zurück
    public static String resolveTypeLabel(String code) {
        return resolveType(code).getLabel();
    }
}

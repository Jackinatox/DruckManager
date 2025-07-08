package de.fernausoft.druckmanager.ui.panels.Settings;

import java.util.*;
import java.util.regex.*;

public class ProgramTypeResolver {
    private static final Map<String, ProgramType> exactMatches = new HashMap<>();
    private static final Map<Pattern, ProgramType> regexPatterns = new LinkedHashMap<>();

    static {
        // Exact matches
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

    public static void main(String[] args) {
        List<String> testCodes = List.of(
                "DR_3030AU_1", "DR_256KFZ_1", "DR_903ETI_1", "DR_PICKER_1", "DR_UNKNOWN_1");

        for (String code : testCodes) {
            ProgramType type = resolveType(code);
            System.out.printf("%s → %s (%s)%n", code, type.name(), type.getLabel());
        }
    }
}

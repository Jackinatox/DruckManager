package de.fernausoft.druckmanager.ui.panels.Settings;

public enum ProgramType {
    WERKSTATT_AUFTRAG("Werkstatt Auftrag", "DR_303", "AU_"),
    WERKSTATT_LIEFERSCHEIN("Werkstatt Lieferschein", "DR_303", "LI_"),
    WERKSTATT_RECHNUNG("Werkstatt Rechnung", "DR_303", "RE_"),
    NEU_GEBRAUCHT_WAGEN_AUFTRAG("Neu/Gebraucht Wagen Auftrag", "DR_660", "AU_"),
    NEU_GEBRAUCHT_WAGEN_LIEFERSCHEIN("Neu/Gebraucht Wagen Lieferschein", "DR_660", "LI_"),
    NEU_GEBRAUCHT_WAGEN_RECHNUNG("Neu/Gebraucht Wagen Rechnung", "DR_660", "RE_"),

    LAGER_ZU_ABGANGS_BUCHUNG("Lagerzu-/abgangsbuchung", "DR_251KFZ_"),
    BESTELLUNGEN_PER_FAX("Bestellungen per Fax", "DR_256KFZ_"),
    LAGERZUGANG_AUS_BESTELLUNG("Lagerzugang aus Bestellung", "DR_258KFZ_"),
    ETIKETTENDRUCK_TEILE("Etikettendruck Teile", "DR_288KFZ_"),
    BONDRUCK_TEILERECHNUNGEN("Bondruck Teilerechnungen", "DR_303BON_"),
    SAMMELRECHNUNGEN("Sammelrechnungen", "DR_304KFZ_"),
    GARANTIERUECKNAHME_GWE("Garantierücknahme GWE", "DR_518GWE_"),

    NEUGEBRAUCHTWAGEN_ANGEBOTE_F0("Neu- & Gebrauchtwagen Angebote Formularweg 0", "DR_677KFZ_"),
    NEUGEBRAUCHTWAGEN_ANGEBOTE_F1("Neu- & Gebrauchtwagen Angebote Formularweg 1", "DR_677KF1_"),

    FAHRZEUG_ANKAUF("Fahrzeugankauf", "DR_681KFZ_"),
    SHOP_BARVERKAUF("Shop/Barverkauf", "DR_701KFZ_"),
    BON_BOXENSTOP("Bon Boxenstop", "DR_721KFZ_"),

    LEIH_WAGEN_VERTRAEGE("Leihwagenverträge", "DR_806KFZ_"),
    LEIH_WAGEN_RECHNUNG("Leihwagenrechnungen", "DR_811KFZ_"),
    ETIKETTEN_REIFEN_EINLAGEERUNG("Etiketten Reifeneinlagerungen", "DR_903ETI_"),
    REIFEN_EINLAGERUNG("Reifeneinlagerungen", "DR_903KFZ_"),
    KUNDENKARTEN("Kundenkarten bedrucken", "DR_KARTEN_"),
    LAGERENTNAHME_SCHEIN("Lagerentnahmeschein", "DR_LENTNA_"),

    DRUCK_AUS_KASSENABWICKLUNG("Druck aus Kassenabwicklung", "DR_751KFZ_"),
    UEBERWEISUNGSTRAEGER("Überweisungsträger", "DR_301UEB_"),
    PICKERZETTEL_WERKSTATT("Pickerzettel Werkstatt", "DR_PICKER_"),

    // Fallback
    UNBEKANNT("Unbekannter Dokumententyp");

    private final String label;
    private String prefix;
    private String sufix;

    ProgramType(String label, String prefix, String sufix) {
        this.label = label;
        this.prefix = prefix;
        this.sufix = sufix;
    }

    ProgramType(String label, String prefix) {
        this(label, prefix, null);
    }

    ProgramType(String label) {
        this(label, null, null);
    }

    public String getLabel() {
        return label;
    }

    public String getPrefix() {
    return prefix;
    }

    public String getSufix() {
    return sufix;
    }

    public String getRegex() {
        return prefix + (sufix == null ? "\\d" : "\\d" + sufix + "\\d");
    }
}
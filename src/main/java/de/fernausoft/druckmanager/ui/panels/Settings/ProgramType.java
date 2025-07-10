package de.fernausoft.druckmanager.ui.panels.Settings;

public enum ProgramType {
    WERKSTATT_AUFTRAG("Werkstatt Auftrag"),
    WERKSTATT_LIEFERSCHEIN("Werkstatt Lieferschein"),
    WERKSTATT_RECHNUNG("Werkstatt Rechnung"),
    NEU_GEBRAUCHT_WAGEN_AUFTRAG("Neu/Gebraucht Wagen Auftrag"),
    NEU_GEBRAUCHT_WAGEN_LIEFERSCHEIN("Neu/Gebraucht Wagen Lieferschein"),
    NEU_GEBRAUCHT_WAGEN_RECHNUNG("Neu/Gebraucht Wagen Rechnung"),

    LAGER_ZU_ABGANGS_BUCHUNG("Lagerzu-/abgangsbuchung"),
    BESTELLUNGEN_PER_FAX("Bestellungen per Fax"),
    LAGERZUGANG_AUS_BESTELLUNG("Lagerzugang aus Bestellung"),
    ETIKETTENDRUCK_TEILE("Etikettendruck Teile"),
    UEBERWEISUNGSTRAEGER("Überweisungsträger"),
    BONDRUCK_TEILERECHNUNGEN("Bondruck Teilerechnungen"),
    SAMMELRECHNUNGEN("Sammelrechnungen"),
    GARANTIERUECKNAHME_GWE("Garantierücknahme GWE"),

    NEUGEBRAUCHTWAGEN_ANGEBOTE_F0("Neu- & Gebrauchtwagen Angebote Formularweg 0"),
    NEUGEBRAUCHTWAGEN_ANGEBOTE_F1("Neu- & Gebrauchtwagen Angebote Formularweg 1"),

    FAHRZEUG_ANKAUF("Fahrzeugankauf"),
    SHOP_BARVERKAUF("Shop/Barverkauf"),
    BON_BOXENSTOP("Bon Boxenstop"),

    LEIH_WAGEN_VERTRÄGE("Leihwagenverträge"),
    LEIH_WAGEN_RECHNUNG("Leihwagenrechnungen"),
    ETIKETTEN_REIFEN("Etiketten Reifeneinlagerungen"),
    REIFEN_EEINLAGERUNG("Reifeneinlagerungen"),
    KUNDENKARTEN("Kundenkarten bedrucken"),
    LAGERENTNAHME("Lagerentnahmeschein"),
    
    DRUCK_AUS_KASSENABWICKLUNG("Druck aus Kassenabwicklung"),
    UEBERWEISUNG("Überweisungsträger"),
    PICKERZETTEL_WERKSTATT("Pickerzettel Werkstatt"),
    
    // Fallback
    UNBEKANNT("Unbekannter Dokumententyp");

    private final String label;

    ProgramType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
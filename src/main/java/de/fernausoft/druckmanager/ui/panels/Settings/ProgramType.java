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

    PICKERZETTEL_WERKSTATT("Pickerzettel Werkstatt"),
    KUNDENKARTEN("Kundenkarten bedrucken"),
    LAGERENTNAHME("Lagerentnahmeschein"),

    KFZ_DOKUMENTE("KFZ Dokumente"),
    KF1_ANGEBOTE("KF1 Angebote Formularweg 1"),
    BONDRUCK("Bondruck Teilerechnungen"),
    UEBERWEISUNG("Überweisungsträger"),
    ETIKETTEN("Etiketten Reifeneinlagerungen"),

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
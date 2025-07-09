package de.fernausoft.druckmanager.ui.panels.Settings.Formularweg;

abstract public class Formularweg {
    protected char FWayChar;
    protected String name;

    public Formularweg(String name, char FWayChar) {
        this.name = name;
        this.FWayChar = FWayChar;
    }

    public String getName() {
        return name;
    }

    public char getFWayChar() {
        return FWayChar;
    }

    @Override
    public String toString() {
        return getName();
    }
}

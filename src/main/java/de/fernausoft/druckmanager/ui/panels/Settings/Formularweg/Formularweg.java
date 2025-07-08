package de.fernausoft.druckmanager.ui.panels.Settings.Formularweg;

import de.fernausoft.druckmanager.ui.panels.Settings.ProgramType;

abstract public class Formularweg {
    protected ProgramType type;
    protected char FWayChar;
    protected String name;

    public Formularweg(String name, char FWayChar, ProgramType type) {
        this.name = name;
        this.FWayChar = FWayChar;
        this.type = type;
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

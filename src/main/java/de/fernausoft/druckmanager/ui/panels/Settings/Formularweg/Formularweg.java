package de.fernausoft.druckmanager.ui.panels.Settings.Formularweg;

abstract public class Formularweg {
    public Formularweg() {
    }

    abstract protected String getName();

    @Override
    public String toString() {
        return getName();
    }
}

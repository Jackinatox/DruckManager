package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.List;
import java.util.Map;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;

public abstract class BaseProgram {
    public BaseProgram() {
    }

    public abstract String getName();
    public abstract List<Formularweg> getFormularweg();


    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
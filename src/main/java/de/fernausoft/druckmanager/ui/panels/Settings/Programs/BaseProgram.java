package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.Map;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;

public abstract class BaseProgram {
    protected String name;
    protected String prefix;
    protected Map<Character, Formularweg> formularwegMap;

    protected BaseProgram() {
    }

    public abstract void addFormularweg(Formularweg formularweg);

    public abstract String getName();

    public abstract String buildEnv();

}
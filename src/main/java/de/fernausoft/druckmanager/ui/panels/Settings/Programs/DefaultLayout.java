package de.fernausoft.druckmanager.ui.panels.Settings.Programs;

import java.util.List;

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;

public class DefaultLayout extends BaseProgram {

    public DefaultLayout(String name, String prefix) {
        formularwegMap = new java.util.HashMap<>();
        this.name = name;
        this.prefix = prefix;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String buildEnv() {
        throw new UnsupportedOperationException("Unimplemented method 'buildEnv'");
    }

    @Override
    public void addFormularweg(Formularweg formularweg) {
        formularwegMap.put(formularweg.getFWayChar(), formularweg);
    }
}

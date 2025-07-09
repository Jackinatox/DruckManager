package de.fernausoft.druckmanager.ui.panels.Settings.panles;


import javax.swing.JPanel;

public abstract class BasePanel {
    protected JPanel panel;

    public BasePanel() {
        this.panel = new JPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

}

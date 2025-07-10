package de.fernausoft.druckmanager.ui.panels;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.*;

import de.fernausoft.druckmanager.ui.models.PCUserTableModel;
import de.fernausoft.druckmanager.ui.panels.Settings.Target;

import de.fernausoft.druckmanager.ui.listeners.PCUserSelectionListener;

public class PCUserMappingPanel extends JPanel {
    private PCUserSelectionListener listener;
    private JTable table;
    private PCUserTableModel tableModel;

    // TODO: also a method for the callback so teh other component knows when to
    // change
    public PCUserMappingPanel(List<Target> targets) {
        setLayout(new BorderLayout());
        tableModel = new PCUserTableModel(targets);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listener != null) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Target selectedTarget = tableModel.getTargetAt(selectedRow);
                    listener.onUserSelected(selectedTarget);
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }

    }

    public void setPcUserSelectionListener(PCUserSelectionListener listener) {
        this.listener = listener;
    }
}

package de.fernausoft.druckmanager.ui.panels;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.*;

import de.fernausoft.druckmanager.ui.models.PCUserTableModel;
import de.fernausoft.druckmanager.ui.panels.Settings.Target;

public class PCUserMappingPanel extends JPanel{
    private JTable table;
    private PCUserTableModel tableModel;
                                            // TODO: also a method for the callback so teh other component knows when to change
    public PCUserMappingPanel(List<Target> targets) {
        setLayout(new BorderLayout());        
        tableModel = new PCUserTableModel(targets);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}

package de.fernausoft.druckmanager.ui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.*;

import de.fernausoft.druckmanager.ui.models.PCUserTableModel;
import de.fernausoft.druckmanager.ui.panels.Settings.Target;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.TargetDef;
import de.fernausoft.druckmanager.ui.listeners.PCUserSelectionListener;

public class PCUserMappingPanel extends JPanel {
    private PCUserSelectionListener listener;
    private JTable table;
    private PCUserTableModel tableModel;
    private XMLWorker xmlWorker;
    private List<Target> targets;

    public PCUserMappingPanel(List<Target> targets, XMLWorker xmlWorker) {
        this.xmlWorker = xmlWorker;
        this.targets = targets;

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

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        JButton createButton = new JButton("Create");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);

        // Add the button panel to the south of the main panel
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners for the buttons
        createButton.addActionListener(this::createTarget);
        deleteButton.addActionListener(this::deleteTarget);
    }

    private void createTarget(ActionEvent e) {
        // For simplicity, let's ask for a username. A more complex UI could be used.
        String userName = JOptionPane.showInputDialog(this, "Enter username for new target:");
        if (userName != null && !userName.trim().isEmpty()) {
            TargetDef newTargetDef = new TargetDef();
            newTargetDef.setUsername(userName.trim());
            Target newTarget = new Target(newTargetDef, xmlWorker);
            targets.add(newTarget);
            tableModel.fireTableDataChanged();
        }
    }

    private void deleteTarget(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            targets.remove(selectedRow);
            tableModel.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a target to delete.", "No Target Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void selectFirstRow() {
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
    }

    public void setPcUserSelectionListener(PCUserSelectionListener listener) {
        this.listener = listener;
    }
}

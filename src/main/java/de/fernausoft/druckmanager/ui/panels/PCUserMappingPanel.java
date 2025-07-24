package de.fernausoft.druckmanager.ui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import de.fernausoft.druckmanager.ui.listeners.PCUserSelectionListener;
import de.fernausoft.druckmanager.ui.models.PCUserTableModel;
import de.fernausoft.druckmanager.ui.panels.Settings.Target;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.TargetDef;

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

        table.setRowHeight(table.getRowHeight() + 10); // Increase row height by 10 pixels
        table.setIntercellSpacing(new java.awt.Dimension(1, 1)); // Add horizontal and vertical spacing

        // Set alternating row colors (zebra striping)
        table.setShowGrid(true);
        table.setGridColor(new java.awt.Color(200, 200, 200)); // Lighter grid lines

        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component comp = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        comp.setBackground(new java.awt.Color(240, 240, 250)); // Light blue-gray
                    } else {
                        comp.setBackground(java.awt.Color.WHITE);
                    }
                }
                if (comp instanceof JLabel label) {
                    label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // top, left, bottom, right
                }
                return comp;
            }
        });
        // setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new
        // java.awt.Color(120, 120, 120)),
        // "PCs/Nutzer",
        // javax.swing.border.TitledBorder.LEFT,
        // javax.swing.border.TitledBorder.TOP));

        setLayout(new java.awt.BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120)),
                                "PCs/Nutzer",
                                javax.swing.border.TitledBorder.LEFT,
                                javax.swing.border.TitledBorder.TOP),
                        BorderFactory.createEmptyBorder(4, 4, 0, 4))));
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listener != null) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Target selectedTarget = tableModel.getTargetAt(selectedRow);
                    listener.onUserSelected(selectedTarget);
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();
                    if (row < 0)
                        return; // No row selected

                    Target target = tableModel.getTargetAt(row);
                    switch (column) {
                        case 0: // First column, so Printer
                            String newName = JOptionPane.showInputDialog(
                                    PCUserMappingPanel.this,
                                    "Hostname ändern:",
                                    target.getHostname());

                            if (newName != null && !newName.trim().isEmpty()
                                    && !newName.trim().equals(target.getHostname())) {
                                String trimmedName = newName.trim();
                                String oldName = target.getHostname();

                                if (!trimmedName.equals(oldName) && !trimmedName.isEmpty()) {

                                    // boolean exists = targets.stream()
                                    // .anyMatch(p -> p.getHostname().equals(trimmedName));
                                    // if (exists) {
                                    // JOptionPane.showMessageDialog(
                                    // PCUserMappingPanel.this,
                                    // "Ein PC mit diesem Hostnamen existiert bereits.",
                                    // "Fehler",
                                    // JOptionPane.ERROR_MESSAGE);
                                    // return;
                                    // } else {
                                    target.setHostname(trimmedName);
                                    tableModel.fireTableRowsUpdated(row, row);
                                    // }
                                }
                            }
                            break;
                        case 1: // Second Columns eg Description
                            String newDesc = JOptionPane.showInputDialog(
                                    PCUserMappingPanel.this,
                                    "Username:",
                                    target.getUsername());

                            if (newDesc != null) {
                                String trimmedName = newDesc.trim();
                                if (newDesc != null && !trimmedName.equals(target.getUsername())) {
                                    String oldName = target.getUsername();

                                    if (!trimmedName.equals(oldName)) {
                                        target.setUsername(trimmedName);
                                        tableModel.fireTableRowsUpdated(row, row);
                                    }
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 6));
        JButton createButton = new JButton("Erstellen");
        JButton deleteButton = new JButton("Löschen");
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
        String newHostname = JOptionPane.showInputDialog(this, "Hostname:");
        if (newHostname != null && !newHostname.trim().isEmpty()) {
            TargetDef newTargetDef = new TargetDef();
            newTargetDef.setHostname(newHostname.trim());
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
            if (table.getRowCount() > 0) {
                int selectNow = Math.min(table.getRowCount() - 1, selectedRow);
                table.setRowSelectionInterval(selectNow, selectNow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bitte ein Mapping auswählen.", "Kein Mapping Ausgewählt",
                    JOptionPane.WARNING_MESSAGE);
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

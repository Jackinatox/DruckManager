package de.fernausoft.druckmanager.ui.panels;

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

import de.fernausoft.druckmanager.ui.models.PrinterTableModel;
import de.fernausoft.druckmanager.ui.panels.Settings.Target;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.PrintersDef;

public class PrinterTablePanel extends JPanel {
    private JTable table;
    private PrinterTableModel tableModel;
    // private PrintersDef printers;
    private XMLWorker xmlWorker;
    private List<Target> myTargets;

    public PrinterTablePanel(PrintersDef printers, XMLWorker xmlWorker, List<Target> myTargets) {
        // this.printers = printers;
        this.xmlWorker = xmlWorker;
        this.myTargets = myTargets;
        setLayout(new java.awt.BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120)),
                                "Drucker",
                                javax.swing.border.TitledBorder.LEFT,
                                javax.swing.border.TitledBorder.TOP),
                        BorderFactory.createEmptyBorder(4, 4, 0, 4))));
        tableModel = new PrinterTableModel(printers, xmlWorker.getAskingPrinter(), xmlWorker.getLinePrinter());
        table = new JTable(tableModel);

        // Add padding to table cells
        table.setRowHeight(table.getRowHeight() + 10);

        table.setShowGrid(true);
        table.setGridColor(new java.awt.Color(200, 200, 200));

        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component comp = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        comp.setBackground(new java.awt.Color(240, 240, 250));
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

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();
                    if (row < 0)
                        return; // No row selected

                    PrinterDef printer = tableModel.getPrinterAt(row);
                    switch (column) {
                        case 0: // First column, so Printer
                            String newName = JOptionPane.showInputDialog(
                                    PrinterTablePanel.this,
                                    "Druckername ändern:",
                                    printer.getName());

                            if (newName != null && !newName.trim().isEmpty()
                                    && !newName.trim().equals(printer.getName())) {
                                String trimmedName = newName.trim();
                                String oldName = printer.getName();

                                if (!trimmedName.equals(oldName) && !trimmedName.isEmpty()) {

                                    boolean exists = printers.getPrinter().stream()
                                            .anyMatch(p -> p.getName().equals(trimmedName));
                                    if (exists) {
                                        JOptionPane.showMessageDialog(
                                                PrinterTablePanel.this,
                                                "Ein Drucker mit diesem Namen existiert bereits.",
                                                "Fehler",
                                                JOptionPane.ERROR_MESSAGE);
                                        return;
                                    } else {
                                        printer.setName(trimmedName);
                                        tableModel.fireTableRowsUpdated(row, row);
                                    }
                                }
                            }
                            break;
                        case 1: // Second Columns eg Description
                            String newDesc = JOptionPane.showInputDialog(
                                    PrinterTablePanel.this,
                                    "Drucker Beschreibung:",
                                    printer.getDescription());

                            if (newDesc != null) {
                                String trimmedName = newDesc.trim();
                                if (newDesc != null && !trimmedName.equals(printer.getDescription())) {
                                    String oldName = printer.getDescription();

                                    if (!trimmedName.equals(oldName)) {
                                        printer.setDescription(trimmedName);
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
        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 6));
        JButton createButton = new JButton("Erstellen");
        JButton deleteButton = new JButton("Löschen");
        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, java.awt.BorderLayout.SOUTH);

        // Add action listeners for the buttons
        createButton.addActionListener(this::createPrinter);
        deleteButton.addActionListener(this::deletePrinter);
    }

    private void createPrinter(ActionEvent e) {
        String printerName = JOptionPane.showInputDialog(this, "Namen für den Drucker(oder UNC):");
        if (printerName != null && !printerName.trim().isEmpty()) {
            PrinterDef newPrinter = xmlWorker.newPrinter(printerName.trim());

            if (newPrinter == null) {
                JOptionPane.showMessageDialog(this, "Drucker mit diesem Namen existiert bereits.", "Fehler",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            tableModel.fireTableDataChanged();
        }
    }

    private void deletePrinter(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            PrinterDef selectedPrinter = tableModel.getPrinterAt(selectedRow);
            xmlWorker.tryDeletePrinter(myTargets, selectedPrinter);
            tableModel.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Drucker zum Löschen aus.",
                    "Kein Drucker ausgewählt", JOptionPane.WARNING_MESSAGE);
        }
    }
}
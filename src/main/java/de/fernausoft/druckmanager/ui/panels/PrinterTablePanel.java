package de.fernausoft.druckmanager.ui.panels;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import de.fernausoft.druckmanager.ui.models.PrinterTableModel;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class PrinterTablePanel extends JPanel {
    private JTable table;
    private PrinterTableModel tableModel;

    public PrinterTablePanel(List<PrinterDef> printers) {
        // TODO:  Maybe add some validation so the name only contains valaid chars
        setLayout(new java.awt.BorderLayout());
        tableModel = new PrinterTableModel(printers);
        table = new JTable(tableModel);
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
                        case 0: // First column eg Printer
                            String newName = JOptionPane.showInputDialog(
                                    PrinterTablePanel.this,
                                    "Druckername ändern:",
                                    printer.getName());

                            if (newName != null && !newName.trim().isEmpty()
                                    && !newName.trim().equals(printer.getName())) {
                                String trimmedName = newName.trim();
                                String oldName = printer.getName();

                                if (!trimmedName.equals(oldName) && !trimmedName.isEmpty()) {

                                    boolean exists = printers.stream()
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
                                    printer.getDescritpion());

                            String trimmedName = newDesc.trim();
                            if (newDesc != null && !trimmedName.equals(printer.getDescritpion())) {
                                String oldName = printer.getDescritpion();

                                if (!trimmedName.equals(oldName)) {
                                    printer.setDescritpion(trimmedName);
                                    tableModel.fireTableRowsUpdated(row, row);
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
    }
}
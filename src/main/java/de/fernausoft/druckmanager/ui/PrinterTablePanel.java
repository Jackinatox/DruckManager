package de.fernausoft.druckmanager.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class PrinterTablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public PrinterTablePanel(List<PrinterDef> printers) {
        setLayout(new java.awt.BorderLayout());
        String[] columnNames = {"Name", "Beschreibung"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        for (PrinterDef printer : printers) {
            tableModel.addRow(new Object[]{printer.getName(), printer.getDescritpion()});
        }
        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);
    }
}

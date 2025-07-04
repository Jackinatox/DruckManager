package de.fernausoft.druckmanager.ui.panels;

import javax.swing.*;
import java.util.List;

import de.fernausoft.druckmanager.ui.models.PrinterTableModel;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class PrinterTablePanel extends JPanel {
    private JTable table;
    private PrinterTableModel tableModel;

    public PrinterTablePanel(List<PrinterDef> printers) {
        setLayout(new java.awt.BorderLayout());
        tableModel = new PrinterTableModel(printers);
        table = new JTable(tableModel);
        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);
    }
}

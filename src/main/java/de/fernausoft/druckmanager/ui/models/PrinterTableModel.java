package de.fernausoft.druckmanager.ui.models;

import javax.swing.table.AbstractTableModel;

import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.PrintersDef;

public class PrinterTableModel extends AbstractTableModel {
    private final String[] columnNames = { "Name", "Beschreibung" };
    private final PrintersDef printers;

    public PrinterTableModel(PrintersDef printers) {
        this.printers = printers;
    }

    @Override
    public int getRowCount() {
        return printers.getPrinter().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PrinterDef printer = printers.getPrinter().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return printer.getName();
            case 1:
                return printer.getDescription();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PrinterDef printer = printers.getPrinter().get(rowIndex);
        switch (columnIndex) {
            case 0:
                printer.setName(aValue.toString());
                break;
            case 1:
                printer.setDescription(aValue.toString());
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // All cells are non-editable; editing is handled via dialog on double-click
        return false;
    }

    public PrinterDef getPrinterAt(int rowIndex) {
        return printers.getPrinter().get(rowIndex);
    }
}

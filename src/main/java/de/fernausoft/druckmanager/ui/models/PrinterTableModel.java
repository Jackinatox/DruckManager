package de.fernausoft.druckmanager.ui.models;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class PrinterTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Name", "Beschreibung"};
    private final List<PrinterDef> printers;

    public PrinterTableModel(List<PrinterDef> printers) {
        this.printers = printers;
    }

    @Override
    public int getRowCount() {
        return printers.size();
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
        PrinterDef printer = printers.get(rowIndex);
        switch (columnIndex) {
            case 0: return printer.getName();
            case 1: return printer.getDescritpion();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PrinterDef printer = printers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                printer.setName(aValue.toString());
                break;
            case 1:
                printer.setDescritpion(aValue.toString());
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // != ist anscheinend falsch in java
        return (!printers.get(rowIndex).getName().equals("FRAGEN"));
    }

    public PrinterDef getPrinterAt(int rowIndex) {
        return printers.get(rowIndex);
    }
}

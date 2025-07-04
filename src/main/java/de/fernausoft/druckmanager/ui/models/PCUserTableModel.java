package de.fernausoft.druckmanager.ui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.fernausoft.druckmanager.xml.schema.TargetDef;

public class PCUserTableModel extends AbstractTableModel {
    private final String[] columnNames = { "PC", "Nutzer" };
    private final List<TargetDef> targets;

    public PCUserTableModel(List<TargetDef> targets) {
        this.targets = targets;
    }

    @Override
    public int getRowCount() {
        return targets.size();
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
        TargetDef target = targets.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return target.getHostname();
            case 1:
                return target.getUsername();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        TargetDef target = targets.get(rowIndex);
        switch (columnIndex) {
            case 0:
                target.setHostname(aValue.toString());
                break;
            case 1:
                target.setUsername(aValue.toString());
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public TargetDef getTargetAt(int rowIndex) {
        return targets.get(rowIndex);
    }
}

package de.fernausoft.druckmanager.ui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.fernausoft.druckmanager.ui.panels.Settings.Target;

public class PCUserTableModel extends AbstractTableModel {
    private final String[] columnNames = { "PC", "Nutzer" };
    private final List<Target> targets;

    public PCUserTableModel(List<Target> targets) {
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
        Target target = targets.get(rowIndex);
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
        // Target target = targets.get(rowIndex);
        switch (columnIndex) {
            case 0:
                // target.setHostname(aValue.toString());
                throw new UnsupportedOperationException("Hostname is not editable");
            case 1:
                // target.setUsername(aValue.toString());
                throw new UnsupportedOperationException("Username is not editable");
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Target getTargetAt(int rowIndex) {
        return targets.get(rowIndex);
    }
}

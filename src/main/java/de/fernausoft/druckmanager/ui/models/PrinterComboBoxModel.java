package de.fernausoft.druckmanager.ui.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.PrintersDef;

public class PrinterComboBoxModel implements ComboBoxModel<PrinterDef> {

    private final PrintersDef printersDef;
    private Object selectedItem;
    private final List<ListDataListener> listeners = new ArrayList<>();

    public PrinterComboBoxModel(PrintersDef printersDef) {
        this.printersDef = printersDef;
        if (!printersDef.getPrinter().isEmpty()) {
            selectedItem = printersDef.getPrinter().get(0);
        }
    }

    @Override
    public int getSize() {
        return printersDef.getPrinter().size();
    
    }

    @Override
    public PrinterDef getElementAt(int index) {
        return printersDef.getPrinter().get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selectedItem = anItem;
        for (ListDataListener listener : listeners) {
            listener.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, -1, -1));
        }
    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }

    
}

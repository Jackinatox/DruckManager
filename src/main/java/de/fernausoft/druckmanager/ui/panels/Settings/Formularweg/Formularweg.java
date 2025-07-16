package de.fernausoft.druckmanager.ui.panels.Settings.Formularweg;

import de.fernausoft.druckmanager.xml.PrinterWrapper;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

abstract public class Formularweg {
    protected Character FWayChar;
    protected String name;
    protected XMLWorker xmlWorker;

    public Formularweg(String name, Character FWayChar, XMLWorker xmlWorker) {
        this.name = name;
        this.FWayChar = FWayChar;
        this.xmlWorker = xmlWorker;
    }

    public boolean getEdited() {
        return true;
    }

    public String getName() {
        return name + " " + FWayChar;
    }

    public char getFWayChar() {
        return FWayChar;
    }

    @Override
    public String toString() {
        return getName();
    }

    public PrinterWrapper getPrinter1() {
        return null;
    }

    public PrinterWrapper getPrinter2() {
        return null;
    }

    public PrinterWrapper getPrinter3() {
        return null;
    }

    public Formularweg setPrinter(char printerIndex, PrinterDef printer) {
        throw new UnsupportedOperationException("This method is not implemented for this Formularweg type.");
    }

}

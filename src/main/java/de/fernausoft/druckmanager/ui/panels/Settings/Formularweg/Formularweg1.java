package de.fernausoft.druckmanager.ui.panels.Settings.Formularweg;

import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class Formularweg1 extends Formularweg {
    private PrinterDef printer1;
    private boolean printer1Enabled = true;

    /**
     * Formularweg1 is a representation of 1 Printer and a Formularweg
     *
     * @param name     The name of the formularweg. Used for building the Name in
     *                 the Dropdown.
     * @param FWayChar The character representing the formularweg in the ENV.
     */
    public Formularweg1(String name, Character FWayChar, XMLWorker xmlWorker) {
        super(name, FWayChar, xmlWorker);
    }

    public void addPrinter(char printerIndex, PrinterDef printer, Boolean enabled) {
        switch (printerIndex) {
            case '1':
                setPrinter1(printer);
                setPrinter1Enabled(enabled);
                break;
            default:
                throw new IllegalArgumentException("Invalid printer index: " + printerIndex);
        }
    }

    public void setPrinter1(PrinterDef printer1) {
        this.printer1 = printer1;
    }

    @Override
    public PrinterDef getPrinter1() {
        return printer1;
    }

    public void setPrinter1Enabled(boolean enabled) {
        this.printer1Enabled = enabled;
    }
    public boolean getPrinter1Enabled() {
        return printer1Enabled;
    }

}

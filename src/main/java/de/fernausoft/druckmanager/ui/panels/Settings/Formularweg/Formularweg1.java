package de.fernausoft.druckmanager.ui.panels.Settings.Formularweg;

import de.fernausoft.druckmanager.xml.PrinterWrapper;
import de.fernausoft.druckmanager.xml.XMLWorker;

public class Formularweg1 extends Formularweg {
    private PrinterWrapper printer1 = new PrinterWrapper();

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

    public void addPrinter(char printerIndex, PrinterWrapper printer) {
        switch (printerIndex) {
            case '1':
                setPrinter1(printer);
                break;
            default:
                throw new IllegalArgumentException("Invalid printer index: " + printerIndex);
        }
    }

    public void setPrinter1(PrinterWrapper printer1) {
        this.printer1 = printer1;
    }

    @Override
    public PrinterWrapper getPrinter1() {
        return printer1;
    }
}

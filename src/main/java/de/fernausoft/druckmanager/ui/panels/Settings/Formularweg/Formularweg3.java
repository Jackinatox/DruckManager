package de.fernausoft.druckmanager.ui.panels.Settings.Formularweg;

import de.fernausoft.druckmanager.xml.PrinterWrapper;
import de.fernausoft.druckmanager.xml.XMLWorker;

public class Formularweg3 extends Formularweg {
    private PrinterWrapper printer1 = new PrinterWrapper();
    private PrinterWrapper printer2 = new PrinterWrapper();
    private PrinterWrapper printer3 = new PrinterWrapper();

    /**
     * Formularweg3 is a representation of 3 Printers and a Formularweg
     *
     * @param name     The name of the formularweg. Used for building the Name in
     *                 the Dropdown.
     * @param FWayChar The character representing the formularweg in the ENV.
     */
    public Formularweg3(String name, char FWayChar, XMLWorker xmlWorker) {
        super(name, FWayChar, xmlWorker);
    }

    public Formularweg3 setPrinter(char printerIndex, PrinterWrapper printer) {

        switch (printerIndex) {
            case '1':
                setPrinter1(printer);
                break;
            case '2':
                setPrinter2(printer);
                break;
            case '3':
                setPrinter3(printer);
                break;
            default:
                throw new IllegalArgumentException("Invalid printer index: " + printerIndex);
        }
        return this;
    }

    @Override
    public boolean getEdited() {
        return printer1.getPrinterDef() != null ||
                printer2.getPrinterDef() != null ||
                printer3.getPrinterDef() != null;
    }

    @Override
    public PrinterWrapper getPrinter1() {
        return printer1;
    }

    @Override
    public PrinterWrapper getPrinter2() {
        return printer2;
    }

    @Override
    public PrinterWrapper getPrinter3() {
        return printer3;
    }

    public void setPrinter1(PrinterWrapper printer1) {
        this.printer1 = printer1;
    }

    public void setPrinter2(PrinterWrapper printer2) {
        this.printer2 = printer2;
    }

    public void setPrinter3(PrinterWrapper printer3) {
        this.printer3 = printer3;
    }


}
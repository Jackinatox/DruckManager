package de.fernausoft.druckmanager.ui.panels.Settings.Formularweg;

import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class Formularweg3 extends Formularweg {
    private PrinterDef printer1;
    private PrinterDef printer2;
    private PrinterDef printer3;
    private boolean printer1Enabled = false;
    private boolean printer2Enabled = false;
    private boolean printer3Enabled = false;

    /**
     * Formularweg3 is a representation of 3 Printers and a Formularweg
     *
     * @param name     The name of the formularweg. Used for building the Name in
     *                 the Dropdown.
     * @param FWayChar The character representing the formularweg in the ENV.
     */
    public Formularweg3(String name, char FWayChar, XMLWorker xmlWorker) {
        super(name, FWayChar, xmlWorker);
        printer1 = xmlWorker.getNoPrinter();
        printer2 = xmlWorker.getNoPrinter();
        printer3 = xmlWorker.getNoPrinter();
    }

    public Formularweg3 setPrinter(char printerIndex, PrinterDef printer, Boolean enabled) {

        switch (printerIndex) {
            case '1':
                setPrinter1(printer);
                printer1Enabled = enabled;
                break;
            case '2':
                setPrinter2(printer);
                printer2Enabled = enabled;
                break;
            case '3':
                setPrinter3(printer);
                printer3Enabled = enabled;
                break;
            default:
                throw new IllegalArgumentException("Invalid printer index: " + printerIndex);
        }
        return this;
    }

    @Override
    public boolean getEdited() {
        return printer1 != xmlWorker.getNoPrinter() ||
                printer2 != xmlWorker.getNoPrinter() ||
                printer3 != xmlWorker.getNoPrinter();
    }

    @Override
    public PrinterDef getPrinter1() {
        return printer1;
    }

    @Override
    public PrinterDef getPrinter2() {
        return printer2;
    }

    @Override
    public PrinterDef getPrinter3() {
        return printer3;
    }

    public void setPrinter1(PrinterDef printer1) {
        this.printer1 = printer1;
    }

    public void setPrinter2(PrinterDef printer2) {
        this.printer2 = printer2;
    }

    public void setPrinter3(PrinterDef printer3) {
        this.printer3 = printer3;
    }

    @Override
    public boolean getPrinterXEnabled(int id) {
        switch (id) {
            case 1:
                return printer1Enabled;
            case 2:
                return printer2Enabled;
            case 3:
                return printer3Enabled;
            default:
                return false; // No other printers are defined
        }
    }

}
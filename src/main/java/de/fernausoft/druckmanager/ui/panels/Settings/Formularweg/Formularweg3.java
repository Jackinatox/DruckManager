package de.fernausoft.druckmanager.ui.panels.Settings.Formularweg;

import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class Formularweg3 extends Formularweg {
    private PrinterDef printer1;
    private PrinterDef printer2;
    private PrinterDef printer3;

    public Formularweg3() {
    }

    protected Formularweg3 addPrinter(char printerId, PrinterDef printer) {
        switch (printerId) {
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
                throw new IllegalArgumentException("Invalid printer environment: " + printerId);
            }
            return this;
    }

    public Formularweg3(PrinterDef printer1, PrinterDef printer2, PrinterDef printer3) {
        this.printer1 = printer1;
        this.printer2 = printer2;
        this.printer3 = printer3;
    }

    public PrinterDef getPrinter1() {
        return printer1;
    }

    public PrinterDef getPrinter2() {
        return printer2;
    }

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
}
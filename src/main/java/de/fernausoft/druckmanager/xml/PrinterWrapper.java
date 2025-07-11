package de.fernausoft.druckmanager.xml;

import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class PrinterWrapper {
    private PrinterDef printerDef;
    private Boolean enabled;
    private Boolean askDialog;

    public PrinterDef getPrinterDef() {
        return printerDef;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAskDialog() {
        return askDialog;
    }

    public void setAskDialog(Boolean askDialog) {
        this.askDialog = askDialog;
    }

    public PrinterWrapper(PrinterDef printerDef) {
        this.printerDef = printerDef;
    }
}

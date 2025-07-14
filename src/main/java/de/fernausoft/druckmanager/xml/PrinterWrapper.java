package de.fernausoft.druckmanager.xml;

import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class PrinterWrapper {
    private PrinterDef printerDef;
    private Boolean enabled;
    private Boolean askDialog;

    public PrinterWrapper(PrinterDef printerDef, Boolean enabled, Boolean askDialog) {
        super();

        this.printerDef = printerDef;
        this.enabled = enabled;
        this.askDialog = askDialog;
    }

    public PrinterWrapper() {
        this.enabled = false;
        this.askDialog = false;
    }

    public void setPrinter(PrinterDef printerDef) {
        this.printerDef = printerDef;
    }

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

    public String getName() {
        return printerDef.getName();
    }

    public String getDescription() {
        return printerDef.getDescription();
    }

    public void setName(String name) {
        printerDef.setName(name);
    }

    public void setDescription(String description) {
        printerDef.setDescription(description);
    }

    @Override
    public String toString() {
        // return printerDef.getName();
        return "Ahhhhhhhhhhhh";
    }
}

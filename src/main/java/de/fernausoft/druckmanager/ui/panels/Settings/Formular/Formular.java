package de.fernausoft.druckmanager.ui.panels.Settings.Formular;

import javax.swing.JPanel;

import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.PrintersDef;

public abstract class Formular {
    private int numOfPrinters;
    private String name;
    private PrintersDef printersDef;
    private PrinterDef printer1;
    private PrinterDef printer2;
    private PrinterDef printer3;

    public Formular(String name, int numOfPrinters, PrintersDef printers, PrinterDef printer1, PrinterDef printer2, PrinterDef printer3) {
        this.name = name;
        this.numOfPrinters = numOfPrinters;
        this.printersDef = printers;
        this.printer1 = printer1;
        this.printer2 = printer2;
        this.printer3 = printer3;
    }

    public String getName() {
        return name;
    }

    public PrinterDef getPrinter1() {
        if (numOfPrinters < 1) {
            throw new IllegalStateException("Number of printers is less than 1.");
        }
        return printer1;
    }

    public PrinterDef getPrinter2() {
        if (numOfPrinters < 2) {
            throw new IllegalStateException("Number of printers is less than 2.");
        }
        return printer2;
    }

    public PrinterDef getPrinter3() {
        if (numOfPrinters < 3) {
            throw new IllegalStateException("Number of printers is less than 3.");
        }
        return printer3;
    }

    public int getNumOfPrinters() {
        return numOfPrinters;
    }

    public JPanel buildPanel(){
        JPanel panel = new JPanel();
        for (int i = 1; i <= numOfPrinters; i++) {
            javax.swing.JLabel label = new javax.swing.JLabel("Drucker " + i + ":");
            javax.swing.JComboBox<String> comboBox = new javax.swing.JComboBox<>();
            if (printersDef != null && printersDef.getPrinter() != null) {
                for (PrinterDef printer : printersDef.getPrinter()) {
                    comboBox.addItem(printer.getName());
                }
            }
            panel.add(label);
            panel.add(comboBox);
        }
        

        return panel;
    }
}

package de.fernausoft.druckmanager.ui.panels.Settings.panles;

import java.util.List;

import javax.swing.JPanel;

import de.fernausoft.druckmanager.ui.panels.Settings.Formular.Formular;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.PrintersDef;

public class NeuGebrauchtWagen extends Formular {
    private final int numOfPrinters = 3;

    public NeuGebrauchtWagen(int numOfPrinters, PrintersDef printers, PrinterDef printer1, PrinterDef printer2, PrinterDef printer3) {
        super("Neu/GebrauchtWagen", numOfPrinters, printers, printer1, printer2, printer3);
        // Initialize the panel with components specific to NeuGebrauchtWagen
    }

}

package de.fernausoft.druckmanager;

import de.fernausoft.druckmanager.ui.forms.DruckManagerPanel;
import de.fernausoft.druckmanager.ui.forms.EditPrinterName;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class DruckManager {

	public static void main(String[] args) {
		XMLWorker xmlworker = new XMLWorker("clientprinterconfig.xml");

		// for (PrinterDef printer : xmlworker.getAllPrinters()) {
		// 	System.out.println("Printer Name: " + printer.getName());
		// }
		EditPrinterName.showPrinterDialog("printe", xmlworker.getAllPrinters());

		// DruckManagerPanel.launch(xmlworker);
	}
}

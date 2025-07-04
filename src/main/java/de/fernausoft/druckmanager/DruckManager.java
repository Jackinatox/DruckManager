package de.fernausoft.druckmanager;

import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.ui.DruckManagerPanel;

public class DruckManager {

	public static void main(String[] args) {
		XMLWorker xmlworker = new XMLWorker("clientprinterconfig.xml");

		for (PrinterDef printer : xmlworker.getAllPrinters()) {
			System.out.println("Printer Name: " + printer.getName());
		}

		DruckManagerPanel.launch(xmlworker);
	}
}

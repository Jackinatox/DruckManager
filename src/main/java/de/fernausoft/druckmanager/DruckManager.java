package de.fernausoft.druckmanager;

import de.fernausoft.druckmanager.ui.forms.DruckManagerPanel;
import de.fernausoft.druckmanager.xml.XMLWorker;

public class DruckManager {

	public static void main(String[] args) {
		XMLWorker xmlworker = new XMLWorker("clientprinterconfig.xml");

		xmlworker.verifyRegex();


		// for (TargetDef target : xmlworker.getAllTargets()) {
		// 	System.out.println("Target Username: " + target.getUsername());
		// }
		// System.out.println(EditPrinterName.showPrinterDialog("printe",
		// xmlworker.getAllPrinters()));
		// String input = JOptionPane.showInputDialog(null, "Neuer Druckername:",
		// "test", 1);
		DruckManagerPanel.launch(xmlworker);
	}
}

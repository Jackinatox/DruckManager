package de.fernausoft.druckmanager.ui;

import javax.swing.*;
import de.fernausoft.druckmanager.xml.XMLWorker;
import java.util.List;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class DruckManagerPanel {

	private JFrame mainWindow;
	private XMLWorker xmlWorker;

	private DruckManagerPanel(XMLWorker xmlWorker) {
		this.xmlWorker = xmlWorker;
		mainWindow = new JFrame();

		List<PrinterDef> printers = xmlWorker.getAllPrinters();
		PrinterTablePanel tablePanel = new PrinterTablePanel(printers);
		mainWindow.getContentPane().add(tablePanel);
	}

	public void show() {
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(400, 300); // Example size
		mainWindow.setVisible(true);
	}

	public static void launch(XMLWorker xmlWorker) {
		// Weil die UI einen speziellen Event Thread braucht, glaube windows macht das gleiche mit in der win api
		SwingUtilities.invokeLater(() -> {
			DruckManagerPanel panel = new DruckManagerPanel(xmlWorker);
			panel.show();
		});
	}
}

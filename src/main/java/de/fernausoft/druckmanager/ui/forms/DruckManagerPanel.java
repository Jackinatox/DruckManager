package de.fernausoft.druckmanager.ui.forms;

import javax.swing.*;
import java.awt.GridLayout;

import de.fernausoft.druckmanager.ui.panels.PCUserMappingPanel;
import de.fernausoft.druckmanager.ui.panels.PrinterTablePanel;
import de.fernausoft.druckmanager.ui.panels.Settings.Settings;
import de.fernausoft.druckmanager.xml.XMLWorker;
import java.util.List;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.TargetDef;

public class DruckManagerPanel {

	private JFrame mainWindow;
	private XMLWorker xmlWorker;

	private DruckManagerPanel(XMLWorker xmlWorker) {
		this.xmlWorker = xmlWorker;
		mainWindow = new JFrame();

		mainWindow.getContentPane().setLayout(new GridLayout(3, 1));

		List<PrinterDef> printers = xmlWorker.getAllPrinters();
		List<TargetDef> targets = xmlWorker.getAllTargets();
		PrinterTablePanel tablePanel = new PrinterTablePanel(printers);
		PCUserMappingPanel pcTaplePanel = new PCUserMappingPanel(targets);
		Settings settingsPanel = new Settings(xmlWorker);

		mainWindow.getContentPane().add(tablePanel);
		mainWindow.getContentPane().add(pcTaplePanel);
		mainWindow.getContentPane().add(settingsPanel);
	}

	public void show() {
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(400, 900); // Example size
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

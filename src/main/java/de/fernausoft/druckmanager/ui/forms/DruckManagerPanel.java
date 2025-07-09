package de.fernausoft.druckmanager.ui.forms;

import javax.swing.*;
import java.awt.GridLayout;

import de.fernausoft.druckmanager.ui.panels.PCUserMappingPanel;
import de.fernausoft.druckmanager.ui.panels.PrinterTablePanel;
import de.fernausoft.druckmanager.ui.panels.Settings.Settings;
import de.fernausoft.druckmanager.ui.panels.Settings.Target;
import de.fernausoft.druckmanager.xml.XMLWorker;

import java.util.ArrayList;
import java.util.List;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

import de.fernausoft.druckmanager.ui.listeners.PCUserSelectionListener;

public class DruckManagerPanel implements PCUserSelectionListener{

	private JFrame mainWindow;
	private XMLWorker xmlWorker;
	private Settings settingsPanel;

	private DruckManagerPanel(XMLWorker xmlWorker) {
		this.xmlWorker = xmlWorker;
		mainWindow = new JFrame();

		mainWindow.getContentPane().setLayout(new GridLayout(3, 1));

		List<Target> myTargets = new ArrayList<>();
		Target target = new Target(xmlWorker.forTesting(), xmlWorker);
		myTargets.add(target);

		List<PrinterDef> printers = xmlWorker.getAllPrinters();

		// The 3 Main UI Panels
		PrinterTablePanel tablePanel = new PrinterTablePanel(printers);
		PCUserMappingPanel pcTaplePanel = new PCUserMappingPanel(myTargets);
		pcTaplePanel.setPcUserSelectionListener(this);
		settingsPanel = new Settings(xmlWorker);

		mainWindow.getContentPane().add(tablePanel);
		mainWindow.getContentPane().add(pcTaplePanel);
		mainWindow.getContentPane().add(settingsPanel);
	}

	@Override
	public void onUserSelected(Target target) {
		// Update the settings panel here
		settingsPanel.setPrograms(target.getPrograms());
	}

	public void show() {
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(400, 900); // Example size
		mainWindow.setVisible(true);
	}

	public static void launch(XMLWorker xmlWorker) {
		// Weil die UI einen speziellen Event Thread braucht, glaube windows macht das
		// gleiche mit in der win api
		SwingUtilities.invokeLater(() -> {
			DruckManagerPanel panel = new DruckManagerPanel(xmlWorker);
			panel.show();
		});
	}
}

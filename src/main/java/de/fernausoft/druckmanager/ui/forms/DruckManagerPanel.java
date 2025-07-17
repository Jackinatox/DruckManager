package de.fernausoft.druckmanager.ui.forms;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fernausoft.druckmanager.ui.listeners.PCUserSelectionListener;
import de.fernausoft.druckmanager.ui.panels.PCUserMappingPanel;
import de.fernausoft.druckmanager.ui.panels.PrinterTablePanel;
import de.fernausoft.druckmanager.ui.panels.Settings.Settings;
import de.fernausoft.druckmanager.ui.panels.Settings.Target;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrintersDef;
import de.fernausoft.druckmanager.xml.schema.TargetDef;

public class DruckManagerPanel implements PCUserSelectionListener {

	private static final Logger logger = LogManager.getLogger(DruckManagerPanel.class);

	private JFrame mainWindow;
	// private XMLWorker xmlWorker;
	private Settings settingsPanel;

	private PCUserMappingPanel pcTaplePanel;
	private List<Target> myTargets;

	private DruckManagerPanel(XMLWorker xmlWorker) {
		// this.xmlWorker = xmlWorker;
		mainWindow = new JFrame();
		mainWindow.setTitle("Druck-Manager");

		mainWindow.getContentPane().setLayout(new GridLayout(3, 1));

		myTargets = new ArrayList<>();
		for (TargetDef target : xmlWorker.getAllTargets()) {
			// Create a Target object for each XML target
			Target newTarget = new Target(target, xmlWorker);
			myTargets.add(newTarget);
		}
		// Target target = new Target(xmlWorker.forTesting(), xmlWorker);
		// myTargets.add(target);

		PrintersDef printers = xmlWorker.getAllPrinters();

		// The 3 Main UI Panels
		settingsPanel = new Settings(xmlWorker);

		PrinterTablePanel tablePanel = new PrinterTablePanel(printers, xmlWorker, myTargets);
		pcTaplePanel = new PCUserMappingPanel(myTargets, xmlWorker);
		pcTaplePanel.setPcUserSelectionListener(this);

		
		mainWindow.getContentPane().add(tablePanel);
		mainWindow.getContentPane().add(pcTaplePanel);
		mainWindow.getContentPane().add(settingsPanel);
		settingsPanel.getOkButton().addActionListener(e -> {
			logger.info("Starting rewrite of XML");
			xmlWorker.rewriteXML(myTargets);
		});
	}

	@Override
	public void onUserSelected(Target target) {
		// Update the settings panel here
		settingsPanel.setPrograms(target.getPrograms());
	}

	public void show() {
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(650, 900); // Example size
		mainWindow.setVisible(true);
		SwingUtilities.invokeLater(() -> {
			pcTaplePanel.selectFirstRow();
		});
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

package de.fernausoft.druckmanager.ui.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

		mainWindow.getContentPane().setLayout(new BorderLayout());

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

		JPanel centerPanel = new JPanel(new GridLayout(3, 1));
		centerPanel.add(tablePanel);
		centerPanel.add(pcTaplePanel);
		centerPanel.add(settingsPanel);

		// Panel for the buttons in the south
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton saveButton = new JButton("Speichern");
		JButton cancelButton = new JButton("Abbrechen");

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		saveButton.addActionListener(e -> {
			int result = javax.swing.JOptionPane.showConfirmDialog(
					mainWindow,
					"Wirklich speichern? Das überschreibt die momentane Drucker Konfiguration",
					"Bestätigung",
					javax.swing.JOptionPane.YES_NO_OPTION);
			if (result != javax.swing.JOptionPane.YES_OPTION) {
				return;
			}
			logger.info("Starting rewrite of XML");
			xmlWorker.rewriteXML(myTargets);
		});
		cancelButton.addActionListener(e -> {
			int result = javax.swing.JOptionPane.showConfirmDialog(
					mainWindow,
					"Wirklich beenden? Änderungen gehen verloren!",
					"Beenden",
					javax.swing.JOptionPane.YES_NO_OPTION);
			if (result != javax.swing.JOptionPane.YES_OPTION) {
				return;
			}
			mainWindow.dispose();
		});

		mainWindow.getContentPane().add(centerPanel, BorderLayout.CENTER);
		mainWindow.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
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

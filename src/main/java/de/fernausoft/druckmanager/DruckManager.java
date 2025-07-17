package de.fernausoft.druckmanager;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.UIManager;

import de.fernausoft.druckmanager.ui.forms.DruckManagerPanel;
import de.fernausoft.druckmanager.xml.XMLWorker;

public class DruckManager {

	public static void main(String[] args) {
		String xmlFileName = args.length > 0 ? args[0] : "clientprinterconfig.xml";
		Path xmlPath = Paths.get(xmlFileName);

		if (!java.nio.file.Files.exists(xmlPath) || !java.nio.file.Files.isRegularFile(xmlPath)) {
			System.err.println("Error: XML file '" + xmlFileName + "' does not exist or is not a valid file.");
			return;
		}

		launch(xmlPath);
	}

	public static void launch(Path path) {
		try {
			String laf = UIManager.getSystemLookAndFeelClassName();

			UIManager.setLookAndFeel(laf);
			Path backupPath = path.resolveSibling(path.getFileName().toString() + ".bak");
			java.nio.file.Files.copy(path, backupPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			System.err.println("Failed to create backup: " + e.getMessage());
		}

		XMLWorker xmlworker = new XMLWorker(path);
		xmlworker.verifyRegex();
		DruckManagerPanel.launch(xmlworker);
	}
}

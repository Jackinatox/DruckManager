package de.fernausoft.druckmanager.ui.forms;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;


import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class EditPrinterName {

    public static String showPrinterDialog(String oldName, List<PrinterDef> allPrinters) {
        String returnString = "";
        JDialog dialog = new JDialog();
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel label = new JLabel("Druckername:");
        JTextField textField = new JTextField(oldName, 20);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Abbrechen");


        cancelButton.addActionListener(e -> dialog.dispose());
        // okButton.addActionListener();

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setTitle("Druckername ändern");
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);

        inputPanel.add(label);
        inputPanel.add(textField);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(mainPanel);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setVisible(true);

        return returnString;
    }
}

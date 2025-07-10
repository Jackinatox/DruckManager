package de.fernausoft.druckmanager.ui.panels.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector; // Using Vector for JComboBox model

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg3;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.BaseProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.DefaultLayoutProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.OnlyOnePrinterProgram;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class Settings extends JPanel {
    private XMLWorker xmlWorker;
    private static final Logger logger = LogManager.getLogger(Settings.class);

    // JComboBoxes for printers and formular, made accessible for external methods
    private JPanel navPanel = new JPanel();
    private JComboBox<PrinterDef> drucker1ComboBox;
    private JComboBox<PrinterDef> drucker2ComboBox;
    private JComboBox<PrinterDef> drucker3ComboBox;
    private JComboBox<Formularweg> formularComboBox;

    public Settings(XMLWorker xmlWorker) {
        this.xmlWorker = xmlWorker;
        // Set the overall layout for the Settings panel
        // Using GridBagLayout to divide the panel into two main sections:
        // a left navigation/list area and a right content/form area.
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding around the panel

        // --- Left Navigation Panel ---

        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(navPanel);
        scrollPane.setBorder(null); // Remove border from scroll pane

        add(navPanel);

        // --- Right Content Panel (Form) ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for the form elements
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Border for visual separation

        GridBagConstraints gbcContent = new GridBagConstraints();
        gbcContent.insets = new Insets(5, 5, 5, 5); // Padding for components within the form

        // Row 1: Formular Label and ComboBox (Text field removed)
        gbcContent.gridx = 0;
        gbcContent.gridy = 0;
        gbcContent.anchor = GridBagConstraints.WEST; // Align to the left
        contentPanel.add(new JLabel("Formular"), gbcContent);

        // Initialize formularComboBox
        formularComboBox = new JComboBox<>(); // Empty initially, will be set by external method
        formularComboBox.setEnabled(false); // Enable the combo box
        formularComboBox.setPreferredSize(new Dimension(160, 25)); // Set preferred size
        formularComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (formularComboBox.getSelectedItem() != null) {
                    // logger.info("selection changed to: " +
                    // formularComboBox.getSelectedItem().getClass());
                    PrinterDef printer1 = ((Formularweg) formularComboBox.getSelectedItem()).getPrinter1();
                    PrinterDef printer2 = ((Formularweg) formularComboBox.getSelectedItem()).getPrinter2();
                    PrinterDef printer3 = ((Formularweg) formularComboBox.getSelectedItem()).getPrinter3();

                    boolean isPrinter1Enabled = printer1 != null;
                    boolean isPrinter2Enabled = printer2 != null;
                    boolean isPrinter3Enabled = printer3 != null;

                    if (isPrinter1Enabled) {
                        drucker1ComboBox.setSelectedItem(printer1);
                    }

                    if (isPrinter2Enabled) {
                        drucker2ComboBox.setSelectedItem(printer2);
                    }

                    if (isPrinter3Enabled) {
                        drucker3ComboBox.setSelectedItem(printer3);
                    }

                    drucker1ComboBox.setEnabled(isPrinter1Enabled);
                    drucker2ComboBox.setEnabled(isPrinter2Enabled);
                    drucker3ComboBox.setEnabled(isPrinter3Enabled);
                }
            }
        });
        gbcContent.gridx = 1;
        gbcContent.gridy = 0;
        gbcContent.gridwidth = 2; // Span across remaining columns
        gbcContent.weightx = 1.0; // Allow it to stretch
        gbcContent.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(formularComboBox, gbcContent);

        // Row 2: Separator Line
        gbcContent.gridx = 0;
        gbcContent.gridy = 1;
        gbcContent.gridwidth = 3; // Span across all columns
        gbcContent.fill = GridBagConstraints.HORIZONTAL;
        gbcContent.insets = new Insets(10, 5, 10, 5); // More padding for separator
        contentPanel.add(new JSeparator(), gbcContent);

        // Row 3: Drucker 1 Label and ComboBox
        gbcContent.gridx = 0;
        gbcContent.gridy = 2;
        gbcContent.gridwidth = 1; // Reset gridwidth
        gbcContent.fill = GridBagConstraints.NONE; // Reset fill
        gbcContent.anchor = GridBagConstraints.WEST;
        contentPanel.add(new JLabel("Drucker 1"), gbcContent);

        // Initialize drucker1ComboBox
        drucker1ComboBox = new JComboBox<>(); // Empty initially, will be populated by setPrinterOptions
        drucker1ComboBox.setEnabled(false);
        drucker1ComboBox.setPreferredSize(new Dimension(150, 25));
        gbcContent.gridx = 1;
        gbcContent.gridy = 2;
        gbcContent.gridwidth = 2; // Span across remaining columns
        gbcContent.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(drucker1ComboBox, gbcContent);

        // Row 4: Drucker 2 Label and ComboBox
        gbcContent.gridx = 0;
        gbcContent.gridy = 3;
        gbcContent.gridwidth = 1;
        gbcContent.fill = GridBagConstraints.NONE;
        contentPanel.add(new JLabel("Drucker 2"), gbcContent);

        // Initialize drucker2ComboBox
        drucker2ComboBox = new JComboBox<>(); // Empty initially
        drucker2ComboBox.setEnabled(false);
        drucker2ComboBox.setPreferredSize(new Dimension(150, 25));
        gbcContent.gridx = 1;
        gbcContent.gridy = 3;
        gbcContent.gridwidth = 2;
        gbcContent.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(drucker2ComboBox, gbcContent);

        // Row 5: Drucker 3 Label and ComboBox
        gbcContent.gridx = 0;
        gbcContent.gridy = 4;
        gbcContent.fill = GridBagConstraints.NONE;
        contentPanel.add(new JLabel("Drucker 3"), gbcContent);

        // Initialize drucker3ComboBox
        drucker3ComboBox = new JComboBox<>(); // Empty initially
        drucker3ComboBox.setEnabled(false);
        drucker3ComboBox.setPreferredSize(new Dimension(150, 25));

        gbcContent.gridx = 1;
        gbcContent.gridy = 4;
        gbcContent.gridwidth = 2;
        gbcContent.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(drucker3ComboBox, gbcContent);

        // Add a vertical strut to push content to the top
        gbcContent.gridx = 0;
        gbcContent.gridy = 5;
        gbcContent.weighty = 1.0; // This makes the last component take up all remaining vertical space
        gbcContent.gridwidth = 3;
        contentPanel.add(Box.createVerticalGlue(), gbcContent);

        // Row for Ok and Abbrechen buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // Align buttons to the right
        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Abbrechen");

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        gbcContent.gridx = 0;
        gbcContent.gridy = 6; // Place below the last Drucker ComboBox
        gbcContent.gridwidth = 3; // Span all columns
        gbcContent.fill = GridBagConstraints.NONE; // Do not fill
        gbcContent.anchor = GridBagConstraints.SOUTHEAST; // Align to bottom right
        gbcContent.weighty = 0.0; // Don't take up extra vertical space
        gbcContent.insets = new Insets(10, 5, 5, 5); // Padding above buttons
        contentPanel.add(buttonPanel, gbcContent);


        add(contentPanel);

        // Initialize Values
        setPrinterOptions(xmlWorker.getAllPrinters());
        // setProgram(new WerkstattAuftrag());

    }

    /**
     * Helper method to create a navigation item (JButton) with specific styling.
     * 
     * @param text       The text to display on the button.
     * @param isSelected A boolean indicating if the item is currently selected.
     * @return A styled JButton.
     */
    private JButton createNavItem(BaseProgram program, boolean isSelected) {
        JButton button = new JButton(program.getName());
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setForeground(UIManager.getColor("Button.foreground"));
        button.setFocusPainted(false);
        button.setOpaque(true);

        // TODO: Remove Later
        if (isSelected) {
            button.setBackground(Color.LIGHT_GRAY); // Light gray for selected
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        } else {
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        }
        button.setFont(button.getFont().deriveFont(Font.PLAIN, 12f));

        // Add onClick listener to change the selected state
        button.addActionListener(e -> {
            setProgram(program);
            logger.info("setting to: " + program.getName());
        });

        return button;
    }

    /**
     * Method to set the available printer options for all printer dropdowns.
     * This method should be called externally with the list of PrinterDef objects.
     * 
     * @param printerDefs A list of PrinterDef objects to populate the dropdowns.
     */
    public void setPrinterOptions(List<PrinterDef> printerDefs) {
        // Clear existing items and add new ones
        DefaultComboBoxModel<PrinterDef> model1 = new DefaultComboBoxModel<>(new Vector<>(printerDefs));
        drucker1ComboBox.setModel(model1);

        DefaultComboBoxModel<PrinterDef> model2 = new DefaultComboBoxModel<>(new Vector<>(printerDefs));
        drucker2ComboBox.setModel(model2);

        DefaultComboBoxModel<PrinterDef> model3 = new DefaultComboBoxModel<>(new Vector<>(printerDefs));
        drucker3ComboBox.setModel(model3);
    }

    private void setProgram(BaseProgram program) {
        // Clear existing items in formularComboBox
        try {
            formularComboBox.removeAllItems();
            logger.info("Setting program: " + program.getName());

            // Populate formularComboBox with the formularweg from the program
            // List<Formularweg> formularList = program.get
            if (program instanceof DefaultLayoutProgram) {
                DefaultLayoutProgram def = (DefaultLayoutProgram) program;

                for (Formularweg formular : def.getFormularwegList()) {
                    formularComboBox.addItem(formular);
                }

            } else if (program instanceof OnlyOnePrinterProgram) {
                OnlyOnePrinterProgram singlePrinterProgram = (OnlyOnePrinterProgram) program;
                for (Formularweg formular : singlePrinterProgram.getFormularwegList()) {
                    formularComboBox.addItem(formular);
                }
            } else {
                logger.error("Program type not supported for formular selection: " + program.getClass().getName());
            }

            formularComboBox.setEnabled(formularComboBox.getItemCount() > 1);

        } catch (Exception e) {
            logger.error("Error setting program: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to set the selected printer for a specific dropdown.
     * You will implement the logic to determine which printer to select based on
     * your application's state.
     * 
     * @param dropdownIndex      The index of the dropdown (1 for Drucker 1, 2 for
     *                           Drucker 2, 3 for Drucker 3).
     * @param selectedPrinterRef The 'ref' of the PrinterDef to be selected.
     */
    public void setSelectedPrinter(int dropdownIndex, String selectedPrinterRef) {
        JComboBox<PrinterDef> targetComboBox;
        switch (dropdownIndex) {
            case 1:
                targetComboBox = drucker1ComboBox;
                break;
            case 2:
                targetComboBox = drucker2ComboBox;
                break;
            case 3:
                targetComboBox = drucker3ComboBox;
                break;
            default:
                System.err.println("Invalid dropdown index: " + dropdownIndex);
                return;
        }

        // Iterate through the model to find the PrinterDef with the matching ref
        ComboBoxModel<PrinterDef> model = targetComboBox.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            PrinterDef printer = model.getElementAt(i);
            if (printer != null && printer.getRef().equals(selectedPrinterRef)) {
                targetComboBox.setSelectedItem(printer);
                return;
            }
        }
        System.err.println("Printer with ref '" + selectedPrinterRef + "' not found for dropdown " + dropdownIndex);
    }

    /**
     * Method to set the selected formular.
     * You will implement the logic to determine which formular to select.
     * 
     * @param selectedFormular The string value of the formular to be selected.
     */
    public void setSelectedFormular(String selectedFormular) {
        formularComboBox.setSelectedItem(selectedFormular);
    }

    public void setPrograms(List<BaseProgram> programs) {
        navPanel.removeAll();
        for (BaseProgram pg : programs) {
            JButton werkstattButton = createNavItem(pg, true); //
            // Pass true for selected

            navPanel.add(werkstattButton);

        }
        navPanel.revalidate();
        navPanel.repaint();
    }
}

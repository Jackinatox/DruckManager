package de.fernausoft.druckmanager.ui.panels.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.Vector; // Using Vector for JComboBox model

import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.BaseProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.DefaultLayoutProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.OnlyOnePrinterProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.ThreePrintersProgram;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;

public class Settings extends JPanel {
    private static final Logger logger = LogManager.getLogger(Settings.class);
    private BaseProgram activeProgram;

    // JComboBoxes for printers and formular, made accessible for external methods
    private NavPanel navPanel = new NavPanel();
    private XMLWorker xmlWorker;
    private JComboBox<PrinterDef> drucker1ComboBox;
    private JComboBox<PrinterDef> drucker2ComboBox;
    private JComboBox<PrinterDef> drucker3ComboBox;
    private JComboBox<Formularweg> formularComboBox;
    private JCheckBox drucker1CheckBox;
    private JCheckBox drucker2CheckBox;
    private JCheckBox drucker3CheckBox;

    public Settings(XMLWorker xmlWorker) {
        this.xmlWorker = xmlWorker;
        // Set the overall layout for the Settings panel
        // Using GridBagLayout to divide the panel into two main sections:
        // a left navigation/list area and a right content/form area.
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding around the panel

        // --- Left Navigation Panel ---
        GridBagConstraints gbcNav = new GridBagConstraints();
        gbcNav.gridx = 0;
        gbcNav.gridy = 0;
        gbcNav.weighty = 1.0;
        gbcNav.anchor = GridBagConstraints.NORTHWEST;
        gbcNav.fill = GridBagConstraints.BOTH;

        JScrollPane scrollPane = new JScrollPane(navPanel);
        scrollPane.setPreferredSize(new Dimension(280, 0));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(scrollPane, gbcNav);

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
                    Formularweg selectedFormular = (Formularweg) formularComboBox.getSelectedItem();
                    PrinterDef printer1 = selectedFormular.getPrinter1();
                    PrinterDef printer2 = selectedFormular.getPrinter2();
                    PrinterDef printer3 = selectedFormular.getPrinter3();

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

                    // Dickes TODO:
                    drucker1ComboBox.setEnabled(selectedFormular.getPrinterXEnabled(1));
                    drucker2ComboBox.setEnabled(selectedFormular.getPrinterXEnabled(2));
                    drucker3ComboBox.setEnabled(selectedFormular.getPrinterXEnabled(3));

                    drucker1CheckBox.setEnabled(isPrinter1Enabled);
                    drucker2CheckBox.setEnabled(isPrinter2Enabled);
                    drucker3CheckBox.setEnabled(isPrinter3Enabled);

                    drucker1CheckBox.setSelected(isPrinter1Enabled && selectedFormular.getPrinterXEnabled(1));
                    drucker2CheckBox.setSelected(isPrinter2Enabled && selectedFormular.getPrinterXEnabled(2));
                    drucker3CheckBox.setSelected(isPrinter3Enabled && selectedFormular.getPrinterXEnabled(3));
                }
            }
        });
        formularComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);

                if (value instanceof Formularweg) {
                    Formularweg formular = (Formularweg) value;
                    boolean isEnabled = formular.getEdited();
                    // boolean isEnabled = false;

                    label.setFont(label.getFont().deriveFont(isEnabled ? Font.BOLD : Font.PLAIN));
                    label.setText(formular.toString());
                }

                return label;
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

        // Create printer rows
        drucker1ComboBox = new JComboBox<>();
        drucker1CheckBox = new JCheckBox();
        createPrinterRow(contentPanel, gbcContent, "Drucker 1", drucker1ComboBox, drucker1CheckBox, 2);

        drucker2ComboBox = new JComboBox<>();
        drucker2CheckBox = new JCheckBox();
        createPrinterRow(contentPanel, gbcContent, "Drucker 2", drucker2ComboBox, drucker2CheckBox, 3);

        drucker3ComboBox = new JComboBox<>();
        drucker3CheckBox = new JCheckBox();
        createPrinterRow(contentPanel, gbcContent, "Drucker 3", drucker3ComboBox, drucker3CheckBox, 4);

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

        GridBagConstraints gbcContentPanel = new GridBagConstraints();
        gbcContentPanel.gridx = 1;
        gbcContentPanel.gridy = 0;
        gbcContentPanel.weightx = 0.8;
        gbcContentPanel.weighty = 1.0;
        gbcContentPanel.fill = GridBagConstraints.BOTH;
        add(contentPanel, gbcContentPanel);

        // Initialize Values
        setPrinterOptions(xmlWorker.getAllPrinters());
        // setProgram(new WerkstattAuftrag());

    }

    private void createPrinterRow(JPanel panel, GridBagConstraints gbc, String label, JComboBox<PrinterDef> comboBox,
            JCheckBox checkBox, int gridy) {
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(checkBox, gbc);

        comboBox.setEnabled(false);
        comboBox.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 2;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comboBox, gbc);

        checkBox.addActionListener(e -> comboBox.setEnabled(checkBox.isSelected()));
    }

    /**
     * Helper method to create a navigation item (JButton) with specific styling.
     * 
     * @param text       The text to display on the button.
     * @param isSelected A boolean indicating if the item is currently selected.
     * @return A styled JButton.
     */
    private JButton createNavItem(BaseProgram program) {
        JButton button = new JButton(program.getName());
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setForeground(UIManager.getColor("Button.foreground"));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.putClientProperty("program", program);

        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        button.setFont(button.getFont().deriveFont(Font.PLAIN, 12f));

        // Add onClick listener to change the selected state
        button.addActionListener(e -> {
            activeProgram = (BaseProgram) ((JButton) e.getSource()).getClientProperty("program");
            setProgram(activeProgram);
            updateNavPanel();
            logger.info("setting to: " + activeProgram.getName());
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
        printerDefs.remove(xmlWorker.getNoPrinter());
        printerDefs.sort(Comparator.comparing(PrinterDef::toString));
        printerDefs.add(0, xmlWorker.getNoPrinter()); // Add "Kein Drucker" at the top



        // drucker1ComboBox.add(new JSeparator(JSeparator.HORIZONTAL));
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
                for (int i = 0; i < formularComboBox.getItemCount(); i++) {
                    if (formularComboBox.getItemAt(i).getEdited()) {
                        formularComboBox.setSelectedIndex(i);
                        break;
                    }
                }

            } else if (program instanceof OnlyOnePrinterProgram) {
                OnlyOnePrinterProgram singlePrinterProgram = (OnlyOnePrinterProgram) program;
                for (Formularweg formular : singlePrinterProgram.getFormularwegList()) {
                    formularComboBox.addItem(formular);
                }
            } else if (program instanceof ThreePrintersProgram) {
                ThreePrintersProgram threePrintersProgram = (ThreePrintersProgram) program;
                for (Formularweg formular : threePrintersProgram.getFormularwegList()) {
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
        if (programs != null && !programs.isEmpty()) {
            activeProgram = programs.get(0);
        } else {
            activeProgram = null;
        }

        for (BaseProgram pg : programs) {
            JButton button = createNavItem(pg);
            navPanel.add(button);
        }

        navPanel.revalidate();
        navPanel.repaint();

        if (activeProgram != null) {
            setProgram(activeProgram);
            updateNavPanel();
        }
    }

    private void updateNavPanel() {
        for (Component comp : navPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                BaseProgram program = (BaseProgram) button.getClientProperty("program");
                if (program != null && program.equals(activeProgram)) {
                    button.setBackground(Color.LIGHT_GRAY);
                } else {
                    button.setBackground(Color.WHITE);
                }
            }
        }
    }

    private class NavPanel extends JPanel implements Scrollable {
        public NavPanel() {
            setLayout(new GridLayout(0, 1));
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}

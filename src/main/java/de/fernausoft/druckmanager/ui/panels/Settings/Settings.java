package de.fernausoft.druckmanager.ui.panels.Settings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fernausoft.druckmanager.ui.models.PrinterComboBoxModel;
import de.fernausoft.druckmanager.ui.panels.Settings.Formularweg.Formularweg;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.BaseProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.DefaultLayoutProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.OnlyOnePrinterProgram;
import de.fernausoft.druckmanager.ui.panels.Settings.Programs.ThreePrintersProgram;
import de.fernausoft.druckmanager.xml.PrinterWrapper;
import de.fernausoft.druckmanager.xml.XMLWorker;
import de.fernausoft.druckmanager.xml.schema.PrinterDef;
import de.fernausoft.druckmanager.xml.schema.PrintersDef;

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
    private JButton okButton;

    public Settings(XMLWorker xmlWorker) {
        this.xmlWorker = xmlWorker;

        // Create a visual panel to wrap the Settings panel for spacing
        JPanel visualPanel = new JPanel(new GridBagLayout());
        visualPanel.setBorder(new EmptyBorder(0, 8, 8, 8)); // More padding for visual separation

        setLayout(new BorderLayout());
        add(visualPanel, BorderLayout.CENTER);

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
        visualPanel.add(scrollPane, gbcNav);

        // --- Right Content Panel (Form) ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        GridBagConstraints gbcContent = new GridBagConstraints();
        gbcContent.insets = new Insets(5, 5, 5, 5);

        gbcContent.gridx = 0;
        gbcContent.gridy = 0;
        gbcContent.anchor = GridBagConstraints.WEST;
        contentPanel.add(new JLabel("Formular"), gbcContent);

        formularComboBox = new JComboBox<>();
        formularComboBox.setEnabled(false);
        formularComboBox.setPreferredSize(new Dimension(160, 25));
        formularComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (formularComboBox.getSelectedItem() != null) {
                    // logger.info("selection changed to: " +
                    // formularComboBox.getSelectedItem().getClass());
                    Formularweg selectedFormular = (Formularweg) formularComboBox.getSelectedItem();
                    PrinterWrapper printer1 = selectedFormular.getPrinter1();
                    PrinterWrapper printer2 = selectedFormular.getPrinter2();
                    PrinterWrapper printer3 = selectedFormular.getPrinter3();

                    boolean isPrinter1Enabled = printer1 != null;
                    boolean isPrinter2Enabled = printer2 != null;
                    boolean isPrinter3Enabled = printer3 != null;

                    drucker1CheckBox.setSelected(isPrinter1Enabled ? printer1.getEnabled() : false);
                    drucker2CheckBox.setSelected(isPrinter2Enabled ? printer2.getEnabled() : false);
                    drucker3CheckBox.setSelected(isPrinter3Enabled ? printer3.getEnabled() : false);

                    drucker1CheckBox.setEnabled(isPrinter1Enabled);
                    drucker2CheckBox.setEnabled(isPrinter2Enabled);
                    drucker3CheckBox.setEnabled(isPrinter3Enabled);

                    if (isPrinter1Enabled) {
                        PrinterDef printer = printer1.getPrinterDef();
                        if (printer1.getAskDialog()) {
                            printer = xmlWorker.getAskingPrinter();
                        } 
                        drucker1ComboBox.setSelectedItem(printer);
                    }

                    if (isPrinter2Enabled) {
                        PrinterDef printer = printer2.getPrinterDef();
                        if (printer2.getAskDialog()) {
                            printer = xmlWorker.getAskingPrinter();
                        } 
                        drucker2ComboBox.setSelectedItem(printer);
                    }
                    if (isPrinter3Enabled) {
                        PrinterDef printer = printer3.getPrinterDef();
                        if (printer3.getAskDialog()) {
                            printer = xmlWorker.getAskingPrinter();
                        } 
                        drucker3ComboBox.setSelectedItem(printer);
                    }

                    drucker1ComboBox.setEnabled(drucker1CheckBox.isSelected());
                    drucker2ComboBox.setEnabled(drucker2CheckBox.isSelected());
                    drucker3ComboBox.setEnabled(drucker3CheckBox.isSelected());

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
        gbcContent.gridwidth = 2;
        gbcContent.weightx = 1.0;
        gbcContent.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(formularComboBox, gbcContent);

        // Row 2: Separator Line
        gbcContent.gridx = 0;
        gbcContent.gridy = 1;
        gbcContent.gridwidth = 3;
        gbcContent.fill = GridBagConstraints.HORIZONTAL;
        gbcContent.insets = new Insets(10, 5, 10, 5);
        contentPanel.add(new JSeparator(), gbcContent);

        // Create printer rows
        drucker1ComboBox = new JComboBox<>();
        drucker1CheckBox = new JCheckBox();
        createPrinterRow(contentPanel, gbcContent, "Drucker 1", drucker1ComboBox, drucker1CheckBox, 2, '1');

        drucker2ComboBox = new JComboBox<>();
        drucker2CheckBox = new JCheckBox();
        createPrinterRow(contentPanel, gbcContent, "Drucker 2", drucker2ComboBox, drucker2CheckBox, 3, '2');

        drucker3ComboBox = new JComboBox<>();
        drucker3CheckBox = new JCheckBox();
        createPrinterRow(contentPanel, gbcContent, "Drucker 3", drucker3ComboBox, drucker3CheckBox, 4, '3');

        // Add a vertical strut to push content to the top
        gbcContent.gridx = 0;
        gbcContent.gridy = 5;
        gbcContent.weighty = 1.0;
        gbcContent.gridwidth = 3;
        contentPanel.add(Box.createVerticalGlue(), gbcContent);

        // Row for Ok and Abbrechen buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Abbrechen");

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        gbcContent.gridx = 0;
        gbcContent.gridy = 6;
        gbcContent.gridwidth = 3;
        gbcContent.fill = GridBagConstraints.NONE;
        gbcContent.anchor = GridBagConstraints.SOUTHEAST;
        gbcContent.weighty = 0.0;
        gbcContent.insets = new Insets(10, 5, 5, 5);
        contentPanel.add(buttonPanel, gbcContent);

        GridBagConstraints gbcContentPanel = new GridBagConstraints();
        gbcContentPanel.gridx = 1;
        gbcContentPanel.gridy = 0;
        gbcContentPanel.weightx = 0.8;
        gbcContentPanel.weighty = 1.0;
        gbcContentPanel.fill = GridBagConstraints.BOTH;
        visualPanel.add(contentPanel, gbcContentPanel);

        // Initialize Values
        setPrinterOptions(xmlWorker.getAllPrinters());
        // setProgram(new WerkstattAuftrag());

        // Add ActionListeners to checkboxes to enable/disable their respective
        // ComboBoxes
        drucker1CheckBox.addActionListener(e -> {
            drucker1ComboBox.setEnabled(drucker1CheckBox.isSelected());
            Formularweg weg = (Formularweg) formularComboBox.getSelectedItem();
            // if (weg != null) {
            weg.getPrinter1().setEnabled(drucker1CheckBox.isSelected());
            // }
        });
        drucker2CheckBox.addActionListener(e -> {
            drucker2ComboBox.setEnabled(drucker2CheckBox.isSelected());
            Formularweg weg = (Formularweg) formularComboBox.getSelectedItem();
            // if (weg != null) {
            weg.getPrinter2().setEnabled(drucker2CheckBox.isSelected());
            // }
        });
        drucker3CheckBox.addActionListener(e -> {
            drucker3ComboBox.setEnabled(drucker3CheckBox.isSelected());
            Formularweg weg = (Formularweg) formularComboBox.getSelectedItem();
            // if (weg != null) {
            weg.getPrinter3().setEnabled(drucker3CheckBox.isSelected());
            // }
        });

    }

    private void createPrinterRow(JPanel panel, GridBagConstraints gbc, String label, JComboBox<PrinterDef> comboBox,
            JCheckBox checkBox, int gridy, char printerId) {
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
        comboBox.addActionListener(e -> {
            Formularweg weg = (Formularweg) formularComboBox.getSelectedItem();
            PrinterDef printer = (PrinterDef) comboBox.getSelectedItem();

            switch (printerId) {
                case '1': {
                    boolean asking = printer == xmlWorker.getAskingPrinter();
                    PrinterWrapper wrap = weg.getPrinter1();
                    if (wrap != null) {
                        wrap.setAskDialog(asking);
                    }
                    break;
                }
                case '2': {
                    boolean asking = printer == xmlWorker.getAskingPrinter();
                    PrinterWrapper wrap = weg.getPrinter2();
                    if (wrap != null) {
                        wrap.setAskDialog(asking);
                    }
                    break;
                }
                case '3': {
                    boolean asking = printer == xmlWorker.getAskingPrinter();
                    PrinterWrapper wrap = weg.getPrinter3();
                    if (wrap != null) {
                        wrap.setAskDialog(asking);
                    }
                    break;
                }
            }

            if (printer == xmlWorker.getAskingPrinter()) {
                weg.setPrinter(printerId, (null));

            } else {
                weg.setPrinter(printerId, printer);
            }
        });
        gbc.gridx = 2;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comboBox, gbc);

        checkBox.addActionListener(e -> {
            comboBox.setEnabled(checkBox.isSelected());
        });
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
    public void setPrinterOptions(PrintersDef printerDefs) {
        // printerDefs.getPrinter().sort(Comparator.comparing(PrinterDef::toString));

        // drucker1ComboBox.add(new JSeparator(JSeparator.HORIZONTAL));
        // Clear existing items and add new ones
        PrinterComboBoxModel model1 = new PrinterComboBoxModel(printerDefs);
        drucker1ComboBox.setModel(model1);

        PrinterComboBoxModel model2 = new PrinterComboBoxModel(printerDefs);
        drucker2ComboBox.setModel(model2);

        PrinterComboBoxModel model3 = new PrinterComboBoxModel(printerDefs);
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

    public JButton getOkButton() {
        return okButton;
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

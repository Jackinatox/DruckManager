package de.fernausoft.druckmanager.ui.panels.Settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
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
    private static final ImageIcon NOT_EDITED = new ImageIcon(
            new ImageIcon(Settings.class.getResource("/not_edited.png")).getImage().getScaledInstance(16, 16,
                    Image.SCALE_SMOOTH));
    private static final ImageIcon EDITED = new ImageIcon(new ImageIcon(Settings.class.getResource("/edited.png"))
            .getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));

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

        // Create a visual panel to wrap the Settings panel for spacing
        JPanel visualPanel = new JPanel(new BorderLayout());
        visualPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120)),
                                "Einstellungen",
                                javax.swing.border.TitledBorder.LEFT,
                                javax.swing.border.TitledBorder.TOP),
                        BorderFactory.createEmptyBorder(4, 4, 0, 4))));

        setLayout(new BorderLayout());
        visualPanel.setLayout(new BorderLayout());
        add(visualPanel, BorderLayout.CENTER);

        // --- Left Navigation Panel ---
        JScrollPane scrollPane = new JScrollPane(navPanel);
        scrollPane.setPreferredSize(new Dimension(350, 0));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        visualPanel.add(scrollPane, BorderLayout.WEST);

        // --- Right Content Panel (Form) ---
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Top panel for formular selection
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.add(new JLabel("Formular"), BorderLayout.WEST);

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
                    } else {
                        drucker2ComboBox.setSelectedItem(null);
                    }

                    if (isPrinter2Enabled) {
                        PrinterDef printer = printer2.getPrinterDef();
                        if (printer2.getAskDialog()) {
                            printer = xmlWorker.getAskingPrinter();
                        }
                        drucker2ComboBox.setSelectedItem(printer);
                    } else {
                        drucker2ComboBox.setSelectedItem(null);
                    }
                    if (isPrinter3Enabled) {
                        PrinterDef printer = printer3.getPrinterDef();
                        if (printer3.getAskDialog()) {
                            printer = xmlWorker.getAskingPrinter();
                        }
                        drucker3ComboBox.setSelectedItem(printer);
                    } else {
                        drucker3ComboBox.setSelectedItem(null);
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

                    String text = formular.toString();

                    // label.setFont(new Font(Font.MONOSPACED, formular.getEdited() ? Font.BOLD :
                    // Font.PLAIN, 12));
                    label.setText(text);
                    label.setIcon(formular.getEdited() ? EDITED : NOT_EDITED);
                }

                return label;
            }
        });

        topPanel.add(formularComboBox, BorderLayout.CENTER);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel for printer rows
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Add separator
        centerPanel.add(new JSeparator());
        centerPanel.add(Box.createVerticalStrut(10));

        // Create printer rows
        drucker1ComboBox = new JComboBox<>();
        drucker1CheckBox = new JCheckBox();
        centerPanel.add(createPrinterRowPanel("Drucker 1", drucker1ComboBox, drucker1CheckBox, '1'));
        centerPanel.add(Box.createVerticalStrut(5));

        drucker2ComboBox = new JComboBox<>();
        drucker2CheckBox = new JCheckBox();
        centerPanel.add(createPrinterRowPanel("Drucker 2", drucker2ComboBox, drucker2CheckBox, '2'));
        centerPanel.add(Box.createVerticalStrut(5));

        drucker3ComboBox = new JComboBox<>();
        drucker3CheckBox = new JCheckBox();
        centerPanel.add(createPrinterRowPanel("Drucker 3", drucker3ComboBox, drucker3CheckBox, '3'));

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        visualPanel.add(contentPanel, BorderLayout.CENTER);

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
            if (drucker1ComboBox.getSelectedItem() == null) {
                drucker1ComboBox.setSelectedItem(xmlWorker.getAskingPrinter());
            }
        });
        drucker2CheckBox.addActionListener(e -> {
            drucker2ComboBox.setEnabled(drucker2CheckBox.isSelected());
            Formularweg weg = (Formularweg) formularComboBox.getSelectedItem();
            // if (weg != null) {
            weg.getPrinter2().setEnabled(drucker2CheckBox.isSelected());
            if (drucker2ComboBox.getSelectedItem() == null) {
                drucker2ComboBox.setSelectedItem(xmlWorker.getAskingPrinter());
            }
        });
        drucker3CheckBox.addActionListener(e -> {
            drucker3ComboBox.setEnabled(drucker3CheckBox.isSelected());
            Formularweg weg = (Formularweg) formularComboBox.getSelectedItem();
            // if (weg != null) {
            weg.getPrinter3().setEnabled(drucker3CheckBox.isSelected());
            if (drucker3ComboBox.getSelectedItem() == null) {
                drucker3ComboBox.setSelectedItem(xmlWorker.getAskingPrinter());
            }
        });
    }

    private JPanel createPrinterRowPanel(String label, JComboBox<PrinterDef> comboBox, JCheckBox checkBox,
            char printerId) {
        // Create a horizontal row with static sizes for label and checkbox, comboBox
        // expands
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));

        JLabel labelComponent = new JLabel(label);
        labelComponent.setPreferredSize(new Dimension(90, 25));
        rowPanel.add(labelComponent);

        checkBox.setPreferredSize(new Dimension(25, 25));
        rowPanel.add(checkBox);

        rowPanel.add(Box.createHorizontalStrut(10)); // spacing

        comboBox.setEnabled(false);
        comboBox.setPreferredSize(new Dimension(150, 25));
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        rowPanel.add(comboBox);

        rowPanel.add(Box.createHorizontalGlue()); // allow comboBox to expand

        comboBox.addActionListener(e -> {
            Formularweg weg = (Formularweg) formularComboBox.getSelectedItem();
            PrinterDef printer = (PrinterDef) comboBox.getSelectedItem();

            if (printer == xmlWorker.getLinePrinter()) {
                comboBox.setSelectedItem(xmlWorker.getAskingPrinter());
                printer = (PrinterDef) comboBox.getSelectedItem();
                // return;
            }

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
                    // testing git build
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
            formularComboBox.repaint();
        });

        checkBox.addActionListener(e -> {
            comboBox.setEnabled(checkBox.isSelected());
            formularComboBox.repaint();
            navPanel.repaint();
        });

        return rowPanel;
    }

    /**
     * Helper method to create a navigation item (JButton) with specific styling.
     * 
     * @param text       The text to display on the button.
     * @param isSelected A boolean indicating if the item is currently selected.
     * @return A styled JButton.
     */
    private JButton createNavItem(BaseProgram program) {
        String name = program.getName();
        PaintAwareButton button = new PaintAwareButton(program, name);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setForeground(UIManager.getColor("Button.foreground"));
        button.setFocusPainted(false);
        button.setOpaque(true);

        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        button.setFont(button.getFont().deriveFont(Font.PLAIN, 12f));

        // Add onClick listener to change the selected state
        button.addActionListener(e -> {
            activeProgram = button.getProgram();
            setProgram(activeProgram);
            navPanel.repaint();
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

    public void setPrograms(List<BaseProgram> programs) {
        navPanel.removeAll();
        Collections.sort(programs, Comparator.comparing(program -> program.getName().toLowerCase()));
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
            return 64;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 64;
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

    private class PaintAwareButton extends JButton {
        private BaseProgram program;

        // private String name;

        public PaintAwareButton(BaseProgram program, String name) {
            super(name);
            this.program = program;
            // this.name = name;
        }

        @Override
        protected void paintComponent(Graphics g) {
            this.setIcon(program.getEdited() ? EDITED : NOT_EDITED);
            // if (program == Settings.this.activeProgram) {
            this.setBackground(program == Settings.this.activeProgram ? new Color(27, 102, 201) : Color.WHITE);
            this.setOpaque(true);
            // this.setForeground(program == Settings.this.activeProgram ? Color.LIGHT_GRAY
            // : Color.WHITE);
            // }
            super.paintComponent(g);
        }

        public BaseProgram getProgram() {
            return program;
        }

    }
}

package de.fernausoft.druckmanager.ui.dialogs;

import javax.swing.JOptionPane;

public class DeletePrinterDialog {

    public enum DeletePrinterOption {
        DELETE_MAPPINGS,
        SET_TO_ASK,
        CANCEL
    }

    public static DeletePrinterOption showDialog() {
        String message = "Dieser Drucker wird noch verwendet. Möchtest du:\n"
                + "1. Die entsprechenden Mappings löschen\n"
                + "2. Die Mappings auf 'Fragen' stellen?\n"
                + "3. Abbrechen?";

        Object[] options = { "Mappings löschen", "Auf 'Fragen' stellen", "Abbrechen" };
        int result = JOptionPane.showOptionDialog(
                null,
                message,
                "Drucker wird verwendet",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        switch (result) {
            case JOptionPane.YES_OPTION:
                return DeletePrinterOption.DELETE_MAPPINGS;
            case JOptionPane.NO_OPTION:
                return DeletePrinterOption.SET_TO_ASK;
            default:
                return DeletePrinterOption.CANCEL;
        }
    }
}

# DruckManager

Der DruckManager ist eine Java-Anwendung zur Verwaltung von Druckern und Druckeinstellungen. Die Anwendung ermöglicht es, Drucker zu konfigurieren und Benutzern spezifische Druckprogramme und -einstellungen zuzuweisen.

## Funktionsweise

Die Anwendung startet mit der `DruckManager`-Klasse, die die Haupt-UI (`DruckManagerPanel`) initialisiert und anzeigt. Die Konfigurationen werden aus einer XML-Datei (`clientprinterconfig.xml`) geladen und von der `XMLWorker`-Klasse verarbeitet.

Die Hauptoberfläche besteht aus drei Hauptbereichen:

1.  **Druckertabelle**: Zeigt eine Liste der verfügbaren Drucker an.
2.  **Benutzer-PC-Zuweisung**: Ermöglicht die Zuweisung von Benutzern zu PCs.
3.  **Einstellungen**: Bietet detaillierte Konfigurationsmöglichkeiten für die ausgewählten Benutzer und Programme.

## ActionListener

In der Anwendung werden verschiedene `ActionListener` verwendet, um auf Benutzerinteraktionen zu reagieren. Hier sind einige der wichtigsten `ActionListener` und ihre Funktionen:

### DruckManagerPanel

Die Klasse `DruckManagerPanel` implementiert das `PCUserSelectionListener`-Interface. Obwohl es sich nicht um einen direkten `ActionListener` handelt, reagiert es auf eine Auswahl in der `PCUserMappingPanel`.

- **Pfad**: `src/main/java/de/fernausoft/druckmanager/ui/forms/DruckManagerPanel.java`
- **Funktion**: Wenn ein Benutzer in der `PCUserMappingPanel` ausgewählt wird, wird die `onUserSelected`-Methode aufgerufen. Diese Methode aktualisiert das `Settings`-Panel, um die Programme und Einstellungen für den ausgewählten Benutzer anzuzeigen.

### Settings-Panel

Das `Settings`-Panel enthält mehrere `ActionListener`, die auf Interaktionen mit den UI-Komponenten reagieren.

- **Pfad**: `src/main/java/de/fernausoft/druckmanager/ui/panels/Settings/Settings.java`
- **Funktionen**:
    - **Formular-Auswahl**: Ein anonymer `ActionListener` ist an die `formularComboBox` gebunden. Wenn ein Formular ausgewählt wird, werden die zugehörigen Drucker in den Drucker-ComboBoxen (`drucker1ComboBox`, `drucker2ComboBox`, `drucker3ComboBox`) automatisch ausgewählt. Wenn ein Drucker nicht verfügbar ist, wird die entsprechende ComboBox deaktiviert.
    - **Navigations-Buttons**: Die `createNavItem`-Methode erstellt Buttons mit einem Lambda-Ausdruck als `ActionListener`. Wenn einer dieser Buttons geklickt wird, wird die `setProgram`-Methode aufgerufen, um die entsprechenden Programmeinstellungen im Panel anzuzeigen.

### EditPrinterName-Dialog

Der `EditPrinterName`-Dialog wird verwendet, um den Namen eines Druckers zu bearbeiten.

- **Pfad**: `src/main/java/de/fernausoft/druckmanager/ui/forms/EditPrinterName.java`
- **Funktion**: Der `cancelButton` hat einen `ActionListener`, der den Dialog schließt, wenn der Button geklickt wird. **Hinweis**: Diese Klasse ist derzeit als unbenutzt markiert.

package de.fernausoft.druckmanager.xml;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "printerconfig", namespace = "http://www.kfz3000plus.de/client/printerconfig")
@XmlType(name = "printerconfig_def", propOrder = { "printers", "targets" })
public class PrinterConfig {

    @XmlElement(namespace = "http://www.kfz3000plus.de/client/printerconfig")
    private Printers printers;

    @XmlElement(namespace = "http://www.kfz3000plus.de/client/printerconfig")
    private Targets targets;

    // getters and setters

    public Printers getPrinters() {
        return printers;
    }

    public void setPrinters(Printers printers) {
        this.printers = printers;
    }

    public Targets getTargets() {
        return targets;
    }

    public void setTargets(Targets targets) {
        this.targets = targets;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "printers_def", propOrder = { "printer" })
    public static class Printers {

        @XmlElement(name = "printer", namespace = "http://www.kfz3000plus.de/client/printerconfig")
        private List<Printer> printer;

        public List<Printer> getPrinter() {
            return printer;
        }

        public void setPrinter(List<Printer> printer) {
            this.printer = printer;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "printer_def", propOrder = { "name", "location", "descritpion" })
    public static class Printer {

        @XmlElement(namespace = "http://www.kfz3000plus.de/client/printerconfig", required = true)
        private String name;

        @XmlElement(namespace = "http://www.kfz3000plus.de/client/printerconfig")
        private String location;

        @XmlElement(namespace = "http://www.kfz3000plus.de/client/printerconfig")
        private String descritpion; // note spelling preserved

        @XmlAttribute(name = "ref", required = true)
        private String ref;

        // getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDescritpion() {
            return descritpion;
        }

        public void setDescritpion(String descritpion) {
            this.descritpion = descritpion;
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "targets_def", propOrder = { "target" })
    public static class Targets {

        @XmlElement(name = "target", namespace = "http://www.kfz3000plus.de/client/printerconfig")
        private List<Target> target;

        @XmlAttribute(name = "continueOnHit")
        private Boolean continueOnHit;

        @XmlAttribute(name = "searchSequence")
        private SearchSequenceType searchSequence = SearchSequenceType.FULL_THEN_HOST;

        @XmlAttribute(name = "duplicateDealing")
        private DuplicateDealingType duplicateDealing = DuplicateDealingType.KEEP;

        // getters and setters
        public List<Target> getTarget() {
            return target;
        }

        public void setTarget(List<Target> target) {
            this.target = target;
        }

        public Boolean getContinueOnHit() {
            return continueOnHit;
        }

        public void setContinueOnHit(Boolean continueOnHit) {
            this.continueOnHit = continueOnHit;
        }

        public SearchSequenceType getSearchSequence() {
            return searchSequence;
        }

        public void setSearchSequence(SearchSequenceType searchSequence) {
            this.searchSequence = searchSequence;
        }

        public DuplicateDealingType getDuplicateDealing() {
            return duplicateDealing;
        }

        public void setDuplicateDealing(DuplicateDealingType duplicateDealing) {
            this.duplicateDealing = duplicateDealing;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "target_def", propOrder = { "env" })
    public static class Target {

        @XmlElement(name = "env", namespace = "http://www.kfz3000plus.de/client/printerconfig")
        private List<KeyValue> env;

        @XmlAttribute(name = "username")
        private String username;

        @XmlAttribute(name = "hostname")
        private String hostname;

        // getters and setters
        public List<KeyValue> getEnv() {
            return env;
        }

        public void setEnv(List<KeyValue> env) {
            this.env = env;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "keyvalue_def")
    public static class KeyValue {

        @XmlAttribute(name = "env", required = true)
        private String env;

        @XmlAttribute(name = "value")
        private String value;

        @XmlAttribute(name = "ref")
        private String ref;

        // getters and setters
        public String getEnv() {
            return env;
        }

        public void setEnv(String env) {
            this.env = env;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }
    }

    public enum SearchSequenceType {
        @XmlEnumValue("fullThenUser")
        FULL_THEN_USER,

        @XmlEnumValue("fullThenHost")
        FULL_THEN_HOST
    }

    public enum DuplicateDealingType {
        @XmlEnumValue("override")
        OVERRIDE,

        @XmlEnumValue("keep")
        KEEP
    }
}

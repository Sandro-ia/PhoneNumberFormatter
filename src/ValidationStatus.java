public enum ValidationStatus {

    VALID("Already in a valid format"),
    CORRECTED("Mask corrected"),
    COMPOSED("Composed from mixed input"),
    INVALID("Invalid entry");

    private final String label;

    ValidationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

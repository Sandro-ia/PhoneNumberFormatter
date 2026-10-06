import java.util.Optional;

public class FormatResult {

    private final ValidationStatus status;
    private final Optional<PhoneType> type;
    private final Optional<String> formattedNumber;
    private final String message;

    public FormatResult(ValidationStatus status, PhoneType type, String formattedNumber, String message) {
        this.status = status;
        this.type = Optional.ofNullable(type);
        this.formattedNumber = Optional.ofNullable(formattedNumber);
        this.message = message;
    }

    public ValidationStatus getStatus() {
        return status;
    }

    public Optional<PhoneType> getType() {
        return type;
    }

    public Optional<String> getFormattedNumber() {
        return formattedNumber;
    }

    public String getMessage() {
        return message;
    }
}

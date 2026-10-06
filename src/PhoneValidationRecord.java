import java.time.LocalDateTime;

public class PhoneValidationRecord {

    private final int id;
    private final String originalInput;
    private final ValidationStatus status;
    private final String message;
    private final LocalDateTime timestamp;

    public PhoneValidationRecord(int id, String originalInput, ValidationStatus status, String message, LocalDateTime timestamp) {
        this.id = id;
        this.originalInput = originalInput;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getOriginalInput() {
        return originalInput;
    }

    public ValidationStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

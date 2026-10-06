import javax.sql.rowset.serial.SerialStruct;
import java.lang.reflect.Type;
import java.util.Optional;

public class PhoneFormatter {

    private PhoneFormatter() {
    }

    public static FormatResult process(String rawInput) {
        String trimmed = rawInput.trim();
        String digitsOnly = trimmed.replaceAll("[^0-9]", "");

        boolean isPureDigits = !trimmed.isEmpty() && trimmed.chars().allMatch(Character::isDigit);

        boolean looksLikePhoneMask = !trimmed.isEmpty() && trimmed.chars().allMatch(c -> Character.isDigit(c) ||
                c == '(' || c == ')' || c == '-' || c == ' ');

        Optional<PhoneType> typeOptional = PhoneType.byDigitCount(digitsOnly.length());

        if (!typeOptional.isPresent()) {
            String message = isPureDigits
                    ? "Invalid number: " + digitsOnly.length() + "digit(s) does not  match  any  accepted format (8, 9, 10 or 11 digits)."
                    : "Invalid entry: no valid phone number could be found in the input.";
            return new FormatResult(ValidationStatus.INVALID, null, null, message);
        }

        PhoneType type = typeOptional.get();
        String correctFormat = type.format(digitsOnly);

        if (isPureDigits) {
            String message = "Formatted: " + correctFormat + " (" + type.getLabel() + ")";
            return new FormatResult(ValidationStatus.VALID, type, correctFormat, message);
        }

        if (trimmed.equals(correctFormat)) {
            String message = "Valid " + type.getLabel() + ": " + correctFormat;
            return new FormatResult(ValidationStatus.VALID, type, correctFormat, message);
        }

        if(looksLikePhoneMask) {
            String message = "Nask corrected: '" + trimmed + "' -> " + correctFormat + "' (" + type.getLabel() + ")";
            return new FormatResult(ValidationStatus.CORRECTED, type, correctFormat, message);
        }

        String message = "Found a " + type.getLabel() + " inside the input: " + correctFormat;
        return new FormatResult(ValidationStatus.COMPOSED, type, correctFormat, message);
    }
}

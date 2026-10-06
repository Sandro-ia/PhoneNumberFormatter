import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum PhoneType {

    LANDLINE_WITHOUT_AREA_CODE(8, false, "Landline phone"),
    LANDLINE_WITH_AREA_CODE(10, true, "Landline phone"),
    MOBILE_WITHOUT_AREA_CODE(9, false, "Mobile phone"),
    MOBILE_WITH_AREA_CODE(11, true, "Mobile phone");

    private final int digitCount;
    private final boolean hasAreaCode;
    private final String label;

    PhoneType(int digitCount, boolean hasAreaCode, String label) {
        this.digitCount = digitCount;
        this.hasAreaCode = hasAreaCode;
        this.label = label;
    }

    public int getDigitCount() {
        return digitCount;
    }

    public String getLabel() {
        return label;
    }

    public String format(String digits) {
        StringBuilder result =  new StringBuilder();

        String localNumber = digits;
        if(hasAreaCode) {
            String areaCode = digits.substring(0, 2);
            localNumber = digits.substring(2);
            result.append('(').append(areaCode).append(')');
        }

        int splitPoint = (localNumber.length() == 9) ? 5 : 4;
        result.append(localNumber, 0, splitPoint).append('-').append(localNumber.substring(splitPoint));

        return result.toString();
    }

    private static final Map<Integer, PhoneType> BY_DIGIT_COUNT = Arrays.stream(values()).collect(Collectors.toMap(PhoneType::getDigitCount, type -> type));

    public static Optional<PhoneType> byDigitCount(int digitCount) {
        return Optional.ofNullable(BY_DIGIT_COUNT.get(digitCount));
    }
}

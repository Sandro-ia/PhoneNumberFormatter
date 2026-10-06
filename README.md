# Phone Number Formatter (Java)

A Java console program, built as a DIO bootcamp exercise, that takes any phone number input — formatted, unformatted, or mixed with other text — and returns it properly formatted, detecting whether it is a **landline** or a **mobile** number.

## Accepted formats

| Type | Without area code | With area code |
|---|---|---|
| Landline | 8 digits → `xxxx-xxxx` | 10 digits → `(xx)xxxx-xxxx` |
| Mobile | 9 digits → `xxxxx-xxxx` | 11 digits → `(xx)xxxxx-xxxx` |

## What it detects

1. **Only digits, correct count** → formats it and says which type it is.
2. **Only digits, wrong count** → reports that it is not a valid number.
3. **Already formatted correctly** → returns it unchanged and says which type it is.
4. **Formatted with the wrong mask** → corrects it and shows the correction applied (`original -> corrected`).
5. **Digits mixed with other text** (e.g. `"Call me at 11987654321"`) → extracts the number, formats it and says what it found, or reports an invalid entry if no valid number is hidden inside.

## Concepts practiced

| Feature | Where it is used |
|---|---|
| **Enums** | `PhoneType` (the 4 formats) and `ValidationStatus` (the 4 outcomes) |
| **Map** | `PhoneType.BY_DIGIT_COUNT`, built once to look up a type by its digit count |
| **Optional** | `PhoneType.byDigitCount()` and the fields in `FormatResult` |
| **Streams API** | `chars().allMatch(...)` to check the input's characters; `Collectors.toMap()` to build the lookup map |
| **Generics** | `HistoryRepository<K, V>`, reused as-is from the calculator project |
| **Wrapper classes** | `Integer` (map keys), `AtomicInteger` (id counter) |
| **String** | `trim`, `replaceAll` (regex), `substring`, `equals`, `equalsIgnoreCase` |
| **StringBuilder** | Assembling the masked number in `PhoneType.format()` |
| **StringBuffer** | Building the history report (written from a background thread) |
| **LocalDateTime** | Timestamp of each validation |
| **Thread / Runnable** | The history report is built on a background thread and joined before printing |

`BigDecimal` was not needed (no arithmetic here), and `Date`, `Calendar`, `OffsetDateTime`, `LocalDate` and `LocalTime` were left out for the same reason as in the calculator project: `LocalDateTime` alone is enough for a single local session with no time zone involved.

## Project structure

```
PhoneFormatter/
├── src/
│   ├── PhoneType.java              # enum: the 4 accepted formats and how to mask them
│   ├── ValidationStatus.java       # enum: the 4 possible outcomes
│   ├── FormatResult.java           # outcome of checking one input
│   ├── PhoneFormatter.java         # core logic: classifies and formats the input
│   ├── PhoneValidationRecord.java  # one entry of the session history
│   ├── HistoryRepository.java      # generic storage, reused from the calculator project
│   ├── PhoneValidationService.java # runs validations, keeps history, builds the report
│   └── Main.java                   # console loop
└── .gitignore
```

## How to run

**Requirements:** JDK 8 or higher.

### Using IntelliJ IDEA

1. Open the project folder in IntelliJ.
2. Open `src/Main.java`.
3. Click the green run button next to `main`.

### Using the command line

From the project root folder:

```
javac -d out src/PhoneType.java src/ValidationStatus.java src/FormatResult.java src/PhoneFormatter.java src/PhoneValidationRecord.java src/HistoryRepository.java src/PhoneValidationService.java src/Main.java
java -cp out Main
```

## Example session

```
=== Phone Number Formatter ===
Accepted formats: 8/9 digits (no area code) or 10/11 digits (with area code).

Type a phone number (any format) or 'exit' to finish:
32114455
Formatted: 3211-4455 (Landline phone)

Type a phone number (any format) or 'exit' to finish:
11-98765-4321
Mask corrected: '11-98765-4321' -> '(11)98765-4321' (Mobile phone)

Type a phone number (any format) or 'exit' to finish:
Call me at 11987654321 please
Found a Mobile phone inside the input: (11)98765-4321

Type a phone number (any format) or 'exit' to finish:
exit

=== Validation history ===
#1 [Already in a valid format] '32114455' -> Formatted: 3211-4455 (Landline phone) (05/10/2026 21:25:44)
#2 [Mask corrected] '11-98765-4321' -> Mask corrected: '11-98765-4321' -> '(11)98765-4321' (Mobile phone) (05/10/2026 21:25:44)
#3 [Composed from mixed input] 'Call me at 11987654321 please' -> Found a Mobile phone inside the input: (11)98765-4321 (05/10/2026 21:25:44)

Goodbye, Sandro!
```

## Author

Sandro ([@Sandro-ia](https://github.com/Sandro-ia))

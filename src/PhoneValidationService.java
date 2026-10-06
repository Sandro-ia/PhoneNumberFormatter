import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class PhoneValidationService {

    private final HistoryRepository<Integer, PhoneValidationRecord> history = new HistoryRepository<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public FormatResult validate(String rawInput) {
        FormatResult result = PhoneFormatter.process(rawInput);

        int id = nextId.getAndIncrement();
        PhoneValidationRecord record = new PhoneValidationRecord(id, rawInput, result.getStatus(), result.getMessage(), LocalDateTime.now());
        history.save(id, record);

        return result;
    }

    public boolean hasHistory() {
        return !history.isEmpty();
    }

    public String buildHistoryReport() {
        StringBuffer report = new StringBuffer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        Runnable reportTask = () -> {
            report.append("======= Validation history =======").append(System.lineSeparator());
            for (PhoneValidationRecord record : history.getAll()) {
                report.append("#").append(record.getId())
                        .append(" [").append(record.getStatus().getLabel()).append("] ")
                                .append("'").append(record.getOriginalInput()).append("' -> ")
                                .append(record.getMessage())
                                .append(" (").append(record.getTimestamp().format(formatter)).append(")")
                                .append(System.lineSeparator());
            }
        };

        Thread reportThread = new Thread(reportTask, "report-builder");
        reportThread.start();
        try {
            reportThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return report.toString();
    }
}


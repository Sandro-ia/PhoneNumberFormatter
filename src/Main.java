import java.text.Format;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PhoneValidationService service = new PhoneValidationService();

        System.out.println("======= Phone Numbeer Formatter =======");
        System.out.println("Accepted formats: 8/9 digits (no area code) or 10/11 digits (with area code).");

        boolean KeepGoing = true;
        while (KeepGoing) {
            System.out.println();
            System.out.println("Type a phone number (any format) or 'exit' to finish: ");
            String input = scanner.nextLine();

            if (input.trim().equalsIgnoreCase("exit")) {
                KeepGoing = false;
                continue;
            }

            FormatResult result = service.validate(input);
            System.out.println(result.getMessage());
        }

        System.out.println();
        if (service.hasHistory()) {
            System.out.println(service.buildHistoryReport());
        } else {
            System.out.println("No numbers were checked.");
        }

        System.out.println("Goodbye, Sandro!");
        scanner.close();
    }
}
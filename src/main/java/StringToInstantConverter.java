import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
public class StringToInstantConverter {


    public static void main(String[] args) {
        String dateTimeString = "2023-11-30T15:30:00"; // Replace with your actual DateTime string

        // Define the format of your DateTime string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try {
            // Parse the string into a LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);

            // Attach a time zone (UTC in this example, adjust as needed)
            ZoneOffset zoneOffset = ZoneOffset.UTC;
            Instant instant = localDateTime.atOffset(zoneOffset).toInstant();

            System.out.println("Original DateTime String: " + dateTimeString);
            System.out.println("Instant: " + instant);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConversionExample {

    public static void main(String[] args) {
        // Input date of birth string
        String dateString = "04021993";

        // Define the input date format
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");

        try {
            // Parse the input date string
            Date date = inputFormat.parse(dateString);

            // Get the timestamp (milliseconds since January 1, 1970, 00:00:00 GMT)
            long timestamp = date.getTime();

            // Print the result
            System.out.println("Input Date String: " + dateString);
            System.out.println("Timestamp: " + timestamp);

            // Convert the timestamp back to Date
            Date dateFromTimestamp = new Date(timestamp);

            // Format the Date back to a string
            String formattedDate = inputFormat.format(dateFromTimestamp);

            // Print the result
            System.out.println("Date from Timestamp: " + dateFromTimestamp);
            System.out.println("Formatted Date: " + formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class DateUtils {
    public static void main(String[] args) throws Exception {
//        String dateString = "06011992";
//        SimpleDateFormat inputDateFormat = new SimpleDateFormat("ddMMyyyy");
//        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//        try {
//            Date date = inputDateFormat.parse(dateString);
//            String formattedDate = outputDateFormat.format(date);
//            System.out.println("Original date string: " + dateString);
//            System.out.println("Parsed and formatted date: " + formattedDate);
//        } catch (ParseException e) {
//            System.out.println("Error parsing date: " + e.getMessage());
//        }

//        String currentDir = System.getProperty("user.dir");
//        System.out.println("Current Directory: " + currentDir);
//
//        Date date = new Date();
//        long time = date.getTime();
//        System.out.println(date);
//        System.out.println(time);

        // Generate a timestamp
        long currentTimeMillis = new Date().getTime();
        // Generate a unique identifier (UUID)
        String uniqueIdentifier = UUID.randomUUID().toString();
        String newFilenameBase = currentTimeMillis + "_" + uniqueIdentifier;

        // Generate a timestamp
        long currentTimeMillis2 = new Date().getTime();
        // Generate a unique identifier (UUID)
        String uniqueIdentifier2 = UUID.randomUUID().toString();
        String newFilenameBase2 = currentTimeMillis2 + "_" + uniqueIdentifier2;

        // Generate a timestamp
        long currentTimeMillis3 = new Date().getTime();
        // Generate a unique identifier (UUID)
        String uniqueIdentifier3 = UUID.randomUUID().toString();
        String newFilenameBase3 = currentTimeMillis3 + "_" + uniqueIdentifier3;

        System.out.println(newFilenameBase + "," + newFilenameBase2 + "," + newFilenameBase3);
    }


}

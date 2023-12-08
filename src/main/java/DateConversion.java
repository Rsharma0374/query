import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversion {
    public static void main(String[] args) {
        String inputDateStr = "10081995";
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(inputDateStr);
            String formattedDate = outputFormat.format(date);
            System.out.println("Original Date String: " + inputDateStr);
            System.out.println("Formatted Date: " + formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

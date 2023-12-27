import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigInteger;

public class HexToTimestamp {

    public static void main(String[] args) {
        String hexValue = "6537d94e5a00000000000000";
        BigInteger bigIntegerValue = new BigInteger(hexValue, 16);
        long timestamp = bigIntegerValue.longValue() / 1000L;

        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);

        System.out.println("Timestamp: " + timestamp);
        System.out.println("Formatted Date: " + formattedDate);
    }
}

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class BitlyConverter {

    public static void main(String[] args) throws IOException {
        String longUrl = "https://ssguat.serviceurl.in/aaramb-api/"; // Replace with your long URL

        URL url = new URL("https://api-ssl.bitly.com/v4/shorten");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setRequestProperty("Authorization", "Bearer YOUR_ACCESS_TOKEN_HERE"); // Replace with your Bitly access token
        connection.setDoOutput(true);

        JSONObject requestBody = new JSONObject();
        requestBody.put("long_url", longUrl);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(requestBody.toString());
        wr.flush();
        wr.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            String bitlyLink = jsonResponse.getString("link");
            System.out.println("Bitly Link: " + bitlyLink);
        } else {
            System.out.println("Error: " + responseCode);
        }
    }
}

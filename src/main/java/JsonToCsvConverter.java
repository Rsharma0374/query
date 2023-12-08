import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class JsonToCsvConverter {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Load JSON data from a file
            JsonNode rootNode = objectMapper.readTree(new File("/home/lentraadmin/Documents/jsonToCSV/GoNoGo-Core_HBL-CD.json"));

            // Prepare CSV output
            FileWriter csvWriter = new FileWriter("output.csv");

            if (rootNode.isArray() && rootNode.size() > 0) {
                JsonNode firstObject = rootNode.get(0); // Get the first JSON object
                Iterator<String> fieldNames = firstObject.fieldNames();

                // Write CSV header
                while (fieldNames.hasNext()) {
                    csvWriter.append(fieldNames.next());
                    if (fieldNames.hasNext()) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");

                // Write CSV data
                for (JsonNode node : rootNode) {
                    fieldNames = node.fieldNames();
                    while (fieldNames.hasNext()) {
                        String fieldName = fieldNames.next();
                        csvWriter.append(node.get(fieldName).asText());
                        if (fieldNames.hasNext()) {
                            csvWriter.append(",");
                        }
                    }
                    csvWriter.append("\n");
                }

                csvWriter.flush();
                csvWriter.close();

                System.out.println("CSV data has been written to output.csv");
            } else {
                System.out.println("JSON data is not in the expected format.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

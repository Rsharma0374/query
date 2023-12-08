import java.io.*;
import java.util.*;

public class CSVDuplicateRemover {

    public static void main(String[] args) {
        // Provide the path to your CSV file
        String csvFilePath = "/home/lentraadmin/Documents/CsvDupFile/gng_names_ids.csv"; //9636
        int columnToCheck = 0; // Column index (0-based) to check for duplicates

        List<String> lines = new ArrayList<>();
        Set<String> seenValues = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > columnToCheck) {
                    String value = columns[columnToCheck].trim();
                    if (!value.isEmpty()) {
                        if (!seenValues.contains(value)) {
                            seenValues.add(value);
                            lines.add(line);
                        } else {
                            System.out.println("Duplicate found: " + value);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the filtered lines back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
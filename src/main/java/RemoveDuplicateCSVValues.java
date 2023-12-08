import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemoveDuplicateCSVValues {
    public static void main(String[] args) {
        String inputFile = "/home/lentraadmin/CKYC/SepLot1/gng_names_ids.csv"; // Replace with your input CSV file
        String outputFile = "/home/lentraadmin/CKYC/SepLot1/output_gng_names_ids.csv"; // Replace with your output CSV file
        int columnToCheckForDuplicates = 0; // Index of the column to check for duplicates (0-based)

        try (CSVReader reader = new CSVReader(new FileReader(inputFile));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {

            String[] nextLine;
            Set<String> uniqueValues = new HashSet<>();
            List<String[]> outputLines = new ArrayList<>();

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length > columnToCheckForDuplicates) {
                    String valueInColumn = nextLine[columnToCheckForDuplicates];
                    if (!uniqueValues.contains(valueInColumn)) {
                        uniqueValues.add(valueInColumn);
                        outputLines.add(nextLine);
                    }
                }
            }

            writer.writeAll(outputLines);
            System.out.println("Duplicates removed and saved to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

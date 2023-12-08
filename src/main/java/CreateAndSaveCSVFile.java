import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class CreateAndSaveCSVFile {
    public static void main(String[] args) {
        // Specify the target directory path
        String directoryPath = "/tmp/gonogo/";

        // Ensure the directory exists, create it if not
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created successfully: " + directoryPath);
            } else {
                System.err.println("Failed to create the directory: " + directoryPath);
                return;
            }
        }

        // Define the file path and name for the CSV file
        String filePath = directoryPath + "example.csv";

        try {
            // Create a FileWriter object to write to the CSV file
            FileWriter writer = new FileWriter(filePath);

            // Create a CSVWriter object with the FileWriter
            CSVWriter csvWriter = new CSVWriter(writer);

            // Define the data to be written to the CSV file
            String[] header = {"Name", "Age", "City"};
            String[] data1 = {"John", "30", "New York"};
            String[] data2 = {"Alice", "25", "Los Angeles"};

            // Write the header and data to the CSV file
            csvWriter.writeNext(header);
            csvWriter.writeNext(data1);
            csvWriter.writeNext(data2);

            // Close the CSVWriter and FileWriter to save the file
            csvWriter.close();
            writer.close();

            System.out.println("CSV file created and saved successfully at " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;
import java.util.*;

public class CSVComparator {

    public static void main(String[] args) {
        String file1Path = "/home/lentraadmin/Documents/Csvcompare/Compare1/file1.csv"; // Path to the first CSV file
        String file2Path = "/home/lentraadmin/Documents/Csvcompare/Compare1/file2.csv"; // Path to the second CSV file
        String columnToCompare = "id"; // Column to compare

        try {
            List<String> file1Data = readCSV(file1Path, columnToCompare);
            List<String> file2Data = readCSV(file2Path, columnToCompare);

            file1Data.removeAll(file2Data);

            writeCSV("/home/lentraadmin/Documents/Csvcompare/Compare1/missing_entries.csv", file1Data);

            System.out.println("Missing entries extracted and saved to missing_entries.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readCSV(String filePath, String columnToCompare) throws IOException {
        List<String> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] headers = br.readLine().split(",");
            int columnIndex = Arrays.asList(headers).indexOf(columnToCompare);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (columnIndex >= 0 && columnIndex < values.length) {
                    data.add(values[columnIndex]);
                }
            }
        }

        return data;
    }

    private static void writeCSV(String filePath, List<String> data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String entry : data) {
                bw.write(entry);
                bw.newLine();
            }
        }
    }
}

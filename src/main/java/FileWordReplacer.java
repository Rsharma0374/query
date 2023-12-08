import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileWordReplacer {

    public static void main(String[] args) {
        String filePath = "/home/lentraadmin/Downloads/gonogo/gonogo.2023-10-17.txt";
        String oldWord = "Exception occur at the time of sending sms email for ref";
        String newWord = "newWord";

        Path path = Paths.get(filePath);

        try {
            Stream<String> lines = Files.lines(path);
            List<String> replacedLines = lines.map(line -> line.replaceAll(oldWord, newWord)).collect(Collectors.toList());
            Files.write(path, replacedLines);
            lines.close();
            System.out.println("File updated successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while processing the file: " + e.getMessage());
        }
    }
}

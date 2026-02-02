import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public static List<String> readCsv(String filePath) throws IOException {
        List<String> teams = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                for (String value : parts) {
                    teams.add(value.trim());
                }
            }
        }

        return teams;
    }
}
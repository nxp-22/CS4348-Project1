
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Logger <logfile>");
            return;
        }

        String filename = args[0];
        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

            PrintWriter writer = new PrintWriter(
                new FileWriter(filename, true))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("QUIT")) {
                    break;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.trim().split("\\s+", 2);
                String action = parts[0];
                String message = parts.length > 1 ? parts[1] : "";
                String timestamp =
                    LocalDateTime.now().format(formatter);
                writer.println(timestamp + " [" + action + "] " + message);
                writer.flush();
            }

        } catch (IOException e) {
            System.err.println("Logger error: " + e.getMessage());
        }
    }
}

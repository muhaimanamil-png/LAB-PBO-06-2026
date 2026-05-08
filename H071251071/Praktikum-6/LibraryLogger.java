import java.time.*;
import java.time.format.*;
import java.util.*;

public class LibraryLogger {
    private List<String> logs;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LibraryLogger() {
        logs = new ArrayList<>();
    }

    public String logActivity(String activity) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(formatter);
        String log = timestamp + " " + activity;
        logs.add(log);
        return log;
    }

    public String getLogs() {
        StringBuilder sb = new StringBuilder();
        for (String log : logs) {
            sb.append(log).append("\n");
        }
        return sb.toString();
    }

    public void clearLogs() {
        logs.clear();
    }
}
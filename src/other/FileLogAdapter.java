package other;

import java.util.ArrayList;
import java.util.List;

/*log.txt dosyasındaki ham satırları LogEntry nesnelerine dönüştürür.*/
public class FileLogAdapter implements LogProvider {

    private static final String LOG_FILE = "log.txt";

    @Override
    public List<LogEntry> getLogs() {
        List<LogEntry> entries = new ArrayList<>();
        List<String> lines = FileManager.read(LOG_FILE);

        for (String line : lines) {
            if (line == null || line.isEmpty()) continue;

            String[] parts = line.split(";");
            if (parts.length != 5) continue;

            entries.add(new LogEntry(
                    parts[0],  
                    parts[1],   
                    parts[2],   
                    parts[3],   
                    parts[4]    
            ));
        }

        return entries;
    }
}

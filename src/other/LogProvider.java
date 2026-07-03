package other;

import java.util.List;

/* Adapter Pattern -  Farklı log kaynakları bu arayüzü implement edebilir.*/
public interface LogProvider {
    List<LogEntry> getLogs();
}

package other;

/**
 * Adapter Pattern */
public class LogEntry {

    private String plate;
    private String action;     
    private String floor;
    private String slot;
    private String timestamp; 
    private String formattedTime;

    public LogEntry(String plate, String action, String floor,
                    String slot, String timestamp) {
        this.plate     = plate;
        this.action    = action;
        this.floor     = floor;
        this.slot      = slot;
        this.timestamp = timestamp;
        this.formattedTime = formatTime(timestamp);
    }

    private String formatTime(String ms) {
        try {
            long t = Long.parseLong(ms);
            return new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                    .format(new java.util.Date(t));
        } catch (Exception e) {
            return ms;
        }
    }

    public String getPlate()  
           { return plate; }
    public String getAction()  
          { return action; }
    public String getFloor()  
           { return floor; }
    public String getSlot()   
           { return slot; }
    public String getTimestamp()  
        { return timestamp; }
    public String getFormattedTime()
     { return formattedTime; }

   
    public String toRawLine() {
        return plate + ";" + action + ";" + floor + ";" + slot + ";" + timestamp;
    }
}

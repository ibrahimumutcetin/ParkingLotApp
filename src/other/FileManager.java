package other;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    
    public static List<String> read(String file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line);
        } catch (IOException e) {
            
        }
        return lines;
    }

   
    public static void write(String file, String text) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(text + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public static void write(String file, List<String> lines, boolean append) {
        try (FileWriter fw = new FileWriter(file, append)) {
            for (String line : lines) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    public static boolean remove(String file, String plaka) {
        List<String> lines = read(file);
        boolean removed = lines.removeIf(l -> l.startsWith(plaka + ";"));
        if (removed) {
            try (FileWriter fw = new FileWriter(file, false)) {
                for (String line : lines) fw.write(line + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return removed;
    }
    
    public static long parseTime(String time) {
        try {
            return new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                    .parse(time).getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    
    
}

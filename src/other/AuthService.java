package other;

import java.util.List;

public class AuthService {

    private static final String USER_FILE = "users.txt";
  
    public static boolean login(String plaka, String sifre) {
        List<String> users = FileManager.read(USER_FILE);

        for (String u : users) {
            String[] p = u.split(";");
            if (p.length == 2 &&
                p[0].equalsIgnoreCase(plaka) &&
                p[1].equals(sifre)) {

                // Singleton
                Session.getInstance().setPlaka(plaka);
                return true;
            }
        }
        return false;
    }
 
    

    public static boolean register(String plaka, String sifre) {
        List<String> users = FileManager.read(USER_FILE);

      
        for (String u : users) {
            String[] p = u.split(";");
            if (p.length > 0 && p[0].equalsIgnoreCase(plaka)) {
                return false;
            }
        }

        
        FileManager.write(USER_FILE, plaka.toUpperCase() + ";" + sifre);
        return true;
    }
}

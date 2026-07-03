package other;

/**Decorator Pattern - Park servisi arayüzü.*/
public interface IParkingService {
    boolean aracGiris(String kat, String plaka, String slot);
    boolean aracCikis(String plaka);
    boolean aracZatenParkta(String plaka);
}

package other;

/**
 * Observer Pattern - Park olaylarını dinleyen gözlemci arayüzü.
 * Bu arayüzü implement eden sınıflar park olaylarından haberdar olur.
 */
public interface ParkingObserver {
    void onParkingEvent(ParkingEvent event);
}

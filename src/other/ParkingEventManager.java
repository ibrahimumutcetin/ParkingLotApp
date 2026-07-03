package other;

import java.util.ArrayList;
import java.util.List;

/**  Observer Pattern Singleton  */
public class ParkingEventManager {

    private static ParkingEventManager instance;

    private ParkingEventManager() {}

    public static ParkingEventManager getInstance() {
        if (instance == null) {
            instance = new ParkingEventManager();
        }
        return instance;
    }

   
    private final List<ParkingObserver> observers = new ArrayList<>();

    public void addObserver(ParkingObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(ParkingObserver observer) {
        observers.remove(observer);
    }

    
    public void notifyObservers(ParkingEvent event) {
        System.out.println("[OBSERVER] Olay bildiriliyor: " + event);
        for (ParkingObserver o : observers) {
            o.onParkingEvent(event);
        }
    }
}

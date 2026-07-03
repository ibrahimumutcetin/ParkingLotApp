package other;

import ui.Kat1;
import ui.Kat2;

/* Factory Method */
public class ParkingFloorFactory {

    public static ParkingFloor createFloor(String floorType) {
        switch (floorType.toUpperCase()) {
            case "KAT1":
                return new Kat1();
            case "KAT2":
                return new Kat2();
            default:
                throw new IllegalArgumentException("Bilinmeyen kat: " + floorType);
        }
    }
}

package other;

/*  Observer Pattern*/
public class ParkingEvent {

    public enum Type {
        GIRIS, CIKIS, REZERVASYON, IPTAL
    }

    private Type   type;
    private String plate;
    private String floor;
    private String slot;

    public ParkingEvent(Type type, String plate, String floor, String slot) {
        this.type  = type;
        this.plate = plate;
        this.floor = floor;
        this.slot  = slot;
    }

    public Type   getType()  { return type; }
    public String getPlate() { return plate; }
    public String getFloor() { return floor; }
    public String getSlot()  { return slot; }

    @Override
    public String toString() {
        return "[ParkingEvent] " + type + " | " + plate + " | " + floor + "/" + slot;
    }
}

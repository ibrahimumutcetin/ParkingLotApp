package other;

/** Decorator Pattern - ParkingService'e loglama özelliği ekleyen dekoratör.*/
public class ParkingServiceLogDecorator implements IParkingService {

    private final IParkingService wrapped;

    public ParkingServiceLogDecorator(IParkingService service) {
        this.wrapped = service;
    }

    @Override
    public boolean aracGiris(String kat, String plaka, String slot) {
        System.out.println("[LOG-DECORATOR] Araç giriş denemesi: " + plaka + " → " + kat + "/" + slot);
        boolean result = wrapped.aracGiris(kat, plaka, slot);
        if (result) {
            System.out.println("[LOG-DECORATOR] Araç giriş BAŞARILI: " + plaka);
        } else {
            System.out.println("[LOG-DECORATOR] Araç giriş BAŞARISIZ: " + plaka);
        }
        return result;
    }

    @Override
    public boolean aracCikis(String plaka) {
        System.out.println("[LOG-DECORATOR] Araç çıkış denemesi: " + plaka);
        boolean result = wrapped.aracCikis(plaka);
        if (result) {
            System.out.println("[LOG-DECORATOR] Araç çıkış BAŞARILI: " + plaka);
        } else {
            System.out.println("[LOG-DECORATOR] Araç çıkış BAŞARISIZ: " + plaka);
        }
        return result;
    }

    @Override
    public boolean aracZatenParkta(String plaka) {
        boolean result = wrapped.aracZatenParkta(plaka);
        System.out.println("[LOG-DECORATOR] Araç parkta mı kontrolü: " + plaka + " → " + result);
        return result;
    }
}

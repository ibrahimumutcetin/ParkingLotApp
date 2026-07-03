package other;

import java.util.List;
import javax.swing.JOptionPane;

/**
 * Decorator Pattern  
 * Observer Pattern   
 */
public class ParkingService implements IParkingService {

    private static final String PARK = "park.txt";
    private static final String LOG  = "log.txt";
    private static final String RES  = "reservation.txt";

  
    private static IParkingService decoratedInstance;

   
    public static IParkingService getService() {
        if (decoratedInstance == null) {
            decoratedInstance = new ParkingServiceLogDecorator(new ParkingService());
        }
        return decoratedInstance;
    }



    @Override
    public boolean aracGiris(String kat, String plaka, String slot) {

        if (plaka == null || slot == null) return false;

        plaka = plaka.toUpperCase();
        kat   = kat.toUpperCase();

        if (aracZatenParkta(plaka)) return false;

        rezervasyonSuresiKontrol(null);

        long zaman = System.currentTimeMillis();

        FileManager.write(PARK,
                plaka + ";" + kat + ";" + slot + ";" + zaman + ";AKTIF");

        FileManager.write(LOG,
                plaka + ";GIRIS;" + kat + ";" + slot + ";" + zaman);

        rezervasyonKullanildi(plaka, kat, slot);

        Session.getInstance().setPlaka(plaka);
        Session.getInstance().setGirisZamani(zaman);
        Session.getInstance().setParktaMi(true);

     
        ParkingEventManager.getInstance().notifyObservers(
                new ParkingEvent(ParkingEvent.Type.GIRIS, plaka, kat, slot));

        return true;
    }

    @Override
    public boolean aracCikis(String plaka) {

        if (plaka == null) return false;
        plaka = plaka.toUpperCase();

        List<String> park = FileManager.read(PARK);
        String kat = null, slot = null;

        for (int i = 0; i < park.size(); i++) {
            String[] p = park.get(i).split(";");
            if (p.length < 5) continue;

            if (p[0].equals(plaka) && p[4].equals("AKTIF")) {
                kat = p[1];
                slot = p[2];
                park.remove(i);
                break;
            }
        }

        if (kat == null) return false;

        FileManager.write(PARK, park, false);

        long zaman = System.currentTimeMillis();
        FileManager.write(LOG,
                plaka + ";CIKIS;" + kat + ";" + slot + ";" + zaman);

        Session.getInstance().setParktaMi(false);

        ParkingEventManager.getInstance().notifyObservers(
                new ParkingEvent(ParkingEvent.Type.CIKIS, plaka, kat, slot));

        return true;
    }

    @Override
    public boolean aracZatenParkta(String plaka) {

        if (plaka == null) return false;
        plaka = plaka.toUpperCase();

        List<String> park = FileManager.read(PARK);
        for (String line : park) {
            String[] p = line.split(";");
            if (p.length >= 5 &&
                p[0].equals(plaka) &&
                p[4].equals("AKTIF")) {
                return true;
            }
        }
        return false;
    }



    public static boolean rezervasyonAl(
            String plaka, String kat, String slot, int dakika) {

        if (plakaninAktifRezervasyonuVar(plaka)) {
            JOptionPane.showMessageDialog(
                null,
                "This vehicle already has an active reservation!"
            );
            return false;
        }

        if (plaka == null || slot == null) return false;

        plaka = plaka.toUpperCase();
        kat   = kat.toUpperCase();

        if (getReservationPlate(kat, slot) != null) return false;

        long zaman = System.currentTimeMillis();

        FileManager.write(RES,
                plaka + ";" + kat + ";" + slot + ";" +
                zaman + ";" + dakika + ";AKTIF");


        ParkingEventManager.getInstance().notifyObservers(
                new ParkingEvent(ParkingEvent.Type.REZERVASYON, plaka, kat, slot));

        return true;
    }

    public static void rezervasyonSuresiKontrol(String plaka) {

        List<String> list = FileManager.read(RES);
        long now = System.currentTimeMillis();
        boolean degisti = false;

        for (int i = 0; i < list.size(); i++) {
            String[] r = list.get(i).split(";");
            if (r.length < 6) continue;

            if (plaka == null || r[0].equalsIgnoreCase(plaka)) {

                if (r[5].equals("AKTIF")) {
                    long bas = Long.parseLong(r[3]);
                    int dk   = Integer.parseInt(r[4]);

                    if ((now - bas) >= dk * 60000L) {
                        list.set(i, String.join(";", r));
                        degisti = true;
                    }
                }
            }
        }

        if (degisti)
            FileManager.write(RES, list, false);
    }

    private static void rezervasyonKullanildi(
            String plaka, String kat, String slot) {

        List<String> list = FileManager.read(RES);
        boolean degisti = false;

        for (int i = 0; i < list.size(); i++) {
            String[] r = list.get(i).split(";");
            if (r.length < 6) continue;

            if (r[0].equals(plaka) &&
                r[1].equals(kat) &&
                r[2].equals(slot) &&
                r[5].equals("AKTIF")) {

                r[5] = "KULLANILDI";
                list.set(i, String.join(";", r));
                degisti = true;
            }
        }

        if (degisti)
            FileManager.write(RES, list, false);
    }

    public static String getReservationPlate(String kat, String slot) {

        List<String> list = FileManager.read(RES);

        for (String line : list) {
            String[] r = line.split(";");
            if (r.length < 6) continue;

            if (r[1].equalsIgnoreCase(kat) &&
                r[2].equalsIgnoreCase(slot) &&
                r[5].equals("AKTIF")) {
                return r[0];
            }
        }
        return null;
    }

    public static void rezervasyonIptal(
            String plaka, String kat, String slot) {

        List<String> list = FileManager.read(RES);

        for (int i = 0; i < list.size(); i++) {
            String[] r = list.get(i).split(";");
            if (r.length < 6) continue;

            if (r[0].equalsIgnoreCase(plaka) &&
                r[1].equalsIgnoreCase(kat) &&
                r[2].equalsIgnoreCase(slot) &&
                r[5].equals("AKTIF")) {

                r[5] = "IPTAL";
                list.set(i, String.join(";", r));
                break;
            }
        }

        FileManager.write(RES, list, false);

   
        ParkingEventManager.getInstance().notifyObservers(
                new ParkingEvent(ParkingEvent.Type.IPTAL, plaka, kat, slot));
    }

    public static List<String[]> suresiDolanRezervasyonlar() {

        List<String[]> expired = new java.util.ArrayList<>();
        List<String> list = FileManager.read(RES);
        long now = System.currentTimeMillis();

        for (String line : list) {
            String[] r = line.split(";");
            if (r.length < 6) continue;

            if (r[5].equals("AKTIF")) {
                long bas = Long.parseLong(r[3]);
                int dk   = Integer.parseInt(r[4]);

                if ((now - bas) >= dk * 60000L) {
                    expired.add(r);
                }
            }
        }
        return expired;
    }

    public static boolean plakaninAktifRezervasyonuVar(String plaka) {

        if (plaka == null) return false;
        plaka = plaka.toUpperCase();

        List<String> list = FileManager.read(RES);

        for (String line : list) {
            String[] r = line.split(";");
            if (r.length < 6) continue;

            if (r[0].equals(plaka) && r[5].equals("AKTIF")) {
                return true;
            }
        }
        return false;
    }

    public static void sureDolmusRezervasyonlariTemizle() {

        List<String> list = FileManager.read(RES);
        long now = System.currentTimeMillis();
        boolean degisti = false;

        for (int i = 0; i < list.size(); i++) {
            String[] r = list.get(i).split(";");
            if (r.length < 6) continue;

            if (!r[5].equals("AKTIF")) continue;

            long bas = Long.parseLong(r[3]);
            int dk   = Integer.parseInt(r[4]);

            if ((now - bas) >= dk * 60000L) {

                r[5] = "SURESI_DOLDU";
                list.set(i, String.join(";", r));
                degisti = true;
            }
        }

        if (degisti) {
            FileManager.write(RES, list, false);
        }
    }
}

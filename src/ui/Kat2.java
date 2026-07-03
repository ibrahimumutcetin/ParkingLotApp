package ui;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import other.IParkingService;
import other.ParkingFloor;
import other.ParkingService;
import other.Session;


public class Kat2 extends JFrame implements ParkingFloor {

    private static final long serialVersionUID = 1L;

    private final int PARK_SAYISI = 40;

    private final Color YESIL   = new Color(0xA5, 0xD6, 0xA7);
    private final Color KIRMIZI = new Color(239, 154, 154);
    private final Color SARI    = new Color(250, 237, 122);

    private JPanel contentPane;
    private JButton[] parkButonlari = new JButton[PARK_SAYISI];

    private boolean[] doluMu = new boolean[PARK_SAYISI];
    private boolean[] rezerveMi = new boolean[PARK_SAYISI];
    private String[] plakalar = new String[PARK_SAYISI];


    private final IParkingService parkingService = ParkingService.getService();

    public Kat2() {

        setTitle("Kat 2");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        contentPane = new BackgroundPanel("/icons/emptylot800x600.png");
        contentPane.setBorder(new EmptyBorder(20,20,20,20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel main = new JPanel(new GridLayout(4,10,20,20));
        main.setOpaque(false);
        contentPane.add(main, BorderLayout.CENTER);

        for (int i = 0; i < PARK_SAYISI; i++) {

            JPanel cell = new JPanel(new BorderLayout());
            cell.setOpaque(false);

            JLabel lbl = new JLabel(slotAdi(i), SwingConstants.CENTER);
            lbl.setForeground(Color.WHITE);

            JButton btn = new JButton();
            btn.setBackground(YESIL);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);

            int index = i;
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {

                    if (SwingUtilities.isLeftMouseButton(e))
                        solTik(index);

                    if (SwingUtilities.isRightMouseButton(e))
                        sagTik(index);
                }
            });

            parkButonlari[i] = btn;
            cell.add(lbl, BorderLayout.NORTH);
            cell.add(btn, BorderLayout.CENTER);
            main.add(cell);
        }

        parklariYukle();
        rezervasyonlariYukle();
        ParkingService.sureDolmusRezervasyonlariTemizle();
    }

  

    @Override
    public void showFloor() {
        setVisible(true);
    }

    @Override
    public String getFloorName() {
        return "KAT2";
    }

    private String slotAdi(int index) {
        char harf = (char) ('E' + index / 10);
        int no = (index % 10) + 1;
        return harf + String.valueOf(no);
    }


    private void solTik(int index) {

        if (doluMu[index]) {
            JOptionPane.showMessageDialog(this,
                    "Occupied\nPlate: " + plakalar[index]);
            return;
        }

        if (rezerveMi[index]) {
            JOptionPane.showMessageDialog(this,
                    "This slot is reserved!");
            return;
        }

        Object[] secenek = {"Park", "Reserve"};
        int secim = JOptionPane.showOptionDialog(
                this,
                slotAdi(index),
                "Choose",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                secenek,
                secenek[0]
        );

        if (secim == 0) parkEt(index);
        if (secim == 1) rezerveEt(index);
    }


    private void sagTik(int index) {

    	if (doluMu[index]) {

    	    if (!plakalar[index].equals(Session.getInstance().getPlaka())) {
    	        JOptionPane.showMessageDialog(
    	                this,
    	                "Only your vehicle can exit!"
    	        );
    	        return;
    	    }

    	    int secim = JOptionPane.showConfirmDialog(
    	            this,
    	            slotAdi(index) + " Do you want to exit?",
    	            "EXIT CONFIRM",
    	            JOptionPane.YES_NO_OPTION
    	    );

    	    if (secim == JOptionPane.YES_OPTION) {
    	        cikisYap(index);
    	    }
    	    return;
    	}

        if (rezerveMi[index]) {

            String slot = slotAdi(index);
            String plaka = Session.getInstance().getPlaka();

            String rezervasyonPlaka =
                    ParkingService.getReservationPlate("KAT2", slot);

            if (!plaka.equalsIgnoreCase(rezervasyonPlaka)) {
                JOptionPane.showMessageDialog(this,
                        "You can only cancel your own reservation!");
                return;
            }

            int secim = JOptionPane.showConfirmDialog(
                    this,
                    slot + " reservation will be cancelled. Continue?",
                    "Cancel Reservation",
                    JOptionPane.YES_NO_OPTION
            );

            if (secim == JOptionPane.YES_OPTION) {

                ParkingService.rezervasyonIptal(
                        plaka, "KAT2", slot);

                rezerveMi[index] = false;
                parkButonlari[index].setBackground(YESIL);
            }
        }
    }


    private void parkEt(int index) {

        String plaka = Session.getInstance().getPlaka();
        String slot = slotAdi(index);

        // Decorator Pattern - decorated servis üzerinden giriş
        if (parkingService.aracZatenParkta(plaka)) {
            JOptionPane.showMessageDialog(this,
                    "Vehicle already parked!");
            return;
        }

        parkingService.aracGiris("KAT2", plaka, slot);

        doluMu[index] = true;
        plakalar[index] = plaka;
        parkButonlari[index].setBackground(KIRMIZI);
    }


    private void rezerveEt(int index) {

        String plaka = Session.getInstance().getPlaka();
        String slot = slotAdi(index);

        ParkingService.rezervasyonAl(
                plaka, "KAT2", slot, 10);

        rezerveMi[index] = true;
        parkButonlari[index].setBackground(SARI);
    }


    private void parklariYukle() {

        File f = new File("park.txt");
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] d = line.split(";");
                if (!d[1].equals("KAT2") || !d[4].equals("AKTIF")) continue;

                int index = slotIndex(d[2]);
                if (index < 0) continue;

                doluMu[index] = true;
                plakalar[index] = d[0];
                parkButonlari[index].setBackground(KIRMIZI);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void rezervasyonlariYukle() {

        File f = new File("reservation.txt");
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

            String line;
            while ((line = br.readLine()) != null) {

            	String[] r = line.split(";");
            	if (r.length < 6) continue;

            	if (!r[1].equals("KAT2") || !r[5].equals("AKTIF")) continue;

                int index = slotIndex(r[2]);
                if (index < 0) continue;

                rezerveMi[index] = true;
                parkButonlari[index].setBackground(SARI);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sureDolmusRezervasyonlariTemizle() {

        var list = ParkingService.suresiDolanRezervasyonlar();

        for (String[] r : list) {

            if (!r[1].equals("KAT2")) continue;

            int index = slotIndex(r[2]);
            if (index < 0) continue;

            rezerveMi[index] = false;
            parkButonlari[index].setBackground(YESIL);
            r[5]="Süresi Doldu";
        }
    }


    private int slotIndex(String slot) {

        char harf = slot.charAt(0);
        int no = Integer.parseInt(slot.substring(1));

        return (harf - 'E') * 10 + (no - 1);
    }


    private void cikisYap(int index) {

  
        parkingService.aracCikis(plakalar[index]);

        doluMu[index] = false;
        plakalar[index] = null;
        parkButonlari[index].setBackground(YESIL);
    }

}

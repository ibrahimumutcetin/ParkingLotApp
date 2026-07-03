package ui;

import other.FileManager;
import other.ParkingEvent;
import other.ParkingEventManager;
import other.ParkingObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/* Observer Pattern */
public class Info extends JFrame implements ParkingObserver {

    private JLabel infoLabel;
    private Timer timer;

    private static final int KAT_KAPASITE = 40;

    public Info() {
        setTitle("ParkingLot - Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel contentPane = new BackgroundPanel("/icons/bg800x600.png");
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        infoLabel = new JLabel();
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setVerticalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE);

        contentPane.add(infoLabel, BorderLayout.CENTER);

        guncelleBosYerler();

      
        timer = new Timer(2000, e -> guncelleBosYerler());
        timer.start();

       
        ParkingEventManager.getInstance().addObserver(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (timer != null) timer.stop();
              
                ParkingEventManager.getInstance().removeObserver(Info.this);
            }
        });
    }

   
    @Override
    public void onParkingEvent(ParkingEvent event) {
        System.out.println("[INFO-OBSERVER] Park olayı alındı: " + event);
        SwingUtilities.invokeLater(this::guncelleBosYerler);
    }

 
    private void guncelleBosYerler() {

        int kat1Dolu = 0;
        int kat2Dolu = 0;

        List<String> parkList = FileManager.read("park.txt");
        for (String line : parkList) {
            if (line == null || line.isEmpty()) continue;

            String[] p = line.split(";");
            if (p.length < 5) continue;
            if (!p[4].equalsIgnoreCase("AKTIF")) continue;

            if (p[1].equalsIgnoreCase("KAT1")) kat1Dolu++;
            if (p[1].equalsIgnoreCase("KAT2")) kat2Dolu++;
        }

        List<String> resList = FileManager.read("reservation.txt");
        for (String line : resList) {
            if (line == null || line.isEmpty()) continue;

            String[] r = line.split(";");
            if (r.length < 6) continue;
            if (!r[5].equalsIgnoreCase("AKTIF")) continue;

            if (r[1].equalsIgnoreCase("KAT1")) kat1Dolu++;
            if (r[1].equalsIgnoreCase("KAT2")) kat2Dolu++;
        }

        int kat1Bos = KAT_KAPASITE - kat1Dolu;
        int kat2Bos = KAT_KAPASITE - kat2Dolu;

        if (kat1Bos < 0) kat1Bos = 0;
        if (kat2Bos < 0) kat2Bos = 0;

        infoLabel.setText(
            "<html>" +
            "<div style='text-align:center; color:white;'>" +

            "<h2>\uD83C\uDD7F️ PARKING INFORMATION</h2>" +

            "<b>GENERAL CAPACITY</b><br>" +
            "• Total Capacity: 80 Vehicles<br>" +
            "• Floor 1 Capacity: 40 Vehicles<br>" +
            "• Floor 2 Capacity: 40 Vehicles<br><br>" +

            "<b>EMPTY SLOTS (LIVE)</b><br>" +
            "<span style='color:#00ff99; font-size:14px;'>" +
                "• Floor 1 Empty: " + kat1Bos + "<br>" +
                "• Floor 2 Empty: " + kat2Bos +
            "</span><br><br>" +

            "<b>PRICING</b><br>" +
            "• Hourly Fee: 20 ₺<br><br>" +

            "<b>USAGE RULES</b><br>" +
            "• Parking service is available 24/7.<br>" +
            "• All vehicle entries and exits are logged.<br>" +
            "• Parking duration is calculated based on entry time.<br>" +
            "• Only registered license plates are accepted.<br>" +
            "• No entry is allowed when the parking lot is full.<br><br>" +

            "<b>SECURITY</b><br>" +
            "• Plate-based tracking system is used.<br>" +
            "• All actions are recorded for security purposes.<br>" +
            "• Unauthorized access is restricted.<br>" +

            "</div></html>"
        );
    }
}

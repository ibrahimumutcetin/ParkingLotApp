package ui;

import javax.swing.*;
import javax.swing.table.*;
import other.FileManager;
import other.PricingStrategy;
import other.HourlyPricingStrategy;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/* Strategy Pattern Abstract Factory Pattern  */
public class ParkedFrame extends JFrame {

    private JTable parkTable;
    private DefaultTableModel parkModel;
    private final String PARK_FILE = "park.txt";


    private final PricingStrategy pricingStrategy = new HourlyPricingStrategy();

  
    private final UIComponentFactory uiFactory = new DarkThemeFactory();

    public ParkedFrame() {
        setTitle("Parked Cars");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(200, 200, 700, 400);

        JPanel contentPane = new BackgroundPanel("/icons/bg800x600.png");
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        parkModel = new DefaultTableModel(new Object[]{"Plate","Floor","Slot","Time(min)","Charge (TL)"},0){
            public boolean isCellEditable(int row,int column){return false;}
        };

     
        parkTable = uiFactory.createTable(parkModel);

        JScrollPane scroll = new JScrollPane(parkTable);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        // Abstract Factory Pattern - buton fabrika üzerinden oluşturulur
        JButton btnForceExit = uiFactory.createButton("Force Exit");
        buttonPanel.add(btnForceExit);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        btnForceExit.addActionListener(e -> forceExit());

        loadPark();

        Timer timer = new Timer(1000, e -> updateParkDurations());
        timer.start();
    }

    private void loadPark() {
        parkModel.setRowCount(0);
        List<String> park = FileManager.read(PARK_FILE);
        for(String line: park){
            String[] p = line.split(";");
            if(p.length==5 && p[4].equals("AKTIF")){
                String plaka=p[0], kat=p[1], slot=p[2];
                long giris=Long.parseLong(p[3]);
                long sureMs = System.currentTimeMillis() - giris;
                long dakika = sureMs / 1000 / 60;

                // Strategy Pattern - ücret hesaplama stratejiye devredildi
                int ucret = pricingStrategy.calculateFee(sureMs);

                parkModel.addRow(new Object[]{plaka,kat,slot,dakika,ucret});
            }
        }
    }

    private void forceExit() {
        int row = parkTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a parked car first!"); return; }
        String plaka = (String) parkModel.getValueAt(row, 0);
        int secim = JOptionPane.showConfirmDialog(this, plaka + " Force EXIT?", "Force Exit", JOptionPane.YES_NO_OPTION);
        if (secim != JOptionPane.YES_OPTION) return;

        List<String> park = FileManager.read(PARK_FILE);
        park.removeIf(l -> l.split(";")[0].equalsIgnoreCase(plaka) && l.split(";")[4].equals("AKTIF"));
        FileManager.write(PARK_FILE, park, false);
        loadPark();
    }

    private void updateParkDurations() {
        List<String> parkLines = FileManager.read(PARK_FILE);

        for (int i = 0; i < parkModel.getRowCount(); i++) {
            if (i >= parkLines.size()) break;

            String[] parkLine = parkLines.get(i).split(";");
            if (parkLine.length == 5 && parkLine[4].equals("AKTIF")) {
                long girisZamani = Long.parseLong(parkLine[3]);
                long sureMs = System.currentTimeMillis() - girisZamani;
                long dakika = sureMs / 1000 / 60;

                // Strategy Pattern - ücret hesaplama stratejiye devredildi
                int ucret = pricingStrategy.calculateFee(sureMs);

                parkModel.setValueAt(dakika, i, 3);
                parkModel.setValueAt(ucret, i, 4);
            }
        }
    }
}

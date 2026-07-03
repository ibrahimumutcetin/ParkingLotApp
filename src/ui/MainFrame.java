package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import other.ParkingFloor;
import other.ParkingFloorFactory;
import other.PricingStrategy;
import other.HourlyPricingStrategy;
import other.Session;

/**
 * Factory Method Pattern 
 * Strategy Pattern      
 * Singleton Pattern    
 */
public class MainFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JButton btnKat1, btnKat2, btnInfo, btnGeri;
    private JLabel ucretBilgi, sureLabel;
    private javax.swing.Timer timer;


    private final PricingStrategy pricingStrategy = new HourlyPricingStrategy();

    public MainFrame() {

        setTitle("ParkingLot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 768);

        contentPane = new BackgroundPanel("/icons/bg800x600.png");
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

  
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(220, 0));
        sidePanel.setBackground(new Color(15, 15, 15));
        contentPane.add(sidePanel, BorderLayout.WEST);

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 8));
        infoPanel.setBorder(new EmptyBorder(20, 15, 20, 15));
        infoPanel.setOpaque(false);

     
        JLabel plakaLabel = new JLabel("Plate: " +
                (Session.getInstance().getPlaka() == null ? "-" : Session.getInstance().getPlaka()));
        plakaLabel.setForeground(Color.WHITE);
        plakaLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        sureLabel = new JLabel("Time: 0 min");
        sureLabel.setForeground(Color.WHITE);
        sureLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        ucretBilgi = new JLabel("Charge: 0 TL");
        ucretBilgi.setForeground(Color.WHITE);
        ucretBilgi.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        infoPanel.add(plakaLabel);
        infoPanel.add(sureLabel);
        infoPanel.add(ucretBilgi);

        sidePanel.add(infoPanel, BorderLayout.CENTER);

        btnGeri = new JButton("LOG OUT");
        btnGeri.setBackground(new Color(255, 70, 70));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnGeri.addActionListener(this);

        sidePanel.add(btnGeri, BorderLayout.SOUTH);

   
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 25));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(260, 260));

        btnKat1 = createButton("1st Floor");
        btnKat2 = createButton("2nd Floor");
        btnInfo = createButton("Info");

        buttonPanel.add(btnKat1);
        buttonPanel.add(btnKat2);
        buttonPanel.add(btnInfo);

        centerPanel.add(buttonPanel);

        timer = new javax.swing.Timer(1000, e -> {

            if (!Session.getInstance().isParktaMi() || Session.getInstance().getGirisZamani() <= 0) {
                sureLabel.setText("Time: 0 min");
                ucretBilgi.setText("Charge: 0 TL");
                return;
            }

            long sureMs = System.currentTimeMillis() - Session.getInstance().getGirisZamani();
            long dakika = sureMs / 60000;


            int ucret = pricingStrategy.calculateFee(sureMs);

            Session.getInstance().setUcret(ucret);
            sureLabel.setText("Time: " + dakika + " min");
            ucretBilgi.setText("Charge: " + ucret + " TL");
        });

        timer.start();
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setBackground(new Color(120, 50, 225));
        b.setForeground(Color.WHITE);
        b.addActionListener(this);
        return b;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnKat1) {
           
            ParkingFloor floor = ParkingFloorFactory.createFloor("KAT1");
            floor.showFloor();
        }
        else if (e.getSource() == btnKat2) {
            
            ParkingFloor floor = ParkingFloorFactory.createFloor("KAT2");
            floor.showFloor();
        }
        else if (e.getSource() == btnInfo) {
            new Info().setVisible(true);
        }
        else if (e.getSource() == btnGeri) {

            timer.stop();

           
            Session.getInstance().setParktaMi(false);
            Session.getInstance().setPlaka(null);
            Session.getInstance().setGirisZamani(0);
            Session.getInstance().setUcret(0);

            dispose();
            new Giris().setVisible(true);
        }
    }
}

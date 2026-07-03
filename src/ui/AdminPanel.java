package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;


/* Abstract Factory Pattern - DarkThemeFactory ile buton oluşturma.*/
public class AdminPanel extends JFrame implements ActionListener {
	private JButton btnUsers;
	private JButton btnParked;
	private JButton btnLogs;
	private JButton btnReservation;


	private final UIComponentFactory uiFactory = new DarkThemeFactory();

    public AdminPanel() {
    	setTitle("Admin Panel");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        JPanel contentPane = new BackgroundPanel("/icons/bg800x600.png");
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        Font font = new Font("Segoe UI", Font.BOLD, 20);
        
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(0, 100, 0, 100));
        contentPane.add(panel, BorderLayout.WEST);
        panel.setOpaque(false);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new EmptyBorder(0, 100, 0, 100));
        contentPane.add(panel_1, BorderLayout.EAST);
        panel_1.setOpaque(false);
        
        JPanel panel_2 = new JPanel();
        contentPane.add(panel_2, BorderLayout.CENTER);
        panel_2.setLayout(new BorderLayout(0, 0));
        panel_2.setOpaque(false);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new EmptyBorder(0, 40, 0, 40));
        panel_2.add(panel_3, BorderLayout.EAST);
        panel_3.setOpaque(false);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBorder(new EmptyBorder(0, 40, 0, 40));
        panel_2.add(panel_4, BorderLayout.WEST);
        panel_4.setOpaque(false);
        
        JPanel panel_5 = new JPanel();
        panel_5.setBorder(new EmptyBorder(100, 0, 0, 0));
        panel_2.add(panel_5, BorderLayout.NORTH);
        panel_5.setOpaque(false);
        panel_5.setLayout(new BorderLayout(0, 0));
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(Color.WHITE);
        panel_5.add(lblNewLabel);
        
        JPanel panel_8 = new JPanel();
        panel_8.setBorder(new EmptyBorder(0, 0, 30, 0));
        panel_5.add(panel_8, BorderLayout.SOUTH);
        panel_8.setOpaque(false);
        
        JLabel lblNewLabel_1 = new JLabel("WELCOME BACK !");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblNewLabel_1.setForeground(Color.WHITE);
        panel_5.add(lblNewLabel_1, BorderLayout.NORTH);
        
        
        
        JPanel panel_6 = new JPanel();
        panel_6.setBorder(new EmptyBorder(0, 0, 100, 0));
        panel_2.add(panel_6, BorderLayout.SOUTH);
        panel_6.setOpaque(false);
        
        
        
       
        
        JPanel panel_7 = new JPanel();
        panel_2.add(panel_7, BorderLayout.CENTER);
        panel_7.setLayout(new GridLayout(4, 1, 0, 30));
        panel_7.setOpaque(false);

        // Abstract Factory Pattern - butonlar fabrika üzerinden oluşturulur
        btnUsers = uiFactory.createButton("USERS");
        btnUsers.setFont(font);
        btnUsers.setPreferredSize(new Dimension(220, 55));
        
        btnParked = uiFactory.createButton("PARKED");
        btnParked.setFont(font);
        btnParked.setPreferredSize(new Dimension(220, 55));
        
        btnLogs = uiFactory.createButton("LOGS");
        btnLogs.setFont(font);
        btnLogs.setPreferredSize(new Dimension(220, 55));

        panel_7.add(btnUsers);
        panel_7.add(btnParked);
        
        btnReservation = uiFactory.createButton("RESERVATIONS");
        btnReservation.setFont(font);
        btnReservation.setPreferredSize(new Dimension(220, 55));
        panel_7.add(btnReservation);
        btnReservation.addActionListener(this);
        panel_7.add(btnLogs);
        
        btnUsers.addActionListener(this);
        btnParked.addActionListener(this);
        btnLogs.addActionListener(this);


        
}
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUsers) {
            new UsersFrame().setVisible(true);
        } else if (e.getSource() == btnParked) {
            new ParkedFrame().setVisible(true);
        }
        else if (e.getSource() == btnLogs) {
        	new LogsFrame().setVisible(true);
        }
        else if (e.getSource() == btnReservation) {
        	new ReservationFrame().setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        AdminPanel admin = new AdminPanel();
        admin.setVisible(true);
   
    }
    
}

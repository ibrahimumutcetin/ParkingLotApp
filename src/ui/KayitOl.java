package ui;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import other.AuthService;

import javax.swing.JPasswordField;



import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class KayitOl extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtplaka2;
	private JTextField txttekrarsifre;
	private JTextField txtsifre2;
	private JLabel lblplaka2;
	private JLabel lblsifre2;
	private JLabel lbltekrarsifre;
	private JButton btnkayit;
	private String plaka2;
	private String sifre2;
	private String tekrarsifre;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KayitOl frame = new KayitOl();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public KayitOl() {
		setTitle("ParkingLot");
		setForeground(new Color(30, 30, 30));
		setBackground(new Color(30, 30, 30));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setBounds(100, 100, 800, 600);
		Font font  = new Font("Segoe UI", Font.PLAIN, 16);
		contentPane = new BackgroundPanel("/icons/background.jpg"); 
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridBagLayout());
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 30, 30));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		gbc_panel.fill = GridBagConstraints.BOTH;   
		gbc_panel.weightx = 1.0;                   
		gbc_panel.weighty = 1.0;                   
		contentPane.add(panel, gbc_panel);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {300, 200, 300};
		gbl_panel.rowHeights = new int[] {50,20,20,30,20,30, 20, 30, 50, 50, 50,50,10};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel.setOpaque(false);
		panel.setLayout(gbl_panel);
		
			
		JLabel lblkayit = new JLabel("SIGN UP");
		lblkayit.setForeground(new Color(255, 255, 255));
		lblkayit.setFont(new Font("Segoe UI", Font.BOLD, 40));
		lblkayit.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblkayit = new GridBagConstraints();
		gbc_lblkayit.gridwidth = 3;
		gbc_lblkayit.gridheight = 2;
		gbc_lblkayit.insets = new Insets(0, 0, 5, 5);
		gbc_lblkayit.gridx = 0;
		gbc_lblkayit.gridy = 0;
		panel.add(lblkayit, gbc_lblkayit);
		
		lblplaka2 = new JLabel("PLATE");
		lblplaka2.setForeground(Color.WHITE);
		lblplaka2.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GridBagConstraints gbc_lblplaka2 = new GridBagConstraints();
		gbc_lblplaka2.insets = new Insets(0, 0, 5, 5);
		gbc_lblplaka2.gridx = 1;
		gbc_lblplaka2.gridy = 2;
		panel.add(lblplaka2, gbc_lblplaka2);
		
		txtplaka2 = new JTextField();
		txtplaka2.setForeground(new Color(255, 255, 255));
		txtplaka2.setBackground(new Color(50, 50, 50));
		txtplaka2.setFont(font);
		txtplaka2.setBorder(new MatteBorder(1, 1, 2, 1, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_txtplaka2 = new GridBagConstraints();
		gbc_txtplaka2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtplaka2.anchor = GridBagConstraints.NORTH;
		gbc_txtplaka2.insets = new Insets(0, 0, 5, 5);
		gbc_txtplaka2.gridx = 1;
		gbc_txtplaka2.gridy = 3;
		panel.add(txtplaka2, gbc_txtplaka2);
		txtplaka2.setColumns(10);
		
		lblsifre2 = new JLabel("PASSWORD");
		lblsifre2.setForeground(Color.WHITE);
		lblsifre2.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GridBagConstraints gbc_lblsifre2 = new GridBagConstraints();
		gbc_lblsifre2.insets = new Insets(0, 0, 5, 5);
		gbc_lblsifre2.gridx = 1;
		gbc_lblsifre2.gridy = 4;
		panel.add(lblsifre2, gbc_lblsifre2);
		
		txtsifre2 = new JTextField();
		txtsifre2.setForeground(new Color(255, 255, 255));
		txtsifre2.setBackground(new Color(50, 50, 50));
		txtsifre2.setFont(font);
		txtsifre2.setBorder(new MatteBorder(1, 1, 2, 1, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_txtsifre2 = new GridBagConstraints();
		gbc_txtsifre2.anchor = GridBagConstraints.NORTH;
		gbc_txtsifre2.insets = new Insets(0, 0, 5, 5);
		gbc_txtsifre2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtsifre2.gridx = 1;
		gbc_txtsifre2.gridy = 5;
		panel.add(txtsifre2, gbc_txtsifre2);
		txtsifre2.setColumns(10);
		
		lbltekrarsifre = new JLabel("PASSWORD AGAIN");
		lbltekrarsifre.setForeground(Color.WHITE);
		lbltekrarsifre.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GridBagConstraints gbc_lbltekrarsifre = new GridBagConstraints();
		gbc_lbltekrarsifre.insets = new Insets(0, 0, 5, 5);
		gbc_lbltekrarsifre.gridx = 1;
		gbc_lbltekrarsifre.gridy = 6;
		panel.add(lbltekrarsifre, gbc_lbltekrarsifre);
		
		txttekrarsifre = new JTextField();
		txttekrarsifre.setForeground(Color.WHITE);
		txttekrarsifre.setBackground(new Color(50, 50, 50));
		txttekrarsifre.setFont(font);
		txttekrarsifre.setBorder(new MatteBorder(1, 1, 2, 1, (Color) new Color(0, 0, 0)));
		GridBagConstraints gbc_txttekrarsifre = new GridBagConstraints();
		gbc_txttekrarsifre.anchor = GridBagConstraints.NORTH;
		gbc_txttekrarsifre.insets = new Insets(0, 0, 5, 5);
		gbc_txttekrarsifre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txttekrarsifre.gridx = 1;
		gbc_txttekrarsifre.gridy = 7;
		panel.add(txttekrarsifre, gbc_txttekrarsifre);
		txttekrarsifre.setColumns(10);
		
		btnkayit = new JButton("Sign Up");
		btnkayit.setForeground(new Color(255, 255, 255));
		btnkayit.setBackground(new Color(120, 50, 215));
		
		GridBagConstraints gbc_btnkayit = new GridBagConstraints();
		gbc_btnkayit.insets = new Insets(0, 0, 5, 5);
		gbc_btnkayit.gridx = 1;
		gbc_btnkayit.gridy = 8;
		
		panel.add(btnkayit, gbc_btnkayit);
		btnkayit.addActionListener(this);
		
		
		JLabel lblLogin = new JLabel("Do have an account? Log in");
		lblLogin.setVerticalAlignment(SwingConstants.TOP);
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLogin.anchor = GridBagConstraints.NORTH;
		gbc_lblLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogin.gridx = 1;
		gbc_lblLogin.gridy = 9;
		panel.add(lblLogin, gbc_lblLogin);
		
		
		

		lblLogin.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        dispose();
		        EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Giris frame = new Giris();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		    }

		    public void mouseEntered(MouseEvent e) {
		        lblLogin.setForeground(new Color(120, 50, 215));
		    }

		    public void mouseExited(MouseEvent e) {
		        lblLogin.setForeground(Color.WHITE);
		    }
		});
		
		 

}
	


	public void actionPerformed(ActionEvent e) {

	    String plaka = txtplaka2.getText().trim().toUpperCase();
	    String sifre = txtsifre2.getText().trim();
	    String tekrar = txttekrarsifre.getText().trim();

	    if (plaka.isEmpty() || sifre.isEmpty() || tekrar.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Please fill in all fields");
	        return;
	    }

	    if (!sifre.equals(tekrar)) {
	        JOptionPane.showMessageDialog(this, "The passwords don't match!");
	        return;
	    }

	    boolean kayitBasarili = AuthService.register(plaka, sifre);

	    if (kayitBasarili) {
	        JOptionPane.showMessageDialog(this, "Registration successful!");
	        dispose();
	        new Giris().setVisible(true);
	    } else {
	        JOptionPane.showMessageDialog(this, "This plate is already registered!");
	    }
	}

	}

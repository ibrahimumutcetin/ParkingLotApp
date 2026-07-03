package ui;


import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.EventQueue;


import other.AuthService;

import javax.swing.border.MatteBorder;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/* Singleton Pattern */
public class Giris extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private String plaka;
    private String sifre;
	private JPanel contentPane;
	private JTextField txtPlaka;
	private JPasswordField txtSifre;
	private JButton btngiris;
	private JLabel lblPlaka;
	private JLabel lblsifre;
	private JButton btngizle;
	private JButton btngoster;
	public ImageIcon eyeofficon;
	public ImageIcon eyeonicon;
	

	
	public static void main(String[] args) {
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

	
	public Giris() {
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
		gbl_panel.rowHeights = new int[] {50,20,20,50,20,20, 50, 50, 50, 50, 50,50,10};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		panel.setOpaque(false);
		
		JLabel lblNewLabel = new JLabel("LOG IN");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.gridheight = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		lblPlaka = new JLabel("PLATE");
		lblPlaka.setForeground(Color.WHITE);
		lblPlaka.setFont(new Font("Segoe UI", Font.BOLD, 20));
		GridBagConstraints gbc_lblPlaka = new GridBagConstraints();
		gbc_lblPlaka.anchor = GridBagConstraints.SOUTH;
		gbc_lblPlaka.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlaka.gridx = 1;
		gbc_lblPlaka.gridy = 2;
		panel.add(lblPlaka, gbc_lblPlaka);
		
		txtPlaka = new JTextField();
		txtPlaka.setForeground(Color.WHITE);
		txtPlaka.setBackground(new Color(50, 50, 50));
		txtPlaka.setFont(font);
		txtPlaka.setToolTipText("Enter a plate");
		txtPlaka.setBorder(new MatteBorder(1, 1, 2, 1, (Color) new Color(0, 0, 0)));
		txtPlaka.setHorizontalAlignment(SwingConstants.LEFT);
		
		GridBagConstraints gbc_txtPlaka = new GridBagConstraints();
		gbc_txtPlaka.insets = new Insets(5, 5, 5, 5);
		gbc_txtPlaka.anchor = GridBagConstraints.NORTH;
		gbc_txtPlaka.gridx = 1;
		gbc_txtPlaka.gridwidth = 1;
		gbc_txtPlaka.gridheight=1;
		gbc_txtPlaka.gridy = 3;
		gbc_txtPlaka.weightx = 1.0;
		gbc_txtPlaka.fill = GridBagConstraints.HORIZONTAL;


		panel.add(txtPlaka, gbc_txtPlaka);
		txtPlaka.setColumns(20);
			
			lblsifre = new JLabel("PASSWORD");
			lblsifre.setForeground(Color.WHITE);
			lblsifre.setFont(new Font("Segoe UI", Font.BOLD, 20));
			GridBagConstraints gbc_lblsifre = new GridBagConstraints();
			gbc_lblsifre.anchor = GridBagConstraints.SOUTH;
			gbc_lblsifre.insets = new Insets(0, 0, 5, 5);
			gbc_lblsifre.gridx = 1;
			gbc_lblsifre.gridy = 4;
			panel.add(lblsifre, gbc_lblsifre);
		

			
			txtSifre = new JPasswordField(20);
			txtSifre.setEchoChar('*');
			txtSifre.setHorizontalAlignment(SwingConstants.LEFT);
			txtSifre.setForeground(Color.WHITE);
			txtSifre.setBackground(new Color(50, 50, 50));
			txtSifre.setFont(font);
			txtSifre.setToolTipText("Enter your password");
			txtSifre.setBorder(new MatteBorder(1, 1, 2, 1, (Color) new Color(0, 0, 0)));
			GridBagConstraints gbc_txtSifre = new GridBagConstraints();
			gbc_txtSifre.insets = new Insets(0, 0, 5, 5);
			gbc_txtSifre.anchor = GridBagConstraints.CENTER;
			gbc_txtSifre.gridx = 1;
			gbc_txtSifre.gridwidth = 1;
			gbc_txtSifre.gridheight=1;
			gbc_txtSifre.gridy = 5;
			gbc_txtSifre.weightx = 1.0;
			gbc_txtSifre.fill = GridBagConstraints.HORIZONTAL;
		

			panel.add(txtSifre, gbc_txtSifre);
			txtSifre.setColumns(20);
				
				eyeofficon = new ImageIcon(getClass().getResource("/icons/hidden24x24.png"));
				eyeonicon = new ImageIcon(getClass().getResource("/icons/eye24x24.png"));
				
				
				btngizle = new JButton(eyeonicon);
				
				
				
				btngizle.setPreferredSize(new Dimension(28,28));
				btngizle.setMinimumSize(new Dimension(24, 24));
				btngizle.setMaximumSize(new Dimension(28, 28));
				
				btngizle.setBackground(Color.WHITE);
				btngizle.setFocusPainted(false);
				btngizle.setContentAreaFilled(false);
				btngizle.setMargin(new Insets(0, 0, 0, 0));
				
				
				
	
				GridBagConstraints gbc_btngizle = new GridBagConstraints();
				gbc_btngizle.anchor = GridBagConstraints.SOUTHWEST;
				gbc_btngizle.fill = GridBagConstraints.NONE;
				gbc_btngizle.insets = new Insets(10, 10, 10, 10);
				gbc_btngizle.gridx = 2;
				gbc_btngizle.gridy = 5;
				btngizle.addActionListener(this);
				panel.add(btngizle, gbc_btngizle);
				
				
				
				
				btngiris = new JButton("Log In");
				btngiris.setForeground(new Color(255, 255, 255));
				btngiris.setBackground(new Color(120, 50, 215));
				
				
				GridBagConstraints gbc_btngiris = new GridBagConstraints();
				btngiris.setFont(font);
				gbc_btngiris.insets = new Insets(0, 0, 5, 5);
				gbc_btngiris.gridx = 1;
				gbc_btngiris.gridy = 7;
				
				panel.add(btngiris, gbc_btngiris);
				btngiris.addActionListener(this);
				
				JLabel lblRegister = new JLabel("Don't have an account? Sign Up ");
				lblRegister.setVerticalAlignment(SwingConstants.TOP);
				lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
				lblRegister.setForeground(Color.WHITE);
				lblRegister.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
				GridBagConstraints gbc_lblRegister = new GridBagConstraints();
				gbc_lblRegister.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblRegister.anchor = GridBagConstraints.NORTH;
				gbc_lblRegister.insets = new Insets(0, 0, 5, 5);
				gbc_lblRegister.gridx = 1;
				gbc_lblRegister.gridy = 8;
				panel.add(lblRegister, gbc_lblRegister);
				
				UIManager.put("OptionPane.background", new Color(30,30,30));
				UIManager.put("Panel.background", new Color(30,30,30));
				UIManager.put("OptionPane.messageForeground", new Color(120,50,215));
				UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
				UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
				
			
			

				lblRegister.addMouseListener(new MouseAdapter() {
				    public void mouseClicked(MouseEvent e) {
				        dispose();
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

				    public void mouseEntered(MouseEvent e) {
				        lblRegister.setForeground(new Color(120, 50, 215));
				    }

				    public void mouseExited(MouseEvent e) {
				        lblRegister.setForeground(Color.WHITE);
				    }
				});

				
	

}
	
	public String getPlaka() {
		return plaka;
	}
	
	
	public void actionPerformed(ActionEvent e) {

	 
	    if (e.getSource() == btngiris) {

	        String plaka = txtPlaka.getText().trim().toUpperCase();
	        String sifre = new String(txtSifre.getPassword()).trim();

	        if (plaka.isEmpty() || sifre.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "The plate and password cannot be blank.!");
	            return;
	        }

	        boolean girisBasarili = AuthService.login(plaka, sifre);

	        if (girisBasarili) {
	           

	            // ADMIN KONTROLÜ
	            if (plaka.equalsIgnoreCase("FLXX")) {
	                AdminPanel admin = new AdminPanel();
	                admin.setVisible(true);
	            } else {
	                MainFrame frame = new MainFrame();
	                frame.setVisible(true);
	            }
	            dispose();
	        } else {
	            JOptionPane.showMessageDialog(this, "The plate or password is incorrect!");
	        }
	    }

	
	    if (e.getSource() == btngizle) {
	        if (txtSifre.getEchoChar() == '*') {
	            txtSifre.setEchoChar((char) 0);
	            btngizle.setIcon(eyeofficon);
	        } else {
	            txtSifre.setEchoChar('*');
	            btngizle.setIcon(eyeonicon);
	        }
	    }
	}



	}
class BackgroundPanel extends JPanel {
    private final Image bg;

    public BackgroundPanel(String resourcePath) {
        bg = new ImageIcon(getClass().getResource(resourcePath)).getImage();
        setLayout(new GridBagLayout());  
        setOpaque(false);
    }

   
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

   
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

     
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}

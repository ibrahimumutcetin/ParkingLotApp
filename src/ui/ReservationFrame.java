package ui;

import other.FileManager;
import javax.swing.border.EmptyBorder;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReservationFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private Timer timer;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ReservationFrame frame = new ReservationFrame();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public ReservationFrame() {

        setTitle("Admin - Rezervasyonlar");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel contentPane = new BackgroundPanel("/icons/bg800x600.png");
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());


        model = new DefaultTableModel(
                new String[]{
                        "Plaka", "Kat", "Slot",
                        "Başlangıç", "Süre (dk)",
                        "Kalan Süre", "Durum"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setOpaque(false);
        table.setBackground(new Color(0, 0, 0, 0));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(30, 30, 30, 200));
        table.getTableHeader().setForeground(Color.WHITE);

        table.setRowHeight(24);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        centerPanel.add(scroll, BorderLayout.CENTER);

        contentPane.add(centerPanel, BorderLayout.CENTER);
        


        JButton btnCancel = new JButton("Cancel the reservation");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(120, 50, 215));
        btnCancel.addActionListener(e -> rezervasyonIptal());

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(10, 0, 20, 0));
        bottom.add(btnCancel);

        contentPane.add(bottom, BorderLayout.SOUTH);
        


        rezervasyonlariYukle();
        sureyiBaslat();
    }

 
    private void rezervasyonlariYukle() {

        model.setRowCount(0);
        List<String> list = FileManager.read("reservation.txt");

        for (String line : list) {

            if (line == null || line.isEmpty()) continue;

         
            String[] r = line.split(";");
            if (r.length < 6) continue;

            model.addRow(new Object[]{
                    r[0],
                    r[1],
                    r[2],
                    timeFormat(r[3]),
                    r[4],
                    kalanSure(r[3], r[4]),
                    r[5]
            });
        }
    }

 
    private String kalanSure(String startMs, String durationMin) {
        try {
            long start = Long.parseLong(startMs);
            long duration = Long.parseLong(durationMin) * 60_000;
            long remaining = (start + duration) - System.currentTimeMillis();

            if (remaining <= 0) return "SÜRE BİTTİ";

            long dk = remaining / 60000;
            long sn = (remaining % 60000) / 1000;
            return dk + " dk " + sn + " sn";

        } catch (Exception e) {
            return "-";
        }
    }

 
    private void sureyiBaslat() {

        timer = new Timer(1000, e -> {

            for (int i = 0; i < model.getRowCount(); i++) {

                String status = model.getValueAt(i, 6).toString();
                if (!status.equals("AKTIF")) continue;

                String startText = model.getValueAt(i, 3).toString();
                String duration = model.getValueAt(i, 4).toString();

                long startMs = FileManager.parseTime(startText);

                model.setValueAt(
                        kalanSure(String.valueOf(startMs), duration),
                        i, 5
                );
            }
        });

        timer.start();
    }

    
    private void rezervasyonIptal() {

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pick a reservation!");
            return;
        }

        String plaka = model.getValueAt(row, 0).toString();

        List<String> list = FileManager.read("reservation.txt");

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).startsWith(plaka + ";")) {

                String[] r = list.get(i).split(";");
                r[5] = "IPTAL";
                list.set(i, String.join(";", r));
                break;
            }
        }

        FileManager.write("reservation.txt", list, false);
        rezervasyonlariYukle();
    }

    
    private String timeFormat(String ms) {
        try {
            long t = Long.parseLong(ms);
            return new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                    .format(new java.util.Date(t));
        } catch (Exception e) {
            return "-";
        }
    }
}

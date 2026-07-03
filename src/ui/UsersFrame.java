package ui;

import javax.swing.*;
import javax.swing.table.*;
import other.FileManager;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/* Abstract Factory Pattern */
public class UsersFrame extends JFrame {

    private JTable userTable;
    private DefaultTableModel userModel;
    private final String USER_FILE = "users.txt";

    private final UIComponentFactory uiFactory = new DarkThemeFactory();

    public UsersFrame() {
        setTitle("Users");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(150, 150, 600, 400);

        JPanel contentPane = new BackgroundPanel("/icons/bg800x600.png");
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        userModel = new DefaultTableModel(new Object[]{"Plate", "Password"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

   
        userTable = uiFactory.createTable(userModel);

        JScrollPane scroll = new JScrollPane(userTable);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);


        JButton btnEdit = uiFactory.createButton("Edit");
        JButton btnDelete = uiFactory.createButton("Delete");

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        btnEdit.addActionListener(e -> editUser());
        btnDelete.addActionListener(e -> deleteUser());

        loadUsers();
    }

    private void loadUsers() {
        userModel.setRowCount(0);
        List<String> users = FileManager.read(USER_FILE);
        for (String u : users) {
            String[] p = u.split(";");
            if (p.length==2) userModel.addRow(new Object[]{p[0],p[1]});
        }
    }

    private void editUser() {
        int row = userTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a user first!"); return; }
        String oldPlaka = (String) userModel.getValueAt(row, 0);
        String oldSifre = (String) userModel.getValueAt(row, 1);

        String newPlaka = JOptionPane.showInputDialog(this, "New Plaka:", oldPlaka);
        if (newPlaka == null || newPlaka.trim().isEmpty()) return;

        String newSifre = JOptionPane.showInputDialog(this, "New Password:", oldSifre);
        if (newSifre == null || newSifre.trim().isEmpty()) return;

        List<String> users = FileManager.read(USER_FILE);
        for (int i = 0; i < users.size(); i++) {
            String[] p = users.get(i).split(";");
            if (p[0].equalsIgnoreCase(oldPlaka)) {
                users.set(i, newPlaka.toUpperCase() + ";" + newSifre);
                break;
            }
        }
        FileManager.write(USER_FILE, users, false);
        loadUsers();
    }

    private void deleteUser() {
        int row = userTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a user first!"); return; }
        String plaka = (String) userModel.getValueAt(row, 0);
        int secim = JOptionPane.showConfirmDialog(this, plaka + " Delete User?", "Delete User", JOptionPane.YES_NO_OPTION);
        if (secim != JOptionPane.YES_OPTION) return;

        List<String> users = FileManager.read(USER_FILE);
        users.removeIf(u -> u.split(";")[0].equalsIgnoreCase(plaka));
        FileManager.write(USER_FILE, users, false);
        loadUsers();
    }
}

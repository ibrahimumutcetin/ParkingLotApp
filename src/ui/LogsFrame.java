package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import other.FileLogAdapter;
import other.LogEntry;
import other.LogProvider;

/** Adapter Pattern - FileLogAdapter 
 * Abstract Factory Pattern - DarkThemeFactory */
public class LogsFrame extends JFrame {

    private static final String LOG_FILE = "log.txt";
    private JTable table;
    private DefaultTableModel model;

    
    private final LogProvider logProvider = new FileLogAdapter();

   
    private final UIComponentFactory uiFactory = new DarkThemeFactory();

    public LogsFrame() {
        setTitle("System Logs");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new BackgroundPanel("/icons/bg800x600.png");
        contentPane.setLayout(new BorderLayout(20, 20));
        setContentPane(contentPane);

        String[] columns = {"PLATE", "ACTION", "FLOOR", "SLOT", "TIME"};
        model = new DefaultTableModel(columns, 0);

        
        table = uiFactory.createTable(model);
        table.setAlignmentX(CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

      
        JButton btnDelete = uiFactory.createButton("DELETE SELECTED LOG");
        btnDelete.setBackground(new Color(200, 70, 70));

        btnDelete.addActionListener(e -> deleteSelectedLog());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(btnDelete);

        contentPane.add(tablePanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        loadLogs();
    }

  
    private void loadLogs() {
        model.setRowCount(0);

     
        List<LogEntry> entries = logProvider.getLogs();

        for (LogEntry entry : entries) {
            model.addRow(new Object[]{
                    entry.getPlate(),
                    entry.getAction(),
                    entry.getFloor(),
                    entry.getSlot(),
                    entry.getTimestamp()
            });
        }
    }

    private void deleteSelectedLog() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a log!");
            return;
        }

        int secim = JOptionPane.showConfirmDialog(
                this,
                "Delete selected log?",
                "CONFIRM",
                JOptionPane.YES_NO_OPTION
        );

        if (secim != JOptionPane.YES_OPTION) return;

        String selectedLine =
                model.getValueAt(row, 0) + ";" +
                model.getValueAt(row, 1) + ";" +
                model.getValueAt(row, 2) + ";" +
                model.getValueAt(row, 3) + ";" +
                model.getValueAt(row, 4);

        List<String> newLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals(selectedLine)) {
                    newLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter(LOG_FILE, false)) {
            for (String l : newLines) {
                fw.write(l + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.removeRow(row);
    }
}

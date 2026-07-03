package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/* Abstract Factory Pattern */
public class DarkThemeFactory implements UIComponentFactory {

    private static final Color PRIMARY    = new Color(120, 50, 215);
    private static final Color HOVER      = new Color(150, 70, 240);
    private static final Color BG_DARK    = new Color(30, 30, 30);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Font  BTN_FONT   = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font  LBL_FONT   = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font  TBL_FONT   = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font  HDR_FONT   = new Font("Segoe UI", Font.BOLD, 14);

    @Override
    public JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(BTN_FONT);
        btn.setForeground(TEXT_WHITE);
        btn.setBackground(PRIMARY);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(HOVER); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(PRIMARY); }
        });

        return btn;
    }

    @Override
    public JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(LBL_FONT);
        lbl.setForeground(TEXT_WHITE);
        return lbl;
    }

    @Override
    public JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(TBL_FONT);
        table.setForeground(TEXT_WHITE);
        table.setBackground(new Color(0, 0, 0, 0));
        table.setOpaque(false);
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Object.class)).setOpaque(false);
        table.setRowHeight(28);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(50, 50, 50));
        header.setForeground(TEXT_WHITE);
        header.setFont(HDR_FONT);

        return table;
    }
}

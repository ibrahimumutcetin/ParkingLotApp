package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/* Abstract Factory Pattern */
public interface UIComponentFactory {
    JButton createButton(String text);
    JLabel createLabel(String text);
    JTable createTable(DefaultTableModel model);
}

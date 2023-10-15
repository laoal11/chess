package main.java.gui.chessframe;

import main.java.logic.Move;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LastMovesPanel extends JPanel {
    private DefaultTableModel tableModel;

    private int currRowId = 0;

    public LastMovesPanel() {
        String[] columnNames = {"Move1", "White", "Black"};
        // Initialize the table model with column names and zero rows
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };

        // Create the table and set its model
        JTable table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                System.out.println("Clicked at row: " + row + ", column: " + col);
            }
        });
        table.setTableHeader(null);
        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200, 300)); // Adjust size as needed

        // Add the scroll pane to this panel
        this.add(scrollPane);
    }

    public void addMove(Move move) {
        int lastRowIndex = tableModel.getRowCount() - 1;

        // No rows or the last row is full: add a new row
        if (lastRowIndex < 0 || tableModel.getValueAt(lastRowIndex, 2) != null) {
            tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, move, null});
            return;
        }

        // Find a column to update in the last row
        if (tableModel.getValueAt(lastRowIndex, 1) == null) {
            tableModel.setValueAt(move, lastRowIndex, 1);
        } else if (tableModel.getValueAt(lastRowIndex, 2) == null) {
            tableModel.setValueAt(move, lastRowIndex, 2);
        }
    }

    public void restart(){
        while(tableModel.getRowCount() > 0){
            tableModel.removeRow(0);
        }
        currRowId = 0;
    }

    public void removeLastMove() {
        int lastRowIndex = tableModel.getRowCount() - 1;

        if (lastRowIndex < 0) return; // No rows to remove value from

        if (tableModel.getValueAt(lastRowIndex, 2) != null) {
            tableModel.setValueAt(null, lastRowIndex, 2);
        } else if (tableModel.getValueAt(lastRowIndex, 1) != null) {
            tableModel.removeRow(lastRowIndex);
        }
    }

}

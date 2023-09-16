package main.java.gui.chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromotionWindow extends JDialog {
    private static String promotionPiece;

    public PromotionWindow() {
        promotionPiece = null;
        setModal(true); // make it a modal dialog
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton queen = new JButton("Queen");
        JButton knight = new JButton("Knight");
        JButton bishop = new JButton("Bishop");
        JButton rook = new JButton("Rook");

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                promotionPiece = button.getText(); // store the button text
                dispose();
            }
        };

        queen.addActionListener(buttonListener);
        knight.addActionListener(buttonListener);
        bishop.addActionListener(buttonListener);
        rook.addActionListener(buttonListener);

        add(queen);
        add(knight);
        add(bishop);
        add(rook);

        pack();
        setVisible(true);
    }

    public String getPromotionPiece() {
        return promotionPiece; // return the button text
    }
}

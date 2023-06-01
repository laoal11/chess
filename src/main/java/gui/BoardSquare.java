package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class BoardSquare extends JPanel {
    final int x;
    final int y;

    private boolean isMarked = false;
    final private BufferedImage marker;
    final private JLabel label;
    private JLabel pieceImage;

    public BoardSquare(int x, int y, BufferedImage marker) {
        super(new BorderLayout());
        this.x = x;
        this.y = y;
        this.setPreferredSize(new Dimension(75, 75));
        if ((x + y) % 2 == 0) {
            this.setBackground(Color.WHITE);
        } else {
            this.setBackground(new Color(102,51,0));
        }
        this.marker = marker;
        this.label = new JLabel(new ImageIcon(marker));
    }

    public void activateMarker() {
        add(label, BorderLayout.CENTER);
        repaintMe();
        isMarked = true;
    }

    public void removeMarker() {
        if(!isMarked) {
            return;
        }
        remove(label);
        repaintMe();
        isMarked = false;
    }

    public void setTileImage(Image image) {
        removeTileImage();
        pieceImage = new JLabel(new ImageIcon(image));
        add(pieceImage, BorderLayout.CENTER);
        repaintMe();
    }
    public void removeTileImage() {
        if(pieceImage == null) return;
        remove(pieceImage);
        repaintMe();
    }

    private void repaintMe() {
        revalidate();
        repaint();
    }
}

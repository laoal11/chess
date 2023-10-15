package main.java.gui.chessframe;

import main.java.gui.logic.Controller;
import main.java.logic.Game;
import main.java.logic.Player;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final BoardPanel boardPanel;
    private final InfoPanel infoPanel;

    public MainFrame(Game game, Player p1, Player p2) {
        this.setTitle("Board");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(850, 650);
        this.boardPanel = new BoardPanel(game);
        this.infoPanel = new InfoPanel(game, p1, p2);
        this.add(boardPanel, BorderLayout.CENTER);
        this.add(infoPanel, BorderLayout.EAST);
        Controller controller = new Controller(boardPanel, infoPanel);
        boardPanel.setController(controller);
        infoPanel.setController(controller);
        this.pack(); // Resize the frame to its preferred size
        this.setVisible(true);
    }

}

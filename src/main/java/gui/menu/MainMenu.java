package main.java.gui.menu;

import main.java.gui.chessframe.MainFrame;
import main.java.logic.Game;
import main.java.logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JFrame {

    public MainMenu(Player white, Player black) {
        this.setTitle("Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        this.setSize(400, 400);
        setLocationRelativeTo(null); // center the frame on the screen

        // Add button 1
        JButton localGame = new JButton("Local game");
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        c1.insets = new Insets(0,0,10,0); // padding between the buttons
        localGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Game match = new Game(white, black);
                //new DisplayBoard(match, white, black);
                new MainFrame(match, white, black);
            }
        });
        add(localGame, c1);

        // Add button 2
        JButton options = new JButton("Options");
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 1;
        options.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Options(white, black);
            }
        });
        add(options, c2);
        this.setVisible(true);
    }

}

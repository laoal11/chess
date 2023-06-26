package main.java.gui.menu;
import main.java.logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Options extends JFrame {
    private JTextField textField1;
    private JTextField textField2;

    public Options(Player white, Player black) {
        setTitle("Options");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        JLabel label1 = new JLabel("Player 1 name");
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        add(label1, c1);
        textField1 = new JTextField(20);
        textField1.setText(white.getName());
        c1.gridy = 1;
        c1.insets = new Insets(10,0,20,0); // add some vertical space
        add(textField1, c1);

        JLabel label2 = new JLabel("Player 2 name");
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 2;
        add(label2, c2);
        textField2 = new JTextField(20);
        textField2.setText(black.getName());
        c2.gridy = 3;
        c2.insets = new Insets(10,0,0,0);
        add(textField2, c2);

        JButton applyChanges = new JButton("Apply");
        applyChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                white.setName(textField1.getText());
                black.setName(textField2.getText());
                dispose();
            }
        });
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 5;
        c3.insets = new Insets(10,0,0,0);
        add(applyChanges, c3);
        this.setVisible(true);
    }

}
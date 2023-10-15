package main.java.gui.chessframe;

import main.java.gui.logic.Controller;
import main.java.logic.Game;
import main.java.logic.GameState;
import main.java.logic.Move;
import main.java.logic.Player;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {


    private JLabel currentStateLabel;

    private JLabel p1Label;
    private JLabel p2Label;
    private LastMovesPanel lastMovesPanel;
    private Game game;
    private Controller controller;
    private final Player p1;
    private final Player p2;

    //private DefaultListModel<String> lastMovesList;

    InfoPanel(Game game, Player p1, Player p2) {
        this.game = game;
        this.p1 = p1;
        this.p2 = p2;

        //lastMovesList = new DefaultListModel<>();
        lastMovesPanel = new LastMovesPanel();

        initCurrentStatePanel();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(createGameButtons());
        add(Box.createRigidArea(new Dimension(0,20)));
        add(currentStateLabel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(createPlayerScores());
        add(Box.createRigidArea(new Dimension(0,5)));
        add(lastMovesPanel);
    }

    private JPanel createGameButtons() {
        JButton restartButton = new JButton("Restart");
        JButton takebackButton = new JButton("Takeback");
        restartButton.addActionListener(e -> restartGame());
        takebackButton.addActionListener(e -> takeBackLastMove());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(restartButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0))); // adds a little space between the buttons
        buttonPanel.add(takebackButton);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return buttonPanel;
    }

    private void initCurrentStatePanel() {
        currentStateLabel = new JLabel(GameState.WHITE_TO_MOVE.getDisplayName());
        currentStateLabel.setFont(new Font(currentStateLabel.getFont().getName(), Font.BOLD, 20));
        currentStateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JPanel createPlayerScores() {
        JPanel playerScores = new JPanel();
        playerScores.setLayout(new BoxLayout(playerScores, BoxLayout.X_AXIS));
        p1Label = new JLabel(getPlayerScore(p1));
        p1Label.setFont(new Font(p1Label.getFont().getName(), Font.BOLD, 16)); // Set font size to 20
        playerScores.add(p1Label);
        playerScores.add(Box.createRigidArea(new Dimension(10,0)));
        p2Label = new JLabel(getPlayerScore(p2));
        p2Label.setFont(new Font(p2Label.getFont().getName(), Font.BOLD, 16)); // Set font size to 20
        playerScores.setAlignmentX(Component.LEFT_ALIGNMENT);
        playerScores.add(p2Label);
        return playerScores;
    }

    public void updateLastMoveList() {
        Move lastMove = game.getLastMove();
        lastMovesPanel.addMove(lastMove);
        //lastMovesList.addElement(lastMove.toString());
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private String getPlayerScore(Player player) {
        return player.getName() + " : " + player.getScore();
    }

    private void restartGame() {
        game.restartGame();
        switchStateLabelToCurrentState();
        controller.resetBoard();
        lastMovesPanel.restart();
    }

    public void switchStateLabelToCurrentState() {
        currentStateLabel.setText(game.getState().getDisplayName());
    }

    public void updateScore() {
        GameState state = game.getState();
        if(state.equals(GameState.WHITE_WON)) {
            updatePlayerScoreAndLabel(p1, 1, p1Label);
        } else if(state.equals(GameState.BLACK_WON)) {
            updatePlayerScoreAndLabel(p2, 1, p2Label);
        } else if(state.equals(GameState.DRAW)){
            updatePlayerScoreAndLabel(p1, 0.5, p1Label);
            updatePlayerScoreAndLabel(p2, 0.5, p2Label);
        }
    }

    private void updatePlayerScoreAndLabel(Player player, double points, JLabel label) {
        player.increaseScore(points);
        label.setText(getPlayerScore(player));
    }

    private void takeBackLastMove() {
        if(game.getLastMove() == null) return;
        game.revertLastMove();
        game.revertToLastGameState();
        game.changeCurrentPlayer();
        game.getBoard().printBoard();
        switchStateLabelToCurrentState();
        lastMovesPanel.removeLastMove();
        controller.removeMarkers();
        controller.drawBoard();
    }
}
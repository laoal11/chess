package main.java.gui.logic;

import main.java.gui.chessframe.BoardPanel;
import main.java.gui.chessframe.InfoPanel;

public class Controller {

    private final BoardPanel boardPanel;
    private final InfoPanel infoPanel;

    public Controller(BoardPanel boardPanel, InfoPanel infoPanel) {
        this.boardPanel = boardPanel;
        this.infoPanel = infoPanel;
    }

    public void resetBoard() {
        boardPanel.setupSquares();
        boardPanel.setupInitialPieces();
    }

    public void updateLastMove() {
        infoPanel.updateLastMoveList();
    }

    public void updateStateLabel() {
        infoPanel.switchStateLabelToCurrentState();
    }

    public void removeMarkers() {
        boardPanel.removeMarkers();
    }

    public void drawBoard() {
        boardPanel.drawBoard();
    }

    public void updateScore() {
        infoPanel.updateScore();
    }

}

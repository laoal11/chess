package main.java.gui.chessboard;

import main.java.logic.*;
import main.java.logic.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayBoard extends JFrame {

    private Game game;

    private ImageHandler imageHandler;

    private static final int BOARD_SIZE = 8;
    private final BoardSquare[][] squares = new BoardSquare[BOARD_SIZE][BOARD_SIZE];

    private List<Tile> markedSquares;

    private Tile selectedTile;

    private JPanel chessBoard;

    private JLabel currentState;
    private JLabel p1Label;
    private JLabel p2Label;
    private Player p1;
    private Player p2;

    public DisplayBoard(Game game, Player p1, Player p2) {
        this.imageHandler = new ImageHandler();
        this.markedSquares = new ArrayList<>();
        this.p1 = p1;
        this.p2 = p2;
        this.setTitle("Board");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(800, 600);
        setLocationRelativeTo(null); // center the frame on the screen

        initBoard();
        JPanel infoPanel = createInfoPanel(p1, p2);

        this.add(chessBoard, BorderLayout.CENTER);
        this.add(infoPanel, BorderLayout.EAST);

        this.pack(); // Resize the frame to its preferred size
        this.setVisible(true);
        this.game = game;
    }

    private JPanel createInfoPanel(Player p1, Player p2) {
        JPanel currentPlayerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        currentState = new JLabel(GameState.WHITE_TO_MOVE.getDisplayName());
        currentState.setFont(new Font(currentState.getFont().getName(), Font.BOLD, 20)); // Set font size to 20
        currentPlayerPanel.add(currentState);

        JPanel p1LabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1Label = new JLabel(getPlayerScore(p1)); // Add score to player's name
        p1LabelPanel.add(p1Label);

        JPanel p2LabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2Label = new JLabel(getPlayerScore(p2)); // Add score to player's name
        p2LabelPanel.add(p2Label);

        JButton restartButton = new JButton("Restart");
        JButton takebackButton = new JButton("Takeback");
        restartButton.addActionListener(e -> restartGame());
        takebackButton.addActionListener(e -> takeBackLastMove());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(restartButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0))); // adds a little space between the buttons
        buttonPanel.add(takebackButton);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(buttonPanel); // Add buttonPanel to infoPanel
        infoPanel.add(Box.createRigidArea(new Dimension(0,20))); // reduces space between buttonPanel and currentPlayer
        infoPanel.add(currentPlayerPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0,5))); // reduces space between labels
        infoPanel.add(p1LabelPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0,5)));
        infoPanel.add(p2LabelPanel);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // creates some padding
        return infoPanel;
    }

    private void initBoard() {
        chessBoard = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        setupSquares();
        chessBoard.setPreferredSize(new Dimension(600, 600));
        setupInitialPieces();
    }

    private void setupSquares() {
        chessBoard.removeAll();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                BoardSquare square = new BoardSquare(i, j, imageHandler.getMarker());
                int finalI = i;
                int finalJ = j;
                square.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        addClickLogic(finalI, finalJ);
                    }
                });
                squares[i][j] = square;
                chessBoard.add(square);
            }
        }
    }

    private void switchStateLabelToCurrentState() {
        currentState.setText(game.getState().getDisplayName());
    }

    private void takeBackLastMove() {
        game.revertLastMove();
        game.changeCurrentPlayer();
        game.getBoard().printBoard();
        switchStateLabelToCurrentState();
        removeMarkers();
        drawBoard();
    }

    private void drawBoard() {
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                Tile tile = game.getBoard().getTile(y, x);
                setTileImage(y , x, imageHandler.getImageByPiece( tile.getPiece()));
            }
        }
    }

    private void restartGame() {
        game.restartGame();
        switchStateLabelToCurrentState();
        setupSquares();
        setupInitialPieces();
    }

    private void addClickLogic(int x, int y) {
        Tile sourceTile = game.getBoard().getTile(x, y);
        if(selectedTile != null) {
            if(markedSquares.contains(sourceTile)) {
                boolean canMakeMove = game.playerMove(selectedTile.getX(), selectedTile.getY(), sourceTile.getX(), sourceTile.getY());
                game.getBoard().printBoard();
                if(!canMakeMove) {
                    return;
                }
                removeMarkers();
                selectedTile = null;
                drawBoard();
                if(game.getState() == GameState.WHITE_WON || game.getState() == GameState.BLACK_WON ) {
                    currentState.setText(game.getState().getDisplayName());
                    turnOffAllSquares();
                    increaseScoreOfWinner(game.getState());
                    return;
                }
                switchStateLabelToCurrentState();
                return;
            }
        }

        Piece piece = game.getBoard().getTile(x, y).getPiece();
        if(piece == null || piece.isWhite() != game.getCurrentPlayer().isWhiteSide()) return;
        removeMarkers();
        if(sourceTile == selectedTile){
            selectedTile = null;
            return;
        }
        List<Tile> validMoves = piece.validMoves(game.getBoard(), game.getBoard().getTile(x, y));
        for(Tile tile : validMoves) {
            setTileMarker(tile.getX(), tile.getY());
        }
        markedSquares = validMoves;
        selectedTile = sourceTile;
    }

    private void increaseScoreOfWinner(GameState state) {
        if(state.equals(GameState.WHITE_WON)) {
            p1.increaseScore();
            p1Label.setText(getPlayerScore(p1));
        } else {
            p2.increaseScore();
            p2Label.setText(getPlayerScore(p2));
        }
    }

    private String getPlayerScore(Player player) {
        return player.getName() + ": " + player.getScore();
    }

    private void turnOffAllSquares() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                MouseListener[] mouseListeners = squares[i][j].getMouseListeners();

                for (MouseListener ml : mouseListeners) {
                    squares[i][j].removeMouseListener(ml);
                }
            }
        }
    }

    private BoardSquare getTile(int row, int col) {
        return squares[row][col];
    }

    private void removeMarkers(){
        for(Tile tile : markedSquares) {
            getTile(tile.getX(), tile.getY()).removeMarker();
        }
        markedSquares = Collections.EMPTY_LIST;
    }

    private void setTileMarker(int row, int col) {
        getTile(row, col).activateMarker();
    }

    private void setTileImage(int row, int col, Image image) {
        BoardSquare square = getTile(row, col);
        square.removeTileImage();
        if(image == null) return;
        square.setTileImage(image);
    }

    private void setupInitialPieces() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                getTile(i, j).removeTileImage();
            }
        }
        //rooks
        setTileImage(0,0, imageHandler.getBlackRook());
        setTileImage(0,7, imageHandler.getBlackRook());
        setTileImage(7,0, imageHandler.getWhiteRook());
        setTileImage(7,7, imageHandler.getWhiteRook());
        //knights
        setTileImage(0,1, imageHandler.getBlackKnight());
        setTileImage(0,6, imageHandler.getBlackKnight());
        setTileImage(7,1, imageHandler.getWhiteKnight());
        setTileImage(7,6, imageHandler.getWhiteKnight());
        //bishop
        setTileImage(0,2, imageHandler.getBlackBishop());
        setTileImage(0,5, imageHandler.getBlackBishop());
        setTileImage(7,2, imageHandler.getWhiteBishop());
        setTileImage(7,5, imageHandler.getWhiteBishop());
        //queen
        setTileImage(0,3, imageHandler.getBlackQueen());
        setTileImage(7,3, imageHandler.getWhiteQueen());
        //king
        setTileImage(0,4, imageHandler.getBlackKing());
        setTileImage(7,4, imageHandler.getWhiteKing());
        //pawns
        for(int i = 0; i < BOARD_SIZE; i++) {
            setTileImage(1, i, imageHandler.getBlackPawn());
            setTileImage(6, i, imageHandler.getWhitePawn());
        }
    }
}
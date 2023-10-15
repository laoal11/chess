package main.java.gui.chessframe;

import main.java.gui.logic.Controller;
import main.java.logic.Game;
import main.java.logic.GameState;
import main.java.gui.logic.*;
import main.java.logic.Tile;
import main.java.logic.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardPanel extends JPanel {

    private ImageHandler imageHandler;
    private static final int BOARD_SIZE = 8;
    private final BoardSquare[][] squares = new BoardSquare[BOARD_SIZE][BOARD_SIZE];

    private JPanel chessBoard;

    private Tile selectedTile;
    private Game game;
    private List<Tile> markedSquares;

    private Controller controller;

    BoardPanel(Game game) {
        this.game = game;

        this.imageHandler = new ImageHandler();
        this.markedSquares = new ArrayList<>();

        initBoard();

        this.add(chessBoard, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void initBoard() {
        chessBoard = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        setupSquares();
        chessBoard.setPreferredSize(new Dimension(600, 600));
        setupInitialPieces();
    }

    public void setupInitialPieces() {
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

    public void setupSquares() {
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

    private void addClickLogic(int x, int y) {
        Tile sourceTile = game.getBoard().getTile(x, y);
        if(selectedTile != null) {
            if(markedSquares.contains(sourceTile)) {
                processPotentialMove(sourceTile);
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

    private void setTileMarker(int row, int col) {
        getTile(row, col).activateMarker();
    }

    private void processPotentialMove(Tile sourceTile) {
        boolean canMakeMove = game.playerMove(selectedTile.getX(), selectedTile.getY(), sourceTile.getX(), sourceTile.getY());
        game.getBoard().printBoard();
        if(!canMakeMove) {
            return;
        }
        removeMarkers();
        selectedTile = null;
        drawBoard();
        controller.updateLastMove();
        if(game.getState() == GameState.WHITE_WON || game.getState() == GameState.BLACK_WON ) {
            controller.updateStateLabel();
            turnOffAllSquares();
            updateScore();
            return;
        }
        controller.updateStateLabel();
    }

    public void updateScore() {
        controller.updateScore();
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
    public void drawBoard() {
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                Tile tile = game.getBoard().getTile(y, x);
                setTileImage(y , x, imageHandler.getImageByPiece( tile.getPiece()));
            }
        }
    }

    private void setTileImage(int row, int col, Image image) {
        BoardSquare square = getTile(row, col);
        square.removeTileImage();
        if(image == null) return;
        square.setTileImage(image);
    }

    public void removeMarkers(){
        for(Tile tile : markedSquares) {
            getTile(tile.getX(), tile.getY()).removeMarker();
        }
        markedSquares = Collections.EMPTY_LIST;
    }

    private BoardSquare getTile(int row, int col) {
        return squares[row][col];
    }

    public static class BoardSquare extends JPanel {
        final int x;
        final int y;

        private boolean isMarked = false;
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

    public static class PromotionWindow extends JDialog {
        private static String promotionPiece;

        public PromotionWindow() {
            promotionPiece = null;
            setModal(true);
            setLayout(new FlowLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JButton queen = new JButton("Queen");
            JButton knight = new JButton("Knight");
            JButton bishop = new JButton("Bishop");
            JButton rook = new JButton("Rook");

            ActionListener buttonListener = e -> {
                JButton button = (JButton) e.getSource();
                promotionPiece = button.getText();
                dispose();
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
}

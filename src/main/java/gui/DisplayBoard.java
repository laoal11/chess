package main.java.gui;

import main.java.logic.Game;
import main.java.logic.Piece;
import main.java.logic.Player;
import main.java.logic.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public DisplayBoard(Player p1, Player p2) {
        this.imageHandler = new ImageHandler();
        this.markedSquares = new ArrayList<>();
        this.setTitle("Board");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        this.setSize(600, 600);
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
                this.add(square);
            }
        }
        setupInitialPieces();
        this.setVisible(true);
        this.game = new Game(p1, p2);
    }

    private void addClickLogic(int x, int y) {
        System.out.println("Clicked on square: (" + x + ", " + y + ")");
        Tile sourceTile = game.getBoard().getTile(x, y);

        if(selectedTile != null) {
            if(markedSquares.contains(sourceTile)) {
                Piece temp = selectedTile.getPiece();
                removeMarkers();
                game.playerMove(selectedTile.getX(), selectedTile.getY(), sourceTile.getX(), sourceTile.getY());
                setTileImage(sourceTile.getX(), sourceTile.getY(), imageHandler.getImageByPiece(temp));
                removeTileImage(selectedTile.getX(), selectedTile.getY());
                selectedTile = null;
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

    private BoardSquare getTile(int row, int col) {
        return squares[row][col];
    }

    private void removeTileImage(int row, int col) {
        getTile(row, col).removeTileImage();
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
        square.setTileImage(image);
    }

    private void setupInitialPieces() {
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
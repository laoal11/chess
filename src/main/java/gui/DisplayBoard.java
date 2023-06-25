package main.java.gui;

import main.java.logic.*;

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

    public DisplayBoard(Game game, Player p1, Player p2) {
        this.imageHandler = new ImageHandler();
        this.markedSquares = new ArrayList<>();
        this.setTitle("Board");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(600, 600);
        //chessBoard = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        initBoard();

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton restartButton = new JButton("Restart");
        JButton takebackButton = new JButton("Takeback");
        restartButton.addActionListener(e -> restartGame());
        takebackButton.addActionListener(e -> takebackLastMove());
        buttonPanel.add(restartButton);
        buttonPanel.add(takebackButton);
        this.add(buttonPanel, BorderLayout.NORTH);
        this.add(chessBoard, BorderLayout.CENTER);

        this.setVisible(true);
        this.game = game;
    }

    private void initBoard() {
        chessBoard = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
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
        setupInitialPieces();
    }

    private void takebackLastMove() {
        Move lastMove = game.revertLastMove();
        if(lastMove == null) {
            return;
        }
        Tile src = lastMove.getSrc();
        Piece srcPiece = src.getPiece();
        if(srcPiece != null) {
            setImageForReverting(srcPiece, src);
        }
        Tile dst = lastMove.getDst();
        Piece dstPiece = dst.getPiece();
        if(dst != null) {
            setImageForReverting(dstPiece, dst);
        }
        game.changeCurrentPlayer();
        game.getBoard().printBoard();
    }

    private void setImageForReverting(Piece piece, Tile tile) {
        if(piece == null) {
            removeTileImage(tile.getX(), tile.getY());
        } else if(piece instanceof Pawn) {
            setTileImage(tile.getX(), tile.getY(), tile.getPiece().isWhite() ? imageHandler.getWhitePawn() : imageHandler.getBlackPawn());
        } else if(piece instanceof Rook) {
            setTileImage(tile.getX(), tile.getY(), tile.getPiece().isWhite() ? imageHandler.getWhiteRook() : imageHandler.getBlackRook());
        }else if(piece instanceof Knight) {
            setTileImage(tile.getX(), tile.getY(), tile.getPiece().isWhite() ? imageHandler.getWhiteKnight() : imageHandler.getBlackKnight());
        }else if(piece instanceof Bishop) {
            setTileImage(tile.getX(), tile.getY(), tile.getPiece().isWhite() ? imageHandler.getWhiteBishop() : imageHandler.getBlackBishop());

        }else if(piece instanceof Queen) {
            setTileImage(tile.getX(), tile.getY(), tile.getPiece().isWhite() ? imageHandler.getWhiteQueen() : imageHandler.getBlackQueen());
        }else {
            setTileImage(tile.getX(), tile.getY(), tile.getPiece().isWhite() ? imageHandler.getWhiteKing() : imageHandler.getBlackKing());
        }

    }

    private void restartGame() {
        game.restartGame();
        removeMarkers();
        setupInitialPieces();
    }

    private void addClickLogic(int x, int y) {
        Tile sourceTile = game.getBoard().getTile(x, y);
        if(selectedTile != null) {
            if(markedSquares.contains(sourceTile)) {
                Piece temp = selectedTile.getPiece();
                boolean canMakeMove = game.playerMove(selectedTile.getX(), selectedTile.getY(), sourceTile.getX(), sourceTile.getY());
                game.getBoard().printBoard();
                if(!canMakeMove) {
                    return;
                }
                removeMarkers();
                setTileImage(sourceTile.getX(), sourceTile.getY(), imageHandler.getImageByPiece(temp));
                removeTileImage(selectedTile.getX(), selectedTile.getY());
                selectedTile = null;
                if(game.getState() == GameState.WHITE_WON || game.getState() == GameState.BLACK_WON ) {
                    System.out.println("GAME IS OVER");
                    System.out.println(game.getState());
                    turnOffAllSquares();
                }
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
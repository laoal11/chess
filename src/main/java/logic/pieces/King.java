package main.java.logic.pieces;

import main.java.logic.Board;
import main.java.logic.Tile;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{


    public King(boolean white) {
        super(white);
        pieceType = PieceEnum.KING;
    }

    @Override
    public List<Tile> validMoves(Board board, Tile source) {
        List<Tile> validMoves = new ArrayList<>();
        int x = source.getX();
        int y = source.getY();
        // Horizontal moves
        addValidMovesInDirection(board, validMoves, x, y, 0, 1);  // to the right
        addValidMovesInDirection(board, validMoves, x, y, 0, -1); // to the left

        // Vertical moves
        addValidMovesInDirection(board, validMoves, x, y, 1, 0);  // upward
        addValidMovesInDirection(board, validMoves, x, y, -1, 0); // downward

        // Diagonal moves
        addValidMovesInDirection(board, validMoves, x, y, 1, 1);  // up-right
        addValidMovesInDirection(board, validMoves, x, y, 1, -1); // up-left
        addValidMovesInDirection(board, validMoves, x, y, -1, 1); // down-right
        addValidMovesInDirection(board, validMoves, x, y, -1, -1); // down-left
        if(!hasMoved()) {
            addValidCastlingMoves(board, source, validMoves);
        }
        return validMoves;
    }

    private void addValidMovesInDirection(Board board, List<Tile> validMoves,
                                          int startX, int startY,
                                          int xDirection, int yDirection) {
        int x = startX + xDirection;
        int y = startY + yDirection;
        if(!isInBounds(x,y )) {
            return;
        }
        Tile tile = board.getTile(x, y);
        if (tile.getPiece() == null) {
            validMoves.add(tile);
        } else if (tile.getPiece().isWhite() != isWhite()) {
            validMoves.add(tile);
        }
    }

    private void addValidCastlingMoves(Board board, Tile source, List<Tile> validMoves) {
        if(isWhite()) {
            // check left side
            Piece leftCorner = board.getTile(7, 0).getPiece();
            if(leftCorner instanceof Rook){
                Rook leftCornerRook = (Rook) leftCorner;
                if(!leftCornerRook.hasMoved()){
                    System.out.println("Is a rook on 7 , 0");
                    boolean allFree = true;
                    for(int i = 1; i < 4; i++) {
                        if(board.getTile(7, i).getPiece() != null) {
                            allFree = false;
                            break;
                        }
                    }
                    if(allFree) {
                        validMoves.add(board.getTile(7, 2));
                    }
                }
            }
            // check right side
            Piece rightCorner = board.getTile(7, 7).getPiece();
            if(rightCorner instanceof Rook){
                Rook rightCornerRook = (Rook) rightCorner;
                if(!rightCornerRook.hasMoved()){
                    System.out.println("Is a rook on 7 , 7");
                    boolean allFree = true;
                    for(int i = 5; i < 7; i++) {
                        if(board.getTile(7, i).getPiece() != null) {
                            allFree = false;
                            break;
                        }
                    }
                    if(allFree) {
                        validMoves.add(board.getTile(7, 6));
                    }
                }
            }
            return;
        }
        // check left side
        Piece leftCorner = board.getTile(0, 0).getPiece();
        if(leftCorner instanceof Rook){
            Rook leftCornerRook = (Rook) leftCorner;
            if(!leftCornerRook.hasMoved()){
                System.out.println("Is a rook on 0 , 0");
                boolean allFree = true;
                for(int i = 1; i < 4; i++) {
                    if(board.getTile(0, i).getPiece() != null) {
                        allFree = false;
                        break;
                    }
                }
                if(allFree) {
                    validMoves.add(board.getTile(0, 2));
                }
            }
        }
        // check right side
        Piece rightCorner = board.getTile(0, 7).getPiece();
        if(rightCorner instanceof Rook){
            Rook rightCornerRook = (Rook) rightCorner;
            if(!rightCornerRook.hasMoved()){
                System.out.println("Is a rook on 0, 7");
                boolean allFree = true;
                for(int i = 5; i < 7; i++) {
                    if(board.getTile(0, i).getPiece() != null) {
                        allFree = false;
                        break;
                    }
                }
                if(allFree) {
                    validMoves.add(board.getTile(0, 6));
                }
            }
        }
    }



}

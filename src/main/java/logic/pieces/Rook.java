package main.java.logic.pieces;


import main.java.logic.Board;
import main.java.logic.Tile;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{

    private boolean hasMoved = false;

    public Rook(boolean white) {
        super(white);
        pieceType = PieceEnum.ROOK;
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

        return validMoves;
    }

    private void addValidMovesInDirection(Board board, List<Tile> validMoves,
                                          int startX, int startY,
                                          int xDirection, int yDirection) {
        int x = startX + xDirection;
        int y = startY + yDirection;

        while (x >= 0 && x < 8 && y >= 0 && y < 8) {
            Tile tile = board.getTile(x, y);
            if (tile.getPiece() == null) {
                validMoves.add(tile);
            } else {
                if (tile.getPiece().isWhite() != isWhite()) {
                    validMoves.add(tile);
                }
                break;
            }
            x += xDirection;
            y += yDirection;
        }
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

}

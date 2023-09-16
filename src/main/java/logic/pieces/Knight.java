package main.java.logic.pieces;

import main.java.logic.Board;
import main.java.logic.Tile;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(boolean white) {
        super(white);
        pieceType = PieceEnum.KNIGHT;
    }

    @Override
    public List<Tile> validMoves(Board board, Tile source) {
        List<Tile> validMoves = new ArrayList<>();
        int x = source.getX();
        int y = source.getY();
        addValidMove(board, validMoves, x + 1, y + 2);
        addValidMove(board, validMoves, x + 1, y - 2);
        addValidMove(board, validMoves, x - 1, y + 2);
        addValidMove(board, validMoves, x - 1, y - 2);
        addValidMove(board, validMoves, x + 2, y + 1);
        addValidMove(board, validMoves, x + 2, y - 1);
        addValidMove(board, validMoves, x - 2, y + 1);
        addValidMove(board, validMoves, x - 2, y - 1);
        return validMoves;
    }

    private void addValidMove(Board board, List<Tile> validMoves, int x, int y) {
        if (isInBounds(x, y)) {
            Tile potentialMove = board.getTile(x, y);
            if (potentialMove.getPiece() == null || potentialMove.getPiece().isWhite() != isWhite()) {
                validMoves.add(potentialMove);
            }
        }
    }
}

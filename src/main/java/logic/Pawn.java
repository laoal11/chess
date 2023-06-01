package main.java.logic;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{

    public Pawn(boolean white) {
        super(white);
        name = "Pawn";
    }

    @Override
    public List<Tile> validMoves(Board board, Tile source) {
        List<Tile> validMoves = new ArrayList<>();
        int x = source.getX();
        int y = source.getY();
        int direction = isWhite() ? -1 : 1;

        addValidMove(board, validMoves, x + direction, y);
        addPotentialCapture(board, validMoves, x + direction, y + 1);
        addPotentialCapture(board, validMoves, x + direction, y - 1);

        if ((isWhite() && x == 6) || (!isWhite() && x == 1)) {
            addValidMove(board, validMoves, x + 2 * direction, y);

        }
        return validMoves;
    }

    private void addPotentialCapture(Board board, List<Tile> validMoves, int x, int y) {
        if (isInBounds(x, y)) {
            Tile potentialMove = board.getTile(x, y);
            if (potentialMove.getPiece() != null && potentialMove.getPiece().isWhite() != isWhite()) {
                validMoves.add(potentialMove);
            }
        }
    }

    private void addValidMove(Board board, List<Tile> validMoves, int x, int y) {
        if (isInBounds(x, y)) {
            Tile potentialMove = board.getTile(x, y);
            if (potentialMove.getPiece() == null) {
                validMoves.add(potentialMove);
            }
        }
    }

}

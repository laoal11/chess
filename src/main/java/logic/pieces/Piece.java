package main.java.logic.pieces;

import main.java.logic.Board;
import main.java.logic.Tile;

import java.util.List;

public abstract class Piece {
    public boolean isWhite() {
        return isWhite;
    }

    public PieceEnum getPieceType() {
        return pieceType;
    }

    private boolean isWhite = false;
    PieceEnum pieceType;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public static Piece fromString(String piece, boolean isWhite) {
        switch(piece) {
            case "Pawn":
                return new Pawn(isWhite);
            case "Knight":
                return new Knight(isWhite);
            case "Bishop":
                return new Bishop(isWhite);
            case "Rook":
                return new Rook(isWhite);
            case "Queen":
                return new Queen(isWhite);
            case "King":
                return new King(isWhite);
        }
        return null;
    }

    public boolean canMove(Board board, Tile source, Tile destination) {
        List<Tile> validTiles = validMoves(board, source);
        if(validTiles.isEmpty()) {
            return false;
        }
        if(!validTiles.contains(destination)) {
            return false;
        }
        return true;
    }

    public abstract List<Tile> validMoves(Board board, Tile source);

    public boolean canReach(Board board, Tile source, Tile destination) {
        List<Tile> tiles = validMoves(board, source);
        for(Tile tile : tiles) {
            if(tile == destination) {
                return true;
            }
        }
        return false;
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    @Override
    public String toString() {
        String color = isWhite ? "White" : "Black";
        return color + "-" + this.pieceType;
    }
}

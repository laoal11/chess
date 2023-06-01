package main.java.logic;

import main.java.logic.Board;
import main.java.logic.Tile;

import java.util.List;

public abstract class Piece {
    public boolean isWhite() {
        return isWhite;
    }

    public String getName() {
        return name;
    }

    private boolean isWhite = false;
    String name;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
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

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    @Override
    public String toString() {
        String color = isWhite ? "White" : "Black";
        return color + "-" + this.name;
    }
}

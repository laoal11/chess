package main.java.logic;

import main.java.logic.pieces.Piece;

public class Tile {
    private Piece piece;

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(o.getClass() != this.getClass()) return false;
        Tile comp = (Tile) o;
        return this.x == comp.getX() && this.y == comp.getY();
    }

    @Override
    public String toString() {
        return "[x = " + x + ", y = " + y + "]";
    }
}

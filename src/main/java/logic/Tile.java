package main.java.logic;

import main.java.logic.pieces.Piece;

public class Tile {
    private Piece piece;

    private int x;
    private int y;
    private final int uniCodeNumber = 65; // Unicode for 'A'


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
        //[x = 6, y = 0] : Left pawn
        //A 2

        // Casting int to char
        char column = (char) (uniCodeNumber + y);
        int row = 8 - x;
        return column + "" + row;
    }
}

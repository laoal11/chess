package main.java.logic.pieces;

public enum PieceEnum {
    QUEEN("Queen"),
    ROOK("Rook"),
    KNIGHT("Knight"),
    BISHOP("Bishop"),
    KING("King"),
    PAWN("Pawn");

    public final String label;

    PieceEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}

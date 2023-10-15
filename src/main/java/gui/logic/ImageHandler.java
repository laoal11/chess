package main.java.gui.logic;

import main.java.logic.pieces.Piece;

import java.awt.image.BufferedImage;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class ImageHandler {

    private final BufferedImage blackBishop;
    private final BufferedImage whiteBishop;
    private final BufferedImage blackRook;
    private final BufferedImage whiteRook;
    private final BufferedImage blackKnight;
    private final BufferedImage whiteKnight;
    private final BufferedImage blackPawn;
    private final BufferedImage whitePawn;
    private final BufferedImage blackQueen;
    private final BufferedImage whiteQueen;
    private final BufferedImage blackKing;
    private final BufferedImage whiteKing;
    private final BufferedImage marker;

    public ImageHandler() {
        try{
            this.blackBishop = read(Objects.requireNonNull(getClass().getResourceAsStream("/images/bishop_black.png")));
            this.whiteBishop = read(Objects.requireNonNull(getClass().getResource("/images/bishop_white.png")));
            this.blackRook = read(Objects.requireNonNull(getClass().getResource("/images/rook_black.png")));
            this.whiteRook = read(Objects.requireNonNull(getClass().getResource("/images/rook_white.png")));
            this.blackKnight = read(Objects.requireNonNull(getClass().getResource("/images/knight_black.png")));
            this.whiteKnight = read(Objects.requireNonNull(getClass().getResource("/images/knight_white.png")));
            this. blackPawn = read(Objects.requireNonNull(getClass().getResource("/images/pawn_black.png")));
            this.whitePawn = read(Objects.requireNonNull(getClass().getResource("/images/pawn_white.png")));
            this.blackQueen = read(Objects.requireNonNull(getClass().getResource("/images/queen_black.png")));
            this.whiteQueen = read(Objects.requireNonNull(getClass().getResource("/images/queen_white.png")));
            this.blackKing = read(Objects.requireNonNull(getClass().getResource("/images/king_black.png")));
            this.whiteKing = read(Objects.requireNonNull(getClass().getResource("/images/king_white.png")));
            this.marker = read(Objects.requireNonNull(getClass().getResource("/images/target.png")));
        }catch (Exception e) {
            throw new RuntimeException("Could not load image");
        }
    }

    public BufferedImage getImageByPiece(Piece piece) {
        if(piece == null) {
            return null;
        }
        boolean isWhite = piece.isWhite();
        return switch (piece.getPieceType()) {
            case PAWN -> isWhite ? whitePawn : blackPawn;
            case ROOK -> isWhite ? whiteRook : blackRook;
            case KNIGHT -> isWhite ? whiteKnight : blackKnight;
            case BISHOP -> isWhite ? whiteBishop : blackBishop;
            case QUEEN -> isWhite ? whiteQueen : blackQueen;
            case KING -> isWhite ? whiteKing : blackKing;
        };
    }

    public BufferedImage getBlackBishop() {
        return blackBishop;
    }

    public BufferedImage getWhiteBishop() {
        return whiteBishop;
    }

    public BufferedImage getBlackRook() {
        return blackRook;
    }

    public BufferedImage getWhiteRook() {
        return whiteRook;
    }

    public BufferedImage getBlackKnight() {
        return blackKnight;
    }

    public BufferedImage getWhiteKnight() {
        return whiteKnight;
    }

    public BufferedImage getBlackPawn() {
        return blackPawn;
    }

    public BufferedImage getWhitePawn() {
        return whitePawn;
    }

    public BufferedImage getBlackQueen() {
        return blackQueen;
    }

    public BufferedImage getWhiteQueen() {
        return whiteQueen;
    }

    public BufferedImage getBlackKing() {
        return blackKing;
    }

    public BufferedImage getWhiteKing() {
        return whiteKing;
    }

    public BufferedImage getMarker() {
        return marker;
    }
}

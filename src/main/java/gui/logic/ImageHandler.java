package main.java.gui.logic;

import main.java.logic.pieces.Piece;
import main.java.logic.pieces.PieceEnum;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageHandler {

    final private BufferedImage blackBishop;
    final private BufferedImage whiteBishop;
    final private BufferedImage blackRook;
    final private BufferedImage whiteRook;
    final private BufferedImage blackKnight;
    final private BufferedImage whiteKnight;
    final private BufferedImage blackPawn;
    final private BufferedImage whitePawn;
    final private BufferedImage blackQueen;
    final private BufferedImage whiteQueen;
    final private BufferedImage blackKing;
    final private BufferedImage whiteKing;
    final private BufferedImage marker;

    public ImageHandler() {
        try{
            this.blackBishop = ImageIO.read(getClass().getResourceAsStream("/images/bishop_black.png"));
            this.whiteBishop = ImageIO.read(getClass().getResource("/images/bishop_white.png"));
            this.blackRook = ImageIO.read(getClass().getResource("/images/rook_black.png"));
            this.whiteRook = ImageIO.read(getClass().getResource("/images/rook_white.png"));
            this.blackKnight = ImageIO.read(getClass().getResource("/images/knight_black.png"));
            this.whiteKnight = ImageIO.read(getClass().getResource("/images/knight_white.png"));
            this. blackPawn = ImageIO.read(getClass().getResource("/images/pawn_black.png"));
            this.whitePawn = ImageIO.read(getClass().getResource("/images/pawn_white.png"));
            this.blackQueen = ImageIO.read(getClass().getResource("/images/queen_black.png"));
            this.whiteQueen = ImageIO.read(getClass().getResource("/images/queen_white.png"));
            this.blackKing = ImageIO.read(getClass().getResource("/images/king_black.png"));
            this.whiteKing = ImageIO.read(getClass().getResource("/images/king_white.png"));
            this.marker = ImageIO.read(getClass().getResource("/images/target.png"));
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Could not load image");
        }
    }

    public BufferedImage getImageByPiece(Piece piece) {
        if(piece == null) {
            return null;
        }
        boolean isWhite = piece.isWhite();
        switch (piece.getPieceType()) {
            case PAWN:
                return isWhite ? whitePawn : blackPawn;
            case ROOK:
                return isWhite ? whiteRook : blackRook;
            case KNIGHT:
                return isWhite ? whiteKnight : blackKnight;
            case BISHOP:
                return isWhite ? whiteBishop : blackBishop;
            case QUEEN:
                return isWhite ? whiteQueen : blackQueen;
            case KING:
                return isWhite ? whiteKing : blackKing;
        }
        return null;
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

package main.java.logic;

public class Move {

    final private Player player;
    final private Tile src;
    final Tile dst;
    private boolean castlingMove = false;
    private boolean isPromotionMove = false;
    private boolean isFirstMove = false;

    public Tile getSrc() {
        return src;
    }

    public Tile getDst() {
        return dst;
    }

    public Move(Player player, Tile src, Tile dst) {
        this.player = player;
        this.src = new Tile(src.getPiece(), src.getX(), src.getY());
        this.dst = new Tile(dst.getPiece(), dst.getX(), dst.getY());
    }

    public void setCastlingMove(boolean castlingMove) {
        this.castlingMove = castlingMove;
    }

    public boolean isCastlingMove() {
        return castlingMove;
    }

    public void setPromotionMove(boolean promotionMove) {
        this.isPromotionMove = promotionMove;
    }

    public boolean isPromotionMove() {
        return isPromotionMove;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setIsFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    @Override
    public String toString() {
        return src.toString() + " to: " + dst.toString();
    }
}

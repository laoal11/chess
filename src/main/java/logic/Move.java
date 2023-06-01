package main.java.logic;

import main.java.logic.Player;
import main.java.logic.Tile;

public class Move {

    final private Player player;
    final private Tile src;
    final Tile dst;
    private boolean castlingMove = false;


    public Player getPlayer() {
        return player;
    }

    public Tile getSrc() {
        return src;
    }

    public Tile getDst() {
        return dst;
    }

    public Move(Player player, Tile src, Tile dst) {
        this.player = player;
        this.src = src;
        this.dst = dst;
    }

    public void setCastlingMove(boolean castlingMove) {
        this.castlingMove = castlingMove;
    }

    public boolean isCastlingMove() {
        return castlingMove;
    }
}

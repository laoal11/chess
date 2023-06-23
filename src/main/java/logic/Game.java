package main.java.logic;

import java.util.List;
import java.util.Stack;

public class Game {

    private Player[] players = new Player[2];
    //used to handle logic of the chess board
    private Board board;
    //used to create a window with shown pieces
    private Player currentPlayer;
    private Stack<Move> lastMoves;

    public GameState getState() {
        return state;
    }

    private GameState state;

    public Game(Player p1, Player p2) {
        init(p1, p2);
    }

    private void init(Player p1, Player p2) {
        players[0] = p1;
        players[1] = p2;
        board = new Board();
        currentPlayer = p1.isWhiteSide() ? p1 : p2;
        lastMoves = new Stack<>();
        state = GameState.READY;
    }

    public void restartGame() {
        init(players[0], players[1]);
    }


    public boolean playerMove( int startRow, int startColumn, int endRow, int endColumn) {
        Tile start = board.getTile(startRow, startColumn);
        Tile end = board.getTile(endRow, endColumn);
        Move move = new Move(currentPlayer, start, end);
        return this.makeMove(currentPlayer, move);
    }

    private boolean makeMove(Player player, Move move) {
        // players turn?
        if(currentPlayer != player) {
            System.out.println("Wrong player trying to make a move");
            return false;
        }
        Piece sourcePiece = move.getSrc().getPiece();
        if(sourcePiece.isWhite() != player.isWhiteSide()) {
            System.out.println("Player is trying to make move with opponents piece..");
            return false;
        }
        // valid move?
        if(!sourcePiece.canMove(board, move.getSrc(), move.getDst())) {
            System.out.println("Move was not valid");
            return false;
        }

        doMove(move);

        if(isCheck(!player.isWhiteSide())) {
            revertLastMove();
            return false;
        }
        if(player == players[0]) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }

        if(isCheck(player.isWhiteSide())) {
            if(isCheckmate(player.isWhiteSide())) {
                state = player.isWhiteSide() ? GameState.WHITE_WON : GameState.BLACK_WON;
                return true;
            }
            if(player.isWhiteSide()){
                state = GameState.BLACK_IN_CHECK;
            } else {
                state = GameState.WHITE_IN_CHECK;
            }
        }

        return true;
    }

    private void doMove(Move move) {
        int srcX = move.getSrc().getX();
        int srcY = move.getSrc().getY();
        int dstX = move.getDst().getX();
        int dstY = move.getDst().getY();
        board.getTile(dstX, dstY).setPiece(move.getSrc().getPiece());
        board.getTile(srcX, srcY).setPiece(null);
        lastMoves.push(move);
    }

    private void revertLastMove() {
        if(lastMoves.isEmpty()) return;
        Move lastMove = lastMoves.peek();
        Tile src = lastMove.getSrc();
        Tile dst = lastMove.getDst();
        board.getTile(dst.getX(), dst.getY()).setPiece(dst.getPiece());
        board.getTile(src.getX(), src.getY()).setPiece(src.getPiece());
    }

    private boolean isCheck(boolean isWhite) {
        //could keep track which pieces of each player are where, makes checking every tile and piece redundant
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile tile = board.getTile(i, j);
                Piece pieceOnTile = tile.getPiece();
                if(pieceOnTile != null && (pieceOnTile.isWhite() == isWhite) && !(pieceOnTile instanceof King)) {
                    for(Tile reachableTile : pieceOnTile.validMoves(board, tile)) {
                        if(reachableTile.getPiece() != null ) {
                            if(reachableTile.getPiece() instanceof King) {
                                return true;
                            }
                        }
                    }

                }
            }
        }
        return false;
    }

    private boolean isCheckmate(boolean isWhite) {
        Player playerToEscape = isWhite ? players[1] : players[0];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile sourceTile = board.getTile(i, j);
                Piece pieceOnTile = sourceTile.getPiece();
                if(pieceOnTile == null || pieceOnTile.isWhite() == isWhite) {
                    continue;
                }
                List<Tile> validMoves = pieceOnTile.validMoves(board, sourceTile);
                for(Tile tileToMove : validMoves) {
                    Move move = new Move(playerToEscape, sourceTile, tileToMove);
                    doMove(move);
                    if(!isCheck(isWhite)) {
                        revertLastMove();
                        return false;
                    }
                    revertLastMove();
                }
            }
        }
        return true;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}

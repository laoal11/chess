package main.java.logic;

import java.util.Stack;

public class Game {

    private Player[] players = new Player[2];
    //used to handle logic of the chess board
    private Board board;
    //used to create a window with shown pieces
    private Player currentPlayer;
    private Stack<Move> lastMoves;

    public Game(Player p1, Player p2) {
        players[0] = p1;
        players[1] = p2;
        board = new Board();
        currentPlayer = p1.isWhiteSide() ? p1 : p2;
        lastMoves = new Stack<>();
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
        lastMoves.push(move);
        move.getSrc().setPiece(null);
        move.getDst().setPiece(sourcePiece);
        if(player == players[0]) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }
        board.printBoard();
        System.out.println(player.getName() + " made a move");
        return true;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}

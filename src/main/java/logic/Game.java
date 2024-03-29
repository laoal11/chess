package main.java.logic;

import main.java.gui.chessframe.BoardPanel;
import main.java.logic.pieces.*;

import java.util.List;
import java.util.Stack;

public class Game {

    private Player[] players = new Player[2];
    //used to handle logic of the chess board
    private Board board;
    //used to create a window with shown pieces
    private Player currentPlayer;
    private Stack<Move> lastMoves;
    private Stack<GameState> lastGameStates;

    private boolean lastMoveCastling = false;

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
        lastGameStates = new Stack<>();
        state = GameState.WHITE_TO_MOVE;
        lastGameStates.add(GameState.WHITE_TO_MOVE);
    }

    public void restartGame() {
        init(players[0], players[1]);
    }

    public void changeCurrentPlayer() {
        currentPlayer = players[0] == currentPlayer ? players[1] : players[0];
        //lastGameStates.push(currentPlayer.isWhiteSide() ? GameState.WHITE_TO_MOVE : GameState.BLACK_TO_MOVE);
        //state = currentPlayer.isWhiteSide() ? GameState.WHITE_TO_MOVE : GameState.BLACK_TO_MOVE;
    }

    public Move getLastMove() {
        if (lastMoves.isEmpty()) return null;
        return lastMoves.peek();
    }

    public boolean playerMove(int startRow, int startColumn, int endRow, int endColumn) {
        Tile start = board.getTile(startRow, startColumn);
        Tile end = board.getTile(endRow, endColumn);
        Move move = new Move(currentPlayer, start, end);
        return this.makeMove(currentPlayer, move);
    }

    private boolean makeMove(Player player, Move move) {
        // players turn?
        if (currentPlayer != player) {
            return false;
        }
        Piece sourcePiece = move.getSrc().getPiece();
        if (sourcePiece.isWhite() != player.isWhiteSide()) {
            return false;
        }
        // valid move?
        if (!sourcePiece.canMove(board, move.getSrc(), move.getDst())) {
            return false;
        }
        lastMoveCastling = false;

        if (isPromotion(move)) {
            move.setPromotionMove(true);
            Piece promotionPiece = openPromotionWindow(currentPlayer.isWhiteSide());
            move.getSrc().setPiece(promotionPiece);
        }

        makeMove(move);
        if (isCheck(!player.isWhiteSide())) {
            revertLastMove();
            return false;
        }
        if (isCastling(move)) {
            if (state.equals(GameState.WHITE_IN_CHECK) || state.equals(GameState.BLACK_IN_CHECK)) {
                revertLastMove();
                return false;
            }
            Tile[] rookTile = retrieveCastlingRook(move);
            if (isReachableByOpponent(rookTile[0].getPiece(), rookTile[1])) {
                System.out.println("Rook is reachable by something");
                revertLastMove();
                return false;
            }
            move.setCastlingMove(true);
            Move castlingMove = new Move(getCurrentPlayer(), rookTile[0], rookTile[1]);
            castlingMove.setCastlingMove(true);
            makeMove(castlingMove);
            setHasMovedFlagForKingOrRook(castlingMove);
            lastMoveCastling = true;
        }
        setHasMovedFlagForKingOrRook(move);

        changeCurrentPlayer();
        state = currentPlayer.isWhiteSide() ? GameState.WHITE_TO_MOVE : GameState.BLACK_TO_MOVE;
        if (isCheck(player.isWhiteSide())) {
            if (isCheckmate(player.isWhiteSide())) {
                state = player.isWhiteSide() ? GameState.WHITE_WON : GameState.BLACK_WON;
                lastGameStates.push(state);
                return true;
            }
            state = player.isWhiteSide() ? GameState.BLACK_IN_CHECK : GameState.WHITE_IN_CHECK;
        }
        lastGameStates.push(state);
        return true;
    }

    private void setHasMovedFlagForKingOrRook(Move move) {
        Piece piece = move.getSrc().getPiece();
        if (piece.hasMoved()) {
            return;
        }
        piece.setHasMoved(true);
        move.setIsFirstMove(true);
    }

    private Piece openPromotionWindow(boolean isWhite) {
        BoardPanel.PromotionWindow promotionWindow = new BoardPanel.PromotionWindow();
        String promotionPiece = promotionWindow.getPromotionPiece();
        return Piece.fromString(promotionPiece, isWhite);
    }

    private boolean isPromotion(Move move) {
        Piece srcPiece = move.getSrc().getPiece();
        if (srcPiece.getPieceType() != PieceEnum.PAWN) {
            return false;
        }
        boolean isWhite = srcPiece.isWhite();
        int dstX = move.getDst().getX();
        return ((isWhite && dstX == 0) || (!isWhite && dstX == 7));
    }

    private boolean isCastling(Move move) {
        // code for castling
        Piece piece = move.getSrc().getPiece();
        // is the piece a King?
        if (!(piece instanceof King)) {
            return false;
        }
        // is it the kings first move?
        if (piece.hasMoved()) {
            return false;
        }
        // does the king move 1 move only to the sides?
        if (Math.abs(move.getSrc().getY() - move.getDst().getY()) == 1 || Math.abs(move.getSrc().getX() - move.getDst().getX()) > 0) {
            return false;
        }
        return true;
    }

    private Tile[] retrieveCastlingRook(Move move) {
        boolean isLeftSideCastling = (move.getSrc().getY() - move.getDst().getY()) > 0;
        King king = (King) move.getSrc().getPiece();
        if (king.isWhite()) {
            if (isLeftSideCastling) {
                return new Tile[]{board.getTile(7, 0), board.getTile(7, 3)};
            }
            return new Tile[]{board.getTile(7, 7), board.getTile(7, 5)};
        }
        if (isLeftSideCastling) {
            return new Tile[]{board.getTile(0, 0), board.getTile(0, 3)};
        }
        return new Tile[]{board.getTile(0, 7), board.getTile(0, 5)};
    }

    private void makeMove(Move move) {
        int srcX = move.getSrc().getX();
        int srcY = move.getSrc().getY();
        int dstX = move.getDst().getX();
        int dstY = move.getDst().getY();
        board.getTile(dstX, dstY).setPiece(move.getSrc().getPiece());
        board.getTile(srcX, srcY).setPiece(null);
        lastMoves.push(move);
    }

    public Move revertLastMove() {
        if (lastMoves.isEmpty()) return null;
        Move lastMove = revertLastMoveHelper();
        if (lastMove.isCastlingMove()) {
            return revertLastMoveHelper();
        }
        return lastMove;
    }

    public void revertToLastGameState() {
        if (lastGameStates.isEmpty()) return;
        lastGameStates.pop();
        if (lastGameStates.isEmpty()){
            lastGameStates.push(GameState.WHITE_TO_MOVE);
        }
        state = lastGameStates.peek();
    }

    public Move revertLastMoveHelper() {
        Move lastMove = lastMoves.pop();
        if (lastMove.isFirstMove()) {
            lastMove.getSrc().getPiece().setHasMoved(false);
        }
        Tile src = lastMove.getSrc();
        Tile dst = lastMove.getDst();
        board.getTile(dst.getX(), dst.getY()).setPiece(dst.getPiece());
        board.getTile(src.getX(), src.getY()).setPiece(getLastMovesSourcePiece(lastMove));
        return lastMove;

    }

    private Piece getLastMovesSourcePiece(Move move) {
        if (move.isPromotionMove()) {
            return new Pawn(move.getSrc().getPiece().isWhite());
        }
        return move.getSrc().getPiece();
    }

    private boolean isCheck(boolean isWhite) {
        King king = null;
        Tile kingTile = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getTile(i, j).getPiece();
                if (piece instanceof King && (piece.isWhite() != isWhite)) {
                    king = (King) piece;
                    kingTile = board.getTile(i, j);
                    break;
                }
            }
        }
        return isReachableByOpponent(king, kingTile);
    }

    private boolean isReachableByOpponent(Piece target, Tile targetTile) {
        //could keep track which pieces of each player are where, makes checking every tile and piece redundant
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile tile = board.getTile(i, j);
                Piece pieceOnTile = tile.getPiece();
                if (pieceOnTile != null && (pieceOnTile.isWhite() != target.isWhite()) && pieceOnTile != target) {
                    for (Tile reachableTile : pieceOnTile.validMoves(board, tile)) {
                        if (reachableTile.equals(targetTile)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isCheckmate(boolean isWhite) {
        Player playerToEscape = isWhite ? players[1] : players[0];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile sourceTile = board.getTile(i, j);
                Piece pieceOnTile = sourceTile.getPiece();
                if (pieceOnTile == null || pieceOnTile.isWhite() == isWhite) {
                    continue;
                }
                List<Tile> validMoves = pieceOnTile.validMoves(board, sourceTile);
                for (Tile tileToMove : validMoves) {
                    Move move = new Move(playerToEscape, sourceTile, tileToMove);
                    makeMove(move);
                    if (!isCheck(isWhite)) {
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

package main.java.logic;

public enum GameState {
    DRAW("DRAW"),
    WHITE_TO_MOVE("White to move"),
    BLACK_TO_MOVE("Black to move"),
    WHITE_IN_CHECK("White is in check"),
    BLACK_IN_CHECK("Black is in check"),
    WHITE_WON("White has won"),
    BLACK_WON("Black has won");

    private final String displayName;

    GameState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}

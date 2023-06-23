package main.java;

import main.java.gui.DisplayBoard;
import main.java.logic.Game;
import main.java.logic.Player;

public class Main {
    public static void main(String[] args) {
        Player whitePlayer = new Player(true, "White");
        Player blackPlayer = new Player(false, "Black");
        Game match = new Game(whitePlayer, blackPlayer);
        DisplayBoard displayBoard = new DisplayBoard(match, whitePlayer, blackPlayer);
    }
}
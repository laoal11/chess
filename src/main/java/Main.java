package main.java;

import main.java.gui.DisplayBoard;
import main.java.logic.Player;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Player whitePlayer = new Player(true, "White");
        Player blackPlayer = new Player(false, "Black");
        DisplayBoard displayBoard = new DisplayBoard(whitePlayer, blackPlayer);
    }
}
package main.java;

import main.java.gui.menu.MainMenu;
import main.java.logic.Player;

public class Main {
    public static void main(String[] args) {
        Player whitePlayer = new Player(true, "White");
        Player blackPlayer = new Player(false, "Black");
        MainMenu mainMenu = new MainMenu(whitePlayer, blackPlayer);
        //DisplayBoard displayBoard = new DisplayBoard(match, whitePlayer, blackPlayer);
    }
}
package main.java.logic;


public class Player {
    final private boolean whiteSide;
    final private String name;

    public Player(boolean whiteSide) {
        this.whiteSide = whiteSide;
        this.name = "Default";
    }

    public Player(boolean whiteSide, String name) {
        this.whiteSide = whiteSide;
        this.name = name;
    }

    public boolean isWhiteSide() {
        return whiteSide;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj.getClass() != getClass()) {
            return false;
        }
        Player compare = (Player) obj;
        return this.name.equals(compare.name) && this.whiteSide == compare.whiteSide;
    }
}

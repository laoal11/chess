package main.java.logic;


public class Board {
    private Tile[][] tiles = new Tile[8][8];

    public Board() {
        // set initial board
        setBoard();
        printBoard();
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || y < 0 || x >= 8 || y >= 8) {
            throw new RuntimeException("Index out of bound");
        }
        return tiles[x][y];
    }

    private void setBoard() {
        //pieces Black
        //rooks
        tiles[0][0] = new Tile(new Rook(false),0,0);
        tiles[0][7] = new Tile(new Rook(false),0,7);
        //knights
        tiles[0][1] = new Tile(new Knight(false),0,1);
        tiles[0][6] = new Tile(new Knight(false),0,6);
        //Bishops
        tiles[0][2] = new Tile(new Bishop(false),0,2);
        tiles[0][5] = new Tile(new Bishop(false),0,5);
        //Queen
        tiles[0][3] = new Tile(new Queen(false),0,3);
        //King
        tiles[0][4] = new Tile(new King(false),0,4);
        //Pawns
        for(int i = 0; i < 8; i++) {
            tiles[1][i] = new Tile(new Pawn(false),1,i);
        }

        //pieces White
        //rooks
        tiles[7][0] = new Tile(new Rook(true),7,0);
        tiles[7][7] = new Tile(new Rook(true),7,7);
        //knights
        tiles[7][1] = new Tile(new Knight(true),7,1);
        tiles[7][6] = new Tile(new Knight(true),7,6);
        //Bishops
        tiles[7][2] = new Tile(new Bishop(true),7,2);
        tiles[7][5] = new Tile(new Bishop(true),7,5);
        //Queen
        tiles[7][3] = new Tile(new Queen(true),7,3);
        //King
        tiles[7][4] = new Tile(new King(true),7,4);
        //Pawns
        for(int i = 0; i < 8; i++) {
            tiles[6][i] = new Tile(new Pawn(true),6, i);
        }
        for(int i = 2; i < 6; i++) {
            for(int j = 0; j < 8; j++) {
                tiles[i][j] = new Tile(null, i, j);
            }
        }
        System.out.println("BOARD IS READY: ");
    }

    public void printBoard() {
        for(Tile[] row : tiles) {
            System.out.print("[ ");
            for(Tile tile : row) {
                System.out.print(padRight(tile.getPiece(), 15));
            }
            System.out.println(" ]");
        }
    }

    private static String padRight(Piece piece, int n) {
        if(piece == null) {
            return String.format("%-" + n + "s", "  -----");
        }
        return String.format("%-" + n + "s", piece);
    }

}

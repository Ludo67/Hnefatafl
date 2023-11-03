package com.company;


public class Board {

    public final static int SIZE = 13;
    private int[][] boardArray = new int[SIZE][SIZE];


    public Board(String config) {

        this.boardArray = initBoard(config);
        displayBoard(this.boardArray);
    }

    public int[][] getBoardArray() {
        return boardArray;
    }
    public int[][] initBoard(String config) {
        int[][] tableau = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tableau[i][j] = (char) (config.charAt(i * SIZE + j) - '0');
            }
        }

        return tableau;
    }



    public void displayBoard(int[][] board){
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                System.out.print(this.boardArray[i][j] + " ");
            }
            System.out.println();
        }
    }
    public void makeMove(Move move) {
        movePiece(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
    }

    // Méthode pour déplacer une pièce
    private void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        int piece = boardArray[fromRow][fromCol]; // Récupère la pièce à déplacer
        // Mise à jour du plateau visuel
        if (piece == 4) {
            boardArray[fromRow][fromCol] = 0;
            boardArray[toRow][toCol] = 4;
        } else if (piece == 2) {
            boardArray[fromRow][fromCol] = 0;
            boardArray[toRow][toCol] = 2;
        } else if (piece == 5) {
            boardArray[fromRow][fromCol] = 0;
            boardArray[toRow][toCol] = 5;
        }

        displayBoard(this.boardArray);
    }


}

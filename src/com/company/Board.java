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

    public void makeMove(Move move) {
        movePiece(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
        capturingEnemy(move.getToRow(), move.getToCol());
    }

    // Méthode pour capturer les pions ennemis
    public void capturingEnemy(int toRow, int toCol) {
        int currentPiece = boardArray[toRow][toCol];

        // Vérifiez les captures dans toutes les directions
        checkAndCapture(toRow, toCol, -1, 0, currentPiece); // Haut
        checkAndCapture(toRow, toCol, 1, 0, currentPiece);  // Bas
        checkAndCapture(toRow, toCol, 0, -1, currentPiece); // Gauche
        checkAndCapture(toRow, toCol, 0, 1, currentPiece);  // Droite
    }

    private void checkAndCapture(int row, int col, int dRow, int dCol, int currentPiece) {
        int enemyPiece = (currentPiece == 4) ? 2 : 4;  // Si currentPiece est un attaquant (4), l'ennemi est un défenseur (2), sinon l'inverse.

        int oppositeRow = row + 2 * dRow;
        int oppositeCol = col + 2 * dCol;

        // Vérifiez si l'opposé est dans les limites et s'il s'agit d'un ennemi, et s'il est encerclé
        if (isInsideBoard(oppositeRow, oppositeCol) && boardArray[oppositeRow][oppositeCol] == currentPiece) {
            int middleRow = row + dRow;
            int middleCol = col + dCol;
            if (boardArray[middleRow][middleCol] == enemyPiece) {
                // Capturer l'ennemi
                boardArray[middleRow][middleCol] = 0;
            }
        }
    }

    private boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    public void displayBoard(int[][] board){
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                System.out.print(this.boardArray[i][j] + " ");
            }
            System.out.println();
        }
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

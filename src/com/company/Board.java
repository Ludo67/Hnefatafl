package com.company;


public class Board {

    public final static int SIZE = 13;
    public static final int ATTACKER = 4;
    public static final int DEFENDER = 2  ;
    public static final int KING = 5;
    public static final int EMPTY = 0;
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
        if (isKing(move.getToRow(), move.getToCol())) {
            if (isKingSurrounded(move.getToRow(), move.getToCol())) {
                System.out.println("Les attaquants ont gagné - Le roi est capturé !");
                System.exit(0); // Fin du jeu
            }
            if (isKingEscaped(move.getToRow(), move.getToCol())) {
                System.out.println("Les défenseurs ont gagné - Le roi s'est échappé !");
                System.exit(0); // Fin du jeu
            }
        } else {
            capturingEnemy(move.getToRow(), move.getToCol());
        }
    }


    private boolean isKingSurrounded(int row, int col) {
        return isInsideBoard(row - 1, col) && isInsideBoard(row + 1, col) &&
                isInsideBoard(row, col - 1) && isInsideBoard(row, col + 1) &&
                boardArray[row - 1][col] == 4 && boardArray[row + 1][col] == 4 &&
                boardArray[row][col - 1] == 4 && boardArray[row][col + 1] == 4;
    }

    private boolean isKingEscaped(int row, int col) {
        return (row == 0 || row == SIZE - 1) && (col == 0 || col == SIZE - 1);
    }

    private boolean isKing(int row, int col) {
        return boardArray[row][col] == 5;
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
        if (piece == ATTACKER) {
            boardArray[fromRow][fromCol] = EMPTY;
            boardArray[toRow][toCol] = ATTACKER;
        } else if (piece == DEFENDER) {
            boardArray[fromRow][fromCol] = EMPTY;
            boardArray[toRow][toCol] = DEFENDER;
        } else if (piece == KING) {
            boardArray[fromRow][fromCol] = EMPTY;
            boardArray[toRow][toCol] = KING;
        }

        displayBoard(this.boardArray);
    }


    public Move[] getAllPossibleMoves(int player) {
        return null;
    }

    public void undoMove(Move move) {
    }

    public boolean isGameOver() {
       return false;
    }
}

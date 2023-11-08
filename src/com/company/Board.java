package com.company;


import java.util.ArrayList;
import java.util.Stack;

public class Board {

    public final static int SIZE = 13;
    public static final int ATTACKER = 4;
    public static final int DEFENDER = 2;
    public static final int KING = 5;
    public static final int EMPTY = 0;
    private int[][] boardArray = new int[SIZE][SIZE];
    public Stack<Move> history = new Stack<>();


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
        history.push(new Move(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol()));
        movePiece(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
//        if (isKing(move.getToRow(), move.getToCol())) {
//            if (isKingSurrounded(move.getToRow(), move.getToCol())) {
//                System.out.println("Les attaquants ont gagné - Le roi est capturé !");
//                System.exit(0); // Fin du jeu
//            }
//            if (isKingEscaped(move.getToRow(), move.getToCol())) {
//                System.out.println("Les défenseurs ont gagné - Le roi s'est échappé !");
//                System.exit(0); // Fin du jeu
//            }
//        } else {
//            capturingEnemy(move.getToRow(), move.getToCol());
//        }
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

    public void capturingEnemy(int toRow, int toCol) {
        int currentPiece = boardArray[toRow][toCol];

        checkAndCapture(toRow, toCol, -1, 0, currentPiece); // Haut
        checkAndCapture(toRow, toCol, 1, 0, currentPiece);  // Bas
        checkAndCapture(toRow, toCol, 0, -1, currentPiece); // Gauche
        checkAndCapture(toRow, toCol, 0, 1, currentPiece);  // Droite
    }

    private boolean checkAndCapture(int row, int col, int dRow, int dCol, int currentPiece) {
        int enemyPiece = (currentPiece == 4) ? 2 : 4;
        int oppositeRow = row + 2 * dRow;
        int oppositeCol = col + 2 * dCol;

        if (isInsideBoard(oppositeRow, oppositeCol) && boardArray[oppositeRow][oppositeCol] == currentPiece) {
            int middleRow = row + dRow;
            int middleCol = col + dCol;
            if (boardArray[middleRow][middleCol] == enemyPiece) {
                // Capturer l'ennemi
                boardArray[middleRow][middleCol] = 0;
            }

            return true;
        }
        return false;
    }

    private boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    public String displayBoard(int[][] board){
        String move = "";

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                move += (board[i][j] + " ");
            }
            move += "";
        }

        return move;
    }


    private void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        int piece = boardArray[fromRow][fromCol];

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
        Client.setMove(displayBoard(this.boardArray));
    }


    public Move[] getAllPossibleMoves(int player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        for (int row = 0; row < boardArray.length; row++) {
            for (int col = 0; col < boardArray[0].length; col++) {
                int piece = boardArray[row][col];

                int playerPiece = (Client.getPlayer() == '1')? 4: 2;
                if (piece == playerPiece) {
                    ArrayList<Move> movesForPiece = getMovesForPiece(piece, row, col);
                    possibleMoves.addAll(movesForPiece);
                }
            }
        }

        return (Move[]) possibleMoves.toArray();
    }

    private ArrayList<Move> getMovesForPiece(int piece, int row, int col) {
        ArrayList<Move> moves = new ArrayList<>();
        int player = Client.getPlayer();

        // Déplacements vers le haut
        if (isInsideBoard(row - 1, col) && checkAndCapture(row - 1, col,-1,0, player)) {
            moves.add(new Move(row, col, row - 1, col));
        }
        // Déplacements vers le bas
        if (isInsideBoard(row + 1, col) && checkAndCapture(row - 1, col,1,0, player)) {
            moves.add(new Move(row, col, row + 1, col));
        }
        // Déplacements vers la gauche
        if (isInsideBoard(row, col - 1) && checkAndCapture(row - 1, col,0,-1, player)) {
            moves.add(new Move(row, col, row, col - 1));
        }
        // Déplacements vers la droite
        if (isInsideBoard(row, col + 1) && checkAndCapture(row - 1, col,0,1, player)) {
            moves.add(new Move(row, col, row, col + 1));
        }
        return moves;
    }

    public void undoMove(Move move) {
        Move lastMove = history.pop();
        movePiece(lastMove.getFromRow(), lastMove.getFromCol(), lastMove.getToRow(), lastMove.getToCol());
    }

    public boolean isGameOver(Move move){
        if(isKingSurrounded(move.getToRow(), move.getToCol())){
            System.out.println("Les attaquants ont gagnés");
            return true;
        } else if(isKingEscaped(move.getToRow(), move.getToCol())){
            System.out.println("Les défenseurs ont gagnés");
            return true;
        } else if(getAllPossibleMoves(Client.getPlayer()).length == 0 &&
                  getAllPossibleMoves(Client.getOpponent()).length == 0){
            System.out.println("Partie nulle");
            return true;
        } else{
            return false;
        }
    }
}

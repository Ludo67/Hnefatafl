package com.company;

import java.util.ArrayList;

// La classe Board est responsable de la gestion du plateau de jeu Tic Tac Toe.
class Board {

    private static final int SIZE = 3;  // Taille du plateau (3x3)
    private Mark[][] board;  // Tableau 2D représentant le plateau

    // Constructeur - initialise un plateau vide
    public Board() {
        this.board = new Mark[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Mark.EMPTY;
            }
        }
    }

    // Renvoie la marque (X, O ou EMPTY) à une position donnée
    public Mark getMarkAt(int row, int col) {
        return board[row][col];
    }

    // Place une marque (X ou O) sur le plateau à la position spécifiée par le mouvement
    public void play(Move m, Mark mark) {
        board[m.getRow()][m.getCol()] = mark;
    }

    // Évalue l'état du plateau :
    // Retourne 100 si le joueur spécifié a gagné,
    // -100 si le joueur spécifié a perdu,
    // et 0 sinon (match nul ou jeu en cours)
    public int evaluate(Mark joueurMark) {

        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == joueurMark && board[i][1] == joueurMark && board[i][2] == joueurMark) return 100;
            else if (board[i][0] != Mark.EMPTY && board[i][0] != joueurMark && board[i][1] == board[i][0] && board[i][2] == board[i][0])
                return -100;

            if (board[0][i] == joueurMark && board[1][i] == joueurMark && board[2][i] == joueurMark) return 100;
            else if (board[0][i] != Mark.EMPTY && board[0][i] != joueurMark && board[1][i] == board[0][i] && board[2][i] == board[0][i])
                return -100;
        }

        // Vérifie les diagonales
        if (board[0][0] == joueurMark && board[1][1] == joueurMark && board[2][2] == joueurMark) return 100;
        else if (board[0][0] != Mark.EMPTY && board[0][0] != joueurMark && board[1][1] == board[0][0] && board[2][2] == board[0][0])
            return -100;

        if (board[0][2] == joueurMark && board[1][1] == joueurMark && board[2][0] == joueurMark) return 100;
        else if (board[0][2] != Mark.EMPTY && board[0][2] != joueurMark && board[1][1] == board[0][2] && board[2][0] == board[0][2])
            return -100;
        return 0;
    }

    // Renvoie une copie de l'objet Board actuel
    public Board copy() {
        Board newBoard = new Board();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                newBoard.board[i][j] = this.board[i][j];
            }
        }
        return newBoard;
    }

    // Renvoie une liste des mouvements disponibles (cases vides)
    public ArrayList<Move> getAvailableMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == Mark.EMPTY) moves.add(new Move(i, j));
            }
        }
        return moves;
    }

}

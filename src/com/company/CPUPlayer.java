package com.company;

import java.util.ArrayList;

class CPUPlayer {

    private final Mark cpuMark;    // Marque du CPU (X ou O)
    private final Mark joueur;     // Marque de l'adversaire
    private int numExploredNodes;  // Nombre de nœuds parcourus lors de la recherche

    // Constructeur
    public CPUPlayer(Mark cpu) {
        this.numExploredNodes = 0;
        this.cpuMark = cpu;

        // Déterminer la marque de l'adversaire
        if (cpu == Mark.X) {
            joueur = Mark.O;
        } else {
            joueur = Mark.X;
        }
    }

    public int getNumOfExploredNodes() {
        return numExploredNodes;
    }

    // Utilise l'algorithme MinMax pour déterminer le meilleur mouvement pour le CPU
    public ArrayList<Move> getNextMoveMinMax(Board board) {
        numExploredNodes = 0;

        ArrayList<Move> bestMoves = new ArrayList<Move>();
        int bestScore;
        if (cpuMark == Mark.X) {
            bestScore = Integer.MIN_VALUE;
        } else {
            bestScore = Integer.MAX_VALUE;
        }

        // Évaluer chaque mouvement possible pour trouver le meilleur
        for (Move move : board.getAvailableMoves()) {
            Board newBoard = board.copy();
            newBoard.play(move, cpuMark);
            int currentScore = minMax(newBoard, joueur);

            bestScore = getBestScore(bestMoves, bestScore, move, currentScore);
        }
        return bestMoves;
    }

    // Met à jour le score le plus élevé et ajoute le mouvement à la liste des meilleurs mouvements
    private int getBestScore(ArrayList<Move> bestMoves, int bestScore, Move move, int currentScore) {
        if ((cpuMark == Mark.X && currentScore > bestScore) ||
                (cpuMark == Mark.O && currentScore < bestScore)) {
            bestScore = currentScore;
            bestMoves.clear();
            bestMoves.add(move);
        } else if (currentScore == bestScore) {
            bestMoves.add(move);
        }
        return bestScore;
    }

    // Implémentation récursive de l'algorithme MinMax
    private int minMax(Board board, Mark currentPlayer) {
        numExploredNodes++;

        // Si la position actuelle est finale
        int score = board.evaluate(cpuMark);
        if (score == 100 || score == -100) {
            // retourner l'évaluation de la position
            return score;
        }

        // Si aucun mouvement n'est disponible, retourner 0 (égalité)
        if (board.getAvailableMoves().isEmpty()) {
            return 0;
        }

        // Si le joueur actuel est Max (ici, le CPU)
        if (currentPlayer == cpuMark) {
            int maxScore = Integer.MIN_VALUE; // Initialisé à -∞

            // Pour chaque mouvement possible à partir de la position actuelle
            for (Move move : board.getAvailableMoves()) {
                Board newBoard = board.copy();
                newBoard.play(move, currentPlayer);

                // Récursivement évaluer le score du mouvement
                int moveScore = minMax(newBoard, joueur);

                // Mettre à jour maxScore si nécessaire
                maxScore = Math.max(maxScore, moveScore);
            }

            // retourner maxScore
            return maxScore;
        } else { // Si le joueur actuel est Min (l'adversaire)
            int minScore = Integer.MAX_VALUE; // Initialisé à ∞

            // Pour chaque mouvement possible à partir de la position actuelle
            for (Move move : board.getAvailableMoves()) {
                Board newBoard = board.copy();
                newBoard.play(move, currentPlayer);

                // Récursivement évaluer le score du mouvement
                int moveScore = minMax(newBoard, cpuMark);

                // Mettre à jour minScore si nécessaire
                minScore = Math.min(minScore, moveScore);
            }

            // retourner minScore
            return minScore;
        }
    }

    // Utilise l'algorithme Alpha-Beta pour déterminer le meilleur mouvement pour le CPU
    public ArrayList<Move> getNextMoveAB(Board board) {
        // Initialisation du compteur de nœuds explorés
        numExploredNodes = 0;

        // Liste pour stocker les meilleurs mouvements possibles
        ArrayList<Move> bestMoves = new ArrayList<Move>();

        // Initialisation du meilleur score en fonction du marqueur du CPU
        int bestScore = (cpuMark == Mark.X) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Initialisation des valeurs alpha et beta pour l'élagage Alpha-Beta
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        // Parcourir tous les mouvements possibles sur le plateau
        for (Move move : board.getAvailableMoves()) {
            // Crée une copie du plateau pour simuler le mouvement
            Board newBoard = board.copy();
            // Joue le mouvement sur le nouveau plateau
            newBoard.play(move, cpuMark);

            int currentScore = (cpuMark == Mark.X) ?
                    alphaBeta(newBoard, joueur, alpha, beta)
                    : alphaBeta(newBoard, cpuMark, alpha, beta);

            // Mettre à jour la liste des meilleurs mouvements et le meilleur score
            bestScore = getBestScore(bestMoves, bestScore, move, currentScore);

            // Mise à jour des valeurs alpha ou beta en fonction du joueur actuel
            if (cpuMark == Mark.X) {
                alpha = Math.max(alpha, bestScore);  // Mise à jour d'alpha pour le joueur maximisant
            } else {
                beta = Math.min(beta, bestScore);    // Mise à jour de beta pour le joueur minimisant
            }
        }

        // Renvoie la liste des meilleurs mouvements possibles
        return bestMoves;
    }


    // Implémentation récursive de l'algorithme Alpha-Beta
    private int alphaBeta(Board board, Mark currentPlayer, int alpha, int beta) {
        numExploredNodes++;

        // Si la position actuelle est finale
        int score = board.evaluate(cpuMark);
        if (score == 100 || score == -100) {
            // retourner l'évaluation de la position
            return score;
        }

        // Si aucun mouvement n'est disponible, retourner 0 (égalité)
        if (board.getAvailableMoves().isEmpty()) {
            return 0;
        }

        // le joueur actuel est Max (ici, le CPU)
        if (currentPlayer == cpuMark) {
            int at = Integer.MIN_VALUE;  // Étape 4: Initialisé à -∞

            // Pour chaque mouvement possible à partir de la position actuelle
            for (Move move : board.getAvailableMoves()) {
                Board newBoard = board.copy();
                newBoard.play(move, currentPlayer);

                // Récursivement évaluer le score du mouvement avec mise à jour d'alpha
                int moveScore = alphaBeta(newBoard, joueur, Math.max(alpha, at), beta);

                // Mettre à jour at si nécessaire
                at = Math.max(at, moveScore);

                // Si at est supérieur ou égal à beta, élagage
                if (at >= beta) {
                    break;
                }
            }

            // retourner at
            return at;
        } else {  // Si le joueur actuel est Min (l'adversaire)
            int bt = Integer.MAX_VALUE;  // Initialisé à ∞

            // Pour chaque mouvement possible à partir de la position actuelle
            for (Move move : board.getAvailableMoves()) {
                Board newBoard = board.copy();
                newBoard.play(move, currentPlayer);

                // Récursivement évaluer le score du mouvement avec mise à jour de beta
                int moveScore = alphaBeta(newBoard, cpuMark, alpha, Math.min(beta, bt));

                // Mettre à jour bt si nécessaire
                bt = Math.min(bt, moveScore);

                // Si bt est inférieur ou égal à alpha, élagage
                if (bt <= alpha) {
                    break;
                }
            }

            // Retourner bt
            return bt;
        }
    }

}

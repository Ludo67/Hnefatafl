package com.company;

public class AIPlayer {

    private Board board;
    private int maxDepth;
    private int player;
    private int opponent;
    private int positionsEvaluated;



    public AIPlayer( Board board,int player){
        this.board = board;
        this.maxDepth = 3;
        this.player = player;
        this.opponent = (player == Board.ATTACKER) ? Board.DEFENDER : Board.ATTACKER;
        this.positionsEvaluated = 0;

    }
    
    
    public Move findBestMove(){
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;
        for (Move move : board.getAllPossibleMoves(player)) {
            board.makeMove(move);
            int score = minimax(maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            board.undoMove(move);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        System.out.println("Positions évaluées: " + positionsEvaluated);
        return bestMove;
    }

    private int minimax(int maxDepth, int minValue, int maxValue, boolean b) {
        if (maxDepth == 0 || board.isGameOver()) {
            positionsEvaluated++;
            return evaluate();
        }
        if (b) {
            int bestScore = Integer.MIN_VALUE;
            for (Move move : board.getAllPossibleMoves(player)) {
                board.makeMove(move);
                int score = minimax(maxDepth - 1, minValue, maxValue, false);
                board.undoMove(move);
                bestScore = Math.max(score, bestScore);
                minValue = Math.max(minValue, bestScore);
                if (maxValue <= minValue) {
                    return bestScore;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (Move move : board.getAllPossibleMoves(opponent)) {
                board.makeMove(move);
                int score = minimax(maxDepth - 1, minValue, maxValue, true);
                board.undoMove(move);
                bestScore = Math.min(score, bestScore);
                maxValue = Math.min(maxValue, bestScore);
                if (maxValue <= minValue) {
                    return bestScore;
                }
            }
            return bestScore;
        }
    }

    private int evaluate() {
return 0;
    }

}

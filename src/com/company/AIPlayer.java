package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class AIPlayer {

    private Board board;
    private int maxDepth;
    private int player;
    private int opponent;
    private int positionsEvaluated;
    private Client client;


    public AIPlayer(Board board,int player){
        this.board = board;
        this.maxDepth = 3;
        this.player = player;
        this.opponent = (player == Board.ATTACKER) ? Board.DEFENDER : Board.ATTACKER;
        this.positionsEvaluated = 0;
        this.client = new Client();
    }
    
    
    public Move findBestMove(){
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;
        System.out.println("moves: " + board.getAllPossibleMoves(Client.player).size());
        for (Move move : board.getAllPossibleMoves(Client.player)) {
            board.makeMove(move);
            int score = minimax(maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            board.undoMove();
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        System.out.println("Positions évaluées: " + positionsEvaluated);
        return bestMove;
    }

    private int minimax(int maxDepth, int minValue, int maxValue, boolean b) {
        //a revoir
        if (maxDepth == 0 || board.isGameOver(board.history.lastElement())) {
            positionsEvaluated++;
            return evaluate(board.getBoardArray());
        }
        if (b) {
            positionsEvaluated++;
            int bestScore = Integer.MIN_VALUE;
            for (Move move : board.getAllPossibleMoves(Client.player)) {
                board.makeMove(move);
                int score = minimax(maxDepth - 1, minValue, maxValue, false);
                board.undoMove();
                bestScore = Math.max(score, bestScore);
                minValue = Math.max(minValue, bestScore);
                if (maxValue <= minValue) {
                    return bestScore;
                }
            }
            return bestScore;
        } else {
            positionsEvaluated++;
            int bestScore = Integer.MAX_VALUE;
            for (Move move : board.getAllPossibleMoves(Client.player)) {
                board.makeMove(move);
                int score = minimax(maxDepth - 1, minValue, maxValue, true);
                board.undoMove();
                bestScore = Math.min(score, bestScore);
                maxValue = Math.min(maxValue, bestScore);
                if (maxValue <= minValue) {
                    return bestScore;
                }
            }
            return bestScore;
        }
    }

    private int evaluate(int[][] board) {
        int score = 0;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                int piece = board[row][col];

                if (piece == '5') {
                    score += (client.getPlayer() == '1') ? 100 : -100;
                } else if (piece == '4') {
                    score += (client.getPlayer() == '1') ? 100 : -100;
                } else if (piece == '2') {
                    score += (client.getPlayer() == '2') ? 100 : -100;
                }
            }
        }

        return score;
    }

    public ArrayList<Move> getNextMoveAB(Board board) {
        this.positionsEvaluated = 0;
        ArrayList<Move> bestMoves = new ArrayList();
        int bestScore;
        //a changer
        if (Client.player == '1') {
            bestScore = -2147483648;
        } else {
            bestScore = 2147483647;
        }

        int alpha = -2147483648;
        int beta = 2147483647;
        Iterator var6 = board.getAllPossibleMoves(Client.player).iterator();

        while(var6.hasNext()) {
            Move move = (Move)var6.next();
            Board newBoard = board.clone(Client.s);
            newBoard.makeMove(move);
            int currentScore;
            //a changer
            if (Client.player == '1') {
                currentScore = this.alphaBeta(newBoard, Client.getPlayer(), alpha, beta);
            } else {
                currentScore = this.alphaBeta(newBoard, Client.getPlayer(), alpha, beta);
            }

            bestScore = this.getBestScore(bestMoves, bestScore, move, currentScore);
            //a changer
            if (Client.player == '1') {
                alpha = Math.max(alpha, bestScore);
            } else {
                beta = Math.min(beta, bestScore);
            }
        }

        return bestMoves;
    }

    private int getBestScore(ArrayList<Move> bestMoves, int bestScore, Move move, int currentScore) {
        //a changer
        if ((Client.player == '2' || currentScore <= bestScore) && (Client.player == '1' || currentScore >= bestScore)) {
            if (currentScore == bestScore) {
                bestMoves.add(move);
            }
        } else {
            bestScore = currentScore;
            bestMoves.clear();
            bestMoves.add(move);
        }

        return bestScore;
    }

    private int alphaBeta(Board board, int currentPlayer, int alpha, int beta) {
        ++this.positionsEvaluated;
        //a verifier
        int score = evaluate(board.getBoardArray());
        if (score != 100 && score != -100) {
            if (board.getAllPossibleMoves(Client.player).isEmpty()) {
                return 0;
            } else {
                int bt;
                Iterator var7;
                Move move;
                Board newBoard;
                int moveScore;
                if (currentPlayer == Client.getPlayer()) {
                    bt = -2147483648;
                    var7 = board.getAllPossibleMoves(Client.player).iterator();

                    while(var7.hasNext()) {
                        move = (Move)var7.next();
                        newBoard = board.clone(Client.s);
                        newBoard.makeMove(move);
                        moveScore = this.alphaBeta(newBoard, Client.getPlayer(), Math.max(alpha, bt), beta);
                        bt = Math.max(bt, moveScore);
                        if (bt >= beta) {
                            break;
                        }
                    }

                    return bt;
                } else {
                    bt = 2147483647;
                    var7 = board.getAllPossibleMoves(Client.player).iterator();

                    while(var7.hasNext()) {
                        move = (Move)var7.next();
                        newBoard = board.clone(Client.s);
                        newBoard.makeMove(move);
                        moveScore = this.alphaBeta(newBoard, Client.getPlayer(), alpha, Math.min(beta, bt));
                        bt = Math.min(bt, moveScore);
                        if (bt <= alpha) {
                            break;
                        }
                    }

                    return bt;
                }
            }
        } else {
            return score;
        }
    }
}

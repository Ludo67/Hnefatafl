package com.company;


import java.awt.*;
import java.util.Scanner;

public class Game {
    public Board board;


    public Game(String config) {
        this.board = new Board(config);
    }


    private void playerMove() {

        String move = getString();

        String[] parts = move.split("-");
        Point start = parsePoint(parts[0]);
        Point end = parsePoint(parts[1]);

        while (!isValidMove(start.x, start.y, end.x, end.y)) {

            System.out.println("Mouvement invalide");
            move = getString();
            parts = move.split("-");
            start = parsePoint(parts[0]);
            end = parsePoint(parts[1]);
        }
        Move playerMove = new Move(start.x, start.y, end.x, end.y); // Crée un nouvel objet Move avec le mouvement du joueur
        board.makeMove(playerMove); // Applique le mouvement du joueur
    }

    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        return toRow >= 0 && toRow < 13 && toCol >= 0 && toCol < 13 && board.getBoardArray()[fromRow][fromCol] != 0 && board.getBoardArray()[toRow][toCol] == 0;

    }

    private static String getString() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Votre coup (ex: A5-G3) :");
        String move = scanner.nextLine().toUpperCase();
        return move;
    }

    private Point parsePoint(String s) {
        int x = s.charAt(0) - 'A';
        int y = Integer.parseInt(s.substring(1)) - 1;
        return new Point(x, y);
    }


    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String config =
                "4000000000000000000000000040002222000004000225200004400022220000440000000000044440000000444400000000000440002222000044000000000004000000000000000000000000000000000000000";

        Game game = new Game(config);
        while (true) {
            game.playerMove();
        }

    }

}

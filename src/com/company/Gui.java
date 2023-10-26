package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// La classe Gui est responsable de la création et de la gestion de l'interface graphique du jeu Tic Tac Toe.
public class Gui {

    private static final int SIZE = 3;  // Taille du plateau (3x3 pour Tic Tac Toe)
    // Déclarations des composants principaux de l'interface
    private JFrame frame;
    private JButton[][] buttons;
    private Board board;  // Représentation du plateau de jeu
    private CPUPlayer cpuPlayer;  // Joueur ordinateur

    // Constructeur
    public Gui() {
        // Initialisation de la fenêtre principale
        frame = new JFrame("Tic Tac Toe");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(SIZE, SIZE));

        board = new Board();  // Initialisation du plateau
        cpuPlayer = new CPUPlayer(Mark.O);  // Initialisation du joueur ordinateur

        buttons = new JButton[SIZE][SIZE];

        // Création des boutons pour chaque case du plateau
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new JButton();
                frame.add(buttons[i][j]);
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 50));

                // Ajout d'un écouteur d'événements pour gérer les clics sur les boutons
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedButton = (JButton) e.getSource();

                        // Recherche de la case correspondant au bouton cliqué
                        int row = -1, col = -1;
                        for (int i = 0; i < SIZE; i++) {
                            for (int j = 0; j < SIZE; j++) {
                                if (buttons[i][j] == clickedButton) {
                                    row = i;
                                    col = j;
                                }
                            }
                        }

                        // Si la case est vide, on joue le coup et on permet au CPU de jouer son tour
                        if (board.getMarkAt(row, col) == Mark.EMPTY) {
                            playMove(clickedButton);
                            playCPUMove();
                        }
                    }
                });
            }
        }
        frame.setVisible(true);  // Affichage de la fenêtre
    }

    // Méthode principale pour lancer l'interface graphique
    public static void main(String[] args) {
        new Gui();
    }

    // Méthode pour jouer le coup du joueur humain
    private void playMove(JButton clickedButton) {
        clickedButton.setText("X");
        int row = -1, col = -1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (buttons[i][j] == clickedButton) {
                    row = i;
                    col = j;
                }
            }
        }
        board.play(new Move(row, col), Mark.X);

        // Vérification si le joueur humain a gagné ou match nul
        if (board.evaluate(Mark.X) == 100 || board.getAvailableMoves().isEmpty()) {
            checkGameOver();
        }
    }

    // Méthode pour jouer le coup de l'ordinateur
    private void playCPUMove() {
        ArrayList<Move> cpuMoves = cpuPlayer.getNextMoveAB(board);
        int nodesExplored = cpuPlayer.getNumOfExploredNodes();
        System.out.println("Nombre de nœuds explorés: " + nodesExplored);

        if (!cpuMoves.isEmpty()) {
            Move cpuMove = cpuMoves.get(0);
            board.play(cpuMove, Mark.O);
            buttons[cpuMove.getRow()][cpuMove.getCol()].setText("O");

            // Vérification si le CPU a gagné
            if (board.evaluate(Mark.O) == 100 || board.getAvailableMoves().isEmpty()) {
                checkGameOver();
            }
        }
    }

    // Méthode pour vérifier si le jeu est terminé et qui est le gagnant
    private void checkGameOver() {
        if (board.evaluate(Mark.X) == 100) {
            JOptionPane.showMessageDialog(frame, "Vous avez gagné !");
        } else if (board.evaluate(Mark.O) == 100) {
            JOptionPane.showMessageDialog(frame, "Le CPU a gagné !");
        } else if(board.getAvailableMoves().isEmpty()){
            JOptionPane.showMessageDialog(frame, "Match nul !");
        }
        restartGame();  // Réinitialisation du jeu pour une nouvelle partie
    }

    // Méthode pour réinitialiser le jeu
    private void restartGame() {
        board = new Board();
        cpuPlayer = new CPUPlayer(Mark.O);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j].setText("");  // Effacement du contenu des boutons
            }
        }
    }
}
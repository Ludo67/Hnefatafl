package com.company;


public class Move {
    private final int x1, y1; // Position de départ
    private final int x2, y2; // Position d'arrivée


    // Constructeur principal
    public Move(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return x1 == move.x1 && y1 == move.y1 && x2 == move.x2 && y2 == move.y2;
    }


    @Override
    public String toString() {
        return "Move(" + x1 + "," + y1 + " -> " + x2 + "," + y2 + ")";
    }

    public int getFromRow() {
        return x1;
    }

    public int getFromCol() {
        return y1;
    }

    public int getToRow() {
        return x2;
    }

    public int getToCol() {
        return y2;
    }
}



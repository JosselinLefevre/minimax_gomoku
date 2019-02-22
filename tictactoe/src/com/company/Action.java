package com.company;

public class Action {
    private int x;
    private int y;

    public Action(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean possible(int N){
        return this.x >= 0 && this.x < N && this.y >= 0 && this.y < N;
    }

    public boolean libreEtPossible(int[][] M){
        return possible(M.length) && M[this.x][this.y] == 0;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object o) {
        Action action = (Action) o;
        return this.x == action.x && this.y == action.y;
    }

}

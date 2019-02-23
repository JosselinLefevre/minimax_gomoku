package com.company;

import java.util.ArrayList;

public class Action {
    private int x;
    private int y;
    // Liste de toutes les actions jouées depuis le début de la partie
    public static ArrayList<Action> PLAYED_ACTIONS;

    // Constructeur
    public Action(int x, int y){
        this.x = x;
        this.y = y;
    }

    // Accesseurs
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    /**
     * @param N la Position
     * @return true si les coordonnées de l'action sont possible pour la position, false sisnon.
     */
    public boolean possible(int N){
        return this.x >= 0 && this.x < N && this.y >= 0 && this.y < N;
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

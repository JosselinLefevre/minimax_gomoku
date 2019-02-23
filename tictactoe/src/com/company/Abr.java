package com.company;

import java.util.ArrayList;

public class Abr {

    private int utilite = 0;
    private Action action;
    private ArrayList<Abr> children;

    // Constructeurs
    public Abr() {
        this.children = new ArrayList<>();
    }

    public Abr(Action action) {

        this.action = action;
        this.children = new ArrayList<>();
    }

    public Abr(Action action, int[][] A, int utilite) {
        this.action = action;
        this.children = new ArrayList<>();
        this.utilite = utilite;
    }

    // Accesseurs
    public Action getAction() {
        return action;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public ArrayList<Abr> getChildren() {
        return children;
    }

    public int getUtilite() {
        return utilite;
    }

    //Mutateurs
    public void setUtilite(int utilite) {
        this.utilite = utilite;
    }

    public Abr setChildren(ArrayList<Abr> children) {
        this.children = children;
        return this;
    }

    /**
     * @param M Position courrante
     * @param arbre Racine de l'arbre
     * @param A Nombre de cases à aligner pour gagner
     * @param depth Profondeur de l'arbre
     * @return Le noeud avec ses enfants calculés jusqu'à la profondeur depth
     */
    public static Abr createAbr(int[][] M, int A, Abr arbre, int depth) {
        return arbre.setChildren(createChildren(M, A, Action.PLAYED_ACTIONS, true, depth));
    }

    /**
     * Les enfants sont calculés jusqu'à ce que leur position comporte un vainqueur. Si la position calculée ne
     * comporte pas de vainqueur, alors nous calculons ses enfants tant que la profondeur est différente de 0.
     * Chaque noeud est associé a son utilité.
     *
     * @param M La position du noeud précédant
     * @param A Nombre de cases à aligner pour gagner
     * @param playedActions Toutes les actions jouées pour le noeud précédent
     * @param isMax true si le noeud précédent est un noeud max, false sinon
     * @param depth Profondeur courrante
     * @return La liste des noeuds successeurs du noeud précédent
     */
    private static ArrayList<Abr> createChildren(int[][] M, int A, ArrayList<Action> playedActions, boolean isMax, int depth) {
        int u;
        int[] al;
        ArrayList<Abr> children = new ArrayList<>();
        ArrayList<Action> newPlayedActions = new ArrayList<>();
        ArrayList<Action> eligibleActions = BoardUtils.casesEligibles(playedActions, M.length);

        if (depth != 0) {
            depth--;
            for (Action action : eligibleActions) {
                newPlayedActions.clear();
                newPlayedActions.addAll(playedActions);
                newPlayedActions.add(action);
                if (M[action.getX()][action.getY()] == 0) {
                    int T[][] = BoardUtils.createChildAbr(M, action, isMax);
                    al = Game.align(T, isMax ? 2 : 1, action.getX(), action.getY());
                    u = Game.eval(al, isMax, A);

                    /*System.out.println("-----------------------------");
                    BoardUtils.affichePosition(T);
                    System.out.println("Coords : " + action.toString() + "\nJoueur courrant " + isMax + " : "
                            + Arrays.toString(al) + "\nUtilite : " + u);
                    System.out.println("-----------------------------");*/

                    if (u != 0)
                        children.add(new Abr(action, T, u));
                    else
                        children.add(new Abr(action).setChildren(createChildren(T, A, newPlayedActions, !isMax, depth)));
                }
            }
        }
        return children;
    }

}


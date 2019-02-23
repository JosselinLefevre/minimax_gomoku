package com.company;

import java.util.ArrayList;

public class BoardUtils {

    /**
     * Initialise toutes les cases de la marice donnée en paramètre à la valeur '0'
     * @param A Position
     */
    public static void initPosition(int[][] A) {
        int i, j;
        for (i = 0; i < A.length; i++)
            for (j = 0; j < A[i].length; j++)
                A[i][j] = 0;
    }

    /**
     * @param A Position
     * @return True s'il ne reste plus de case, false sinon
     */
    public static boolean plusDeCasesLibres(int[][] A) {
        int i, j;
        for (i = 0; i < A.length; i++)
            for (j = 0; j < A[i].length; j++)
                if (A[i][j] == 0) return false;

        return true;
    }

    /**
     * Affiche la position sous la forme d'un plateau de jeu.
     * La valeur 0 correspond à une case vide, 1 à une croix, 2 à un rond et 3 à une valeur de debuggage
     * @param A Position
     */
    public static void affichePosition(int[][] A) {
        int i, j;
        for (i = 0; i < A.length; i++) {
            System.out.print("|");
            for (j = 0; j < A[i].length; j++) {
                switch (A[i][j]) {
                    case 1:
                        System.out.print("X");
                        break;
                    case 2:
                        System.out.print("O");
                        break;
                    case 3:
                        System.out.print("A");
                        break;
                    default:
                        System.out.print(" ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    /**
     * @param M Position
     * @return Nouvelle matrice identique à la matrice entrée en paramètre
     */
    public static int[][] copyBoard(int[][] M) {
        int[][] T = new int[M.length][M.length];
        int i, j;
        for (i = 0; i < M.length; i++)
            for (j = 0; j < M[i].length; j++)
                T[i][j] = M[i][j];
        return T;
    }

    public static boolean joueurCoup(int[][] M, Action action, int joueur) {
        int x = action.getX(), y = action.getY();

        if (M[x][y] != 0) {
            System.out.println("Case déjà occupée");
            return false;
        } else M[x][y] = joueur;

        return true;
    }

    /**
     * @param M Position du noeud précédent
     * @param action Action à réaliser sur la position
     * @param isMax True si le joueur est le joueur max, false sinon
     * @return Une nouvelle position avec l'action jouée
     */
    public static int[][] createChildAbr(int[][] M, Action action, boolean isMax) {
        int[][] T = copyBoard(M);
        joueurCoup(T, action, isMax ? 2 : 1);
        return T;
    }

    /**
     * Nous selectionons uniquement un certain nombre de case selon un critère de proximité pour limiter les calculs.
     * @param actions Liste des actions déjà effectuées sur une position donnée.
     * @param N Taille de la position
     * @return Liste des cases adjacentes aux cases déjà jouées.
     */
    public static ArrayList<Action> casesEligibles(ArrayList<Action> actions, int N) {
        ArrayList<Action> eligibles = new ArrayList<>();
        for (Action a : actions)
            for (int i = a.getX() - 1; i <= a.getX() + 1; i++)
                for (int j = a.getY() - 1; j <= a.getY() + 1; j++) {
                    Action aa = new Action(i, j);
                    if (aa.possible(N) && !eligibles.contains(aa) && !actions.contains(aa))
                        eligibles.add(new Action(i, j));
                }
        return eligibles;
    }
}

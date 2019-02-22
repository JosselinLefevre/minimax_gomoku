package com.company;

import java.util.ArrayList;

public class Main {
    public static int N = 3;
    public static int A = 3;
    public static int[][] M;
    public static Abr root;
    public static ArrayList<Action> actions;

    public static void main(String[] args) {
        actions = new ArrayList<>();
        M = new int[N][N];
        BoardUtils.initPosition(N, M);
        root = new Abr();
        // joueur = 1 & ia = 2

        System.out.println("Coups simulés");

        try {
            jouer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void jouer() {
        ActionScanner as = new ActionScanner();
        Action currentAction;
        for (int i = 0; i < N * N / 2 + 1; i++) {

            do {
                currentAction = as.getPlayerAction(M);
            } while (!BoardUtils.saisirCoup(M, currentAction, 1));

            actions.add(currentAction);
            System.out.println("Coup Joueur : " + currentAction.toString());

            root = Abr.createAbr(M, root, true, 3);

            /*System.out.println("-----------------------------");
            Abr.dispAbr(root);
            System.out.println("-----------------------------");*/

            root = Minimax.valeurMinimax(root);
            actions.add(root.getAction());
            BoardUtils.saisirCoup(M, root.getAction(), 2);
            System.out.println("Coup IA : " + root.getAction().toString());

            // Afficher le coup jouer par IA
            BoardUtils.affichePosition(M);
        }
    }

}

package com.company;

public class Main {
    public static int N = 3;
    private static int A = 0;
    private static int[][] M;
    public static Abr root;

    public static void main(String[] args) {
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

    private static void jouer() throws Exception {
        boolean isMax = false;
        for (int i = 0; i < N * N / 2 + 1; i++) {
            BoardUtils.saisirCoupJoueur(M);
            root = Abr.createAbr(M, isMax, 2);

            System.out.println("-----------------------------");
            Abr.dispAbr(root);
            System.out.println("-----------------------------");

            root = Minimax.valeurMinimax(root);

            jouerCoup(M, root.getAction().getX(), root.getAction().getY());
            // Afficher le coup jouer par IA
            System.out.println("Coup IA : (" + root.getAction().getX() + "," + root.getAction().getY() + ")");
            Thread.sleep(250);
            BoardUtils.affichePosition(M);
            isMax = !isMax;
        }
    }

    private static void jouerCoup(int[][] A, int x, int y) {
        A[x][y] = 2;
    }
}

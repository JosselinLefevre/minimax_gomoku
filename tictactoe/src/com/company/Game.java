package com.company;

import java.util.ArrayList;

public class Game {
    private int N = 5;
    private int A = 4;
    private int depth = 5;
    private int[][] M;
    private Abr root;
    private ActionScanner actionScanner;
    private GameUI gameUI;

    // Constructeur
    public Game() {
        Action.PLAYED_ACTIONS = new ArrayList<>();
        M = new int[N][N];
        BoardUtils.initPosition(M);
        root = new Abr();
        actionScanner = new ActionScanner();
        gameUI = new GameUI(N);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                gameUI.addBouton(i, j, N);


    }

    private Action waitingForPlayer() {
        while (gameUI.waiting)
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return gameUI.getPlayersAction();
    }

    /**
     * Méthode dans laquelle le jeu a lieu
     */
    public void jouer() {
        Action coupJoueur;

        // Plutôt que d'utiliser une fonction qui parcourt tout le tableau, on fait une boucle de 0
        // au nombre d'actions qu'on peut réaliser au maximum.
        for (int i = 0; i < N * N / 2 + 1; i++) {

            // Demande au joueur d'entrer l'action qu'il veut effectuer et l'effectue
            //coupJoueur = coupJoueur();
            coupJoueur = waitingForPlayer();
            BoardUtils.joueurCoup(M, coupJoueur, 1);

            // Si le joueur a gagné on sort dela fonction
            if (checkWin(coupJoueur, 1)) return;

            // On ajoute l'action du joueur à la liste des action qui ont eu lieu durant le jeu.
            Action.PLAYED_ACTIONS.add(coupJoueur);
            System.out.println("Coup Joueur : " + coupJoueur.toString());

            // Calcule un arbre de profondeur depth
            root = Abr.createAbr(M, A, root, depth);

            // Calcule la valeur maximale de l'arbre et la racine prend la valeur du meilleur noeud
            root = Minimax.valeurMinimax(root);

            // Joue le coup de l'IA
            BoardUtils.joueurCoup(M, root.getAction(), 2);

            // On ajoute l'action de l'IA à la liste des action qui ont eu lieu durant le jeu.
            Action.PLAYED_ACTIONS.add(root.getAction());
            System.out.println("Coup IA : " + root.getAction().toString());

            gameUI.setBouton(root.getAction(), 2);

            // Afficher la position courrante
            System.out.println("Current board\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            BoardUtils.affichePosition(M);

            // Si l'IA a gagné, on sort de la fonction
            if (checkWin(root.getAction(), 2)) return;
        }
        System.out.println("Egalite");
    }

    /**
     * Demande une action au joueur tant que l'action entrée n'est pas valide puis retourel'action validée
     *
     * @return Action saisie par le joueur
     */
    private Action coupJoueur() {
        Action currentAction;
        do {
            currentAction = actionScanner.getPlayerAction();
        } while (!BoardUtils.joueurCoup(M, currentAction, 1));
        return currentAction;
    }

    /**
     * Check si le joueur courrant à gagné grâce à sa dernière action.
     * Si un des deux joueur a gagné, affiche un message.
     *
     * @param action Dernière action du joueur
     * @param joueur Index du joueur
     * @return True s'il y a un vainqeur, false sinon
     */
    private boolean checkWin(Action action, int joueur) {
        if (gagne(align(M, joueur, action.getX(), action.getY()), A, joueur) == joueur) {
            System.out.println(joueur == 1 ? "Gagné ! " : "Perdu !");
            return true;
        }
        return false;
    }

    /**
     * @param al           Liste du nombre de cases alignées issu de la methode "align"
     * @param nbAlignement Nombre de case à aligner pour gagner
     * @param joueur       Index du joueur
     * @return L'index du joueur s'il a aligné assez de case, 0 sinon
     */
    public static int gagne(int[] al, int nbAlignement, int joueur) {
        for (int c : al) {
            if (c == nbAlignement && joueur == 1) return 1;
            else if (c == nbAlignement && joueur == 2) return 2;
        }
        return 0;
    }

    /**
     * @param P      Position
     * @param joueur Index du joueur
     * @param x      Coordonnée en ordonnée
     * @param y      Coordonnée en abscisse
     * @return Liste du nombre de cases jouées par le même joueur dans l'ordre suivant
     * - droite-gauche</br>
     * - haut-bas</br>
     * - diagonale nord ouest - sud est</br>
     * - diagonale nord est - sud ouest</br>
     */
    public static int[] align(int[][] P, int joueur, int x, int y) {
        int dg = 0;
        int hb = 0;
        int nose = 0;
        int neso = 0;

        // gauche
        for (int i = y; i > 0; i--)
            if (((P[x][i - 1]) == joueur) && P[x][i] == joueur)
                dg++;


        // droite
        for (int i = y; i < P.length - 1; i++)
            if (P[x][i] == joueur && P[x][i + 1] == joueur)
                dg++;

        // haut
        for (int i = x; i > 0; i--)
            if (P[i][y] == joueur && P[i - 1][y] == joueur)
                hb++;

        // bas
        for (int i = x; i < P.length - 1; i++)
            if (P[i][y] == joueur && P[i + 1][y] == joueur)
                hb++;

        // no
        for (int i = x, j = y; i > 0 && j > 0; i--, j--)
            if (P[i][j] == joueur && P[i - 1][j - 1] == joueur)
                nose++;

        // se
        for (int i = x, j = y; i < P.length - 1 && j < P.length - 1; i++, j++)
            if (P[i][j] == joueur && P[i + 1][j + 1] == joueur)
                nose++;

        // ne
        for (int i = x, j = y; i > 0 && j < P.length - 1; i--, j++)
            if (P[i][j] == joueur && P[i - 1][j + 1] == joueur)
                neso++;

        // so
        for (int i = x, j = y; i < P.length - 1 && j > 0; i++, j--)
            if (P[i][j] == joueur && P[i + 1][j - 1] == joueur)
                neso++;

        int[] al = {dg, hb, nose, neso};
        for (int i = 0; i < al.length; i++) {
            if (al[i] != 0) al[i] += 1;
        }

        return al;
    }

    /**
     * @param al           Liste du nombre de cases alignées issu de la methode "align"
     * @param isMax        True si le joueur est le joueur max, false sinon
     * @param nbAlignement Nombre de case à aligner pour gagner
     * @return 1000 si le joueur max gagne, -1000 s'il perd et 0 sinon
     */
    public static int eval(int[] al, boolean isMax, int nbAlignement) {
        for (int i = 0; i < 4; i++)
            if (al[i] == nbAlignement) return isMax ? 1000 : -1000;
        return 0;
    }
}

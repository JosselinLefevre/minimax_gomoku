package com.company;

import java.util.ArrayList;
import java.util.Arrays;

import static com.company.Main.N;

public class Abr {

    private int utilite = -1;
    private Action action;
    private ArrayList<Abr> children;
    private int[][] M;

    public Abr() {
        this.children = new ArrayList<>();
        this.M = new int[N][N];
    }

    public Abr(Action action, int[][] A) {

        this.action = action;
        this.children = new ArrayList<>();
        this.M = A;
    }

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

    public int[][] getBoard() {
        return M;
    }

    public void setUtilite(int utilite) {
        this.utilite = utilite;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Abr setChildren(ArrayList<Abr> children) {
        this.children = children;
        return this;
    }

    public void setBoard(int[][] board) {
        M = board;
    }

    public static void dispAbr(Abr a) {
        BoardUtils.affichePosition(a.getBoard());
        if (a.utilite != -1) System.out.println("Utilite " + a.utilite + "\n");
        else System.out.println();
        if (a.hasChildren()) {
            for (Abr aa : a.getChildren()) {
                dispAbr(aa);
            }
        }
    }

    public static Abr createAbr(int[][] M, Abr arbre, boolean isMax, int depth) {
        arbre.setBoard(M);

        return arbre.setChildren(createChildren(M, casesEligibles(Main.actions, M.length), isMax, depth));
    }

    private static ArrayList<Abr> createChildren(int[][] M, ArrayList<Action> actions, boolean isMax, int depth) {
        int i, j;
        int[] alIA, alJoueur;

        ArrayList<Abr> a = new ArrayList<>();
        if (depth != 0) {
            depth--;
            for (i = 0; i < M.length; i++)
                for (j = 0; j < M[i].length; j++)
                    if (actions.contains(new Action(i, j))) {
                        int[][] T = BoardUtils.copyBoard(M);
                        if (T[i][j] == 0) {
                            T[i][j] = isMax ? 2 : 1;
                            BoardUtils.affichePosition(T);
                            System.out.println();
                            actions.add(new Action(i, j));
                            a.add(new Abr(new Action(i, j), T).setChildren(createChildren(T, actions, !isMax, depth)));
                        }
                    }
        }
        if (depth == 0)
            for (Abr aa : a) {
                System.out.println("Current Board");
                BoardUtils.affichePosition(aa.getBoard());
                alIA = align(aa.getBoard(), 2, aa.getAction().getX(), aa.getAction().getY());
                alJoueur = align(aa.getBoard(), 1, aa.getAction().getX(), aa.getAction().getY());
                aa.setUtilite(eval(aa.getBoard(), alIA, alJoueur, aa.getAction(), Main.A));
                System.out.println("-----------------------------");
                System.out.println("Coords : (" + aa.getAction().getX() + ", " + aa.getAction().getY() + ")\n" +
                        "IA : " + Arrays.toString(alIA) + "\nJoueur : " + Arrays.toString(alJoueur)
                        + "\nUtilité : " + aa.getUtilite());
                System.out.println("-----------------------------");
            }
        return a;
    }

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

    public static int[] align(int[][] P, int joueur, int x, int y) {

        int dg = 0;
        int hb = 0;
        int nose = 0;
        int neso = 0;

        // gauche
        for (int i = y; i > 0; i--)
            if (P[x][i - 1] == joueur && P[x][i] == joueur)
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

    public int gagne(int[] al, int nbAlignement, int joueur) {
        for (int c : al) {
            if (c == nbAlignement && joueur == 1) return 1;
            else if (c == nbAlignement && joueur == 2) return 2;
        }
        return 0;
    }

    public static int eval(int[][] P, int[] alIA, int[] alJoueur, Action action, int nbAlignement) {
        int maxAI = 0, maxJoueur = 0, avantage, max, index = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0,
                x = action.getX(), y = action.getY();

        for (int i = 0; i < alJoueur.length; i++) {
            if (maxAI < alIA[i]) {
                maxAI = alIA[i];
                index = i;
            }
            if (maxJoueur < alJoueur[i]) {
                maxJoueur = alJoueur[i];
                index = i;
            }
        }

        avantage = maxAI > maxJoueur ? 2 : 1;
        max = avantage == 2 ? maxAI : maxJoueur;

        switch (index) {
            case 0:
                x1 = x;
                y1 = y - 1;
                x2 = x;
                y2 = y + 1;
                break;
            case 1:
                x1 = x - 1;
                y1 = y;
                x2 = x + 1;
                y2 = y;
                break;
            case 2:
                x1 = --x;
                y1 = --y;
                x2 = ++x;
                y2 = ++y;
                break;
            case 3:
                x1 = --x;
                y1 = ++y;
                x2 = ++x;
                y2 = --y;
                break;
        }


        if (maxAI == nbAlignement - 2 && new Action(x1, y1).libreEtPossible(P) && new Action(x2, y2).libreEtPossible(P))
            return 1000;
        else if (maxAI == nbAlignement - 1 && (new Action(x1, y1).libreEtPossible(P) || new Action(x2, y2).libreEtPossible(P)))
            return 1000;
        if (maxJoueur == nbAlignement - 2 && new Action(x1, y1).libreEtPossible(P) && new Action(x2, y2).libreEtPossible(P))
            return -1000;
        else if (maxJoueur == nbAlignement - 1 && (new Action(x1, y1).libreEtPossible(P) || new Action(x2, y2).libreEtPossible(P)))
            return -1000;
        else {
            return avantage == 2 ? scoreContinuite(alIA) : scoreContinuite(alJoueur);
        }
    }

    public static int scoreContinuite(int[] al) {
        int score = 0;
        for (int s : al) {
            score += s * 250 / Main.N;
        }
        return score;
    }
}


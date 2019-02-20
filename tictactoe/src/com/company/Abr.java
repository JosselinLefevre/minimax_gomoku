package com.company;

import java.util.ArrayList;
import java.util.Random;

import static com.company.Main.N;

public class Abr {

    private int utilite = -1;
    private Action action;
    private ArrayList<Abr> children;
    private int[][] M;

    public Abr(){
        this.children = new ArrayList<>();
        this.M = new int[N][N];
    }

    public Abr(Action action) {

        this.action = action;
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

    public boolean hasChildren(){
        return !children.isEmpty();
    }

    public ArrayList<Abr> getChildren(){ return children; }

    public int getUtilite(){ return utilite; }

    public int[][] getBoard() { return M; }

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

    public void setBoard(int[][] board) { M = board; }

    public static void dispAbr(Abr a){
        BoardUtils.affichePosition(a.getBoard());
        if (a.utilite != -1) System.out.println("Utilite " + a.utilite + "\n");
        else System.out.println();
        if (a.hasChildren()){
            for (Abr aa : a.getChildren()) {
                dispAbr(aa);
            }
        }
    }

    public static Abr createAbr(int[][] M, boolean isMax, int depth){
        Abr arbre = new Abr( null);
        arbre.setBoard(M);

        return arbre.setChildren(createChildren(M, depth, !isMax));
    }

    private static ArrayList<Abr> createChildren(int[][] M, int depth, boolean isMax) {
        int i, j;

        ArrayList<Abr> a = new ArrayList<>();
        if (depth != 0) {
            depth--;
            for (i = 0; i < M.length; i++)
                for (j = 0; j < M[i].length; j++)
                    if (M[i][j] == 0) {
                        int[][] T = BoardUtils.copyBoard(M);
                        T[i][j] = isMax ? 2 : 1 ;
                        a.add(new Abr(new Action(i, j), T).setChildren(createChildren(T, depth, !isMax)));
                    }
        }
        if (depth == 0)
            for (Abr aa : a) {
                aa.setUtilite(new Random().nextInt(1000));
            }
        return a;
    }
}


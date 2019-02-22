package com.company;

import java.util.Scanner;

public class BoardUtils {

    public static void initPosition(int n, int[][] A) {
        int i, j;
        for (i = 0; i < A.length; i++)
            for (j = 0; j < A[i].length; j++)
                A[i][j] = 0;
    }

    public static int plusDeCasesLibres(int[][] A) {
        int i, j;
        for (i = 0; i < A.length; i++)
            for (j = 0; j < A[i].length; j++)
                if (A[i][j] == 0) return 0;

        return 1;
    }

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

    public static int[][] copyBoard(int[][] M) {
        int[][] T = new int[M.length][M.length];
        int i, j;
        for (i = 0; i < M.length; i++)
            for (j = 0; j < M[i].length; j++)
                T[i][j] = M[i][j];
        return T;
    }

    public static boolean saisirCoup(int[][] M, Action action, int joueur){
        int x = action.getX(), y = action.getY();

        if (M[x][y] != 0){
            System.out.println("Case déjà occupée");
            return false;
        }else  M[x][y] = joueur;

        return true;
    }
}

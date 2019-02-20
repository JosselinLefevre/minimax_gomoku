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

    public static int[][] saisirCoupJoueur(int[][] A) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Saisir coordonnées -> x:y");
        String s = sc.nextLine();

        int x = 0, y = 0;

        try {
            x = Integer.parseInt(s.substring(0, 1));
            y = Integer.parseInt(s.substring(2, 3));
        } catch (Exception e) {
            throw new Exception("Error");
        }

        if (A[x][y] != 0) throw new Exception("Error");

        A[x][y] = 1;
        System.out.println("Coup Joueur : (" + x + "," + y + ")");
        return A;
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
}

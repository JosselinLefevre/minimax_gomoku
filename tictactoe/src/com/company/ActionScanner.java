package com.company;

import java.util.Scanner;

public class ActionScanner {

    private Scanner sc;

    public ActionScanner() {
        sc = new Scanner(System.in);
    }

    public Action getPlayerAction(int[][] A) {
        int x = 0, y = 0;
        System.out.println("Saisir coordonnées -> x:y");
        String s = sc.nextLine();

        try {
            x = Integer.parseInt(s.substring(0, 1));
            y = Integer.parseInt(s.substring(1, 2));
        } catch (Exception e) {
            return getPlayerAction(A);
        }

        return new Action(x, y);
    }


}

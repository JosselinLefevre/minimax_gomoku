package com.company;

import java.util.Scanner;

public class ActionScanner {
    private Scanner sc;

    // Constructeur
    public ActionScanner() {
        sc = new Scanner(System.in);
    }

    /**
     * Tant que l'entrée est incorrecte, demande une action au joueur
     * @return Action entrée par le joueur
     */
    public Action getPlayerAction() {
        int x = 0, y = 0;
        System.out.println("Saisir coordonnées -> x:y");
        String s = sc.nextLine();

        try {
            x = Integer.parseInt(s.substring(0, 1));
            y = Integer.parseInt(s.substring(2, 3));
        } catch (Exception e) {
            System.out.println("Entrée incorrecte");
            return getPlayerAction();
        }

        return new Action(x, y);
    }


}

package com.company;

public class Minimax {

    public static Abr valeurMinimax(Abr a){
        int i = tourMax(a);
        System.out.println("Minimax value : " + i);
        for (Abr aa: a.getChildren()) {
            if (aa.getUtilite() == i) a = aa;
        }
        return a;
    }

    private static int tourMax(Abr a){
        if (!a.hasChildren()) return a.getUtilite();
        int u = -1001;

        for (Abr aa : a.getChildren()) {
            if (tourMin(aa) > u) {
                u = aa.getUtilite();
            }
        }

        a.setUtilite(u);
        return u;
    }

    private static int tourMin(Abr a){
        if (!a.hasChildren()) return a.getUtilite();
        int u = 1001;

        for (Abr aa : a.getChildren()) {
            if (tourMax(aa) < u) {
                u = aa.getUtilite();
            }
        }

        a.setUtilite(u);
        return u;
    }


}

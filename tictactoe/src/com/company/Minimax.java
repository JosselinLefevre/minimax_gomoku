package com.company;

public class Minimax {

    /**
     * @param a Noeud de l'arbre
     * @return L'abre qui à été choisis comme meilleur choix après avoir appelé tourMax
     */
    public static Abr valeurMinimax(Abr a) {
        int i = tourMax(a);
        System.out.println("Minimax value : " + i);
        for (Abr aa : a.getChildren())
            if (aa.getUtilite() == i) a = aa;

        return a;
    }

    /**
     * Si le noeud est une feuille, retourne son utilité, sinon cherche le maximum de l'utilité de tous
     * les noeuds successeurs.
     * @param a Noeud pour lequel on cherche la valeur tour max
     * @return Valeur du noeud à l'étage max
     */
    private static int tourMax(Abr a) {
        if (!a.hasChildren()) return a.getUtilite();
        int u = -1001;

        for (Abr aa : a.getChildren())
            if (tourMin(aa) > u)
                u = aa.getUtilite();
        a.setUtilite(u);
        return u;
    }

    /**
     * Si le noeud est une feuille, retourne son utilité, sinon cherche le minimum de l'utilité de tous
     * les noeuds successeurs.
     * @param a Noeud pour lequel on cherche la valeur tour min
     * @return Valeur du noeud à l'étage min
     */
    private static int tourMin(Abr a) {
        if (!a.hasChildren()) return a.getUtilite();
        int u = 1001;

        for (Abr aa : a.getChildren())
            if (tourMax(aa) < u)
                u = aa.getUtilite();
        a.setUtilite(u);
        return u;
    }


}

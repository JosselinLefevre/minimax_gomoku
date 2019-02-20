import java.util.Scanner;

public class Game {
    public int N;
    public int A;
    public int[][] P;

    public Game(int nbCases, int nbAlignement){
        Scanner sc = new Scanner(System.in);
        N = nbCases;
        A = nbAlignement;
        P = new int[N][N];
        initPosition(N,P);
        while(plusDeCasesLibres(N,P)==0){
            saisirCoupJoueur(N, P, sc);
            affichePosition(N, P);
            saisirCoupIA(N, P);
            affichePosition(N, P);
        }
        affichePosition(N,P);
        sc.close();
    }


    public void initPosition(int N, int[][] P){
        for(int i = 0 ; i<N ; i++){
            for(int j = 0 ; j<N; j++){
                P[i][j] = 0;
            }
        }
    }

    public void affichePosition(int N, int[][] P){
        for(int j = 0 ; j<N ; j++){
            System.out.print("|");
            for(int i = 0 ; i<N; i++){
                switch(P[i][j]){
                    case 1 :
                        System.out.print("O");
                        break;
                    case 2 :
                        System.out.print("X");
                        break;
                    default :
                        System.out.print(" ");
                        break;
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    public int plusDeCasesLibres(int N, int[][] P){
        for(int i = 0 ; i<N ; i++){
            for(int j = 0 ; j<N; j++){
                if(P[i][j] == 0) return 0;
            }
        }
        return 1;
    }

    public void saisirCoupJoueur(int N, int[][] P, Scanner sc){
        int x = 0, y = 0;
        boolean erreur = false;
        String xSaisi, ySaisi;
        do {
            try {
                System.out.println("Saisir coordonnées : ");

                System.out.print("x : ");
                xSaisi = sc.nextLine();

                System.out.print("y : ");
                ySaisi = sc.nextLine();
                try {
                    x = Integer.parseInt(xSaisi);
                    y = Integer.parseInt(ySaisi);
                    if(x<0 || x>=N || y<0 || y>=N || P[x][y] != 0){
                        throw new Exception("Erreur");
                    }
                    else{
                        erreur = false;
                    }
                } catch(Exception e){
                    throw new Exception("Erreur");
                }
                System.out.println("Coup Joueur : ("+x+","+y+")");

            }catch(Exception e){
                erreur = true;
            }
        } while(erreur);
        P[x][y] = 1;
    }

    public void saisirCoupIA(int N, int[][] P){
        int x = 0;
        int y = 0;
        for(int i = 0 ; i<N ; i++){
            for(int j = 0 ; j<N; j++){
                if(P[i][j] == 0){
                    x=i;
                    y=j;
                }
            }
        }
        P[x][y] = 2;
        System.out.println("Coup IA : ("+x+","+y+")");
    }
}

#include <stdio.h>
#include <time.h>
#include <stdlib.h>


typedef struct Alignement{
	int dg;
	int hb;
	int NoSe;
	int NeSo;	
} Alignement;

typedef struct Case{
	int x;
	int y;
	int valeur;
	Alignement a;
} Case;

void evalPosition(int N, int (*P)[N], int A, Case *c, int joueur) {
	align(N,P,c,joueur);
	if(c->a.dg == A-1 || c->a.hb == A-1 || c->a.NoSe == A-1 || c->a.NeSo == A-1){
		c->valeur = 1000;
	}  	
}


void align(int N, int (*P)[N], Case *c, int joueur){
	int x = c->x;
	int y = c->y;
	
	// a gauche
	while(x > 0 && P[x-1][y] == joueur){
		c->a.dg++;
		x--;
	}

	x = c->x;
	y = c->y;
	// a droite
	while(x < N-1 && P[x+1][y] == joueur){
		c->a.dg++;
		x++;
	}

	x = c->x;
	y = c->y;
	// en haut
	while(y > 0 && P[x][y-1] == joueur){
		c->a.hb++;
		y--;
	}

	x = c->x;
	y = c->y;
	// en bas
	while(y < N-1 && P[x][y+1] == joueur){
		c->a.hb++;
		y++;
	}

	x = c->x;
	y = c->y;
	// No
	while(x > 0 && y > 0 && P[x-1][y-1] == joueur){
		c->a.NoSe++;
		x--;
		y--;
	}

	x = c->x;
	y = c->y;
	// Se
	while(x < N-1 && y < N-1 && P[x+1][y+1] == joueur){
		c->a.NoSe++;
		x++;
		y++;
	}

	x = c->x;
	y = c->y;
	// Ne
	while(x < N-1 && y > 0 && P[x+1][y-1] == joueur){
		c->a.NeSo++;
		x++;
		y--;
	}
	
	x = c->x;
	y = c->y;
	// So
	while(x > 0 && y < N-1 && P[x-1][y+1] == joueur){
		c->a.NeSo++;
		x--;
		y++;
	}
	printf("dg = %d ; hb = %d ; NoSe = %d ; NeSo = %d ;\n", c->a.dg, c->a.hb , c->a.NoSe, c->a.NeSo);
	
}

void initPosition(int N, int (*P)[N]){
	for(int i = 0 ; i<N ; i++){
		for(int j = 0 ; j<N; j++){
		P[i][j] = 0;
		}	
	}
}

void affichePosition(int N, int (*P)[N]){
	for(int j = 0 ; j<N ; j++){
		printf("|");
		for(int i = 0 ; i<N; i++){
			switch(P[i][j]){
				case 1 : 
					printf("O");
				break;	
				case 2 : 
					printf("X");
				break;	
				default : 
					printf(" ");
				break;		
			}
		printf("|");
		}
	printf("\n");	
	}
} 

int plusDeCasesLibres(int N, int (*P)[N]){
	for(int i = 0 ; i<N ; i++){
		for(int j = 0 ; j<N; j++){
			if(P[i][j] == 0) return 0;
		}	
	}
	return 1;	
}

void saisirCoupJoueur(int N, int (*P)[N]){
	int x;
	int y;
	char* c = "S";
	do{
		printf("%saisir coordonnées x : y \n", c);
		scanf("%d : %d", &x, &y);
		c = "Ress";
	}while(P[x][y] != 0);
	P[x][y] = 1;
}

void saisirCoupIA(int N, int (*P)[N]){
	int x;
	int y;
	do{
		x = 1;
		y = 2;
	}while(P[x][y] != 0);
	P[x][y] = 2;
}

int main(){
	int N = 3;
	int A = 3;
	int P[N][N];
	initPosition(N,P);
	
	/*while(plusDeCasesLibres(N,P)==1){
		saisirCoupJoueur(N, P);
		affichePosition(N,P);
		saisirCoupIA(N, P);
		affichePosition(N,P);
	}*/
	P[0][1] = 2;
	P[0][2] = 2;
	P[2][0] = 2;
	P[2][2] = 2;

	affichePosition(N,P);

	Case c;
	c.x = 1;
	c.y = 1;
	c.a.dg = 0;
	c.a.hb = 0;
	c.a.NoSe = 0;
	c.a.NeSo = 0;
	align(N, P, &c, 2);


	return 0;
}

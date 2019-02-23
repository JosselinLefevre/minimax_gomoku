package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameUI extends JFrame implements ActionListener {
    public JPanel panel;
    public JButton Cases[][];
    public int N;
    public int maxSize;
    private Action playersAction;
    public boolean waiting;

    public GameUI(int N) {
        super();
        this.N = N;
        this.waiting = true;
        this.maxSize = 666;
        this.panel = new JPanel();
        this.panel.setLayout(new GridLayout(N, N, 2, 2));

        build();//On initialise notre fenêtre
        this.setVisible(true);//On la rend visible
        this.Cases = new JButton[N][N];
    }

    public void actionPerformed(ActionEvent e) {
        if (waiting) {
            Object source = e.getSource();
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (source == Cases[i][j]) {
                        setBouton(i, j, 1);
                        playersAction = new Action(i, j);
                        this.waiting = false;
                        break;
                    }
        }
    }

    public Action getPlayersAction() {
        return playersAction;
    }

    public void setBouton(int i, int j, int joueur) {
        if (joueur == 1) {
            Cases[i][j].setText("X");
            Cases[i][j].setEnabled(false);
        } else if (joueur == 2) {
            Cases[i][j].setText("O");
            Cases[i][j].setEnabled(false);
            this.waiting = true;
        } else
            Cases[i][j].setText("");
        System.out.println("Bouton : (" + i + ", " + j + ")");
    }

    public void setBouton(Action action, int joueur){
        setBouton(action.getX(), action.getY(), joueur);
    }

    public void addBouton(int j, int i, int N) {
        Cases[i][j] = new JButton();
        Cases[i][j].addActionListener(this);
        Cases[i][j].setFont(new Font("Arial", Font.PLAIN, maxSize / 2 / N));
        panel.add(Cases[i][j]);
    }

    public void block() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Cases[i][j].setEnabled(false);
            }
        }
    }

    private void build() {
        setTitle("Tic Tac Toe"); //On donne un titre à l'application
        setSize(maxSize, maxSize); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
        setContentPane(panel);
    }
}
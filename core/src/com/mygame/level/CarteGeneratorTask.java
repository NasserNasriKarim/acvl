package com.mygame.level;

import java.util.Random;

/**
 * La classe CarteGeneratorTask représente une tâche de génération de carte qui peut être exécutée dans un thread séparé.
 * Elle creuse des chemins dans une section de la carte en utilisant un algorithme de génération de labyrinthe.
 */
public class CarteGeneratorTask implements Runnable {
    private int startX;
    private int startY;
    private int width;
    private int height;
    private char[][] carte;
    private Random random;

    /**
     * Constructeur de la classe CarteGeneratorTask.
     * Initialise les paramètres de la tâche de génération de carte.
     *
     * @param startX Le point de départ en X pour la génération du chemin.
     * @param startY Le point de départ en Y pour la génération du chemin.
     * @param width  La largeur de la section de la carte à générer.
     * @param height La hauteur de la section de la carte à générer.
     * @param carte  La carte à modifier.
     */
    public CarteGeneratorTask(int startX, int startY, int width, int height, char[][] carte) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.carte = carte;
        this.random = new Random();
    }

    /**
     * Méthode exécutée lorsque la tâche est lancée dans un thread.
     * Démarre la génération du chemin à partir du point de départ spécifié.
     */
    @Override
    public void run() {
        creuserChemin(startX, startY);
    }

    /**
     * Creuse des chemins à partir des coordonnées spécifiées en utilisant un algorithme de génération de labyrinthe.
     *
     * @param x La coordonnée X de départ.
     * @param y La coordonnée Y de départ.
     */
    private void creuserChemin(int x, int y) {
        if (!estDansLesLimites(x, y)) return;

        int[][] directions = {{2, 0}, {-2, 0}, {0, 2}, {0, -2}};
        melangerTableau(directions);

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (estDansLesLimites(newX, newY) && carte[newY][newX] == '#') {
                carte[newY][newX] = ' ';
                carte[y + dir[1] / 2][x + dir[0] / 2] = ' ';
                creuserChemin(newX, newY);
            }
        }
    }

    /**
     * Mélange les éléments d'un tableau de directions pour générer des chemins aléatoires.
     *
     * @param array Le tableau de directions à mélanger.
     */
    private void melangerTableau(int[][] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    /**
     * Vérifie si les coordonnées spécifiées sont dans les limites de la carte.
     *
     * @param x La coordonnée X à vérifier.
     * @param y La coordonnée Y à vérifier.
     * @return true si les coordonnées sont dans les limites, sinon false.
     */
    private boolean estDansLesLimites(int x, int y) {
        return x > 0 && x < width - 1 && y > 0 && y < height - 1;
    }
}

package com.mygame.level;

/**
 * La classe Position représente la position d'un objet dans le jeu.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Constructeur de la classe Position.
     * Initialise la position avec des coordonnées x et y.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne la coordonnée x de la position.
     *
     * @return La coordonnée x.
     */
    public int getX() {
        return x;
    }

    /**
     * Définit la coordonnée x de la position.
     *
     * @param x La nouvelle coordonnée x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retourne la coordonnée y de la position.
     *
     * @return La coordonnée y.
     */
    public int getY() {
        return y;
    }

    /**
     * Définit la coordonnée y de la position.
     *
     * @param y La nouvelle coordonnée y.
     */
    public void setY(int y) {
        this.y = y;
    }
}

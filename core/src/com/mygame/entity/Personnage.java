package com.mygame.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.level.Position;

/**
 * La classe Personnage représente un personnage dans le jeu, avec des points de vie, des points d'attaque, une position et une texture.
 */
public class Personnage {
    private int pointsDeVie;
    private int pointsDAttaque;
    private Position position;
    private Texture texture;

    /**
     * Constructeur de la classe Personnage.
     * Initialise les attributs de base du personnage.
     *
     * @param pointsDeVie    Le nombre de points de vie du personnage.
     * @param pointsDAttaque Le nombre de points d'attaque du personnage.
     * @param position       La position du personnage.
     * @param texture        La texture du personnage.
     */
    public Personnage(int pointsDeVie, int pointsDAttaque, Position position, Texture texture) {
        this.pointsDeVie = pointsDeVie;
        this.pointsDAttaque = pointsDAttaque;
        this.position = position;
        this.texture = texture;
    }

    /**
     * Déplace le personnage d'un certain nombre de pixels en x et y.
     *
     * @param dx Le déplacement en x.
     * @param dy Le déplacement en y.
     */
    public void deplacer(int dx, int dy) {
        position.setX(position.getX() + dx);
        position.setY(position.getY() + dy);
    }

    /**
     * Rend le personnage sur l'écran.
     *
     * @param batch L'objet SpriteBatch utilisé pour dessiner la texture.
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.getX(), position.getY());
    }

    /**
     * Retourne le nombre de points de vie du personnage.
     *
     * @return Le nombre de points de vie.
     */
    public int getPointsDeVie() {
        return pointsDeVie;
    }

    /**
     * Définit le nombre de points de vie du personnage.
     *
     * @param pointsDeVie Le nombre de points de vie.
     */
    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = pointsDeVie;
    }

    /**
     * Retourne le nombre de points d'attaque du personnage.
     *
     * @return Le nombre de points d'attaque.
     */
    public int getPointsDAttaque() {
        return pointsDAttaque;
    }

    /**
     * Retourne la position du personnage.
     *
     * @return La position du personnage.
     */
    public Position getPosition() {
        return position;
    }
}

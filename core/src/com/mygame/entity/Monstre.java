package com.mygame.entity;

import com.badlogic.gdx.graphics.Texture;
import com.mygame.level.Position;

/**
 * La classe Monstre représente un monstre dans le jeu.
 * Un monstre a des points de vie, des points d'attaque, une position et un état d'action.
 */
public class Monstre extends Personnage {
    private boolean aAgit;

    /**
     * Constructeur de la classe Monstre.
     * Initialise le monstre avec ses attributs de base.
     *
     * @param pointsDeVie    Le nombre de points de vie du monstre.
     * @param pointsDAttaque Le nombre de points d'attaque du monstre.
     * @param position       La position du monstre sur la carte.
     * @param texture        La texture du monstre.
     */
    public Monstre(int pointsDeVie, int pointsDAttaque, Position position, Texture texture, String nom) {
        super(pointsDeVie, pointsDAttaque, position, texture);
        this.aAgit = false;
    }

    /**
     * Indique si le monstre a déjà agi dans ce tour.
     *
     * @return true si le monstre a agi, sinon false.
     */
    public boolean aAgit() {
        return aAgit;
    }

    /**
     * Définit si le monstre a agi dans ce tour.
     *
     * @param aAgit true si le monstre a agi, sinon false.
     */
    public void setAAgit(boolean aAgit) {
        this.aAgit = aAgit;
    }
    
    public void reduirePointsDeVie(int points) {
    	setPointsDeVie(getPointsDeVie() - points);
    }
}

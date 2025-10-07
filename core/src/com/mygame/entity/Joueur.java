package com.mygame.entity;

import com.badlogic.gdx.graphics.Texture;
import com.mygame.exception.EntityInitializationException;
import com.mygame.exception.InventoryOperationException;
import com.mygame.level.Position;

/**
 * La classe Joueur représente un joueur dans le jeu.
 * Un joueur a un nom, un inventaire, des points de vie, des points d'attaque et une position.
 */
public class Joueur extends Personnage {
    private String nom;
    private Inventaire inventaire;

    /**
     * Constructeur de la classe Joueur.
     * Initialise le joueur avec ses attributs de base.
     *
     * @param pointsDeVie    Le nombre de points de vie du joueur.
     * @param pointsDAttaque Le nombre de points d'attaque du joueur.
     * @param position       La position du joueur sur la carte.
     * @param texture        La texture du joueur.
     * @param nom            Le nom du joueur.
     */
    public Joueur(int pointsDeVie, int pointsDAttaque, Position position, Texture texture, String nom) {
    	super(pointsDeVie, pointsDAttaque, position, texture);
        try {
            this.nom = nom;
            this.inventaire = new Inventaire();
        } catch (Exception e) {
            throw new EntityInitializationException("Failed to initialize player", e);
        }
    }

    /**
     * Retourne l'inventaire du joueur.
     *
     * @return L'inventaire du joueur.
     */
    public Inventaire getInventaire() {
        return inventaire;
    }

    /**
     * Retourne le nom du joueur.
     *
     * @return Le nom du joueur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Ajoute des pièces d'or à l'inventaire du joueur.
     *
     * @param pieces Le nombre de pièces d'or à ajouter.
     */
    public void ajouterPiecesOr(int pieces) {
    	try {
            inventaire.ajouterGoldCoins(pieces);
        } catch (Exception e) {
            throw new InventoryOperationException("Impossible d'ajouter les pièces à l'inventaire", e);
        }
    }

    /**
     * Récupère les items et les pièces d'or d'un coffre.
     *
     * @param coffre Le coffre à récupérer.
     */
    public void recupererCoffre(Coffre coffre) {
    	try {
            ajouterPiecesOr(coffre.getGoldCoins());
            for (Item item : coffre.getItems()) {
                inventaire.ajouterItem(item);
            }
        } catch (Exception e) {
            throw new InventoryOperationException("Impossible de fouiller le coffre", e);
        }
    }

    /**
     * Réduit les points de vie du joueur.
     *
     * @param points Le nombre de points de vie à réduire.
     */
    public void reduirePointsDeVie(int points) {
        setPointsDeVie(getPointsDeVie() - points);
    }
    
    public void attaquer(Monstre monstre) {
        monstre.reduirePointsDeVie(getPointsDAttaque());
    }
}

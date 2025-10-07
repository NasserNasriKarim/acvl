package com.mygame.entity;

import com.mygame.level.Niveau;

/**
 * La classe Item représente un objet dans le jeu.
 * Un item peut avoir un nom, une description et peut être spécial.
 */
public abstract class Item {
    private String nom;
    private String description;
    private boolean isSpecial;

    /**
     * Constructeur de la classe Item.
     * Initialise l'item avec un nom, une description et un indicateur spécial.
     *
     * @param nom         Le nom de l'item.
     * @param description La description de l'item.
     * @param isSpecial   Indique si l'item est spécial.
     */
    public Item(String nom, String description, boolean isSpecial) {
        this.nom = nom;
        this.description = description;
        this.isSpecial = isSpecial;
    }

    /**
     * Retourne le nom de l'item.
     *
     * @return Le nom de l'item.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la description de l'item.
     *
     * @return La description de l'item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Indique si l'item est spécial.
     *
     * @return true si l'item est spécial, sinon false.
     */
    public boolean isSpecial() {
        return isSpecial;
    }

    /**
     * Utilise l'item dans le niveau et sur le joueur spécifiés.
     * Cette méthode est conçue pour être redéfinie par les sous-classes.
     *
     * @param niveau Le niveau dans lequel l'item est utilisé.
     * @param joueur Le joueur qui utilise l'item.
     */
    public abstract void utiliser(Niveau niveau, Joueur joueur);

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'item.
     *
     * @return Une chaîne de caractères représentant l'item.
     */
    @Override
    public String toString() {
        return nom + ": " + description;
    }
}

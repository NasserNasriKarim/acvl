package com.mygame.entity;

import java.util.ArrayList;
import java.util.List;

import com.mygame.level.Position;

/**
 * La classe Coffre représente un coffre au trésor dans le jeu.
 * Un coffre peut contenir des items et des pièces d'or et est placé à une position spécifique.
 */
public class Coffre {
    private List<Item> items;
    private Position position;
    private int goldCoins;

    /**
     * Constructeur de la classe Coffre.
     * Initialise la position du coffre et le nombre de pièces d'or qu'il contient.
     *
     * @param position  La position du coffre dans le jeu.
     * @param goldCoins Le nombre de pièces d'or dans le coffre.
     */
    public Coffre(Position position, int goldCoins) {
        this.position = position;
        this.items = new ArrayList<>();
        this.goldCoins = goldCoins;
    }

    /**
     * Ajoute un item au coffre.
     *
     * @param item L'item à ajouter.
     */
    public void ajouterItem(Item item) {
        items.add(item);
    }

    /**
     * Retourne la liste des items dans le coffre.
     *
     * @return La liste des items.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Retourne le nombre de pièces d'or dans le coffre.
     *
     * @return Le nombre de pièces d'or.
     */
    public int getGoldCoins() {
        return goldCoins;
    }

    /**
     * Retourne la position du coffre.
     *
     * @return La position du coffre.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du contenu du coffre.
     *
     * @return Une chaîne de caractères représentant le contenu du coffre.
     */
    @Override
    public String toString() {
        StringBuilder contenu = new StringBuilder("Coffre à " + position + " contient:\n");
        contenu.append("- ").append(goldCoins).append(" pièces d'or\n");
        for (Item item : items) {
            contenu.append("- ").append(item).append("\n");
        }
        return contenu.toString();
    }
}

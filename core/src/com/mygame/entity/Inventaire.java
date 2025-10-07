package com.mygame.entity;

import com.mygame.exception.InventoryOperationException;
import com.mygame.level.Niveau;

/**
 * La classe Inventaire représente l'inventaire d'un joueur, contenant des pièces d'or et des items spéciaux.
 */
public class Inventaire {
    private int goldCoins;
    private Item itemSpecial;

    /**
     * Constructeur de la classe Inventaire.
     * Initialise l'inventaire avec zéro pièces d'or et aucun item spécial.
     */
    public Inventaire() {
        this.goldCoins = 0;
        this.itemSpecial = null;
    }

    /**
     * Retourne le nombre de pièces d'or dans l'inventaire.
     *
     * @return Le nombre de pièces d'or.
     */
    public int getGoldCoins() {
        return goldCoins;
    }

    /**
     * Ajoute des pièces d'or à l'inventaire.
     *
     * @param pieces Le nombre de pièces d'or à ajouter.
     */
    public void ajouterGoldCoins(int pieces) {
    	try {
            this.goldCoins += pieces;
        } catch (Exception e) {
            throw new InventoryOperationException("Erreur dans l'ajout des pièces dans l'inventaire", e);
        }
    }

    /**
     * Retourne l'item spécial dans l'inventaire.
     *
     * @return L'item spécial.
     */
    public Item getItemSpecial() {
        return itemSpecial;
    }

    /**
     * Ajoute un item à l'inventaire.
     * Si l'item est spécial, il remplace l'item spécial actuel.
     *
     * @param item L'item à ajouter.
     */
    public void ajouterItem(Item item) {
    	try {
            if (item.isSpecial()) {
                this.itemSpecial = item;
            } 
        } catch (Exception e) {
            throw new InventoryOperationException("Impossible d'ajouter l'item à l'inventaire", e);
        }
    }

    /**
     * Utilise l'item spécial dans l'inventaire.
     * Applique l'effet de l'item spécial au niveau et au joueur, puis le consomme.
     *
     * @param niveau Le niveau actuel du jeu.
     * @param joueur Le joueur utilisant l'item spécial.
     */
    public void utiliserItemSpecial(Niveau niveau, Joueur joueur) {
    	 try {
             if (itemSpecial != null) {
                 itemSpecial.utiliser(niveau, joueur);
                 itemSpecial = null; // L'item est consommé après utilisation
             }
         } catch (Exception e) {
             throw new InventoryOperationException("Impossible d'utiliser l'item", e);
         }
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du contenu de l'inventaire.
     *
     * @return Une chaîne de caractères représentant le contenu de l'inventaire.
     */
    @Override
    public String toString() {
        StringBuilder contenu = new StringBuilder("Inventaire:\n");
        contenu.append("- ").append(goldCoins).append(" pièces d'or\n");
        if (itemSpecial != null) {
            contenu.append("- ").append(itemSpecial).append("\n");
        } else {
            contenu.append("- Aucun item\n");
        }
        return contenu.toString();
    }
}

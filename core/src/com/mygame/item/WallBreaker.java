package com.mygame.item;

import com.mygame.entity.Item;
import com.mygame.entity.Joueur;
import com.mygame.level.Niveau;

/**
 * La classe WallBreaker représente un item spécial qui détruit les murs autour du joueur.
 */
public class WallBreaker extends Item {

    /**
     * Constructeur de la classe WallBreaker.
     * Initialise l'item avec un nom et une description.
     */
    public WallBreaker() {
        super("Wall Breaker", "Casse les murs autour du joueur", true);
    }

    /**
     * Utilise l'item pour détruire les murs autour du joueur.
     *
     * @param niveau Le niveau dans lequel l'item est utilisé.
     * @param joueur Le joueur utilisant l'item.
     */
    @Override
    public void utiliser(Niveau niveau, Joueur joueur) {
        int joueurX = joueur.getPosition().getX() / niveau.getTileWidth();
        int joueurY = joueur.getPosition().getY() / niveau.getTileHeight();

        // Détruire les murs autour du joueur
        int[][] directions = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

        for (int[] dir : directions) {
            int newX = joueurX + dir[0];
            int newY = joueurY + dir[1];
            if (newX >= 0 && newX < niveau.getCols() && newY >= 0 && newY < niveau.getRows()) {
                if (niveau.getCarte()[newY][newX] == '#') {
                    niveau.getCarte()[newY][newX] = ' ';
                }
            }
        }
        System.out.println("Wall Breaker utilisé : murs détruits autour du joueur.");
    }
}

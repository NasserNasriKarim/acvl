package com.mygame.item;

import com.mygame.entity.Item;
import com.mygame.entity.Joueur;
import com.mygame.level.Niveau;

/**
 * La classe ZeusLightning représente un item spécial qui tue tous les monstres sur la carte et ajoute des points au joueur.
 */
public class ZeusLightning extends Item {

    /**
     * Constructeur de la classe ZeusLightning.
     * Initialise l'item avec un nom et une description.
     */
    public ZeusLightning() {
        super("Zeus Lightning", "Tue tous les monstres sur la carte et ajoute des points au joueur", true);
    }

    /**
     * Utilise l'item pour tuer tous les monstres et ajouter des points au joueur.
     *
     * @param niveau Le niveau dans lequel l'item est utilisé.
     * @param joueur Le joueur utilisant l'item.
     */
    @Override
    public void utiliser(Niveau niveau, Joueur joueur) {
        int pointsAjoutes = niveau.tuerTousLesMonstres();
        joueur.ajouterPiecesOr(pointsAjoutes);
        System.out.println("Zeus Lightning utilisé : tous les monstres ont été tués. " + pointsAjoutes + " pièces d'or ajoutées.");
    }
}

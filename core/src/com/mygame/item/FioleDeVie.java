package com.mygame.item;

import com.mygame.entity.Item;
import com.mygame.entity.Joueur;
import com.mygame.level.Niveau;

public class FioleDeVie extends Item {
    public FioleDeVie() {
        super("Fiole De Vie", "Permet de régénérer 100 points de vie",true);
    }

    @Override
    public void utiliser(Niveau niveau, Joueur joueur) {
        joueur.setPointsDeVie(joueur.getPointsDeVie()+ 100);
        System.out.println("Plante utilisée : +100 PV");
    }
}

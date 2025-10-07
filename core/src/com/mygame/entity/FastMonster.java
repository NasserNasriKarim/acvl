package com.mygame.entity;

import com.badlogic.gdx.graphics.Texture;
import com.mygame.level.Position;

/**
 * La classe FastMonster représente un monstre qui se déplace de 3 cases à chaque fois et a 20 points d'attaque.
 */
public class FastMonster extends Monstre {

    public FastMonster(int pointsDeVie, Position position, Texture texture) {
        super(pointsDeVie, 20, position, texture,"fastmonster");
    }

    @Override
    public void deplacer(int dx, int dy) {
        super.deplacer(dx, dy);
    }
}

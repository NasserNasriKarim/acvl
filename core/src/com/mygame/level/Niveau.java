package com.mygame.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.entity.Coffre;
import com.mygame.entity.Joueur;
import com.mygame.entity.Monstre;
import com.mygame.entity.FastMonster;
import com.mygame.exception.LevelInitializationException;
import com.mygame.exception.ResourceLoadingException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * La classe Niveau représente un niveau du jeu, contenant la carte, les personnages et les objets.
 */
public class Niveau {
    private static final int MAP_COLUMNS = 20;
    private static final int MAP_ROWS = 20;

    private char[][] carte;
    private int rows;
    private int cols;
    private Texture salleTexture;
    private Texture joueurTexture;
    private Texture tresorTexture;
    private Texture monstreTexture;
    private Texture fastmonstreTexture;
    private Texture porteTexture;
    private Texture sortieTexture;
    private Texture murTexture;
    private Joueur joueur;
    private List<Monstre> monstres;
    private List<Coffre> coffres;
    private int tileWidth;
    private int tileHeight;

    /**
     * Constructeur de la classe Niveau.
     * Initialise le niveau avec les textures, la carte, les coffres et les personnages.
     *
     * @param salleTexture    La texture des salles.
     * @param joueurTexture   La texture du joueur.
     * @param tresorTexture   La texture des trésors.
     * @param monstreTexture  La texture des monstres.
     * @param monstreTexture  La texture de l'autre type de monstre.
     * @param porteTexture    La texture des portes.
     * @param sortieTexture   La texture des sorties.
     * @param murTexture      La texture des murs.
     * @param tileWidth       La largeur d'une tuile.
     * @param tileHeight      La hauteur d'une tuile.
     * @param coffres         La liste des coffres dans le niveau.
     * @param carte           La carte du niveau.
     * @param playerName      Le nom du joueur.
     * @param pointsDeVie     Les points de vie du joueur.
     * @param piecesOr        Les pièces d'or du joueur.
     */
    public Niveau(Texture salleTexture, Texture joueurTexture, Texture tresorTexture, Texture monstreTexture, Texture fastmonstreTexture,Texture porteTexture, Texture sortieTexture, Texture murTexture, int tileWidth, int tileHeight, List<Coffre> coffres, char[][] carte, String playerName, int pointsDeVie, int piecesOr) {
        try {
            this.coffres = coffres;
            this.carte = carte;
            this.rows = carte.length;
            this.cols = carte[0].length;
            this.salleTexture = salleTexture;
            this.joueurTexture = joueurTexture;
            this.tresorTexture = tresorTexture;
            this.monstreTexture = monstreTexture;
            this.fastmonstreTexture = fastmonstreTexture;
            this.porteTexture = porteTexture;
            this.sortieTexture = sortieTexture;
            this.murTexture = murTexture;
            this.monstres = new ArrayList<>();
            this.tileWidth = tileWidth;
            this.tileHeight = tileHeight;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    char cell = carte[i][j];
                    if (cell == 'J') {
                        joueur = new Joueur(pointsDeVie, 10, new Position(j * tileWidth, i * tileHeight), joueurTexture, playerName);
                        joueur.ajouterPiecesOr(piecesOr); // Conserver les pièces d'or accumulées
                    } else if (cell == 'M') {
                        monstres.add(new Monstre(10, 5, new Position(j * tileWidth, i * tileHeight), monstreTexture, "Monstre"));
                    } else if (cell == 'F') {
                        monstres.add(new FastMonster(20, new Position(j * tileWidth, i * tileHeight), fastmonstreTexture));
                    }
                }
            }
        } catch (Exception e) {
            throw new LevelInitializationException("Impossible de démarrer la création du niveau", e);
        }
    }

    /**
     * Rend le niveau sur l'écran.
     *
     * @param batch L'objet SpriteBatch utilisé pour dessiner les textures.
     */
    public void render(SpriteBatch batch) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char cell = carte[i][j];
                int x = j * tileWidth;
                int y = i * tileHeight; // Coordonnées corrigées pour le rendu

                // Rendu de la salle ou du mur
                if (cell == '#') {
                    batch.draw(murTexture, x, y, tileWidth, tileHeight);
                } else {
                    batch.draw(salleTexture, x, y, tileWidth, tileHeight);
                }

                // Rendu des autres éléments
                if (cell == 'T') {
                    batch.draw(tresorTexture, x, y, tileWidth, tileHeight);
                } else if (cell == 'P') {
                    batch.draw(porteTexture, x, y, tileWidth, tileHeight);
                } else if (cell == 'S') {
                    batch.draw(sortieTexture, x, y, tileWidth, tileHeight);
                }
            }
        }

        // Rendre le joueur
        if (joueur != null) {
            joueur.render(batch);
        }

        // Rendre les monstres
        for (Monstre monstre : monstres) {
            monstre.render(batch);
        }
        
        verifierPorteAutomatique();
    }
    
    private void verifierPorteAutomatique() {
        int joueurX = joueur.getPosition().getX() / tileWidth;
        int joueurY = joueur.getPosition().getY() / tileHeight;

        // Vérifier si le joueur est à une case autour de la porte (haut, bas, gauche, droite)
        if (joueurY > 0 && carte[joueurY - 1][joueurX] == 'P') {
            // Faire disparaître la porte au-dessus
            carte[joueurY - 1][joueurX] = ' ';
        }
        if (joueurY < rows - 1 && carte[joueurY + 1][joueurX] == 'P') {
            // Faire disparaître la porte en-dessous
            carte[joueurY + 1][joueurX] = ' ';
        }
        if (joueurX > 0 && carte[joueurY][joueurX - 1] == 'P') {
            // Faire disparaître la porte à gauche
            carte[joueurY][joueurX - 1] = ' ';
        }
        if (joueurX < cols - 1 && carte[joueurY][joueurX + 1] == 'P') {
            // Faire disparaître la porte à droite
            carte[joueurY][joueurX + 1] = ' ';
        }
    }

    /**
     * Déplace le joueur dans la direction spécifiée.
     *
     * @param dx La direction en x.
     * @param dy La direction en y.
     * @return true si le déplacement est effectué, sinon false.
     */
    public boolean deplacerJoueur(int dx, int dy) {
        int newX = joueur.getPosition().getX() + dx * tileWidth;
        int newY = joueur.getPosition().getY() + dy * tileHeight;

        int col = newX / tileWidth;
        int row = newY / tileHeight;

        if (col >= 0 && col < cols && row >= 0 && row < rows && carte[row][col] != '#' && !estOccupeParMonstre(col, row)) {
            joueur.deplacer(dx * tileWidth, dy * tileHeight);
        }
        return true;
    }


    /**
     * Vérifie si une cellule de la carte est occupée par un monstre.
     *
     * @param col La colonne de la cellule.
     * @param row La ligne de la cellule.
     * @return true si la cellule est occupée par un monstre, sinon false.
     */
    private boolean estOccupeParMonstre(int col, int row) {
        for (Monstre monstre : monstres) {
            if (monstre.getPosition().getX() / tileWidth == col && monstre.getPosition().getY() / tileHeight == row) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si une cellule de la carte est occupée par le joueur.
     *
     * @param col La colonne de la cellule.
     * @param row La ligne de la cellule.
     * @return true si la cellule est occupée par le joueur, sinon false.
     */
    private boolean estOccupeParJoueur(int col, int row) {
        return joueur.getPosition().getX() / tileWidth == col && joueur.getPosition().getY() / tileHeight == row;
    }

    /**
     * Retourne le joueur du niveau.
     *
     * @return Le joueur.
     */
    public Joueur getJoueur() {
        return joueur;
    }

    /**
     * Retourne la liste des monstres du niveau.
     *
     * @return La liste des monstres.
     */
    public List<Monstre> getMonstres() {
        return monstres;
    }

    /**
     * Retourne la carte du niveau.
     *
     * @return La carte du niveau.
     */
    public char[][] getCarte() {
        return carte;
    }

    /**
     * Vérifie si le joueur se trouve sur un coffre et récupère le contenu du coffre si c'est le cas.
     */
    public void verifierCoffreDessous() {
        int joueurX = joueur.getPosition().getX() / tileWidth;
        int joueurY = joueur.getPosition().getY() / tileHeight;
      
        
        Iterator<Coffre> it = coffres.iterator();
        while (it.hasNext()) {
            Coffre coffre = it.next();
            int coffreX = coffre.getPosition().getX() / tileWidth;
            int coffreY = coffre.getPosition().getY() / tileHeight;

            if (joueurX == coffreX && joueurY == coffreY) {
                joueur.recupererCoffre(coffre);
                carte[coffreY][coffreX] = ' '; // Retirer le coffre de la carte
                it.remove();
                break;
            }
        }
    }

    /**
     * Déplace tous les monstres dans le niveau.
     */
    public void deplacerMonstres() {
        for (Monstre monstre : monstres) {
            if (!monstre.aAgit()) {
                if (peutAttaquer(monstre)) {
                    attaquerJoueur(monstre);
                    monstre.setAAgit(true);
                } else {
                    int joueurX = joueur.getPosition().getX() / tileWidth;
                    int joueurY = joueur.getPosition().getY() / tileHeight;
                    int monstreX = monstre.getPosition().getX() / tileWidth;
                    int monstreY = monstre.getPosition().getY() / tileHeight;

                    int dx = 0, dy = 0;

                    if (joueurX > monstreX) {
                        dx = 1;
                    } else if (joueurX < monstreX) {
                        dx = -1;
                    } else if (joueurY > monstreY) {
                        dy = 1;
                    } else if (joueurY < monstreY) {
                        dy = -1;
                    }

                    if (monstre instanceof FastMonster) {
                        boolean deplacementEffectue = false;
                        for (int i = 3; i >= 1; i--) {
                            int tentativeDx = dx * i;
                            int tentativeDy = dy * i;
                            int newX = monstreX + tentativeDx;
                            int newY = monstreY + tentativeDy;
                            if (newX >= 0 && newX < cols && newY >= 0 && newY < rows && carte[newY][newX] != '#' && !estOccupeParJoueur(newX, newY)) {
                                monstre.deplacer(tentativeDx * tileWidth, tentativeDy * tileHeight);
                                monstre.setAAgit(true);
                                deplacementEffectue = true;
                                break;
                            }
                        }
                        if (deplacementEffectue) continue;
                    }

                    int newX = monstreX + dx;
                    int newY = monstreY + dy;

                    if (newX >= 0 && newX < cols && newY >= 0 && newY < rows && carte[newY][newX] != '#' && !estOccupeParJoueur(newX, newY)) {
                        monstre.deplacer(dx * tileWidth, dy * tileHeight);
                        monstre.setAAgit(true);
                    }
                }
            }
        }
        verifierPorteAutomatique();
    }



    /**
     * Réinitialise l'état d'action de tous les monstres.
     */
    public void resetMonstreActions() {
        for (Monstre monstre : monstres) {
            monstre.setAAgit(false);
        }
    }

    /**
     * Vérifie si un monstre peut attaquer le joueur.
     *
     * @param monstre Le monstre à vérifier.
     * @return true si le monstre peut attaquer, sinon false.
     */
    public boolean peutAttaquer(Monstre monstre) {
        int[][] directions = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        int monstreX = monstre.getPosition().getX() / tileWidth;
        int monstreY = monstre.getPosition().getY() / tileHeight;

        for (int[] dir : directions) {
            int newX = monstreX + dir[0];
            int newY = monstreY + dir[1];
            if (estOccupeParJoueur(newX, newY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Attaque un monstre à une position donnée.
     *
     * @param col La colonne de la position du monstre.
     * @param row La ligne de la position du monstre.
     */
    public void attaquerMonstre(int col, int row) {
        Iterator<Monstre> it = monstres.iterator();
        while (it.hasNext()) {
            Monstre monstre = it.next();
            if (monstre.getPosition().getX() / tileWidth == col && monstre.getPosition().getY() / tileHeight == row) {
                joueur.attaquer(monstre);
                if (monstre.getPointsDeVie() <= 0) {
                    it.remove();
                    joueur.ajouterPiecesOr(10); // Ajouter 10 pièces d'or à l'inventaire du joueur
                }
                break;
            }
        }
    }

    /**
     * Le joueur attaque les cases adjacentes.
     *
     * @param joueur Le joueur qui attaque.
     */
    public void joueurAttaque(Joueur joueur) {
        int joueurX = joueur.getPosition().getX() / tileWidth;
        int joueurY = joueur.getPosition().getY() / tileHeight;

        // Attaquer les cases adjacentes
        int[][] directions = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

        for (int[] dir : directions) {
            int newX = joueurX + dir[0];
            int newY = joueurY + dir[1];

            if (newX >= 0 && newX < MAP_COLUMNS && newY >= 0 && newY < MAP_ROWS) {
                attaquerMonstre(newX, newY);
            }
        }
    }

    /**
     * Le monstre attaque le joueur.
     *
     * @param monstre Le monstre qui attaque.
     */
    public void attaquerJoueur(Monstre monstre) {
        joueur.reduirePointsDeVie(monstre.getPointsDAttaque());
    }

    /**
     * Vérifie s'il reste des monstres dans le niveau.
     *
     * @return true s'il reste des monstres, sinon false.
     */
    public boolean resteMonstres() {
        return !monstres.isEmpty();
    }

    /**
     * Tue tous les monstres du niveau et ajoute des points au joueur.
     *
     * @return Le nombre de points ajoutés.
     */
    public int tuerTousLesMonstres() {
        int pointsAjoutes = monstres.size() * 10;
        monstres.clear();
        return pointsAjoutes;
    }

    /**
     * Retourne le nombre de colonnes de la carte.
     *
     * @return Le nombre de colonnes.
     */
    public int getCols() {
        return MAP_COLUMNS;
    }

    /**
     * Retourne le nombre de lignes de la carte.
     *
     * @return Le nombre de lignes.
     */
    public int getRows() {
        return MAP_ROWS;
    }

    /**
     * Retourne la largeur d'une tuile.
     *
     * @return La largeur d'une tuile.
     */
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * Retourne la hauteur d'une tuile.
     *
     * @return La hauteur d'une tuile.
     */
    public int getTileHeight() {
        return tileHeight;
    }

    /**
     * Libère les ressources utilisées par le niveau.
     */
    public void dispose() {
        try {
            if (salleTexture != null) {
                salleTexture.dispose();
                salleTexture = null;
            }
            if (joueurTexture != null) {
                joueurTexture.dispose();
                joueurTexture = null;
            }
            if (tresorTexture != null) {
                tresorTexture.dispose();
                tresorTexture = null;
            }
            if (monstreTexture != null) {
                monstreTexture.dispose();
                monstreTexture = null;
            }
            if (porteTexture != null) {
                porteTexture.dispose();
                porteTexture = null;
            }
            if (sortieTexture != null) {
                sortieTexture.dispose();
                sortieTexture = null;
            }
            if (murTexture != null) {
                murTexture.dispose();
                murTexture = null;
            }
        } catch (Exception e) {
            throw new ResourceLoadingException("Impossible de libérer les textures", e);
        }
    }
}

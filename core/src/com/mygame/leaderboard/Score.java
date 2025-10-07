package com.mygame.leaderboard;

/**
 * La classe Score représente le score d'un joueur.
 * Elle contient le nom du joueur et son score.
 */
public class Score {
    private String nom;
    private int score;

    /**
     * Constructeur de la classe Score.
     * Initialise le nom du joueur et son score.
     *
     * @param nom   Le nom du joueur.
     * @param score Le score du joueur.
     */
    public Score(String nom, int score) {
        this.nom = nom;
        this.score = score;
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
     * Retourne le score du joueur.
     *
     * @return Le score du joueur.
     */
    public int getScore() {
        return score;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du score.
     * Le format est "nom: score".
     *
     * @return Une chaîne de caractères représentant le score.
     */
    @Override
    public String toString() {
        return nom + ": " + score;
    }
}

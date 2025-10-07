package com.mygame.leaderboard;

import java.io.*;
import java.util.*;

/**
 * La classe Leaderboard gère le classement des scores des joueurs.
 * Elle permet de charger, sauvegarder et mettre à jour les scores à partir d'un fichier.
 */
public class Leaderboard {
    private Map<String, Score> scoresMap;
    private String fichier;

    /**
     * Constructeur de la classe Leaderboard.
     * Initialise la map des scores et charge les scores à partir du fichier spécifié.
     *
     * @param fichier Le chemin du fichier contenant les scores.
     */
    public Leaderboard(String fichier) {
        this.scoresMap = new HashMap<>();
        this.fichier = fichier;
        chargerScores();
    }

    /**
     * Ajoute un score au classement.
     * Met à jour le score si le joueur existe déjà et que le nouveau score est plus élevé.
     *
     * @param score Le score à ajouter.
     */
    public void ajouterScore(Score score) {
        // Mettre à jour le score si le joueur existe déjà ou ajouter un nouveau score
        if (scoresMap.containsKey(score.getNom())) {
            if (scoresMap.get(score.getNom()).getScore() < score.getScore()) {
                scoresMap.put(score.getNom(), score);
            }
        } else {
            scoresMap.put(score.getNom(), score);
        }
        sauvegarderScores();
    }

    /**
     * Charge les scores à partir du fichier.
     * Lit le fichier et initialise la map des scores.
     */
    private void chargerScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(":");
                if (parts.length == 2) {
                    String nom = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    scoresMap.put(nom, new Score(nom, score));
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des scores: " + e.getMessage());
        }
    }

    /**
     * Sauvegarde les scores dans le fichier.
     * Écrit les scores actuels de la map dans le fichier.
     */
    private void sauvegarderScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
            for (Score score : scoresMap.values()) {
                writer.write(score.getNom() + ": " + score.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des scores: " + e.getMessage());
        }
    }

    /**
     * Retourne une liste triée des scores.
     * Trie les scores du plus élevé au plus bas.
     *
     * @return Une liste des scores triée.
     */
    public List<Score> getScores() {
        List<Score> scores = new ArrayList<>(scoresMap.values());
        scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));
        return scores;
    }
}

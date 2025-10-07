package com.mygame.level;

/**
 * La classe CarteGeneratorThread représente un thread de génération de carte.
 * Elle crée une nouvelle carte en utilisant un générateur de carte dans un thread séparé.
 */
public class CarteGeneratorThread extends Thread {
    private int rows;
    private int cols;
    private char[][] carte;
    private boolean generationComplete = false;

    /**
     * Constructeur de la classe CarteGeneratorThread.
     * Initialise les dimensions de la carte à générer.
     *
     * @param rows Le nombre de lignes de la carte.
     * @param cols Le nombre de colonnes de la carte.
     */
    public CarteGeneratorThread(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Méthode exécutée lorsque le thread est démarré.
     * Génère la carte et met à jour l'état de génération.
     */
    @Override
    public void run() {
        CarteGenerator generator = new CarteGenerator(rows, cols);
        synchronized (this) {
            this.carte = generator.getCarte();
            generationComplete = true;
        }
    }

    /**
     * Retourne la carte générée.
     * Cette méthode est synchronisée pour garantir la cohérence des données.
     *
     * @return La carte générée.
     */
    public synchronized char[][] obtenirCarte() {
        return carte;
    }

    /**
     * Vérifie si la génération de la carte est terminée.
     * Cette méthode est synchronisée pour garantir la cohérence des données.
     *
     * @return true si la génération est terminée, sinon false.
     */
    public synchronized boolean estGenerationComplete() {
        return generationComplete;
    }
}

package com.mygame.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.mygame.entity.Coffre;
import com.mygame.exception.MapGenerationException;
import com.mygame.item.FioleDeVie;
import com.mygame.item.WallBreaker;
import com.mygame.item.ZeusLightning;

/**
 * La classe CarteGenerator est responsable de générer une carte pour le jeu.
 * Elle utilise un pool de threads pour générer la carte en parallèle.
 */
public class CarteGenerator {
    private int rows;
    private int cols;
    private char[][] carte;
    private List<Coffre> coffres;
    private Random random;

    private static final int MAX_GENERATION_TIME = 5000; // Temps maximum de génération en millisecondes
    private static final int MAX_ATTEMPTS = 3; // Nombre maximum de tentatives de génération

    /**
     * Constructeur de la classe CarteGenerator.
     * Initialise les dimensions de la carte, la liste des coffres et démarre la génération de la carte.
     *
     * @param rows Le nombre de lignes de la carte.
     * @param cols Le nombre de colonnes de la carte.
     */
    public CarteGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.carte = new char[rows][cols];
        this.coffres = new ArrayList<>();
        this.random = new Random();
        generateCarte();
    }

    /**
     * Génère la carte en utilisant plusieurs tentatives et un pool de threads pour paralléliser la génération.
     * En cas d'échec après plusieurs tentatives, une carte par défaut est générée.
     */
    private void generateCarte() {
        int attempts = 0;
        long startTime = System.currentTimeMillis();

        while (attempts < MAX_ATTEMPTS) {
            try {
                // Réinitialisation de la carte avec des murs
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        carte[i][j] = '#';
                    }
                }

                // Utiliser un pool de threads pour générer la carte en parallèle
                ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                for (int i = 0; i < rows; i += rows / 2) {
                    for (int j = 0; j < cols; j += cols / 2) {
                        executor.execute(new CarteGeneratorTask(j, i, cols, rows, carte));
                    }
                }

                executor.shutdown();
                if (executor.awaitTermination(MAX_GENERATION_TIME, TimeUnit.MILLISECONDS)) {
                    // Génération réussie dans le temps imparti
                    break;
                } else {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                throw new MapGenerationException("Génération de carte interrompu", e);
            } catch (Exception e) {
                throw new MapGenerationException("Erreur dans la génération de carte", e);
            }

            attempts++;

            // Vérifiez si la génération a été rapide, sinon relancez-la
            if (System.currentTimeMillis() - startTime < MAX_GENERATION_TIME) {
                break;
            }

            // Réinitialiser le temps de démarrage pour la prochaine tentative
            startTime = System.currentTimeMillis();
        }

        // Si toutes les tentatives échouent, générer une carte par défaut
        if (attempts == MAX_ATTEMPTS) {
            generateDefaultCarte();
        }

        // Placer les portes de manière aléatoire
        addDoors();

        // Placer le joueur et la sortie
        placeElementsAvecDistance('J', 'S', 5);

        // Placer quelques trésors
        placeCoffresAvecItems(2); // Placer 2 coffres avec des items

        // Placer quelques monstres
        placeElement('M');
        placeElement('M');
        placeElement('M');
        placeElement('F'); // Placer un FastMonster
        placeElement('F');
    }


    /**
     * Génère une carte par défaut simple si toutes les tentatives échouent.
     */
    private void generateDefaultCarte() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    carte[i][j] = '#';
                } else {
                    carte[i][j] = ' ';
                }
            }
        }
        carte[1][1] = 'J';
        carte[rows - 2][cols - 2] = 'S';
    }

    /**
     * Ajoute des portes à la carte avec une probabilité de 5%.
     */
    private void addDoors() {
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (carte[i][j] == ' ' && random.nextFloat() < 0.05) { // 5% de chance de placer une porte
                    carte[i][j] = 'P';
                }
            }
        }
    }

    /**
     * Place un élément sur la carte à une position aléatoire.
     *
     * @param element Le caractère représentant l'élément à placer.
     */
    private void placeElement(char element) {
        int x, y;
        do {
            x = random.nextInt(cols - 2) + 1; // évite les bords
            y = random.nextInt(rows - 2) + 1; // évite les bords
        } while (carte[y][x] != ' ');
        carte[y][x] = element;
    }

    /**
     * Place deux éléments sur la carte avec une distance minimale entre eux.
     *
     * @param element1   Le premier élément à placer.
     * @param element2   Le deuxième élément à placer.
     * @param minDistance La distance minimale entre les deux éléments.
     */
    private void placeElementsAvecDistance(char element1, char element2, int minDistance) {
        int x1, y1, x2, y2;
        do {
            x1 = random.nextInt(cols - 2) + 1;
            y1 = random.nextInt(rows - 2) + 1;
        } while (carte[y1][x1] != ' ');
        carte[y1][x1] = element1;

        do {
            x2 = random.nextInt(cols - 2) + 1;
            y2 = random.nextInt(rows - 2) + 1;
        } while (carte[y2][x2] != ' ' || Math.abs(x2 - x1) + Math.abs(y2 - y1) < minDistance);
        carte[y2][x2] = element2;
    }

    /**
     * Place un nombre spécifié de coffres avec des items sur la carte.
     *
     * @param nombreDeCoffres Le nombre de coffres à placer.
     */
    private void placeCoffresAvecItems(int nombreDeCoffres) {
        for (int i = 0; i < nombreDeCoffres; i++) {
            int x, y;
            do {
                x = random.nextInt(cols - 2) + 1; // évite les bords
                y = random.nextInt(rows - 2) + 1; // évite les bords
            } while (carte[y][x] != ' ');

            int tileWidth = 800 / cols;
            int tileHeight = 600 / rows;
            Position position = new Position(x * tileWidth, y * tileHeight);
            int goldCoins = random.nextInt(50) + 1; // Nombre aléatoire de pièces d'or entre 1 et 50
            Coffre coffre = new Coffre(position, goldCoins);
            

            // Ajouter un item spécial de manière rare (5% de chance)
            if (random.nextFloat() < 0.05) {
                int specialItemType = random.nextInt(2);
                if (specialItemType == 0) {
                    coffre.ajouterItem(new WallBreaker());
                } else {
                    coffre.ajouterItem(new ZeusLightning());
                }
            }else if (random.nextFloat() < 0.10) {
            	coffre.ajouterItem(new FioleDeVie());
            }
            coffres.add(coffre);
            carte[y][x] = 'T'; // Représentation du coffre sur la carte
        }
    }

    /**
     * Retourne la liste des coffres générés.
     *
     * @return La liste des coffres.
     */
    public List<Coffre> getCoffres() {
        return coffres;
    }

    /**
     * Retourne la carte générée.
     *
     * @return La carte sous forme de tableau 2D de caractères.
     */
    public char[][] getCarte() {
        return carte;
    }

    /**
     * Affiche la carte dans la console.
     */
    public void printCarte() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(carte[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Point d'entrée principal pour tester la génération de la carte.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        CarteGenerator generator = new CarteGenerator(10, 10);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convertir en millisecondes
        generator.printCarte();

    }
}

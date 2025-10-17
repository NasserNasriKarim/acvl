package com.mygame.exploration;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygame.entity.Inventaire;
import com.mygame.entity.Joueur;
import com.mygame.leaderboard.Leaderboard;
import com.mygame.leaderboard.Score;
import com.mygame.level.Niveau;
import com.mygame.level.TextureUtils;
import com.mygame.exception.*;

import java.util.List;
import com.badlogic.gdx.InputProcessor;

/**
 * La classe Game représente le jeu principal et gère la boucle de jeu, les rendus graphiques, les entrées utilisateur
 * et les différents niveaux.
 */
public class Game extends ApplicationAdapter implements InputProcessor {
    private SpriteBatch batch;
    private Niveau niveau;
    private boolean jeuDemarre;
    private Texture instructionsTexture;
    private BitmapFont font;
    private BitmapFont titleFont;
    private Leaderboard leaderboard;

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800; // Augmenter la hauteur pour inclure la partie noire

    private static final int MAP_COLUMNS = 20;
    private static final int MAP_ROWS = 20;

    private int tileWidth;
    private int tileHeight;

    private int currentLevel;
    private static final int MAX_LEVELS = 10;

    private String playerName;
    private boolean gameOver;
    private long gameOverTime;
    
    // Variables pour la saisie du nom
    private boolean saisieNom;
    private StringBuilder nomBuffer;
    private boolean nomValide;

    /**
     * Méthode appelée lors de la création du jeu.
     * Initialise les ressources graphiques, les polices, et charge le leaderboard.
     */
    @Override
    public void create() {
        try {
            batch = new SpriteBatch();
            tileWidth = WINDOW_WIDTH / MAP_COLUMNS;
            tileHeight = (WINDOW_HEIGHT - 200) / MAP_ROWS;
            instructionsTexture = new Texture(Gdx.files.internal("textures/instructions.png"));

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/slkscr.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 24;
            font = generator.generateFont(parameter);
            parameter.size = 32;
            titleFont = generator.generateFont(parameter);
            generator.dispose();

            currentLevel = 1;
            jeuDemarre = false;
            gameOver = false;
            saisieNom = true;
            nomBuffer = new StringBuilder();
            nomValide = false;
            playerName = "";
            leaderboard = new Leaderboard("leaderboard.txt");
            
            // Configurer l'InputProcessor pour la saisie de caractères
            Gdx.input.setInputProcessor(this);
        } catch (Exception e) {
            throw new GameInitializationException("Initialisation du jeu raté", e);
        }
    }

    /**
     * Initialise un niveau avec les points de vie et les pièces d'or spécifiés.
     *
     * @param pointsDeVie Le nombre de points de vie du joueur.
     * @param piecesOr    Le nombre de pièces d'or du joueur.
     */
    private void initializeLevel(int pointsDeVie, int piecesOr) {
    	try {
            Texture salleTexture = new Texture(Gdx.files.internal("textures/sol.png"));
            Texture joueurTexture = TextureUtils.resizeTexture(new Texture(Gdx.files.internal("textures/player.png")), 48, 48); 
            Texture tresorTexture = new Texture("textures/tresor.png"); 
            Texture monstreTexture = new Texture("textures/monstre.png");
            Texture porteTexture = new Texture("textures/porte.png");
            Texture sortieTexture = new Texture("textures/sortie.png");
            Texture murTexture = new Texture("textures/salle.png");
            Texture fastmonstreTexture = new Texture("textures/fastmonstre.png");

            niveau = new Niveau(salleTexture, joueurTexture, tresorTexture, monstreTexture,fastmonstreTexture, porteTexture, sortieTexture, murTexture, tileWidth, tileHeight, playerName, pointsDeVie, piecesOr);
        } catch (Exception e) {
            throw new ResourceLoadingException("Impossible de créer le niveau", e);
        }
    }

    /**
     * Méthode appelée pour rendre les éléments graphiques à chaque frame.
     * Gère les différents états du jeu (écran d'accueil, jeu en cours, game over).
     */
    @Override
    public void render() {
        if (batch == null) {
            return; // ou vous pouvez gérer cela de manière appropriée, comme une initialisation de secours
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (saisieNom) {
            afficherSaisieNom(batch);
        } else if (!jeuDemarre) {
            afficherEcranAccueil(batch);
        } else {
            if (!gameOver) {
                handleInput();
                niveau.render(batch);
                verifierSortie();
                afficherScore(batch);
            } else {
                afficherLeaderboard(batch);
                if (System.currentTimeMillis() - gameOverTime > 60000 || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    Gdx.app.exit(); // Quitter proprement l'application
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                    gameOver = false;
                    currentLevel = 1;
                    jeuDemarre = false;
                    saisieNom = true;
                    nomBuffer = new StringBuilder();
                    nomValide = false;
                    playerName = "";
                    Gdx.input.setInputProcessor(this); // Réactiver l'InputProcessor
                }
            }
        }
        if(batch != null) {
        	batch.end();
        }
        
    }


    /**
     * Affiche l'écran d'accueil et attend que le joueur appuie sur ENTER pour démarrer le jeu.
     *
     * @param batch L'objet SpriteBatch utilisé pour dessiner les textures.
     */
    private void afficherEcranAccueil(SpriteBatch batch) {
        batch.draw(instructionsTexture, 0, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            jeuDemarre = true;
            initializeLevel(170, 0); // Initialiser avec des points de vie et des pièces d'or par défaut
        }
    }

    /**
     * Affiche l'écran de saisie du nom du joueur.
     *
     * @param batch L'objet SpriteBatch utilisé pour dessiner les textures.
     */
    private void afficherSaisieNom(SpriteBatch batch) {
        // Arrière-plan
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        
        // Titre
        titleFont.setColor(Color.YELLOW);
        titleFont.draw(batch, "CASTLE WISDOM", WINDOW_WIDTH / 2 - 150, WINDOW_HEIGHT - 100);
        
        // Instructions
        font.setColor(Color.WHITE);
        font.draw(batch, "Entrez votre nom :", WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2 + 50);
        
        // Nom saisi
        font.setColor(Color.CYAN);
        String nomAffiche = nomBuffer.toString();
        if (nomAffiche.length() == 0) {
            nomAffiche = "_";
        } else {
            nomAffiche = nomAffiche + "_";
        }
        font.draw(batch, nomAffiche, WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT / 2);
        
        // Instructions de validation
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Appuyez sur ENTREE pour valider", WINDOW_WIDTH / 2 - 140, WINDOW_HEIGHT / 2 - 50);
        font.draw(batch, "BACKSPACE pour effacer", WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2 - 80);
        
        // Gestion des entrées
        gererSaisieNom();
    }

    /**
     * Gère la saisie du nom du joueur.
     */
    private void gererSaisieNom() {
        // Validation avec ENTER
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (nomBuffer.length() > 0) {
                playerName = nomBuffer.toString().trim();
                if (playerName.length() > 0) {
                    saisieNom = false;
                    nomValide = true;
                    Gdx.input.setInputProcessor(null); // Désactiver l'InputProcessor
                }
            } else {
                playerName = "Joueur";
                saisieNom = false;
                nomValide = true;
                Gdx.input.setInputProcessor(null); // Désactiver l'InputProcessor
            }
        }
        
        // Effacer avec BACKSPACE
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && nomBuffer.length() > 0) {
            nomBuffer.deleteCharAt(nomBuffer.length() - 1);
        }
    }

    /**
     * Gère les entrées utilisateur pour déplacer le joueur, attaquer, ouvrir les coffres, etc.
     */
    private void handleInput() {
        Joueur joueur = niveau.getJoueur();
        boolean actionEffectuee = false;

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            actionEffectuee = niveau.deplacerJoueur(-1, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            actionEffectuee = niveau.deplacerJoueur(1, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            actionEffectuee = niveau.deplacerJoueur(0, 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            actionEffectuee = niveau.deplacerJoueur(0, -1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            actionEffectuee = true; // Considérer l'attaque comme une action effectuée
            joueurAttaque(joueur);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            niveau.verifierCoffreDessous();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            joueur.getInventaire().utiliserItemSpecial(niveau, joueur);
            actionEffectuee = true; // Considérer l'utilisation de l'item comme une action effectuée
        }

        // Si une action (déplacement ou attaque) a été effectuée, les monstres se déplacent ou attaquent
        if (actionEffectuee) {
            niveau.deplacerMonstres();
            niveau.resetMonstreActions(); // Réinitialiser l'état des monstres après chaque tour
        }

        verifierPointsDeVie(joueur);
    }

    /**
     * Gère l'attaque du joueur sur les cases adjacentes.
     *
     * @param joueur L'objet Joueur représentant le joueur.
     */
    private void joueurAttaque(Joueur joueur) {
        int joueurX = joueur.getPosition().getX() / tileWidth;
        int joueurY = joueur.getPosition().getY() / tileHeight;

        // Attaquer les cases adjacentes
        int[][] directions = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

        for (int[] dir : directions) {
            int newX = joueurX + dir[0];
            int newY = joueurY + dir[1];

            if (newX >= 0 && newX < MAP_COLUMNS && newY >= 0 && newY < MAP_ROWS) {
                niveau.attaquerMonstre(newX, newY);
            }
        }
    }

    /**
     * Vérifie les points de vie du joueur et met fin au jeu si les points de vie sont à zéro.
     *
     * @param joueur L'objet Joueur représentant le joueur.
     */
    private void verifierPointsDeVie(Joueur joueur) {
        if (joueur.getPointsDeVie() <= 0) {
            System.out.println("Game Over! Vous avez été tué par les monstres.");
            leaderboard.ajouterScore(new Score(playerName, joueur.getInventaire().getGoldCoins()));
            gameOver = true;
            gameOverTime = System.currentTimeMillis();
        }
    }

    /**
     * Vérifie si le joueur a atteint la sortie et passe au niveau suivant si toutes les conditions sont remplies.
     */
    private void verifierSortie() {
        Joueur joueur = niveau.getJoueur();
        int joueurX = joueur.getPosition().getX() / tileWidth;
        int joueurY = joueur.getPosition().getY() / tileHeight;

        if (niveau.getCarte()[joueurY][joueurX] == 'S' && !niveau.resteMonstres()) {
            currentLevel++;
            if (currentLevel > MAX_LEVELS) {
                System.out.println("Félicitations ! Vous avez terminé le jeu !");
                leaderboard.ajouterScore(new Score(playerName, joueur.getInventaire().getGoldCoins()));
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            } else {
                initializeLevel(joueur.getPointsDeVie() + 10, joueur.getInventaire().getGoldCoins());
            }
        }
    }

    /**
     * Affiche le score actuel du joueur sur l'écran.
     *
     * @param batch L'objet SpriteBatch utilisé pour dessiner les textes.
     */
    private void afficherScore(SpriteBatch batch) {
        Joueur joueur = niveau.getJoueur();
        Inventaire inventaire = joueur.getInventaire();
        font.draw(batch, "Niveau: " + currentLevel, 10, WINDOW_HEIGHT - 10);
        font.draw(batch, "Pieces d'or: " + inventaire.getGoldCoins(), 10, WINDOW_HEIGHT - 40);
        font.draw(batch, "Points de vie: " + joueur.getPointsDeVie(), 10, WINDOW_HEIGHT - 70);
        font.draw(batch, "Nom: " + joueur.getNom(), 10, WINDOW_HEIGHT - 100);
        if (inventaire.getItemSpecial() != null) {
            font.draw(batch, "Item special: " + inventaire.getItemSpecial().getNom(), 10, WINDOW_HEIGHT - 130);
        }
    }

    /**
     * Affiche le leaderboard des scores sur l'écran de fin de jeu.
     *
     * @param batch L'objet SpriteBatch utilisé pour dessiner les textes.
     */
    private void afficherLeaderboard(SpriteBatch batch) {
        List<Score> scores = leaderboard.getScores();

        // Variables pour le positionnement du leaderboard
        float titleX = (WINDOW_WIDTH / 2) - 100;
        float titleY = WINDOW_HEIGHT - 50;
        float startY = titleY - 50;
        float startX = 100;

        titleFont.setColor(Color.GOLD);
        titleFont.draw(batch, "Leaderboard", titleX, titleY);

        int rank = 1;
        for (Score score : scores) {
            font.setColor(Color.WHITE);
            font.draw(batch, rank + ". " + score.getNom() + ": " + score.getScore(), startX, startY - (rank * 30));
            rank++;
        }

        font.setColor(Color.RED);
        font.draw(batch, "Appuyez sur ENTER pour quitter", startX, startY - ((rank + 1) * 30));
        font.draw(batch, "Appuyez sur R pour recommencer", startX, startY - ((rank + 2) * 30));
        font.setColor(Color.WHITE);
    }

    /**
     * Libère les ressources utilisées par le jeu.
     */
    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
            batch = null;
        }
        if (instructionsTexture != null) {
            instructionsTexture.dispose();
            instructionsTexture = null;
        }
        if (niveau != null) {
            niveau.dispose();
            niveau = null;
        }
        if (font != null) {
            font.dispose();
            font = null;
        }
        if (titleFont != null) {
            titleFont.dispose();
            titleFont = null;
        }
    }

    // Implémentation des méthodes InputProcessor
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // Cette méthode capture les caractères réels tapés selon la disposition du clavier
        if (saisieNom && nomBuffer.length() < 15) {
            if (Character.isLetterOrDigit(character) || character == ' ') {
                if (character == ' ' && nomBuffer.length() == 0) {
                    return false; // Pas d'espace au début
                }
                nomBuffer.append(character);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}

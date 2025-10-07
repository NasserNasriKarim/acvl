package com.mygame.exploration;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(30);
        config.setTitle("Jeu d'exploration");

        config.setWindowIcon(Files.FileType.Internal, "textures/player.png", "textures/player.png", "textures/player.png");

        config.setWindowedMode(800, 800);
        config.useVsync(true);
        new Lwjgl3Application(new Game(), config);
    }
}

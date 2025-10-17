# Rendu — Jeu d'exploration (libGDX)

## Contenu du rendu
- `README.md` — ce fichier.
- `gradlew` — wrapper Gradle (rendre exécutable avant usage).
- `gradlew.bat` — wrapper pour Windows.
- `gradle/wrapper/*` — fichiers du wrapper Gradle.
- `settings.gradle`, `build.gradle` (root) — configuration Gradle globale.
- `core/` — module core contenant la logique du jeu et les assets partagés.
    - `core/src/.../Game.java` — classe principale du jeu (logique).
    - `core/assets/` — ressources (par ex. `textures/player.png`).
- `desktop/` — module desktop pour lancer l'application sur PC/Mac.
    - `desktop/src/com/mygame/exploration/DesktopLauncher.java` — lanceur desktop (fichier ouvert dans l'éditeur).
    - `desktop/build.gradle` — configuration spécifique au desktop (application plugin).
- Éventuels autres modules (android, ios) selon le projet — non obligatoires pour le rendu desktop.

## Compiler et exécuter
1. Rendre le wrapper exécutable (important — ne pas utiliser de backticks) :
    - `chmod +x gradlew`

2. Compiler tout le projet :
    - `./gradlew build`

3. Lancer l'application
    - Sur Linux/Windows/MacOS:
        - `./gradlew :desktop:run`

## Erreurs courantes
- `zsh: permission denied: ./gradlew` — corriger avec `chmod +x gradlew`.
- `zsh: command not found: gradlew` — ne pas utiliser de backticks autour de `gradlew` dans `chmod`; exécutez `chmod +x gradlew`.
- `Cannot locate tasks that match ':core:run'` — la tâche `run` se trouve normalement dans le module `desktop`, donc utilisez `:desktop:run` et non `:core:run`.

## Remarques
- Toujours utiliser le wrapper `./gradlew` fourni pour garantir la version de Gradle attendue.
- Si vous modifiez `desktop/build.gradle` pour ajouter `jvmArgs = ['-XstartOnFirstThread']` à la tâche `run`, la variable d'environnement `JAVA_TOOL_OPTIONS` n'est plus nécessaire sur macOS.

package com.tloj.game.game;

import java.util.ArrayList;


public class Game {
    private static Game instance;
    
    Level currentLevel;
    Character player;
    ArrayList<Level> levels;

    private Game() {
        this.levels = new ArrayList<Level>();
    }

    private Game(GameData gameData) {
        this.currentLevel = gameData.currentLevel;
        this.player = gameData.player;
        this.levels = gameData.levels;
    }

    public static Game getInstance() {
        if (instance == null) instance = new Game();
        return instance;
    }

    public static Game getInstance(GameData gameData) {
        if (instance == null) instance = new Game(gameData);
        return instance;
    }
}

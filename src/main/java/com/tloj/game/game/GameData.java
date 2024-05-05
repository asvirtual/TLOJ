package com.tloj.game.game;

import java.util.ArrayList;
import com.tloj.game.entities.Character;


public class GameData {
    Level currentLevel;
    Character player;
    ArrayList<Level> levels;

    public GameData(Level currentLevel, Character player, ArrayList<Level> levels) {
        this.currentLevel = currentLevel;
        this.player = player;
        this.levels = levels;
    }

    public String serializeJSON() {
        return null;
    }

    public static GameData deserializeJSON(String json) {
        return null;
    }
}

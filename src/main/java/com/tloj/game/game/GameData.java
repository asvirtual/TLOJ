package com.tloj.game.game;

import java.util.ArrayList;
import com.tloj.game.utilities.Coordinates;


public class GameData {
    Level currentLevel;
    Coordinates currentPosition;
    Character player;
    ArrayList<Level> levels;

    public GameData(Level currentLevel, Coordinates currentPosition, Character player, ArrayList<Level> levels) {
        this.currentLevel = currentLevel;
        this.currentPosition = currentPosition;
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

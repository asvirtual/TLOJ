package com.tloj.game.game;

import java.util.ArrayList;
import com.tloj.game.entities.Character;
import com.tloj.game.rooms.Room;


public class GameData {
    long seed;
    Level currentLevel;
    Character player;
    ArrayList<Level> levels;

    public GameData(Game game) {
        this.seed = game.getSeed();
        this.currentLevel = game.getLevel();
        this.player = game.getPlayer();
        this.levels = game.getLevels();
    }

    /* TODO */
    public String serializeJSON() {
        return null;
    }

    /* TODO */
    public static GameData deserializeJSON(String json) {
        return null;
    }

    /* TODO
     * ArrayList<ArrayList<ArrayList<Room>>> or ArrayList<Level> ?
     */
    public static ArrayList<ArrayList<ArrayList<Room>>> deserializeMap(String json) {
        return null;
    }
}

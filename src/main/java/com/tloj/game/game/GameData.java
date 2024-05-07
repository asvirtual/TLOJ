package com.tloj.game.game;

import java.util.ArrayList;

import com.tloj.game.entities.Character;
import com.tloj.game.rooms.Room;


/**
 * Represents the Game Data of a game.<br>
 * The Game Data contains the seed of the game, the current level, the player and the levels of the game.<br>
 * It can be serialized and deserialized to and from JSON.<br>
 * It can also be used to create a new Game instance, with which it will be tightly coupled since the member Objects will point to the same references.
 */
public class GameData {
    public long seed;
    public Level currentLevel;
    public Character player;
    public ArrayList<Level> levels;

    public GameData(long seed, Level currentLevel, Character player, ArrayList<Level> levels) {
        this.seed = seed;
        this.currentLevel = currentLevel;
        this.player = player;
        this.levels = levels;
    }

    public Game getGame() {
        return new Game(
            this.seed,
            this.currentLevel,
            this.player,
            this.levels
        );
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

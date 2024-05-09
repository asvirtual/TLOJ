package com.tloj.game.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.tloj.game.entities.Character;
import com.tloj.game.rooms.Room;


/**
 * Represents the Game Data of a game.<br>
 * The Game Data contains the seed of the game, the current level, the player and the levels of the game.<br>
 * It can be serialized and deserialized to and from JSON.<br>
 * It can also be used to create a new Game instance, with which it will be tightly coupled since the member Objects will point to the same references.
 */
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "seed")
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

    public GameData() {
        this.seed = 0;
        this.currentLevel = null;
        this.player = null;
        this.levels = new ArrayList<Level>();
    }

    @JsonIgnore
    public Game getGame() {
        return new Game(
            this.seed,
            this.currentLevel,
            this.player,
            this.levels
        );
    }

    public void saveToFile(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(filename), this);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + filename + " for writing");
            e.printStackTrace();
        } catch (StackOverflowError e) {
            System.out.println("Error: StackOverflowError");
            e.printStackTrace();
        }
    }

    public static GameData loadFromFile(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(filename), GameData.class);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + filename + " for reading");
            e.printStackTrace();
        }

        return null;
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
    public static ArrayList<Level> deserializeMapFromFile(String filename) {
        GameData gameData = GameData.loadFromFile("test.json");
        if (gameData != null) return gameData.levels;
        return null;

        /* 
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(filename), new TypeReference<ArrayList<Level>>(){});
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + filename + " for reading");
            e.printStackTrace();
        }
        
        return null;

        */
    }

    /* Getters and setters, needed for ObjectMapper to recognize fields and map them in JSON */
    public long getSeed() {
        return this.seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Character getPlayer() {
        return this.player;
    }

    public void setPlayer(Character player) {
        this.player = player;
    }

    public ArrayList<Level> getLevels() {
        return this.levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }
}

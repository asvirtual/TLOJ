package com.tloj.game.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.tloj.game.entities.Character;


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
    /** The seed of the game. */
    public long seed;
    /** The current level of the game. */
    public Level currentLevel;
    /** The player character of the game. */
    public Character player;
    /** The levels of the game. */
    public ArrayList<Level> levels;

    /**
     * Constructs a new GameData object with the given seed, current level, player, and levels.
     *
     * @param seed The seed of the game.
     * @param currentLevel The current level of the game.
     * @param player The player character of the game.
     * @param levels The levels of the game.
     */
    @JsonCreator
    public GameData(
        @JsonProperty("seed") long seed, 
        @JsonProperty("currentLevel") Level currentLevel, 
        @JsonProperty("player") Character player, 
        @JsonProperty("levels") ArrayList<Level> levels
    ) {
        this.seed = seed;
        this.currentLevel = currentLevel;
        this.player = player;
        this.levels = levels;
    }

    /**
     * Returns a new Game instance based on the data in this GameData.
     *
     * @return A new Game instance.
     */
    @JsonIgnore
    public Game getGame() {
        return new Game(
            this.seed,
            this.currentLevel,
            this.player,
            this.levels
        );
    }

    /**
     * Saves the given Game object to a JSON file with the specified filename.
     *
     * @param game The Game object to be saved.
     * @param filename The filename for the JSON file.
     */
    public static void saveToFile(Game game, String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(filename), game);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + filename + " for writing");
            e.printStackTrace();
        } 
    }

    /**
     * Loads GameData from a JSON file with the specified filename.
     *
     * @param filename The filename of the JSON file.
     * @return The loaded GameData object.
     */
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

    /**
     * Serializes this GameData object to a JSON string.
     *
     * @return The JSON string representing this GameData.
     */
    public String serializeJSON() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from GameData");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deserializes a GameData object from a JSON string.
     *
     * @param json The JSON string to deserialize.
     * @return The deserialized GameData object.
     */
    public static GameData deserializeJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, GameData.class);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from GameData");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deserializes a list of levels from a JSON string.
     *
     * @param json The JSON string representing the levels.
     * @return The deserialized list of levels.
     */
    public static ArrayList<Level> deserializeMap(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, new TypeReference<ArrayList<Level>>(){});
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.out.println("Error processing JSON from GameData");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deserializes a list of levels from a JSON file with the specified filename.
     *
     * @param filename The filename of the JSON file.
     * @return The deserialized list of levels.
     */
    public static ArrayList<Level> deserializeMapFromFile(String filename) {
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

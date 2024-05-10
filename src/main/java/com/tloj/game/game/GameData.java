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
    public long seed;
    public Level currentLevel;
    public Character player;
    public ArrayList<Level> levels;

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

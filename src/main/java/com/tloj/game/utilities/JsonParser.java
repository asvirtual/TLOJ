package com.tloj.game.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tloj.game.game.Game;
import com.tloj.game.game.Floor;

/**
 * This class is a utility class for parsing JSON files.
 */
public class JsonParser {
    /**
     * Private constructor to prevent instantiation.
     * This class should only be used statically.
     */
    private JsonParser() {};

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
     * Loads Game from a JSON file with the specified filename.
     *
     * @param filename The filename of the JSON file.
     * @return The loaded Game object.
     */
    public static Game loadFromFile(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(filename), Game.class);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Serializes this GameData object to a JSON string.
     *
     * @return The JSON string representing this GameData.
     */
    public static String serializeJSON(Game game) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(game);
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
    public static Game deserializeJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, Game.class);
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
     * Deserializes a list of floors from a JSON file with the specified filename.
     *
     * @param filename The filename of the JSON file.
     * @return The deserialized list of floors.
     */
    public static ArrayList<Floor> deserializeMapFromFile(String filename) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(filename), new TypeReference<ArrayList<Floor>>(){});
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
}

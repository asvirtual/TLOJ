package com.tloj.game.game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.JsonParser;


/**
 * A utility class for managing the index of saved games.
 * This class is used to keep track of the saved games and their filenames.
 * The index is saved to a JSON file in the saves directory.
 * The index is loaded when the game starts and saved when the game ends.
 */
public class GameIndex {
    private static Map<Long, String> games;

    private GameIndex() {}

    /**
     * Adds a new entry to the index with the given filename.
     */
    public static void addEntry(long key, String filename) {
        games.put(key, filename);
        saveGames();
    }

    /**
     * Returns the filename of the game with the given id.
     * @param id The id of the game.
     * @return The filename of the game.
     */
    public static String getFile(long key) {
        return games.get(key);
    }

    /**
     * Removes the entry with the given id from the index.
     * @param id The id of the entry to be removed.
     * @return The filename of the removed entry.
     */
    public static String removeEntry(long key) {
        String removed = games.remove(key);

        File file = new File(Constants.BASE_SAVES_DIRECTORY + removed);
        file.delete();

        saveGames();
        return removed;
    }

    /**
     * Clears the index of all entries.
     */
    public static void clearGames() {
        games.clear();
        saveGames();
    }

    public static List<Long> getKeys() {
        return games.keySet().stream().toList();
    }

    public static long getLastPlayedKey() {
        if (games.isEmpty()) return -1;

        long key = -1;
        long lastPlayed = -1;
        try {

            for (Map.Entry<Long, String> entry : games.entrySet()) {
                String file = entry.getValue();
                Game game = JsonParser.loadFromFile(Constants.BASE_SAVES_DIRECTORY + file);
                long played = game.getSessionStartTime();
                if (played > lastPlayed) {
                    lastPlayed = played;
                    key = entry.getKey();
                }
            }
    
            return key;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Returns the Game object with the given id.
     * @param id The id of the game.
     * @return The Game object.
     */
    public static Game getGame(long key) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(Constants.BASE_SAVES_DIRECTORY + games.get(key)), Game.class);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from game data");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from game data");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + games.get(key) + " for reading");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Loads the index of saved games from the JSON file.
     */
    public static void loadGames() {
        ObjectMapper mapper = new ObjectMapper();
        File savesDir = new File(Constants.BASE_SAVES_DIRECTORY);
        
        if (!savesDir.exists()) savesDir.mkdirs();
        File file = new File(Constants.BASE_SAVES_DIRECTORY + Constants.GAMES_INDEX_FILE_PATH);
        
        if (!file.exists()) {
            games = new HashMap<Long, String>();

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write("{}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            games = mapper.readValue(file, new TypeReference<Map<Long, String>>(){});
            File[] files = savesDir.listFiles();

            for (File f : files) {
                String filename = f.getName();
                if (filename.equals(Constants.GAMES_INDEX_FILE_PATH)) continue;
                if (games.containsValue(filename)) continue;

                Game game = JsonParser.loadFromFile(Constants.BASE_SAVES_DIRECTORY + filename);

                // Delete old saves that are not on the cloud anymore, except for those 
                // that were never backup up after the last time they were played
                if (game.isBackedUp() || game.getPlayer() == null || game.hasEnded()) f.delete();
                else games.put(game.getSessionStartTime(), filename);
            }

            // Save to index also files that were not picked from the cloud as they weren't backed up before
            saveGames();
        } catch (JsonGenerationException e) {
            System.out.println("Error deleting outdated saves");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error deleting outdated saves");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + Constants.GAMES_INDEX_FILE_PATH + " for reading");
            e.printStackTrace();
        }
    }

    /**
     * Saves the index of saved games to the JSON file.
     */
    public static void saveGames() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(Constants.BASE_SAVES_DIRECTORY + Constants.GAMES_INDEX_FILE_PATH), games);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + Constants.GAMES_INDEX_FILE_PATH + " for writing");
            e.printStackTrace();
        }
    }
}

package com.tloj.game.game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private static ArrayList<String> games;

    private GameIndex() {}

    /**
     * Adds a new entry to the index with the given filename.
     */
    public static int addEntry(String filename) {
        games.add(filename);
        saveGames();
        return games.size();
    }

    /**
     * Returns the filename of the game with the given id.
     * @param id The id of the game.
     * @return The filename of the game.
     */
    public static String getFile(String id) {
        try {
            int index = Integer.parseInt(id) - 1;
            if (index < 0 || index >= games.size()) return null;

            return games.get(index);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Removes the entry with the given id from the index.
     * @param id The id of the entry to be removed.
     * @return The filename of the removed entry.
     */
    public static String removeEntry(String id) {
        try {
            int index = Integer.parseInt(id) - 1;
            if (index < 0 || index >= games.size()) return null;

            String removed = games.remove(index);

            File file = new File(Constants.BASE_SAVES_DIRECTORY + removed);
            file.delete();

            saveGames();
            return removed;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Clears the index of all entries.
     */
    public static void clearGames() {
        games.clear();
        saveGames();
    }

    public static List<String> getEntries() {
        return games;
    }

    /**
     * Returns the Game object with the given id.
     * @param id The id of the game.
     * @return The Game object.
     */
    public static Game getGame(String id) {
        int index;

        try {
            index = Integer.parseInt(id) - 1;
            if (index < 0 || index >= games.size()) return null;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(Constants.BASE_SAVES_DIRECTORY + games.get(index)), Game.class);
        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error opening file " + games.get(index) + " for reading");
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
            games = new ArrayList<>();
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("[]");
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        try {
            games = mapper.readValue(file, new TypeReference<ArrayList<String>>(){});
            File[] files = savesDir.listFiles();
            for (File f : files)
                if (!f.getName().equals(Constants.GAMES_INDEX_FILE_PATH) && !games.contains(f.getName()))
                    f.delete();

            // Sort the games by creation time
            games.sort((first, second) -> {
                try {
                    return 
                        new Date(
                            JsonParser.loadFromFile(
                                Constants.BASE_SAVES_DIRECTORY + second
                            ).getCreationTime()
                        ).compareTo(
                            new Date(
                                JsonParser.loadFromFile(
                                    Constants.BASE_SAVES_DIRECTORY + first
                                ).getCreationTime()
                            )
                        );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return 0;
            });

        } catch (JsonGenerationException e) {
            System.out.println("Error generating JSON from GameData");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping JSON from GameData");
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

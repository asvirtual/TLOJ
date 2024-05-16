package com.tloj.game.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tloj.game.utilities.Constants;


public class GameIndex {
    private static ArrayList<String> games;

    public static int addEntry(String filename) {
        games.add(filename);
        saveGames();
        return games.size() - 1;
    }

    public static String getFile(String id) {
        try {
            int index = Integer.parseInt(id);
            if (index < 0 || index >= games.size()) {
                System.out.println("Invalid choice: " + index);
                return null;
            }

            return games.get(index);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing id " + id);
            e.printStackTrace();
        }

        return null;
    }

    public static void removeEntry(String id) {
        try {
            int index = Integer.parseInt(id);
            if (index < 0 || index >= games.size()) {
                System.out.println("Invalid choice: " + index);
                return;
            }

            games.remove(index);
            saveGames();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing id " + id);
            e.printStackTrace();
        }
    }

    public static void clearGames() {
        games.clear();
        saveGames();
    }

    public static List<String> getEntries() {
        return games;
    }

    public static Game getGame(String id) {
        int index;

        try {
            index = Integer.parseInt(id);
            if (index < 0 || index >= games.size()) {
                System.out.println("Invalid choice: " + index);
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing id " + id);
            e.printStackTrace();
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(new File(games.get(index)), Game.class);
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

    public static void loadGames() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            games = mapper.readValue(new File(Constants.GAMES_INDEX_FILE_PATH), new TypeReference<ArrayList<String>>(){});
            games.size();
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

    public static void saveGames() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(Constants.GAMES_INDEX_FILE_PATH), games);
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

package com.tloj.game.game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tloj.game.utilities.Constants;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.utilities.JsonParser;


public class GameIndexTest {
    private ArrayList<Floor> floors;

    @BeforeEach
    void setUpMap(){
        this.floors = new ArrayList<>();

        ArrayList<ArrayList<Room>> firstFloorMap = new ArrayList<>();
        ArrayList<Room> firstRooms = new ArrayList<>();
        StartRoom room = new StartRoom(new Coordinates(0, 0));

        firstRooms.add(room);
        firstFloorMap.add(firstRooms);
        
        Floor firstFloor = new Floor(1, firstFloorMap);       
        this.floors.add(firstFloor);
    }
  
    @BeforeAll
    static void backupLocalSaves() {
        File backupDir = new File("test-backup");
        backupDir.mkdir();

        File savesDir = new File(Constants.BASE_SAVES_DIRECTORY);
        if (!savesDir.exists()) savesDir.mkdir();
        File file = new File(Constants.BASE_SAVES_DIRECTORY + Constants.GAMES_INDEX_FILE_PATH);
    
        if (!file.exists()) return;
        if (savesDir.length() == 0) return;
        
        File[] files = savesDir.listFiles();
        for (File f : files) {
            File backupFile = new File(backupDir.getPath() + "/" + f.getName());
            try {
                Files.move(f.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }            
        }
    }

    @AfterAll
    static void restoreLocalSaves() {
        File backupDir = new File("test-backup");
        File savesDir = new File(Constants.BASE_SAVES_DIRECTORY);

        File[] testSaveFiles = savesDir.listFiles();
        for (File f : testSaveFiles) f.delete();
        
        File[] backupFiles = backupDir.listFiles();
        for (File f : backupFiles) {
            File originalFile = new File(savesDir.getPath() + "/" + f.getName());
            try {
                Files.move(f.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }            
        }

        backupDir.delete();
    }
    
    @Test
    void checkJson() {
        GameIndex.loadGames();
        Game mockGame = new Game(this.floors);

        String saveName = "test" + Constants.SAVE_GAME_FILENAME_SEPARATOR + mockGame.getCreationTime() + ".json";
        JsonParser.saveToFile(mockGame, Constants.BASE_SAVES_DIRECTORY + saveName);
        int currentGameId = GameIndex.addEntry(saveName);
        mockGame.setId(currentGameId);

        GameIndex.loadGames();
        assertEquals(GameIndex.getGame(String.valueOf(currentGameId)).getCreationTime(), mockGame.getCreationTime());
    }

    @Test
    void loadTest() {
        Game mockGame = new Game(this.floors);
        String saveName = "test.json";
        File index = new File(Constants.BASE_SAVES_DIRECTORY + "index.json");

        try (FileWriter writer = new FileWriter(index)) { 
            writer.write("[\"test.json\"]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonParser.saveToFile(mockGame, Constants.BASE_SAVES_DIRECTORY + saveName);

        GameIndex.loadGames();
        assertEquals(GameIndex.getFile("1"), "test.json");
    }
}

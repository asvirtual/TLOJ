package com.tloj.game.collectables.items;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Game;
import com.tloj.game.game.GameIndex;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.utilities.Constants;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Inventory;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Floor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SpecialKeyTest {  
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
    void noKeyTest() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();

        Coordinates startCoordinates = new Coordinates(0, 0);
        Coordinates endCoordinates = new Coordinates(1, 0);
        StartRoom mockStartRoom = new StartRoom(startCoordinates);
        LootRoom mockLootRoom = new LootRoom(endCoordinates, true, null);
        
        rooms.add(mockStartRoom);
        rooms.add(mockLootRoom);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockStartRoom, new LaserBlade(), new Inventory(), startCoordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0, true, false, 0);
        Controller.getInstance().setGame(mockGame);

        assertThrows(IllegalArgumentException.class, () -> mockGame.movePlayer(Coordinates.Direction.EAST));
        assertNotEquals(endCoordinates, mockGame.getPlayer().getPosition());
    }

    @Test
    void useKeyTest() {
        try {
            Thread.sleep(100);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        
        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();

        Coordinates startCoordinates = new Coordinates(0, 0);
        Coordinates endCoordinates = new Coordinates(1, 0);
        StartRoom mockStartRoom = new StartRoom(startCoordinates);
        LootRoom mockLootRoom = new LootRoom(endCoordinates, true, null);
        
        rooms.add(mockStartRoom);
        rooms.add(mockLootRoom);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);

        GameIndex.loadGames();
        
        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockStartRoom, new LaserBlade(), new Inventory(), startCoordinates);
        mockCharacter.addInventoryItem(new SpecialKey());

        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0, true, false, 0);
        Controller.getInstance().setGame(mockGame);
        
        assertDoesNotThrow(() -> mockGame.movePlayer(Coordinates.Direction.EAST));
        assertEquals(endCoordinates, mockGame.getPlayer().getPosition());
        assertFalse(mockGame.getPlayer().hasItem(new SpecialKey()));
    }
}

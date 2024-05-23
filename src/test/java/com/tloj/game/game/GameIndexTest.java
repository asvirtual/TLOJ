package com.tloj.game.game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.utilities.Constants;
import com.tloj.game.game.Game;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;
import com.google.protobuf.MapEntry;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.utilities.JsonParser;


public class GameIndexTest {

    static Map<String, Game> savesFolder = new HashMap<>();
    private static final InputStream originalSystemIn = System.in;
    private Coordinates startCoordinates = new Coordinates(0, 0);
    private Game game;
    static private ArrayList<Game> games = new ArrayList<>(); 
    private ArrayList<Floor> floors = new ArrayList<>(); 
    private Controller controller;
    String input;
    
    private void setUpGame() {
        input = "test\n12345\nyes\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Controller.instance = null;
        controller = Controller.getInstance();
        
        GameIndex.loadGames();

        controller.handleUserInput("new");
        game = new Game(floors, 1);
        controller.setGame(game);
        controller.handleUserInput("1");
    }

    @BeforeEach
    void setUpMap(){
        try {
            Thread.sleep(100); 
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        ArrayList<ArrayList<Room>> firstFloor = new ArrayList<>();
        ArrayList<Room> firstRooms = new ArrayList<>();
        StartRoom room = new StartRoom(startCoordinates);
        LootRoom mockLootRoom = new LootRoom(new Coordinates(0, 1));
        
        firstRooms.add(room);
        firstRooms.add(mockLootRoom);
        firstFloor.add(firstRooms);
        
        Floor firstLevel = new Floor(1, firstFloor);
        
        ArrayList<ArrayList<Room>> secondFloor = new ArrayList<>();
        ArrayList<Room> secondRooms = new ArrayList<>();
        HealingRoom mockHealingRoom = new HealingRoom(new Coordinates(1, 0));
       
        secondRooms.add(mockHealingRoom);
        secondFloor.add(secondRooms);

        Floor secondLevel = new Floor(2, secondFloor);
       
        floors.add(firstLevel);
        floors.add(secondLevel);
    }
  
    @BeforeAll
    static void backupLocalSaves() {
        File savesDir = new File(Constants.BASE_SAVES_DIRECTORY);
        
        if (!savesDir.exists()) savesDir.mkdirs();
        File file = new File(Constants.BASE_SAVES_DIRECTORY + Constants.GAMES_INDEX_FILE_PATH);
    
        if (!file.exists()) return;
        if (savesDir.length() == 0) return;
        
        try {
            File[] files = savesDir.listFiles();
            for (File f : files)
                savesFolder.put(
                    f.getName().toString(), 
                    JsonParser.loadFromFile(Constants.BASE_SAVES_DIRECTORY + f.getName().toString())
                );
        } catch (IOException e) {
            return;
        }
    }
    
    @Test
    void checkJson(){
        Game mockGame = new Game(floors, 1);
        mockGame.saveLocally();
        File savesDir = new File(Constants.BASE_SAVES_DIRECTORY);
        assertNotNull(savesDir.list());
        File[] nullArr = savesDir.listFiles();
        for (File f : nullArr)
            if (f.getName().equals("null"))
                f.delete();
    }

    @Test
    void loadTest() {
        int initialFileCount = GameIndex.getEntries() == null ? 0 : GameIndex.getEntries().size();
        GameIndex.loadGames(); 
        int finalFileCount = GameIndex.getEntries().size();
        assertNotEquals(initialFileCount, finalFileCount); 
    }

    @Test
    void getGameTest() {
        Game mockGame = new Game(floors, 1);
        mockGame.saveLocally();

        GameIndex.loadGames();
        Game loadedGame = GameIndex.getGame("1");

        assertNotNull(loadedGame);

        long expectedCreationTime = mockGame.getCreationTime();
        long actualCreationTime = loadedGame.getCreationTime();
        
        assertEquals(expectedCreationTime, actualCreationTime); 
    }
}

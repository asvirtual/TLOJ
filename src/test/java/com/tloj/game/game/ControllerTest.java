package com.tloj.game.game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.utilities.Constants;


public class ControllerTest {
    private static final InputStream originalSystemIn = System.in;
    private Coordinates startCoordinates = new Coordinates(0, 0);
    private Game game;
    private ArrayList<Floor> floors = new ArrayList<>(); 
    private Controller controller;
    
    private void setupGame() {
        // Create a custom map
        ArrayList<ArrayList<Room>> firstFloorMap = new ArrayList<>();
        ArrayList<ArrayList<Room>> secondFloorMap = new ArrayList<>();

        ArrayList<Room> firstRooms = new ArrayList<Room>(List.of(
            new StartRoom(startCoordinates),
            new LootRoom(new Coordinates(0, 1))
        ));

        ArrayList<Room> secondRooms = new ArrayList<Room>(
            List.of(new HealingRoom(new Coordinates(1, 0)))
        );
            
        firstFloorMap.add(firstRooms);
        secondFloorMap.add(secondRooms);
        
        Floor firstFloor = new Floor(1, firstFloorMap);
        Floor secondFloor = new Floor(2, secondFloorMap);
       
        this.floors.add(firstFloor);
        this.floors.add(secondFloor);

        // Define mock input to create a new game called "test", with a BasePlayer character
        String input = "1\ny\ntest\n\n\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Controller.instance = null;
        this.controller = Controller.getInstance();
        
        GameIndex.loadGames();

        this.controller.handleUserInput("new");
        // Get the player character from the game
        Character player = this.controller.game.getPlayer();
        player.setPosition(startCoordinates);

        // Create a new game with the custom map and the player character created manually
        this.controller.setGame(
            new Game(
                12345,
                firstFloor,
                player,
                this.floors,
                0,
                0,
                0,
                true,
                false,
                0
            )
        );

        this.game = this.controller.game;
        this.controller.setState(GameState.MOVING);
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

    @AfterAll
    static void tearDown() {
        ControllerHandler.resetInput(originalSystemIn);
    }
   
    @Test
    void SingletonTest() {
        Controller controller1 = Controller.getInstance();
        Controller controller2 = Controller.getInstance();
        assertTrue(controller1 == controller2);
    }

    @Test
    void moveTest() {
        this.setupGame();
        this.controller.handleUserInput("ge");
        assertNotEquals(startCoordinates, this.game.getPlayer().getPosition());
    }

    @Test
    void invalidCoordinateMoveTest() {
        this.setupGame();
        this.controller.handleUserInput("gs");
        assertEquals(startCoordinates, this.game.getPlayer().getPosition());
    }

    @Test
    void changeFloorTest() {
        this.setupGame();
        this.controller.setState(GameState.BOSS_DEFEATED);
        int floorBefore = this.game.getPlayer().getCurrentFloor().getFloorNumber();
        this.controller.handleUserInput("ge");
        int floorafter = this.game.getPlayer().getCurrentFloor().getFloorNumber();
        
        assertNotEquals(floorBefore, floorafter);
    }
    
    @Test
    void characterFactoryTest() {
        this.setupGame();
        assertTrue(this.game.getPlayer() instanceof BasePlayer);
    }   

    @Test
    void validCommandTest() {
        class MockGameCommand extends GameCommand {
            public MockGameCommand(Game game, String[] commands){
                super(game, null);
                this.validStates = List.of(
                    GameState.MOVING
                );
    
            }
        }

        Controller.getInstance();
        Controller.getInstance().setState(GameState.MOVING);
        GCInvoker invoker = new GCInvoker();
        invoker.setCommand(new MockGameCommand(this.game, null));
        assertDoesNotThrow(() -> invoker.executeCommand());

    }
    
    @Test
    void invalidCommandTest() {
        class MockGameCommand extends GameCommand {
            public MockGameCommand(Game game, String[] commands){
                super(game, null);
                this.invalidStates = List.of(
                    GameState.MOVING
                );
    
            }
        }

        Controller.getInstance();
        Controller.getInstance().setState(GameState.MOVING);
        GCInvoker invoker = new GCInvoker();
        invoker.setCommand(new MockGameCommand(this.game, null));
        assertThrows(IllegalStateException.class, () -> invoker.executeCommand());

    }

    @Test
    void wrongLengthCommandTest(){
        class MockGameCommand extends GameCommand {

            public MockGameCommand(Game game, String[] commands){
                super(game, commands);
            }
        }

        Controller.getInstance();
        GCInvoker invoker = new GCInvoker();
        invoker.setCommand(new MockGameCommand(this.game, "test".split(" ")));
        assertDoesNotThrow(() -> invoker.executeCommand());

    }

    @Test
    void rightLengthCommandTest(){
        class MockGameCommand extends GameCommand {

            public MockGameCommand(Game game, String[] commands){
                super(game, commands);
            }
        }

        Controller.getInstance();
        GCInvoker invoker = new GCInvoker();
        invoker.setCommand(new MockGameCommand(this.game, "test test".split(" ")));
        assertThrows(IllegalArgumentException.class, () -> invoker.executeCommand());

    }
}


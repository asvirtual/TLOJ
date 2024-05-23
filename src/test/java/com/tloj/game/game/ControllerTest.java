package com.tloj.game.game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.entities.characters.BasePlayer;


public class ControllerTest {
    private static final InputStream originalSystemIn = System.in;
    private Coordinates startCoordinates = new Coordinates(0, 0);
    private Game game;
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
  
    @AfterAll
    static void tearDown() {
        System.setIn(originalSystemIn);
    }
   
    @Test
    void SingletonTest() {
        Controller controller1 = Controller.getInstance();
        Controller controller2 = Controller.getInstance();
        assertEquals(controller1, controller2);
    }

    @Test
    void moveTest() {
        this.setUpGame();
        controller.handleUserInput("ge");
        assertNotEquals(startCoordinates, game.getPlayer().getPosition());
    }

    @Test
    void invalidCoordinateMoveTest() {
        this.setUpGame();
        controller.handleUserInput("gs");
        assertEquals(startCoordinates, game.getPlayer().getPosition());
    }

    @Test
    void changeFloorTest() {
        this.setUpGame();
        controller.setState(GameState.BOSS_DEFEATED);
        int floorBefore = game.getPlayer().getCurrentFloor().getFloorNumber();
        controller.handleUserInput("ge");
        int floorafter = game.getPlayer().getCurrentFloor().getFloorNumber();
        
        assertNotEquals(floorBefore, floorafter);
    }
    
    @Test
    void characterFactoryTest() {
        this.setUpGame();
        assertTrue(game.getPlayer() instanceof BasePlayer);
    }   

    @Test
    void validCommandTest(){
        class MockGameCommand extends GameCommand{

            public MockGameCommand(Game game, String[] commands){
                super(game, null);
                this.validListStates = List.of(
                    GameState.MOVING
                );
    
            }
        }

        Controller.getInstance();
        Controller.getInstance().setState(GameState.MOVING);
        GCInvoker invoker = new GCInvoker();
        invoker.setCommand(new MockGameCommand(game, null));
        assertDoesNotThrow(() -> invoker.executeCommand());

    }
    
    @Test
    void invalidCommandTest(){

        class MockGameCommand extends GameCommand{

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
        invoker.setCommand(new MockGameCommand(game, null));
        assertThrows(IllegalStateException.class, () -> invoker.executeCommand());

    }

    @Test
    void wrongLenghtCommandTest(){
        class MockGameCommand extends GameCommand{

            public MockGameCommand(Game game, String[] commands){
                super(game, commands);
            }
        }

        Controller.getInstance();
        GCInvoker invoker = new GCInvoker();
        invoker.setCommand(new MockGameCommand(game, "test".split(" ")));
        assertDoesNotThrow(() -> invoker.executeCommand());

    }

    @Test
    void rightLenghtCommandTest(){
        class MockGameCommand extends GameCommand{

            public MockGameCommand(Game game, String[] commands){
                super(game, commands);
            }
        }

        Controller.getInstance();
        GCInvoker invoker = new GCInvoker();
        invoker.setCommand(new MockGameCommand(game, "test test".split(" ")));
        assertThrows(IllegalArgumentException.class, () -> invoker.executeCommand());

    }
}


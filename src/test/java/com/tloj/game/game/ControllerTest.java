package com.tloj.game.game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.entities.characters.BasePlayer;


public class ControllerTest {
    private final InputStream originalSystemIn = System.in;
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

        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        StartRoom room = new StartRoom(startCoordinates);
        LootRoom mockLootRoom = new LootRoom(new Coordinates(0, 1));
        rooms.add(room);
        rooms.add(mockLootRoom);
        floor.add(rooms);

        Floor level = new Floor(1, floor);
        floors.add(level);
    }
    @AfterEach
    void tearDown() {
        this.controller.setState(GameState.MAIN_MENU);
        controller = null;
        game = null;
        System.setIn(originalSystemIn);
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
    void characterFactoryTest() {
        this.setUpGame();
        assertTrue(game.getPlayer() instanceof BasePlayer);
    }
}
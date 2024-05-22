package com.tloj.game.game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.utilities.Coordinates;

public class ControllerTest {
    private final InputStream originalSystemIn = System.in;
    private Coordinates startCoordinates = new Coordinates(0, 0);
    private Game game;
    
    @BeforeEach
    void setUpGame(){
        try {
            Thread.sleep(100); 
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    @Test
    void controllerCreationTest() {
        // assertNotNull(Controller.getInstance());
    }

    @Test
    void BasePlayerFactoryTest() {
        String input = "\n12345\n\n\n\n\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Controller controller = Controller.getInstance();

        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> floors = new ArrayList<>();
        StartRoom room = new StartRoom(startCoordinates);
        rooms.add(room);
        floor.add(rooms);

        Floor level = new Floor(1, floor);
        floors.add(level);
        
        game = new Game(floors, 1);
        controller.setGame(game);

        controller.handleUserInput("new");
        //controller.handleUserInput("test");
        //controller.handleUserInput("12345");
        controller.handleUserInput("1");
        controller.handleUserInput("y");
        assertTrue(game.getPlayer() instanceof BasePlayer);

    
    }
    
}

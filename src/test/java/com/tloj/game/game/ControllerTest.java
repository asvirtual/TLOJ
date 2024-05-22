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
        String input = "new\ntest\n12345\n1\ny\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Controller controller = Controller.getInstance();
        //game = new Game(floors, 1);
        //controller.setGame(game);

        controller.run();
        //GameIndex.loadGames();

        //controller.handleUserInput("new");
        //controller.handleUserInput("test");
        //controller.handleUserInput("12345");
        //controller.handleUserInput("1");
        //controller.handleUserInput("y");
        assertTrue(game.getPlayer() instanceof BasePlayer);


    
    }
    
}

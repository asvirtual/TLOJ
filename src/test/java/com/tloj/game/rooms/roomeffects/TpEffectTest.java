package com.tloj.game.rooms.roomeffects;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Inventory;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Game;
import com.tloj.game.game.Floor;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.rooms.TrapRoom;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;


/**
 * {@code TpEffectTest} is a test class for the {@link TpEffect} effect on the teleport type trap room.<br>
 * It tests the ability of the trap to teleport the player to a different room.<br>
 */

public class TpEffectTest {

    private final InputStream originalSystemIn = System.in;
    Thread inputThread;
    
    @BeforeEach
    public void setUpInput() {
        this.inputThread =  new Thread(() -> {
            while (true) {
                System.setIn(new ByteArrayInputStream("\n\n".getBytes()));
                try {
                    Thread.sleep(100);  // Sleep for a short time to ensure the input is read
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        inputThread.start();
        Dice.setSeed(1);
        Controller.getInstance();
    }

    @AfterEach
    public void restoreSystemIn() {
        this.inputThread.interrupt();
        System.setIn(originalSystemIn);
    }

    @Test
    public void applyEffectTest() {

        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();

        Coordinates startCoordinates = new Coordinates(0, 0);
        TrapRoom mockRoom = new TrapRoom(startCoordinates, new TpEffect());
        StartRoom mockStartRoom = new StartRoom(new Coordinates(0, 1));
        
        rooms.add(mockRoom);
        rooms.add(mockStartRoom);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockRoom, new LaserBlade(), new Inventory(), startCoordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0);
        Controller.getInstance().setGame(mockGame);
       
        mockRoom.triggerTrap(mockCharacter);
        Coordinates endCoordinates = mockCharacter.getPosition();
        
        assertNotEquals(startCoordinates, endCoordinates);
        assertFalse(mockRoom.isVisited());
    }    
}

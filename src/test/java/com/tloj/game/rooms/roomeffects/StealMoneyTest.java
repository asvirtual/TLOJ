package com.tloj.game.rooms.roomeffects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.rooms.Room;
import com.tloj.game.game.Game;
import com.tloj.game.game.Floor;
import com.tloj.game.rooms.TrapRoom;


/**
 * {@code StealMoneyTest} is a test class for the {@link StealMoney} effect on the steal money type trap room.<br>
 * It tests the ability of the trap to steal money from the player.<br>
 * It also tests wether it triggers or not.<br>
 */

public class StealMoneyTest {
    private final InputStream originalSystemIn = System.in;
    
    @BeforeEach
    public void setUpInput() {
        try {
            Thread.sleep(100); 
            
            String input = "";
            for (int i = 0; i < 10000; i++) {
                input += "\n";
            }

            System.setIn(new ByteArrayInputStream(input.getBytes()));

            Dice.setSeed(1);
            Controller.getInstance();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @AfterEach
    public void restoreSystemIn() {
        System.setIn(originalSystemIn);
    }
    
    @Test
    public void applyEffectTest() {
        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();

        Coordinates coordinates = new Coordinates(0, 0);
        TrapRoom mockRoom = new TrapRoom(coordinates, new StealMoney());
        
        rooms.add(mockRoom);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockRoom, new LaserBlade(), null, coordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0);
        Controller.getInstance().setGame(mockGame);
        
        int startMoney = mockCharacter.getMoney();
        boolean triggered = false;
    
        while (!triggered) {
            triggered = mockRoom.triggerTrap(mockCharacter);
            int endMoney = mockCharacter.getMoney();
            
            if (triggered) {
                assertEquals(startMoney - StealMoney.COST, endMoney);
                return;
            }
        } ;
    }
}

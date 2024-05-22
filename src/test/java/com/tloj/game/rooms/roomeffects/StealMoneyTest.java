package com.tloj.game.rooms.roomeffects;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.rooms.Room;
import com.tloj.game.game.Game;
import com.tloj.game.game.Floor;
import com.tloj.game.rooms.TrapRoom;
import com.tloj.game.utilities.Coordinates;


/**
 * {@code StealMoneyTest} is a test class for the {@link StealMoney} effect on the steal money type trap room.<br>
 * It tests the ability of the trap to steal money from the player.<br>
 * It also tests wether it triggers or not.<br>
 */

public class StealMoneyTest {
    
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

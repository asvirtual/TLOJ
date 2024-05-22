package com.tloj.game.rooms.roomeffects;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
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


public class InflictDamageTest {

    @Test
    public void applyEffectTest() {
        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();

        Coordinates coordinates = new Coordinates(0, 0);
        TrapRoom mockRoom = new TrapRoom(coordinates, new InflictDamage());
        
        rooms.add(mockRoom);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockRoom, new LaserBlade(), null, coordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0);
        Controller.getInstance().setGame(mockGame);
        
        int startHp = mockCharacter.getHp();
        boolean triggered = false;

        while (!triggered) {
            
            triggered = mockRoom.triggerTrap(mockCharacter);
            int endHp = mockCharacter.getHp();
            
            if (triggered) {
                assertEquals(startHp - InflictDamage.DAMAGE, endHp);
                return;
            }
        } ;
    }

    @Test
    public void EffectKillPlayerTest() {
        
        String input = "\n\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
      
        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();

        Coordinates coordinates = new Coordinates(0, 0);
        TrapRoom mockRoom = new TrapRoom(coordinates, new InflictDamage());
        
        rooms.add(mockRoom);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(5, 3, 3, 10, 0, 1, 5, 10, level, mockRoom, new LaserBlade(), null, coordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0);
        Controller.getInstance().setGame(mockGame);
        
        boolean triggered  = false;
        
        while (!triggered) {
            
            triggered = mockRoom.triggerTrap(mockCharacter);
            if (triggered) {
                assertTrue(!mockCharacter.isAlive());
                return;
            }
        }
    }
}

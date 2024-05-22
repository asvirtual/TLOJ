package com.tloj.game.entities.mobs;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Inventory;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.Floor;
import com.tloj.game.game.Game;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.rooms.TrapRoom;
import com.tloj.game.rooms.roomeffects.Teleport;
import com.tloj.game.game.Coordinates;
import com.tloj.game.entities.Mob;

public class GlitchedTest {
    @Test
    void testConstructor() {
        Glitched glitched = new Glitched(new Coordinates(0, 0), 1);
        
        assertEquals(70, glitched.getHp());
        assertEquals(10, glitched.getAtk());
        assertEquals(6, glitched.getDef());
        assertEquals(10, glitched.getCurrentFightAtk());
        assertEquals(6, glitched.getCurrentFightDef());
        assertEquals(6, glitched.getDiceFaces());
        assertEquals(16, glitched.dropXp());
        assertEquals(6, glitched.getMoneyDrop());
    }

    @Test
    void testTeleport() {
        
        String input = "\n\n\n\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        
        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();
        ArrayList<Mob> mobs = new ArrayList<>();
        
        Coordinates startCoordinates = new Coordinates(0, 0);
        Glitched glitched = new Glitched(startCoordinates, 1);

        mobs.add(glitched);     
        
        HostileRoom mockRoomfrom = new HostileRoom(startCoordinates, mobs);
        HostileRoom mockRoomto = new HostileRoom(new Coordinates(0, 1), new ArrayList<Mob>());
        
        rooms.add(mockRoomfrom);
        rooms.add(mockRoomto);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockRoomfrom, new LaserBlade(), new Inventory(), startCoordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0);
        Controller.getInstance().setGame(mockGame);
       
        glitched.attack(mockCharacter);
        Coordinates endCoordinates = glitched.getPosition();

        assertNotEquals(startCoordinates, endCoordinates);
    }
    
}

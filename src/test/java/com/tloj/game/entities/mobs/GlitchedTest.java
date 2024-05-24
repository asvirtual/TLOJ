package com.tloj.game.entities.mobs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;

import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Inventory;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.Floor;
import com.tloj.game.game.Game;
import com.tloj.game.game.MockController;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.entities.Mob;



/**
 * {@code GlitchedTest} is a test class for the {@link Glitched} mob.<br>
 * It tests its moving method and checks if it disappears after 4 encounters.<br>
 */
public class GlitchedTest {
    private final InputStream originalSystemIn = System.in;
   
    @BeforeEach
    public void setUpInput() {
        
        Dice.setSeed(1);
        MockController.deleteController();
        Controller.getInstance();
    }

    @AfterEach
    public void restoreSystemIn() {
        MockController.resetInput(originalSystemIn);
    }

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

        MockController.deleteController();
        MockController.setInput("\n\n");
        Controller.getInstance();

        glitched.attack(mockCharacter);
        Coordinates endCoordinates = glitched.getPosition();

        assertNotEquals(startCoordinates, endCoordinates);
    }
    
    @Test
    void disappearTest() {
        
        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();
        ArrayList<Mob> mobs = new ArrayList<>();
        
        Coordinates startCoordinates = new Coordinates(0, 0);
        Coordinates endCoordinates = new Coordinates(0, 1);
        Glitched glitched = new Glitched(startCoordinates, 1);

        mobs.add(glitched);
        
        HostileRoom mockRoomFrom = new HostileRoom(startCoordinates, mobs);
        HostileRoom mockRoomTo = new HostileRoom(endCoordinates, new ArrayList<>());
        
        rooms.add(mockRoomFrom);
        rooms.add(mockRoomTo);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(50, 3, 3, 10, 0, 1, 5, 10, level, mockRoomFrom, new LaserBlade(), new Inventory(), startCoordinates);
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0);
        Controller.getInstance().setGame(mockGame);
        
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < rooms.size(); j++){
                HostileRoom roomToCheck = (HostileRoom) rooms.get(j);
                Mob mobToCheck = roomToCheck.getMob();
                if (mobToCheck != null) {
                    mockCharacter.move(mobToCheck.getPosition());
                    mockCharacter.setHp(mockCharacter.getMaxHp());
                    
                    MockController.deleteController();
                    MockController.setInput("\n\n");
                    Controller.getInstance();

                    mobToCheck.attack(mockCharacter);
                }
            }
        }

        boolean foundGlitched = false;
        for (int i = 0; i < rooms.size(); i++){
            HostileRoom roomToCheck = (HostileRoom) rooms.get(i);
            if (roomToCheck.getMob() != null) foundGlitched = true;
        }

        assertFalse(foundGlitched);  
    }
}

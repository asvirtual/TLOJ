package com.tloj.game.rooms;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.collectables.items.ManaPotion;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Inventory;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Dice;
import com.tloj.game.game.Floor;
import com.tloj.game.game.Game;
import com.tloj.game.game.MockController;

public class LootRoomTest {
    private final InputStream originalSystemIn = System.in;

    @BeforeEach
    public void setUpInput() {
        Dice.setSeed(1);
        MockController.deleteController();
        MockController.setInput("\n");
        Controller.getInstance();
    }

    @AfterEach
    public void restoreSystemIn() {
        MockController.resetInput(originalSystemIn);
    }

    @Test   
    void freeInvTest() {

        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();

        Coordinates startCoordinates = new Coordinates(0, 0);
        Coordinates endCoordinates = new Coordinates(1, 0);
        LootRoom mockLootRoom = new LootRoom(endCoordinates, false, new WeaponShard());
        StartRoom mockStartRoom = new StartRoom(startCoordinates);
        
        rooms.add(mockStartRoom);
        rooms.add(mockLootRoom);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockStartRoom, new LaserBlade(), new Inventory(), startCoordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0);
        Controller.getInstance().setGame(mockGame);

        mockGame.movePlayer(Coordinates.Direction.EAST);
        assertTrue(mockGame.getPlayer().hasItem(new WeaponShard()));
        assertTrue(mockGame.getPlayer().getPosition().equals(endCoordinates));

    }

    @Test
    void fullInvTest(){
        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();

        Coordinates startCoordinates = new Coordinates(0, 0);
        Coordinates endCoordinates = new Coordinates(1, 0);
        LootRoom mockLootRoom = new LootRoom(endCoordinates, false, new WeaponShard());
        StartRoom mockStartRoom = new StartRoom(startCoordinates);
        
        rooms.add(mockStartRoom);
        rooms.add(mockLootRoom);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockStartRoom, new LaserBlade(), new Inventory(), startCoordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0);
        Controller.getInstance().setGame(mockGame);
    
        while(mockGame.getPlayer().canCarry(new ManaPotion()))
            mockGame.getPlayer().addInventoryItem(new ManaPotion());

        mockGame.movePlayer(Coordinates.Direction.EAST);
        assertFalse(mockGame.getPlayer().hasItem(new WeaponShard()));
        assertTrue(mockGame.getPlayer().getPosition().equals(endCoordinates));

        mockGame.movePlayer(Coordinates.Direction.WEST);
        
        while(mockGame.getPlayer().getFreeWeight() < new WeaponShard().getWeight())
            mockGame.getPlayer().removeInventoryItem(0);

        mockGame.movePlayer(Coordinates.Direction.EAST);

        assertTrue(mockGame.getPlayer().hasItem(new WeaponShard()));
        assertTrue(mockGame.getPlayer().getPosition().equals(endCoordinates));
        
    }
}

package com.tloj.game.rooms;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
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
import com.tloj.game.game.ControllerHandler;

public class HealingRoomTest {
    private final InputStream originalSystemIn = System.in;

    @Test   
    void fullHealTest() {

        
        Dice.setSeed(1);
        ControllerHandler.deleteController();
        ControllerHandler.setInput("\n");
        Controller.getInstance();

        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();

        Coordinates startCoordinates = new Coordinates(0, 0);
        Coordinates endCoordinates = new Coordinates(1, 0);
        HealingRoom mockLootRoom = new HealingRoom(endCoordinates);
        StartRoom mockStartRoom = new StartRoom(startCoordinates);
        
        rooms.add(mockStartRoom);
        rooms.add(mockLootRoom);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockStartRoom, new LaserBlade(), new Inventory(), startCoordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, true);
        Controller.getInstance().setGame(mockGame);

        mockGame.getPlayer().setHp(10);
        mockGame.getPlayer().setMana(5);

        mockGame.movePlayer(Coordinates.Direction.EAST);
        
        assertEquals(mockGame.getPlayer().getMaxHp(), mockGame.getPlayer().getHp());
        assertEquals(mockGame.getPlayer().getMaxMana(), mockGame.getPlayer().getMana());

        ControllerHandler.resetInput(originalSystemIn);

    }
}

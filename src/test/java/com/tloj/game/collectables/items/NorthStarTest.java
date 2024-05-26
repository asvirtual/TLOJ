package com.tloj.game.collectables.items;

import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.Inventory;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.Floor;
import com.tloj.game.game.Game;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class NorthStarTest {

    @Test
    void showMapTest() {

        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Floor> levels = new ArrayList<>();

        Coordinates startCoordinates = new Coordinates(0, 0);
        StartRoom mockStartRoom = new StartRoom(startCoordinates);
        HostileRoom mockRoom = new HostileRoom(new Coordinates(0, 1));
        
        rooms.add(mockRoom);
        rooms.add(mockStartRoom);
        floor.add(rooms);
        
        Floor level = new Floor(1, floor);
        levels.add(level);
        
        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockRoom, new LaserBlade(), new Inventory(), startCoordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, -1, 0, 0, true);
        Controller.getInstance().setGame(mockGame);
        
        String NoStarString = String.join("\n", mockGame.generateMapLines());
        
        mockGame.getFloor().getRoomStream().forEach(rowRooms -> {
            rowRooms.forEach(room -> {
                if (room != null) room.visit();
            });
        });     
        
        String expectedString = String.join("\n", mockGame.generateMapLines());

        mockGame.getFloor().getRoomStream().forEach(rowRooms -> {
            rowRooms.forEach(room -> {
                if (room != null) room.visit();
            });
        });

        mockCharacter.addInventoryItem(new NorthStar());
        
        String starString = String.join("\n", mockGame.generateMapLines());
        
        assertEquals(NoStarString, starString);
        assertEquals(expectedString, starString);

        
    }
}

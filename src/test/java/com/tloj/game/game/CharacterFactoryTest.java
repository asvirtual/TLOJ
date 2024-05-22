package com.tloj.game.game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.game.Coordinates;

public class CharacterFactoryTest {
    private Coordinates startCoordinates = new Coordinates(0, 0);
    private Game game;
    private ArrayList<Floor> floors = new ArrayList<>(); 
    private Controller controller;
    
    private void setUpGame() {
        String input = "test\n12345\nyes\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        controller = Controller.getInstance();
        
        GameIndex.loadGames();

        controller.handleUserInput("new");
        game = new Game(floors, 1);
        controller.setGame(game);
        controller.handleUserInput("1");
    }

    @BeforeEach
    void setUpMap(){
        try {
            Thread.sleep(100); 
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        StartRoom room = new StartRoom(startCoordinates);
        LootRoom mockLootRoom = new LootRoom(new Coordinates(0, 1));
        rooms.add(room);
        rooms.add(mockLootRoom);
        floor.add(rooms);

        Floor level = new Floor(1, floor);
        floors.add(level);
    }
}

package com.tloj.game;

import com.tloj.game.entities.characters.NeoSamurai;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.GameData;
import com.tloj.game.game.Level;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.utilities.Coordinates;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("test")) {
            testWrite();
            testRead();
            return;
        }

        Controller controller = Controller.getInstance();
        controller.run();
    }

    public static void testRead() {
        System.out.println(GameData.deserializeMapFromFile("test.json"));
        System.out.println(GameData.loadFromFile("test.json"));
    }

    public static void testWrite() {
        Level level = new Level
            (
                1, 
                new ArrayList<ArrayList<Room>>(
                    List.of(
                        new ArrayList<Room>(
                            List.of(
                                new HostileRoom(new Coordinates(0, 0), new CyberGoblin(new Coordinates(0, 0), 0)),
                                new LootRoom(new Coordinates(1, 0))
                            )
                        ),
                        new ArrayList<Room>(
                            List.of(
                                new LootRoom(new Coordinates(0, 1)),
                                new HealingRoom(new Coordinates(1, 1))
                            )
                        )
                    )
                )
            );

        ArrayList<Level> levels = new ArrayList<Level>();
        levels.add(level);

        GameData gameData = new GameData(
            Long.parseLong("1234567890"), 
            level,
            new NeoSamurai(new Coordinates(0, 0), 0),
            levels
        );

        gameData.saveToFile("test.json");
    }
}
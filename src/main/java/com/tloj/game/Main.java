package com.tloj.game;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.cloud.storage.Bucket;

import com.tloj.game.entities.characters.NeoSamurai;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.game.Controller;
import com.tloj.game.game.GameSaveHandler;
import com.tloj.game.game.Level;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.HealingRoom;
import com.tloj.game.rooms.HostileRoom;
import com.tloj.game.rooms.LootRoom;
import com.tloj.game.utilities.Constants;
import com.tloj.game.utilities.Coordinates;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Main class of the game<br>
 * Contains the main method to run the game
 */

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
        System.out.println(Constants.NANITE_LEECH_BLADE);

        Random random = new Random(1);

        try {
            FileWriter writer = new FileWriter("randomNumbers.txt");

            for (int i = 0; i < 1000; i++) {
                writer.write(random.nextInt() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // System.out.println(GameSaveHandler.deserializeMapFromFile("test.json"));
        // System.out.println(GameSaveHandler.loadFromFile("test.json"));
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

        // GameData gameData = new GameData(
        //     Long.parseLong("1234567890"), 
        //     level,
        //     new NeoSamurai(new Coordinates(0, 0)),
        //     levels,
        //     0,
        //     0
        // );

        // gameData.saveToFile("test.json");
    }
}
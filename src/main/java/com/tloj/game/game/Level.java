package com.tloj.game.game;

import java.util.ArrayList;
import com.tloj.game.rooms.Room;


public class Level {
    int levelNumber;
    ArrayList<ArrayList<Room>> rooms;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        this.rooms = new ArrayList<ArrayList<Room>>();
        this.rooms.forEach(room -> {
            room = new ArrayList<Room>();
        });
    }
}

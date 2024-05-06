package com.tloj.game.game;

import java.util.ArrayList;

import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.utilities.Coordinates;


public class Level {
    private int levelNumber;
    private ArrayList<ArrayList<Room>> rooms;
    private StartRoom startRoom;

    public Level(int levelNumber, ArrayList<ArrayList<Room>> rooms) {
        this.levelNumber = levelNumber;
        this.rooms = rooms;
        rooms.forEach(row -> { 
            row.forEach(room -> {
                if (room.getType() == RoomType.START_ROOM) 
                    this.startRoom = (StartRoom) room;
            });
        });
    }

    public StartRoom getStartRoom() {
        return this.startRoom;
    }

    public int getRoomsRowCount() {
        return this.rooms.size();
    }

    public int getRoomsColCount() {
        return this.rooms.get(0).size();
    }

    public Room getRoom(Coordinates coordinates) {
        return this.rooms.get(coordinates.getX()).get(coordinates.getY());
    }

    public int getLevelNumber() {
        return this.levelNumber;
    }
}

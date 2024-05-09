package com.tloj.game.game;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.rooms.Room;
import com.tloj.game.rooms.RoomType;
import com.tloj.game.rooms.StartRoom;
import com.tloj.game.utilities.Coordinates;


public class Level {
    private int levelNumber;
    @JsonProperty
    private ArrayList<ArrayList<Room>> rooms;
    private StartRoom startRoom;

    public Level(int levelNumber, ArrayList<ArrayList<Room>> rooms) {
        this.levelNumber = levelNumber;
        this.rooms = rooms;
        rooms.forEach(row -> { 
            row.forEach(room -> {
                if (room != null && room.getType() == RoomType.START_ROOM) 
                    this.startRoom = (StartRoom) room;
            });
        });
    }

    /**
     * Default constructor to allow Jackson to deserialize JSON.
     */
    public Level() {}

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

    @JsonIgnore
    public Stream<ArrayList<Room>> getRoomStream() {
        return this.rooms.stream();
    }

    public boolean areCoordinatesValid(Coordinates coordinates) {
        int roomsRowCount = this.getRoomsRowCount();
        int roomsColCount = this.getRoomsColCount();
        
        return coordinates.getY() >= 0 && coordinates.getY() < roomsRowCount &&
               coordinates.getX() >= 0 && coordinates.getX() < roomsColCount;
    }
}

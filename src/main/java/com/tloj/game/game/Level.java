package com.tloj.game.game;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public Level(
        @JsonProperty("levelNumber") int levelNumber, 
        @JsonProperty("rooms") ArrayList<ArrayList<Room>> rooms
    ) {
        this.levelNumber = levelNumber;
        this.rooms = rooms;
        this.rooms.forEach(row -> { 
            row.forEach(room -> {
                if (room != null && room.getType() == RoomType.START_ROOM) 
                    this.startRoom = (StartRoom) room;
            });
        });
    }

    public StartRoom getStartRoom() {
        return this.startRoom;
    }

    @JsonIgnore
    public int getRoomsRowCount() {
        return this.rooms.size();
    }

    @JsonIgnore
    public int getRoomsColCount() {
        return this.rooms.get(0).size();
    }

    @JsonIgnore
    public Room getRoom(Coordinates coordinates) {
        return this.rooms.get(coordinates.getY()).get(coordinates.getX());
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
               coordinates.getX() >= 0 && coordinates.getX() < roomsColCount &&
               this.rooms.get(coordinates.getY()).get(coordinates.getX()) != null;
    }
}

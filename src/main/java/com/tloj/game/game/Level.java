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


/**
 * Represents a level in the game. <br>
 * A level is a grid of rooms that the player can navigate through. <br>
 * The level has a start room and many other different types . <br>
 * <br>
 * @see Room
 * @see StartRoom
 * @see RoomType
 */
public class Level {
    /** The level number. */
    private int levelNumber;
    @JsonProperty
    /** List of rooms in the level. */
    private ArrayList<ArrayList<Room>> rooms;
    /** The start room of the level. */
    private StartRoom startRoom;

    /**
     * Constructs a new Level object with the given level number and rooms.
     *
     * @param levelNumber The level number.
     * @param rooms       The grid of rooms in the level.
     */
    @JsonCreator
    public Level(
        @JsonProperty("levelNumber") int levelNumber, 
        @JsonProperty("rooms") ArrayList<ArrayList<Room>> rooms
    ) {
        // Iterate through rooms to find the start room.
        this.levelNumber = levelNumber;
        this.rooms = rooms;
        this.rooms.forEach(row -> { 
            row.forEach(room -> {
                if (room != null && room instanceof StartRoom) 
                    this.startRoom = (StartRoom) room;
            });
        });
    }

    /**
     * Returns the start room of the level.
     *
     * @return The start room of the level.
     */
    public StartRoom getStartRoom() {
        return this.startRoom;
    }

    /**
     * Returns the number of rows in the grid of rooms.
     *
     * @return The number of rows in the grid of rooms.
     */
    @JsonIgnore
    public int getRoomsRowCount() {
        return this.rooms.size();
    }

    /**
     * Returns the number of columns in the grid of rooms.
     *
     * @return The number of columns in the grid of rooms.
     */
    @JsonIgnore
    public int getRoomsColCount() {
        return this.rooms.get(0).size();
    }

    /**
     * Returns the room at the specified coordinates.
     *
     * @param coordinates The coordinates of the room.
     * @return The room at the specified coordinates.
     */
    @JsonIgnore
    public Room getRoom(Coordinates coordinates) {
        return this.rooms.get(coordinates.getY()).get(coordinates.getX());
    }

    /**
     * Returns the level number.
     *
     * @return The level number.
     */
    public int getLevelNumber() {
        return this.levelNumber;
    }

    /**
     * Returns a stream of rows of rooms in the level.
     *
     * @return A stream of rows of rooms in the level.
     */
    @JsonIgnore
    public Stream<ArrayList<Room>> getRoomStream() {
        return this.rooms.stream();
    }

    /**
     * Checks if the given coordinates are valid within the level grid.
     *
     * @param coordinates The coordinates to check.
     * @return True if the coordinates are valid, false otherwise.
     */
    public boolean areCoordinatesValid(Coordinates coordinates) {
        int roomsRowCount = this.getRoomsRowCount();
        int roomsColCount = this.getRoomsColCount();
        
        return coordinates.getY() >= 0 && coordinates.getY() < roomsRowCount &&
               coordinates.getX() >= 0 && coordinates.getX() < roomsColCount &&
               this.rooms.get(coordinates.getY()).get(coordinates.getX()) != null;
    }
}

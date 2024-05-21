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
 * Represents a floor in the game. <br>
 * A floor is a grid of rooms that the player can navigate through. <br>
 * The floor has a start room and many other different types . <br>
 * <br>
 * @see Room
 * @see StartRoom
 * @see RoomType
 */
public class Floor {
    /** The floor number. */
    private int floorNumber;
    @JsonProperty
    /** List of rooms in the floor. */
    private ArrayList<ArrayList<Room>> rooms;
    /** The start room of the floor. */
    private StartRoom startRoom;

    /**
     * Constructs a new Floor object with the given floor number and rooms.
     *
     * @param floorNumber The floor number.
     * @param rooms       The grid of rooms in the floor.
     */
    @JsonCreator
    public Floor(
        @JsonProperty("floorNumber") int floorNumber, 
        @JsonProperty("rooms") ArrayList<ArrayList<Room>> rooms
    ) {
        // Iterate through rooms to find the start room.
        this.floorNumber = floorNumber;
        this.rooms = rooms;
        this.rooms.forEach(row -> { 
            row.forEach(room -> {
                if (room != null && room instanceof StartRoom) 
                    this.startRoom = (StartRoom) room;
            });
        });
    }

    /**
     * Returns the start room of the floor.
     *
     * @return The start room of the floor.
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
     * Returns the floor number.
     *
     * @return The floor number.
     */
    public int getFloorNumber() {
        return this.floorNumber;
    }

    /**
     * Returns a stream of rows of rooms in the floor.
     *
     * @return A stream of rows of rooms in the floor.
     */
    @JsonIgnore
    public Stream<ArrayList<Room>> getRoomStream() {
        return this.rooms.stream();
    }

    /**
     * Checks if the given coordinates are valid within the floor grid.
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

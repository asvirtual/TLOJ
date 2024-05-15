package com.tloj.game.rooms;

import java.util.ArrayList;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.entities.Character;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.roomeffects.RoomEffect;

// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * Abstract class that represents a room in the game<br>
 * @see RoomType
 * @see RoomEffect
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")
public abstract class Room {
    // The score drop when a room is cleared
    public static final int SCORE_DROP = 15;
    // The coordinates of the room
    protected Coordinates coordinates;
    // The visited status of the room
    protected boolean visited;
    // The cleared status of the room
    protected boolean cleared;
    // The lock status of the room
    protected boolean locked;
    // The list of friendly entities present in the room
    @JsonProperty
    protected ArrayList<FriendlyEntity> friendlyEntities;

    /**
     * Constructs a new Room object with the specified coordinates.
     *
     * @param coordinates the coordinates of the room
     */
    protected Room(Coordinates coordinates) {
        this.visited = false;
        this.cleared = false;
        this.coordinates = coordinates;
        this.friendlyEntities = new ArrayList<FriendlyEntity>();
    }

    /**
     * Returns the type of the room.
     *
     * @return the type of the room
     */
    @JsonIgnore
    public abstract RoomType getType();

    /**
     * Accepts a visitor to perform actions specific to the room.
     *
     * @param visitor the visitor object
     */
    public abstract void accept(PlayerRoomVisitor visitor);

    /**
     * Returns a string representation of the room.
     *
     * @return a string representation of the room
     */
    public abstract String toString();

    /**
     * Returns a string representation of the room
     * based on its type indipedently of the visited status.
     *
     * @return a string representation of the room
     */
    @JsonIgnore
    public abstract String getRoomRepresentation();

    /**
     * Returns the coordinates of the room.
     *
     * @return the coordinates of the room
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Checks if the room is locked.
     *
     * @return true if the room is locked, false otherwise
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * Checks if the room is cleared.
     *
     * @return true if the room is cleared, false otherwise
     */
    public boolean isCleared() {
        return cleared;
    }

    /**
     * Sets the cleared status of the room.
     *
     * @param cleared the cleared status of the room
     */
    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    /**
     * Clears the room with the specified player.
     *
     * @param player the player object
     */
    public void clear(Character player) {
        this.cleared = true;
    }

    /**
     * Marks the room as visited.
     */
    public void visit() {
        this.visited = true;
    }

    /**
     * Checks if the room has been visited.
     *
     * @return true if the room has been visited, false otherwise
     */
    public boolean isVisited() {
        return this.visited;
    }

    /**
     * Resets the visited status of the room.
     */
    public void forget() {
        this.visited = false;
    }

    /**
     * Returns the friendly entity in the room with the specified name.
     *
     * @param name the name of the friendly entity
     * @return the friendly entity with the specified name, or null if not found
     */
    public FriendlyEntity getFriendlyEntityByName(String name) {
        return this.friendlyEntities.stream().filter(entity -> entity.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
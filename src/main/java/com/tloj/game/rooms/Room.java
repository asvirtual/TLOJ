package com.tloj.game.rooms;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.entities.FriendlyEntity;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.roomeffects.RoomEffect;

/**
 * Abstract class that represents a room in the game<br>
 * @see RoomType
 * @see RoomEffect
 */

// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")

public abstract class Room {
    public static final int SCORE_DROP = 15;
    protected Coordinates coordinates;
    protected boolean visited;
    protected boolean cleared;
    protected boolean locked;
    @JsonProperty
    protected ArrayList<FriendlyEntity> friendlyEntities;

    protected Room(Coordinates coordinates) {
        this.visited = false;
        this.cleared = false;
        this.coordinates = coordinates;
        this.friendlyEntities = new ArrayList<FriendlyEntity>();
    }

    @JsonIgnore
    public abstract RoomType getType();
    public abstract void accept(PlayerRoomVisitor visitor);
    public abstract String toString();
    
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void clear() {
        this.cleared = true;
    }

    public void visit() {
        this.visited = true;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void forget() {
        this.visited = false;
    }

    public FriendlyEntity getFriendlyEntity(String name) {
        return this.friendlyEntities.stream().filter(entity -> entity.getName().equals(name)).findFirst().orElse(null);
    }
}
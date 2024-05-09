package com.tloj.game.rooms;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;


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

    protected Room(Coordinates coordinates) {
        this.visited = false;
        this.cleared = false;
        this.coordinates = coordinates;
    }

    public abstract RoomType getType();
    public abstract void accept(PlayerRoomVisitor visitor);
    public abstract void exit();
    public abstract String toString();
    
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public boolean isVisited() {
        return visited;
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
}
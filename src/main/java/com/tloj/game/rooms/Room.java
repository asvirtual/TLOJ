package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;


public abstract class Room {
    public static final int SCORE_DROP = 15;
    protected Coordinates coordinates;
    protected boolean visited;
    protected boolean cleared;

    protected Room(Coordinates coordinates) {
        this.visited = false;
        this.cleared = false;
        this.coordinates = coordinates;
    }

    public abstract RoomType getType();
    public abstract void accept(PlayerRoomVisitor visitor);
    public abstract void exit();
    
    public Coordinates getCoordinates() {
        return coordinates;
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
}
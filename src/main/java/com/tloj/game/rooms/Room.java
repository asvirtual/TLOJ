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

    public void visit() {
        this.roomVisited();
    }

    public abstract RoomType getType();
    public abstract void accept(PlayerRoomVisitor PlayerRoomVisitor);
    public abstract void exit();
    public abstract String toString();
    
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public boolean isCleared() {
        return cleared;
    }
    public void roomCleared() {
        this.cleared = true;
    }

    public void roomVisited() {
        this.visited = true;
    }

    public boolean isVisited() {
        return this.visited;
    }

}
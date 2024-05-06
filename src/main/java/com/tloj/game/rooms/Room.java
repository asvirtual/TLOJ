package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;


public abstract class Room {
    protected Coordinates coordinates;
    protected boolean visited;
    protected boolean cleared;

    protected Room(Coordinates coordinates) {
        this.visited = false;
        this.cleared = false;
        this.coordinates = coordinates;
    }

    public void visit() {
        this.visited = true;
    }

    public abstract RoomType getType();
    public abstract void accept(PlayerRoomVisitor PlayerRoomVisitor);
    public abstract void exit();
    
    public Coordinates getCoordinates() {
        return coordinates;
    }
}
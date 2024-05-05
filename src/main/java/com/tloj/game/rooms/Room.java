package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;


public abstract class Room {
    protected Coordinates coordinates;

    protected Room(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public abstract RoomType getType();
    public abstract void enter();
    public abstract void exit();
    
    public Coordinates getCoordinates() {
        return coordinates;
    }
}
package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;


public abstract class Room {
    protected Coordinates coordinates;
    protected Character character;
    protected boolean isLocked;

    protected Room(Coordinates coordinates, Character character, boolean isLocked) {
        this.coordinates = coordinates;
        this.character = character;
        this.isLocked = isLocked;
    }
}

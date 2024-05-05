package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;


public class TrapRoom extends Room {
    public TrapRoom(Coordinates coordinates, Character character, boolean isLocked) {
        super(coordinates, character, isLocked);
    }
}

package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;


public class HealingRoom extends Room {
    public HealingRoom(Coordinates coordinates, Character character, boolean isLocked) {
        super(coordinates, character, isLocked);
    }
}

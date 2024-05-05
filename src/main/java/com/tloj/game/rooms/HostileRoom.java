package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Coordinates;


public class HostileRoom extends Room {
    private Mob mob;

    public HostileRoom(Coordinates coordinates, Mob mob, Character character, boolean isLocked) {
        super(coordinates, character, isLocked);
        this.mob = mob;
    }
}

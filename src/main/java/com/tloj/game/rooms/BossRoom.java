package com.tloj.game.rooms;

import com.tloj.game.entities.Boss;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.entities.Character;


public class BossRoom extends Room {
    Boss boss;
    
    BossRoom(Coordinates coordinates, Boss boss, Character character, boolean isLocked) {
        super(coordinates, character, isLocked);
        this.boss = boss;
    }
}

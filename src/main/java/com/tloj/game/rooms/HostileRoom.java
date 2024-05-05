package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.Coordinates;


public class HostileRoom extends Room {
    private Mob mob;

    public HostileRoom(Coordinates coordinates, Mob mob) {
        super(coordinates);
        this.mob = mob;
    }

    @Override
    public RoomType getType() {
        return RoomType.HOSTILE_ROOM;
    }

    @Override
    public void enter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }
}

package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;


public class TrapRoom extends Room {
    public TrapRoom(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public RoomType getType() {
        return RoomType.TRAP_ROOM;
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

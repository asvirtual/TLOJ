package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;


public class StartRoom extends Room {
    public StartRoom(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public RoomType getType() {
        return RoomType.START_ROOM;
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

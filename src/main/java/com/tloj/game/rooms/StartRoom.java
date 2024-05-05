package com.tloj.game.rooms;

import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;


public class StartRoom extends Room {
    public StartRoom(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public RoomType getType() {
        return RoomType.START_ROOM;
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }
}

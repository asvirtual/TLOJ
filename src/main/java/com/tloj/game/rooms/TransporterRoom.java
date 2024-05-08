package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;


public class TransporterRoom extends Room {
    public TransporterRoom(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public RoomType getType() {
        return RoomType.TRANSPORTER_ROOM;
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

package com.tloj.game.rooms;

import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.utilities.Coordinates;


public class EndRoom extends Room {
    public EndRoom(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public RoomType getType() {
        return RoomType.END_ROOM;
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
    }

    @Override
    public String toString() {
        return "E";
    }
    
}
